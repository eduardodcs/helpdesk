package br.edu.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
