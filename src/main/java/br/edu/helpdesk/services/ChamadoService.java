package br.edu.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.helpdesk.domain.Chamado;
import br.edu.helpdesk.domain.Cliente;
import br.edu.helpdesk.domain.Tecnico;
import br.edu.helpdesk.domain.dtos.ChamadoDTO;
import br.edu.helpdesk.domain.enums.Prioridade;
import br.edu.helpdesk.domain.enums.Status;
import br.edu.helpdesk.repositories.ChamadoRepository;
import br.edu.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> cham = chamadoRepository.findById(id);
		return cham.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! Id: " + id));
	}

	public List<Chamado> findAll() {
		List<Chamado> list = chamadoRepository.findAll();
		return list;
	}

	public Chamado create(ChamadoDTO dto) {
		return chamadoRepository.save(this.newChamado(dto));
	}
	
	private Chamado newChamado(ChamadoDTO dto) {
		Tecnico tec = tecnicoService.findById(dto.getTecnico());
		Cliente cli = clienteService.findById(dto.getCliente());
		
		Chamado cham = new Chamado();
		if (dto.getId() != null) {
			cham.setId(dto.getId());
		}
		cham.setTecnico(tec);
		cham.setCliente(cli);
		cham.setPrioridade(Prioridade.toEnum(dto.getPrioridade()));
		cham.setStatus(Status.toEnum(dto.getStatus()));
		cham.setObservacao(dto.getObservacao());
		cham.setTitulo(dto.getTitulo());
		return cham;
	}
	
	

}
