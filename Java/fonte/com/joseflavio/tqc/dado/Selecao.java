
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
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

import java.util.List;

import com.joseflavio.tqc.Aparencia;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.anotacao.TQCOpcoes;
import com.joseflavio.util.Lista;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Dado} que permite a escolha de um único objeto entre vários opcionais.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see TQCOpcoes
 */
public class Selecao<T> extends Opcoes<T> {
	
	private static final int SEL_NULO = -1;
	private static final int SEL_INDEFINIDO = -2;

	private List<T> opcoes;
	
	private T selecionado;
	
	private int selecionadoIndice = SEL_INDEFINIDO;
	
	private Aparencia<T> aparencia;
	
	/**
	 * @param editavel Seleção permitida?
	 */
	public Selecao( String nome, List<T> opcoes, boolean editavel ) {
		super( nome, editavel );
		this.opcoes = opcoes != null ? opcoes : new Lista<T>();
	}
	
	public Selecao( String nome, T[] opcoes, boolean editavel ) {
		this( nome, new Lista<T>( opcoes ), editavel );
	}
	
	public Selecao( String nome, List<T> opcoes ) {
		this( nome, opcoes, true );
	}
	
	public Selecao( String nome, T[] opcoes ) {
		this( nome, new Lista<T>( opcoes ) );
	}
	
	public Selecao( List<T> opcoes ) {
		this( null, opcoes, true );
	}
	
	public Selecao( T[] opcoes ) {
		this( new Lista<T>( opcoes ) );
	}
	
	public Selecao( String nome, List<T> opcoes, T selecaoInicial ) {
		this( nome, opcoes, selecaoInicial, true );
	}
	
	public Selecao( String nome, T[] opcoes, T selecaoInicial ) {
		this( nome, new Lista<T>( opcoes ), selecaoInicial );	
	}
	
	public Selecao( String nome, List<T> opcoes, T selecaoInicial, boolean editavel ) {
		this( nome, opcoes, editavel );
		setIndiceSelecionado( opcoes.indexOf( selecaoInicial ) );
	}
	
	public Selecao( String nome, T[] opcoes, T selecaoInicial, boolean editavel ) {
		this( nome, new Lista<T>( opcoes ), selecaoInicial, editavel );
	}

	public Selecao( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		super( nome );
		this.opcoes = opcoes != null ? opcoes : new Lista<T>();
		this.selecionado = selecaoInicial;
		configurarPor( classe, atributo );
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Selecao( String nome, Class<? extends Object> classe, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		this( nome, classe, nome, opcoes, selecaoInicial, editavel );
	}
	
	public Selecao( String nome, Class<? extends Object> classe, T[] opcoes, T selecaoInicial, Boolean editavel ) {
		this( nome, classe, nome, new Lista<T>( opcoes ), selecaoInicial, editavel );
	}
	
	public Selecao( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, Boolean editavel ) {
		this( nome, classe, atributo, opcoes, null, editavel );
	}
	
	public Selecao( String nome, Class<? extends Object> classe, List<T> opcoes, Boolean editavel ) {
		this( nome, classe, nome, opcoes, null, editavel );
	}
	
	public Selecao( String nome, Class<? extends Object> classe, T[] opcoes, Boolean editavel ) {
		this( nome, classe, nome, new Lista<T>( opcoes ), null, editavel );
	}
	
	public Object getConteudo() {
		return getSelecionado();
	}
	
	/**
	 * @return <code>null</code> caso não haja objeto selecionado.
	 */
	public T getSelecionado() {
		if( selecionadoIndice == SEL_INDEFINIDO ) getIndiceSelecionado();
		return selecionado;
	}
	
	public void setSelecionado( T selecionado ) {
		this.selecionado = selecionado;
		this.selecionadoIndice = SEL_INDEFINIDO;
	}
	
	/**
	 * @return -1 caso não haja objeto selecionado.
	 */
	public int getIndiceSelecionado() {
		
		if( selecionado == null ) return selecionadoIndice = SEL_NULO;
		
		if( selecionadoIndice == SEL_INDEFINIDO ){
			selecionadoIndice = opcoes.indexOf( selecionado );
			if( selecionadoIndice == SEL_NULO ) selecionado = null;
		}
		
		return selecionadoIndice;
		
	}
	
	public Selecao<T> setIndiceSelecionado( int indice ) {
		
		if( indice < -1 || indice >= opcoes.size() ) throw new IndexOutOfBoundsException();

		this.selecionado = indice != SEL_NULO ? opcoes.get( indice ) : null;
		this.selecionadoIndice = indice;
		
		return this;
		
	}
	
	@Override
	public Selecao<T> setOpcoes( List<T> opcoes ) {
		this.opcoes = opcoes != null ? opcoes : new Lista<T>();
		setSelecionado( selecionado );
		return this;
	}
	
	@Override
	public Selecao<T> setOpcoes( T cabeca, T... cauda ) {
		return setOpcoes( new Lista<T>( cabeca, cauda ) );
	}
	
	@Override
	public Selecao<T> setOpcoes( T cabeca, List<T> cauda ) {
		return setOpcoes( new Lista<T>( cabeca, cauda ) );
	}
	
	@Override
	public List<T> getOpcoes() {
		return opcoes;
	}

	@Override
	public int getTotalOpcoes() {
		return opcoes.size();
	}
	
	@Override
	public T getOpcao( int indice ) {
		if( indice == selecionadoIndice ) return selecionado;
		return opcoes.get( indice );
	}
	
	public String getTexto( int indice ) {
		T obj = indice == selecionadoIndice ? selecionado : opcoes.get( indice );
		return aparencia != null ? aparencia.texto( obj ) : ( obj != null ? obj.toString() : "" );
	}
	
	public String getImagem( int indice ) {
		return aparencia != null ? aparencia.imagem( indice == selecionadoIndice ? selecionado : opcoes.get( indice ) ) : null;
	}
	
	public Aparencia<T> getAparencia() {
		return aparencia;
	}
	
	public Selecao<T> setAparencia( Aparencia<T> aparencia ) {
		this.aparencia = aparencia;
		return this;
	}

	public Selecao<T> maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Selecao<T> maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Selecao<T> maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
}
