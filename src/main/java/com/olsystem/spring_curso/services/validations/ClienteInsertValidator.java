package com.olsystem.spring_curso.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.olsystem.spring_curso.domain.Cliente;
import com.olsystem.spring_curso.domain.enums.TipoCliente;
import com.olsystem.spring_curso.dto.ClienteNewDTO;
import com.olsystem.spring_curso.repositories.ClienteRepository;
import com.olsystem.spring_curso.resources.exceptions.FieldMessage;
import com.olsystem.spring_curso.services.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {

			list.add(new FieldMessage("CpfOuCnpj", "CPF inválido!"));

		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCnpj(objDto.getCpfOuCnpj())) {

			list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido!"));

		}

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {

			list.add(new FieldMessage("Email", "Email já existente."));

		}

// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
