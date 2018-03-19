package br.com.fdl.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fdl.drogaria.dao.FuncionarioDAO;
import br.com.fdl.drogaria.dao.PessoaDAO;
import br.com.fdl.drogaria.domain.Funcionario;
import br.com.fdl.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable{
	
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private List<Pessoa> pessoas;
	
	//Getters e Setters
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public void novo(){
		try {
			funcionario = new Funcionario();
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao carregar a Pessoa");
			erro.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao carregar o Funcionário");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.merge(funcionario);
			funcionario = new Funcionario();
			funcionarios = funcionarioDAO.listar();
			Messages.addGlobalInfo("Usuário Salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao carregar o Funcionário");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.excluir(funcionario);
			funcionarioDAO.listar();
			Messages.addGlobalInfo("Usuário excluido com sucesso!");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao editar o Funcionário");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.editar(funcionario);
			funcionarioDAO.listar();
			Messages.addGlobalInfo("Usuário excluido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao editar o Funcionário");
			erro.printStackTrace();
		}
	}
}
