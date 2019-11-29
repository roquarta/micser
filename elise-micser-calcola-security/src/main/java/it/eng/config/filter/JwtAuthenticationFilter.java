package it.eng.config.filter;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import it.eng.config.ConstansBean;
import it.eng.model.User;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private ConstansBean constants;

	@Autowired
	private JwtTokenUtil util;

	public JwtAuthenticationFilter() {

		super();
	}

	public JwtAuthenticationFilter(ConstansBean constants, JwtTokenUtil util) {

		super();
		this.constants = constants;
		this.util = util;
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

		final String header = request.getHeader(constants.getHeader());
		logger.info("Token JWT received {}", header);

		if (StringUtils.isBlank(header)) {
			logger.info("No Authentication Data!");
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Junction Authentication!");
			return;
		} else {

			final String authToken = header;
			String username = null;

			try {
				if (util.isRoleEnable(authToken)) {
					username = util.getUsernameFromToken(authToken);
					List<GrantedAuthority> authorities = util.getGroupsFromToken(authToken).stream()
						.map(authString -> new SimpleGrantedAuthority(authString)).collect(toList());

					String uoFromToken = util.getUoFromToken(authToken);
					String uo = null;
					try {
						int intUo;
						intUo = Integer.parseInt(uoFromToken);
						uo = String.format("%04d", intUo);
					}
					catch (Exception e) {
						logger.info("Not numeric UO!");
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not numeric UO!");
						return;
					}

					User u = new User(username, util.getGroupsFromToken(authToken), authorities, authToken, util.getEmailFromToken(authToken),
						util.getNameFromToken(authToken), util.getSurnameFromToken(authToken), util.getAbiFromToken(authToken), uo,
						util.isSpecialista(authToken));
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(u, null, authorities);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					if (SecurityContextHolder.getContext().getAuthentication() == null
						|| !((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMatricola()
							.equalsIgnoreCase(username)) {
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				} else {
					logger.info("No Authentication group!");
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Authentication group!");
					return;
				}
				logger.info("...Authenticated user by jwt token" + username + ", security context set!");
			}
			catch (ExpiredJwtException e) {
				logger.error("Token expired", e);
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired!");
				return;
			}
			catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Rigth Authentication!");
				return;
			}
			catch (Exception e) {
				logger.warn("generic error in jwtFilter", e);
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Generic error!");
				return;
			}

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Authentication!");
				return;
			}

			UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();

			if (!((User) authentication.getPrincipal()).getMatricola().equalsIgnoreCase(username)) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Righ Authentication!");
				return;
			}

			logger.info(String.format("User %s  OK!", username));
		}

		chain.doFilter(request, res);
	}

}