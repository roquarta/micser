package it.eng.config.filter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import it.eng.config.ConstansBean;

@Component
public final class JwtTokenUtil {

	@Autowired
	private ConstansBean constants;

	public final String getUsernameFromToken(String token) {
		return fromTokenToClaimValueFromID(token, "user");
	}

	public final String getEmailFromToken(String token) {
		return fromTokenToClaimValueFromID(token, "mail");
	}

	public final String getSurnameFromToken(String token) {
		return fromTokenToClaimValueFromID(token, "surname");
	}

	public final String getNameFromToken(String token) {
		return fromTokenToClaimValueFromID(token, "name");
	}

	public final String getAbiFromToken(String token) {
		return "03111";
	}

	public final String getUoFromToken(String token) {
		return fromTokenToClaimValueFromID(token, "office");
	}

	@SuppressWarnings("unchecked")
	public final List<String> getGroupsFromToken(String token) {
		return (List<String>) getAllClaimsFromToken(token).get("groups");
	}

	public final Date getExpirationDateFromToken(String token) {
		return fromTokenToClaimValue(token, Claims::getExpiration);
	}

	public final <T> T fromTokenToClaimValue(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public final String fromTokenToClaimValueFromID(String token, String id) {
		final Claims claims = getAllClaimsFromToken(token);
		return (String) claims.get(id);
	}

	public boolean validateToken(String token, String userId) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userId) && !isTokenExpired(token));
	}

	private final Claims getAllClaimsFromToken(String token) {
		final String[] elements = token.split("\\.");
		final int len0 = elements[0].length();
		final int len1 = elements[1].length();

		StringBuilder tokenWithoutSignature = new StringBuilder(len0 + len1 + 2).append(elements[0]).append('.')
				.append(elements[1]).append('.');
		return (Claims) Jwts.parser().parse(tokenWithoutSignature.toString()).getBody();
	}

	private final boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public boolean isRoleEnable(String token) {
		return getGroupsFromToken(token).stream().anyMatch(group -> Arrays.asList(constants.getGroups()).contains(group));
	}
	
	public boolean isSpecialista(String token) {
		return getGroupsFromToken(token).stream().anyMatch(group -> Arrays.asList(constants.getSpecialistaGroups()).contains(group));
	}

}