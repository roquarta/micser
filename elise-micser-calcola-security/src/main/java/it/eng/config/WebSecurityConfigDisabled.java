package it.eng.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import it.eng.config.filter.JwtFakeAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
@Profile("SecurityOff")
public class WebSecurityConfigDisabled extends AbstractWebSecurityConfig {

	@Override
	protected Filter createJwtTokenFilter () {

		return new JwtFakeAuthenticationFilter();
	}

}