package com.curso.springboot.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String login;
	private String senha;
	
	/*
	 * @JoinTable(name = "usuarios_role", uniqueConstraints
	 * = @UniqueConstraint(columnNames = {"usuario_id", "role_id"}, name =
	 * "unique_role_user"), joinColumns = @JoinColumn(name = "usuario_id",
	 * referencedColumnName = "id", table = "usuario", unique = false, foreignKey
	 * = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)),
	 * inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName =
	 * "id", table = "role", unique = false, updatable = false, foreignKey
	 * = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
	 */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role", // CRIA TABELA DE ACESSO DO USÁRIO
				joinColumns = @JoinColumn(
						name = "usuario_id",         // Conecta nome da nova tabela "usuario_id" com 
						referencedColumnName = "id", // o "id" da tabela "usuario"
						table = "usuario"), 
				inverseJoinColumns = @JoinColumn(    // Conecta nome da nova tabela "role_id" com
						name = "role_id",            // o "id" da tabela "role"
						referencedColumnName = "id", 
						table = "role"))
	private List<Role> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	
}