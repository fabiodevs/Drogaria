package br.com.fdl.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fdl.drogaria.dao.CidadeDAO;
import br.com.fdl.drogaria.dao.EstadoDAO;
import br.com.fdl.drogaria.domain.Cidade;
import br.com.fdl.drogaria.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {

	private List<Cidade> cidades;
	private Cidade cidade;
	private List<Estado> estados;

	/**
	 * Metodos Get e Set
	 */
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void novo() {
		try {
			cidade = new Cidade();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("sigla");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os estados");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar lista as cidades!");
		}
	}

	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);
			cidade = new Cidade();
			cidades = cidadeDAO.listar("nome");

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("sigla");
			Messages.addGlobalInfo("Cidade salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao tentar salvar a cidade!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");
			cidadeDAO.excluir(cidade);
			cidades = cidadeDAO.listar();
			Messages.addGlobalInfo("Cidade excluida com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Cidade!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.editar(cidade);
			cidades = cidadeDAO.listar();
			Messages.addGlobalInfo("Cidade editada com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar o fabricante!");
		}
		
	}

}
