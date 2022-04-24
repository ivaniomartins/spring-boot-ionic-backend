package com.olsystem.spring_curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.olsystem.spring_curso.domain.Categoria;
import com.olsystem.spring_curso.repositories.CategoriaRepository;
import com.olsystem.spring_curso.services.exceptions.DataIntegrityException;
import com.olsystem.spring_curso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! ID: " + id + ", Tipo: " + Categoria.class.getName()));

	}
	
	public List<Categoria> findAll() {
	
		return repo.findAll();
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		
		try {
		repo.deleteById(id);
		}
		catch(DataIntegrityViolationException d) {
			throw new DataIntegrityException("Não é possivel excluir uma Categoria vinculada à um ou mais produtos.");
		}
	}

	

}
