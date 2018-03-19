package br.com.fdl.drogaria.main;

import org.hibernate.Session;

import br.com.fdl.drogaria.util.HibernateUtil;

public class HibernateUtilTeste {
	
	public static void main(String[] args) {
		Session sessao =  HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		
		
	}

}
