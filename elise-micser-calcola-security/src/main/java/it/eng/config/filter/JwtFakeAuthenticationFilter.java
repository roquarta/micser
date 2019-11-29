package it.eng.config.filter;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import it.eng.model.User;

public class JwtFakeAuthenticationFilter extends OncePerRequestFilter {

	private static Logger logger = LoggerFactory.getLogger(JwtFakeAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		List<GrantedAuthority> authorities = Arrays.stream("GA_TBD_1,GA_TBD_2".split(","))
				.map(authString -> new SimpleGrantedAuthority(authString)).collect(toList());
		User u = new User("UEL1GE1", new ArrayList<String>(), authorities, StringUtils.EMPTY,
				"mario.bianchi@ubisss.it", "Mario",
				"Bianchi", "03111",
				"00200", true);
		logger.info("Fake JWT authentication with user {}", u);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(u, null,
				authorities);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		if (SecurityContextHolder.getContext().getAuthentication() == null
				|| !((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
						.getMatricola().equalsIgnoreCase("UEL1GE1")) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, res);
	}

}