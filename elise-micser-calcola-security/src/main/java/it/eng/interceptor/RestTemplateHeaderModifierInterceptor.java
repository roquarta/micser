package it.eng.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import it.eng.model.User;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

	private static Logger logger = LoggerFactory.getLogger(RestTemplateHeaderModifierInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		try {
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			request.getHeaders().add("jwt",((User)auth.getPrincipal()).getJwtToken());
		} catch (Exception e) {
			logger.error("Errore nell'aggiunta del token JWT " +  e.getMessage());
		}
		ClientHttpResponse response = execution.execute(request, body);
		return response;
	}
}
