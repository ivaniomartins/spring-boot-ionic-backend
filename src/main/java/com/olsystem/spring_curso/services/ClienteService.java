package com.olsystem.spring_curso.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.olsystem.spring_curso.domain.Cidade;
import com.olsystem.spring_curso.domain.Cliente;
import com.olsystem.spring_curso.domain.Endereco;
import com.olsystem.spring_curso.domain.enums.TipoCliente;
import com.olsystem.spring_curso.dto.ClienteDTO;
import com.olsystem.spring_curso.dto.ClienteNewDTO;
import com.olsystem.spring_curso.repositories.CidadeRepository;
import com.olsystem.spring_curso.repositories.ClienteRepository;
import com.olsystem.spring_curso.repositories.EnderecoRepository;
import com.olsystem.spring_curso.services.exceptions.DataIntegrityException;
import com.olsystem.spring_curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private CidadeRepository cidadeRepositoriy;
	@Autowired
	private EnderecoRepository enderecoRepository; 

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! ID: " + id + ", Tipo: " + Cliente.class.getName()));

	}

	public List<Cliente> findAll() {

		return repo.findAll();
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		enderecoRepository.saveAll(obj.getEnderecos());
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) {
		// incluir a lógica de atualização com base no registro do banco de dados.
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);

		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException d) {
			throw new DataIntegrityException("Não é possivel excluir um Cliente porquê há entidades relacionadas");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// metodo auxiliar que instancia um cliente apartir de um DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {

		Cliente cli1 = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = cidadeRepositoriy.findById(objDTO.getCidadeId()).get();
		Endereco end1 = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),objDTO.getBairro(), objDTO.getCep(), cli1, cid);
		cli1.getEnderecos().add(end1);
		cli1.getTelefones().add(objDTO.getTelefone1());

		if (objDTO.getTelefone2() != null) {
			cli1.getTelefones().add(objDTO.getTelefone2());
		}

		if (objDTO.getTelefone3() != null) {
			cli1.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli1;

	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
