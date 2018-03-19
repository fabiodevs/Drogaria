package br.com.fdl.drogaria.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.junit.Test;

import br.com.fdl.drogaria.domain.Cliente;
import br.com.fdl.drogaria.domain.Pessoa;

public class ClienteDAOTeste {
	@Test
	
	public void salvar() throws ParseException {
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.buscar(1L);

		Cliente cliente = new Cliente();
		cliente.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("21/02/02"));
		cliente.setLiberado(false);
		cliente.setPessoa(pessoa);

		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.salvar(cliente);

		System.out.println("Cliente salvo com sucesso.");
	}
}