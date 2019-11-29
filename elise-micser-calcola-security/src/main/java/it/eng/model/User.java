package it.eng.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class User implements Principal {

	private final String matricola;
	private final List<String> groups;
	private final List<GrantedAuthority> authorities;
	private final String desc;
	private final String jwtToken;
	private final String email;
	private final String name;
	private final String surname;
	private final String abi;
	private final String uo;
	private boolean isSpecialista;
	
	

	public User(String matricola, List<String> ivGroups, List<GrantedAuthority> authorities, String jwtToken, String email, String name, String surname,String abi,String uo, boolean isSpecialista) {
		this.matricola = matricola;
		this.groups = ivGroups;
		this.authorities = authorities;
		this.desc = matricola + "::(" + ivGroups + ")";
		this.jwtToken = jwtToken;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.abi = abi;
		this.uo = uo;
		this.isSpecialista = isSpecialista;
	}

	public String getMatricola() {
		return matricola;
	}

	public List<String> getGroups() {
		return groups;
	}

	public List<GrantedAuthority> getAuthorities() {
		return new ArrayList<>(authorities);
	}

	@Override
	public String getName() {
		return name;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public String getDesc() {
		return desc;
	}

	public String getEmail() {
		return email;
	}

	public String getSurname() {
		return surname;
	}

	public String getAbi() {
		return abi;
	}

	public String getUo() {
		return uo;
	}

	public boolean isSpecialista() {
		return isSpecialista;
	}

	public void setSpecialista(boolean isSpecialista) {
		this.isSpecialista = isSpecialista;
	}

	@Override
	public String toString() {
		return "User [matricola=" + matricola + ", groups=" + groups + ", authorities=" + authorities + ", desc=" + desc
				+ ", jwtToken=" + jwtToken + ", email=" + email + ", name=" + name + ", surname=" + surname + ", abi="
				+ abi + ", uo=" + uo + ", isSpecialista=" + isSpecialista + "]";
	}
	
	
}