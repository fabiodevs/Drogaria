package br.com.fdl.drogaria.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class GenericDomain implements Serializable{
	
	@Id
	@Column(name="codigo", columnDefinition="serial")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long codigo;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return String.format("%s[id=%d]",getClass().getSimpleName(), getCodigo());
	}

}
