package com.olsystem.spring_curso.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.olsystem.spring_curso.domain.Cliente;
import com.olsystem.spring_curso.services.validations.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	@NotEmpty(message="O nome do cliente é obrigatório")
	@Length(min=5, max= 120, message="O nome do cliente deve estar entre 5 e 120 carácteres")
	private String nome;
	@NotEmpty(message="É necessário informar um email")
	@Email(message="e-mail inválido")
	private String email;
	
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
