package br.edu.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.helpdesk.domain.Chamado;
import br.edu.helpdesk.domain.Cliente;
import br.edu.helpdesk.domain.Tecnico;
import br.edu.helpdesk.domain.enums.Perfil;
import br.edu.helpdesk.domain.enums.Prioridade;
import br.edu.helpdesk.domain.enums.Status;
import br.edu.helpdesk.repositories.ChamadoRepository;
import br.edu.helpdesk.repositories.ClienteRepository;
import br.edu.helpdesk.repositories.PessoaRepository;
import br.edu.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	TecnicoRepository tecnicoRepository;
	
	@Autowired
	ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Tecnico tec1 = new Tecnico(null, "Eduardo Silva", "33484545810", "eddcesilva@gmail.com",  "123456");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "14868316052", "linus@gmail.com", "456123");
		
		Chamado c1 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
		
	}
}
