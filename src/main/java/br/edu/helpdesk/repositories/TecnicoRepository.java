package br.edu.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
