package br.com.fdl.drogaria.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.fdl.drogaria.dao.FabricanteDAO;
import br.com.fdl.drogaria.dao.ProdutoDAO;
import br.com.fdl.drogaria.domain.Fabricante;
import br.com.fdl.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable{
	
		private Produto produto;
		private List<Produto> produtos;
		private List<Fabricante> fabricantes;
		
		//metodos Getters e Setters
		public Produto getProduto() {
			return produto;
		}
		public void setProduto(Produto produto) {
			this.produto = produto;
		}
		public List<Produto> getProdutos() {
			return produtos;
		}
		public void setProdutos(List<Produto> produtos) {
			this.produtos = produtos;
		}
		public List<Fabricante> getFabricantes() {
			return fabricantes;
		}
		public void setFabricantes(List<Fabricante> fabricantes) {
			this.fabricantes = fabricantes;
		}
		//Fim dos metodos Getters e Setters
		
		public void novo() {
			try {
				produto = new Produto();
				FabricanteDAO fabricanteDAO = new FabricanteDAO();
				fabricantes = fabricanteDAO.listar();
			} catch (RuntimeException erro) {
				Messages.addGlobalInfo("Erro ao carregar os Fabricantes!");
				erro.printStackTrace();
			}
		}
		
		@PostConstruct
		public void listar() {
			
			try {
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produtos = produtoDAO.listar();
			} catch (RuntimeException erro) {
				Messages.addGlobalInfo("Erro ao carregar os Produtos!");
				erro.printStackTrace();
			}
			
		}
		
		public void salvar() {
			try {
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produtoDAO.merge(produto);
				produto = new Produto();
				produtos = produtoDAO.listar();
				Messages.addFlashGlobalInfo("Produto salvo com sucesso!");
			} catch (RuntimeException erro) {
				Messages.addGlobalInfo("Erro ao tentar salvar o Produto!");
				erro.printStackTrace();
			}
		}
		
		public void excluir(ActionEvent evento) {
			
			try {
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
				produtoDAO.excluir(produto);
				produtos=produtoDAO.listar();
				Messages.addGlobalInfo("Produto excluido com sucesso!");

			} catch (RuntimeException erro) {
				Messages.addGlobalError("Erro ao tentar excluir!");
				erro.printStackTrace();
			}
		}
		
		public void editar(ActionEvent evento) {
			try {
				produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
				ProdutoDAO produtoDAO =new ProdutoDAO();
				produtoDAO.merge(produto);
				produtos = produtoDAO.listar();
				Messages.addGlobalInfo("Produto editado com sucesso!");
			} catch (RuntimeException erro) {
				Messages.addGlobalError("Erro ao tentar editar!");
				erro.printStackTrace();
			}
		}
		
		public void upload(FileUploadEvent evento){
			
			try {
				UploadedFile arquivoUpload = evento.getFile();
				Path arquivoTemp = Files.createTempFile(null, null);
				Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException erro) {
				Messages.addFlashGlobalError("Erro ao carregar a foto!");
			}
		}
}
