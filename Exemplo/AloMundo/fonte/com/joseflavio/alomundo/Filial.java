
/*
 *  Copyright (C) 2009-2013 José Flávio de Souza Dias Júnior
 *  
 *  This file is part of José Flávio Livre - <http://www.joseflavio.com/livre/>.
 *  
 *  José Flávio Livre is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  José Flávio Livre is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with José Flávio Livre. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Direitos Autorais Reservados (C) 2009-2013 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.alomundo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.joseflavio.modelo.JFApresentacao;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoNaoNulo;
import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.modelo.JFValidacaoPrimitiva;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;

/**
 * Filial da empresa.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
@JFApresentacao(value="Filial", plural="Filiais", masculino=false)
@Entity
public class Filial {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;

	@JFApresentacao("Sigla")
	@JFTexto(largura=5, max=5)
	@JFValidacaoPrimitiva(erro="Informe corretamente a sigla.")
	@JFValidacaoNaoNulo(erro="Informe a sigla.")
	@JFValidacaoTamanhoLimite(erro="A sigla deve conter no máximo 5 caracteres.")
	@Column(length=5)
	private String sigla;

	@JFApresentacao("Nome")
	@JFTexto(largura=50, max=50)
	@JFValidacaoNaoVazio(erro="Informe o nome.")
	@JFValidacaoTamanhoLimite(erro="O nome deve conter no máximo 50 caracteres.")
	@Column(length=50)
	private String nome;
	
	public Filial() {
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || ! ( obj instanceof Filial ) ) return false;
		Filial o = (Filial) obj;
		return sigla == o.sigla || ( sigla != null && o.sigla != null && sigla.equals( o.sigla ) );
	}
	
	@Override
	public int hashCode() {
		return sigla != null ? sigla.hashCode() : super.hashCode();
	}
	
	@Override
	public String toString() {
		return nome + " - " + sigla;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla( String sigla ) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}

	public static Filial obter( AplicacaoTQC_JPA aplicacao, String sigla ) {
		return aplicacao.obter( "select o from Filial o where upper(o.sigla) = :p0", sigla.toUpperCase() );
	}

	public static List<Filial> listar( AplicacaoTQC_JPA aplicacao ) {
		return aplicacao.listar( "select o from Filial o order by o.nome asc" );
	}
	
}
