package br.edu.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
