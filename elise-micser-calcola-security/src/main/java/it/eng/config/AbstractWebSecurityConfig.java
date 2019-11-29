package it.eng.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.eng.config.filter.JwtTokenUtil;

public abstract class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	protected ConstansBean constants;

	@Autowired
	protected JwtTokenUtil util;

	@Value("${spring.security.exclude.path}")
	protected String[] excludeSecurityString;

	@Override
	protected void configure (HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().addFilterAfter(createJwtTokenFilter(),
			UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure (WebSecurity web) throws Exception {

		if (excludeSecurityString != null || excludeSecurityString.length != 0) {
			web.ignoring().antMatchers(excludeSecurityString);
		}
	}

	protected abstract Filter createJwtTokenFilter ();
}
