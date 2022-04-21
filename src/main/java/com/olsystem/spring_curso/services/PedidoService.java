package com.olsystem.spring_curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olsystem.spring_curso.domain.Pedido;
import com.olsystem.spring_curso.repositories.PedidoRepository;
import com.olsystem.spring_curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! ID: " + id + ", Tipo: " + Pedido.class.getName()));

	}

}
