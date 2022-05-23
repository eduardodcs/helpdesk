package br.edu.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.helpdesk.domain.Chamado;
import br.edu.helpdesk.repositories.ChamadoRepository;
import br.edu.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;

	public Chamado findById(Integer id) {
		Optional<Chamado> cham = chamadoRepository.findById(id);
		return cham.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! Id: " + id));
	}

	public List<Chamado> findAll() {
		List<Chamado> list = chamadoRepository.findAll();
		return list;
	}
	
	

}
