package br.edu.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecDTO) {
		tecDTO.setId(null);
		tecDTO.setSenha(encoder.encode(tecDTO.getSenha()));
		validaPorCpfEEmail(tecDTO);
		Tecnico tec = new Tecnico(tecDTO);
		return tecnicoRepository.save(tec);
	}

	private void validaPorCpfEEmail(TecnicoDTO tecDTO) {
		Optional<Pessoa> pes = pessoaRepository.findByCpf(tecDTO.getCpf());
		if (pes.isPresent() && pes.get().getId() != tecDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		pes = pessoaRepository.findByEmail(tecDTO.getEmail());
		if (pes.isPresent() && pes.get().getId() != tecDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = this.findById(id);
		if (!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		this.validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return tecnicoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = this.findById(id);
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}
		tecnicoRepository.delete(obj);
	}

}
