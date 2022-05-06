package com.olsystem.spring_curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olsystem.spring_curso.domain.Categoria;
import com.olsystem.spring_curso.domain.Produto;
import com.olsystem.spring_curso.dto.CategoriaDTO;
import com.olsystem.spring_curso.dto.ProdutoDTO;
import com.olsystem.spring_curso.services.ProdutoService;

@RestController
@RequestMapping("/pedidos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {

		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam (value="page", defaultValue ="0") Integer page, 
			                                          @RequestParam (value="linesPerPage", defaultValue ="24") Integer linesPerPage, 
			                                          @RequestParam (value="orderBy", defaultValue ="nome") String orderBy, 
			                                          @RequestParam (value="direction", defaultValue ="ASC") String direction){

		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);

		// para cada elemento da lista será instanciado um DTO

		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);

	}

}
