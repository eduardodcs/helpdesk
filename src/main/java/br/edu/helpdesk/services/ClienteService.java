package br.edu.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.helpdesk.domain.Cliente;
import br.edu.helpdesk.domain.Pessoa;
import br.edu.helpdesk.domain.dtos.ClienteDTO;
import br.edu.helpdesk.repositories.ClienteRepository;
import br.edu.helpdesk.repositories.PessoaRepository;
import br.edu.helpdesk.services.exceptions.DataIntegrityViolationException;
import br.edu.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> cli = clienteRepository.findById(id);
		return cli.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO dto) {
		dto.setId(null);
		dto.setSenha(encoder.encode(dto.getSenha()));
		validaPorCpfEEmail(dto);
		Cliente cli = new Cliente(dto);
		return clienteRepository.save(cli);
	}

	private void validaPorCpfEEmail(ClienteDTO dto) {
		Optional<Pessoa> pes = pessoaRepository.findByCpf(dto.getCpf());
		if (pes.isPresent() && pes.get().getId() != dto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		pes = pessoaRepository.findByEmail(dto.getEmail());
		if (pes.isPresent() && pes.get().getId() != dto.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

	public Cliente update(Integer id, @Valid ClienteDTO dto) {
		dto.setId(id);
		Cliente oldObj = this.findById(id);
		if (!dto.getSenha().equals(oldObj.getSenha())) {
			dto.setSenha(encoder.encode(dto.getSenha()));
		}
		this.validaPorCpfEEmail(dto);
		oldObj = new Cliente(dto);
		return clienteRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = this.findById(id);
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}
		clienteRepository.delete(obj);
	}

}
