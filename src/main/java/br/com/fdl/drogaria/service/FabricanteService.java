package br.com.fdl.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.fdl.drogaria.dao.FabricanteDAO;
import br.com.fdl.drogaria.domain.Fabricante;

@Path("fabricante")
public class FabricanteService {
	@GET
	public String listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> fabricantes = fabricanteDAO.listar("descricao");
		
		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);
		
		return json;
	}
	
	@GET
	@Path("{codigo}")
	public String buscar(@PathParam("codigo") Long codigo) {
		
		try {
			
			
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			Fabricante fabricante = fabricanteDAO.buscar(codigo);
			
			Gson gson = new Gson();
			String json = gson.toJson(fabricante);
			
			return json;
			
			
		} catch (RuntimeException erro) {
			erro.printStackTrace();
			return "Erro";
		}
		
	}
	
	@POST
	public String salvar(String json) {
		
	
			Gson gson = new Gson();
			Fabricante fabricante = gson.fromJson(json, Fabricante.class);
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.merge(fabricante);
			
			String jsonSaida = gson.toJson(fabricante);
			return jsonSaida;
		
	}
	
	@PUT
	public String editar(String json) {
		
			Gson gson = new Gson();
			Fabricante fabricante = gson.fromJson(json, Fabricante.class);
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.editar(fabricante);
			
			String jsonSaida = gson.toJson(fabricante);
			return jsonSaida;
		
	}
	
	@DELETE
	public String excluir(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		FabricanteDAO fabricanteDAO =  new FabricanteDAO();
		fabricanteDAO.excluir(fabricante);
		
		String jsonSaida = gson.toJson(fabricante);
		return jsonSaida;
	}

}
