package br.com.fdl.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fdl.drogaria.dao.ClienteDAO;
import br.com.fdl.drogaria.dao.PessoaDAO;
import br.com.fdl.drogaria.domain.Cliente;
import br.com.fdl.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable{
	
	private Cliente cliente;
	private List<Cliente> clientes;
	private List<Pessoa> pessoas;
	
	//Metodos Getters e Setters
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public void novo() {
		
		try {
			cliente = new Cliente();
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar carregar a Pessoa!");
			erro.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("dataCadastro");
			
		} catch (Exception erro) {
			Messages.addGlobalError("Erro ao tentar carregar os clientes!");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			cliente = new Cliente();
			clientes = clienteDAO.listar();
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
			
			Messages.addGlobalInfo("Cidade salva com sucesso!");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o cliente!");
			erro.printStackTrace();
		}	
		
	}
	
	public void excluir(ActionEvent evento) {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			clienteDAO.excluir(cliente);
			clientes = clienteDAO.listar();
			Messages.addGlobalInfo("Cliente excluido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir o cliente!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			clienteDAO.merge(cliente);
			clientes = clienteDAO.listar();
			Messages.addGlobalInfo("Cliente editado com sucesso!");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o cliente!");
			erro.printStackTrace();
		}
	}

}
