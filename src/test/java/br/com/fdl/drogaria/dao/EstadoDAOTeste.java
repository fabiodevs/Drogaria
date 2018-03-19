package br.com.fdl.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.fdl.drogaria.domain.Estado;

public class EstadoDAOTeste {
	
	@Test
	@Ignore
	public void salvar(){
		
		Estado estado = new Estado();
		estado.setNome("Bahia");
		estado.setSigla("BH");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
		
	}
	
	@Test
	@Ignore
	public void listar() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resultado = estadoDAO.listar();
		
		for(Estado estado:resultado) {
			System.out.println(estado.getCodigo()+" - "+ estado.getSigla()+ " - " + estado.getNome());
		}
	}
	
	@Test
	@Ignore
	public void buscar() {
		Long codigo =3L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if (estado==null) {
			System.out.println("Registro não encontrado");
		}else {
			System.out.println(estado.getCodigo()+ " - "+ estado.getNome() + " - " + estado.getSigla());
		}
		
	}
	
	@Test
	@Ignore
	public void excluir() {
		Long codigo=7L;
		EstadoDAO estadoDAO = new EstadoDAO();
		
		Estado estado = estadoDAO.buscar(codigo);
		
		
		if(estado ==null) {
			System.out.println("Registro não encontrado");
		}else {
			estadoDAO.excluir(estado);
			System.out.println(estado.getCodigo()+ " - "+ estado.getNome() + " - " + estado.getSigla());
		}

	}
	
	@Test
	@Ignore
	public void editar() {
		
		Long codigo = 1L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);
		
		if(estado==null) {
			System.out.print("Registro não encontrado");
		}else {
			estado.setNome("Rio de Janeiro");
			estado.setSigla("RJ");
			estadoDAO.editar(estado);
		}
		
	}
	
	@Test
	
	public void merge(){
		
		Estado estado = new Estado();
		estado.setNome("Rio de Janeiro");
		estado.setSigla("RJ");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
		
	}

}
