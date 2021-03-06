package com.olsystem.spring_curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olsystem.spring_curso.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@org.springframework.transaction.annotation.Transactional
	Cliente findByEmail(String email);

}
