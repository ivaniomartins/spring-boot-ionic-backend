package com.olsystem.spring_curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.olsystem.spring_curso.domain.Categoria;
import com.olsystem.spring_curso.domain.Produto;
import com.olsystem.spring_curso.repositories.CategoriaRepository;
import com.olsystem.spring_curso.repositories.ProdutoRepository;
import com.olsystem.spring_curso.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! ID: " + id + ", Tipo: " + Produto.class.getName()));

	}
	
	
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	List<Categoria> categorias  = categoriaRepository.findAllById(ids);
		return  repo.search(nome, categorias,  pageRequest);
    }
}