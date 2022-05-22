package br.edu.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.helpdesk.domain.Pessoa;
import br.edu.helpdesk.domain.Tecnico;
import br.edu.helpdesk.domain.dtos.TecnicoDTO;
import br.edu.helpdesk.repositories.PessoaRepository;
import br.edu.helpdesk.repositories.TecnicoRepository;
import br.edu.helpdesk.services.exceptions.DataIntegrityViolationException;
import br.edu.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecDTO) {
		tecDTO.setId(null);
		validaPorCpfEEmail(tecDTO);
		Tecnico tec = new Tecnico(tecDTO);
		return tecnicoRepository.save(tec);
	}

	private void validaPorCpfEEmail(TecnicoDTO tecDTO) {
		Optional<Pessoa> pes = pessoaRepository.findByCpf(tecDTO.getCpf());
		if(pes.isPresent() && pes.get().getId() != tecDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		pes = pessoaRepository.findByEmail(tecDTO.getEmail());
		if(pes.isPresent() && pes.get().getId() != tecDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
	
}
