
/*
 *  Copyright (C) 2009-2015 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2015 José Flávio de Souza Dias Júnior
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

package com.joseflavio.tqc.dado;

import java.lang.reflect.Field;

import com.joseflavio.modelo.JFApresentacao;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Boolean}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Binario extends ComplexoDado {

	private String rotulo;
	
	private Boolean valor;
	
	/**
	 * @param valor Conteúdo. Pode ser <code>null</code>.
	 */
	public Binario( String nome, String rotulo, Boolean valor, boolean editavel ) {
		super( nome, editavel );
		this.rotulo = rotulo;
		this.valor = valor;
	}
	
	public Binario( String nome, Class<? extends Object> classe, String atributo, Boolean valor, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.valor = valor;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Binario( String nome, Class<? extends Object> classe, Boolean valor, Boolean editavel ) {
		this( nome, classe, nome, valor, editavel );
	}
	
	public Object getConteudo() {
		return valor;
	}
	
	public Boolean getValorValidado() throws ValidacaoException {
		validar();
		return valor;
	}
	
	public Boolean getValor() {
		return valor;
	}
	
	public Binario setValor( Boolean valor ) {
		this.valor = valor;
		return this;
	}
	
	public boolean isVerdadeiro() {
		return valor != null && valor == true;
	}
	
	public String getRotulo() {
		return rotulo;
	}
	
	public Binario setRotulo( String rotulo ) {
		this.rotulo = rotulo;
		return this;
	}
	
	public Binario maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Binario maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Binario maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
	/**
	 * @see JFApresentacao
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFApresentacao jfApresentacao = atributo.getAnnotation( JFApresentacao.class );

		if( jfApresentacao != null ) this.rotulo = jfApresentacao.value();
		
		return this;
		
	}
	
}
