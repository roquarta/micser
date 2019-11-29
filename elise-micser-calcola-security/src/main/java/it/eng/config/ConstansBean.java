package it.eng.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:sec-props.properties")
@ConfigurationProperties(prefix = "security.bean")
public class ConstansBean {

	private long validity;
	private String token_prefix;
	private String header;
	private String[] groups;
	private String[] specialistaGroups;

	public ConstansBean() {
	}

	public long getValidity() {
		return validity;
	}

	public void setValidity(long validity) {
		this.validity = validity;
	}

	public String getToken_prefix() {
		return token_prefix;
	}

	public void setToken_prefix(String token_prefix) {
		this.token_prefix = token_prefix + " ";
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String[] getGroups() {
		return groups;
	}

	public String[] getSpecialistaGroups() {
		return specialistaGroups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}
	
	public void setSpecialistaGroups(String[] specialistaGroups) {
		this.specialistaGroups = specialistaGroups;
	}
	
	

}
