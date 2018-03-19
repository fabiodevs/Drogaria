package br.com.fdl.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.fdl.drogaria.dao.ClienteDAO;
import br.com.fdl.drogaria.dao.FuncionarioDAO;
import br.com.fdl.drogaria.dao.ProdutoDAO;
import br.com.fdl.drogaria.dao.VendaDAO;
import br.com.fdl.drogaria.domain.Cliente;
import br.com.fdl.drogaria.domain.Funcionario;
import br.com.fdl.drogaria.domain.ItemVenda;
import br.com.fdl.drogaria.domain.Produto;
import br.com.fdl.drogaria.domain.Venda;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private Venda venda;
	
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	
	public Venda getVenda() {
		return venda;
	}
	
	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}
	

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setValorTotal(new BigDecimal("0.00"));
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensVenda = new ArrayList<>();	
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));

			itensVenda.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		
		calcular();
	}
	
	public void diminuir(ActionEvent evento) {
        ItemVenda itemVendaEvento = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");
 
        int achou = -1;
 
        for (int i = 0; i < itensVenda.size(); i++) {
            if (itensVenda.get(i).getProduto().equals(itemVendaEvento.getProduto())) {
                achou = i;
            }
        }
 
        // Se o objeto existir na lista, acontecerá o efeito inverso do método adicionar
        // Diminuindo a quantidade e o valor a cada clique correspondente a uma unidade
        // do produto
        if (achou >= 0) {
            ItemVenda itemVenda = itensVenda.get(achou);
            itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() - 1 + ""));
            itemVenda.setPrecoParcial(itemVenda.getPrecoParcial().subtract(itemVenda.getProduto().getPreco()));
 
            // se a quantidade chegar a zero (ou menos) o item não deve mais existir na
            // lista (cesta de compras)
            if (itemVenda.getQuantidade() <= 0) {
                itensVenda.remove(achou);
            }
        }
        
        calcular();
    }

	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");
		
		int achou = -1;
		for(int posicao = 0; posicao < itensVenda.size(); posicao++){
			if(itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())){
				achou = posicao;
			}
		}
		
		if(achou > -1){
			itensVenda.remove(achou);
		}
		
		calcular();
	}
	
	public void calcular(){
		venda.setValorTotal(new BigDecimal("0.00"));
		
		for(int posicao = 0; posicao < itensVenda.size(); posicao++){
			ItemVenda itemVenda = itensVenda.get(posicao);
			venda.setValorTotal(venda.getValorTotal().add(itemVenda.getPrecoParcial()));
		}
	}
	
	public void finalizar(){
		try {
			venda.setHorario(new Date());
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao carregar o funcinário!");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			if(venda.getValorTotal().signum()==0) {
				Messages.addGlobalInfo("Informe pelo menos um item para a venda!");
			}
			VendaDAO vendaDao = new VendaDAO();
			vendaDao.salvar(venda, itensVenda);
			Messages.addFlashGlobalInfo("Venda realizada com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar!");
		}
	}
}