package br.com.fdl.drogaria.dao;

import org.junit.Test;

import br.com.fdl.drogaria.domain.Fabricante;

public class FabricanteDAOTeste {
	
	@Test
	public void salvar() {
		
		Fabricante fabricante=new Fabricante();
		fabricante.setDescricao("Genérico");
				
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.salvar(fabricante);
		
	}

}
