package br.com.fdl.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fdl.drogaria.dao.EstadoDAO;
import br.com.fdl.drogaria.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable{
	
	private Estado estado;
	private List<Estado> estados;
	
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
	
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public void novo() {
		estado = new Estado();
	}
	
	public void salvar() {
		
		try {
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.merge(estado);
		Messages.addGlobalInfo("Estado salvo com sucesso!");
		novo();
		estados = estadoDAO.listar();
		}catch(RuntimeException erro){
			Messages.addGlobalError("Ocorreu um erro ao salvar o Estado!");
			erro.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listar() {
		
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
		}catch(RuntimeException erro){
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Estados!");
			erro.printStackTrace();
		}			
	}
	
	public void excluir(ActionEvent evento) {
		
		try {
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			estadoDAO.excluir(estado);
			estados =estadoDAO.listar();
			Messages.addGlobalInfo("Estado excluido com sucesso!");
			
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Estado!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			estadoDAO.editar(estado);
			estados = estadoDAO.listar();
			
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar o Estado!");
			erro.printStackTrace();
		}
	}

}
