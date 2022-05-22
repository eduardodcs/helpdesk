package br.edu.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
