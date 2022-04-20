package com.olsystem.spring_curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olsystem.spring_curso.domain.Categoria;
import com.olsystem.spring_curso.domain.Cliente;
import com.olsystem.spring_curso.repositories.ClienteRepository;
import com.olsystem.spring_curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! ID: " + id + ", Tipo: " + Cliente.class.getName()));

	}

}
