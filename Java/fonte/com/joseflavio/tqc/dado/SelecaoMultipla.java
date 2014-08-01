
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

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.tqc.Aparencia;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.anotacao.TQCOpcoes;
import com.joseflavio.util.Lista;
import com.joseflavio.validacao.NaoVazioValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Dado} que permite a escolha de um ou mais objetos entre vários opcionais.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see TQCOpcoes
 */
public class SelecaoMultipla<T> extends Opcoes<T> {
	
	private List<T> opcoes;
	
	private List<T> selecionados;
	
	private Aparencia<T> aparencia;
	
	private boolean disposicaoHorizontal = false;
	
	private transient boolean selecionadosValidados = false;
	
	/**
	 * @param editavel Seleção permitida?
	 */
	public SelecaoMultipla( String nome, List<T> opcoes, boolean editavel ) {
		super( nome, editavel );
		setOpcoes( opcoes );
		setSelecionados( null );
	}
	
	public SelecaoMultipla( String nome, T[] opcoes, boolean editavel ) {
		this( nome, new Lista<T>( opcoes ), editavel );
	}
	
	public SelecaoMultipla( String nome, List<T> opcoes ) {
		this( nome, opcoes, true );
	}
	
	public SelecaoMultipla( String nome, T[] opcoes ) {
		this( nome, new Lista<T>( opcoes ) );
	}
	
	public SelecaoMultipla( List<T> opcoes ) {
		this( null, opcoes, true );
	}
	
	public SelecaoMultipla( T[] opcoes ) {
		this( new Lista<T>( opcoes ) );
	}
	
	public SelecaoMultipla( String nome, List<T> opcoes, List<T> selecaoInicial, boolean editavel ) {
		this( nome, opcoes, editavel );
		setSelecionados( selecaoInicial );
	}
	
	public SelecaoMultipla( String nome, List<T> opcoes, List<T> selecaoInicial ) {
		this( nome, opcoes, selecaoInicial, true );
	}
	
	public SelecaoMultipla( String nome, T[] opcoes, List<T> selecaoInicial ) {
		this( nome, new Lista<T>( opcoes ), selecaoInicial );	
	}
	
	public SelecaoMultipla( String nome, T[] opcoes, List<T> selecaoInicial, boolean editavel ) {
		this( nome, new Lista<T>( opcoes ), selecaoInicial, editavel );
	}

	public SelecaoMultipla( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		super( nome );
		setOpcoes( opcoes );
		setSelecionados( selecaoInicial );
		configurarPor( classe, atributo );
		if( editavel != null ) setEditavel( editavel );
	}
	
	public SelecaoMultipla( String nome, Class<? extends Object> classe, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		this( nome, classe, nome, opcoes, selecaoInicial, editavel );
	}
	
	public SelecaoMultipla( String nome, Class<? extends Object> classe, T[] opcoes, List<T> selecaoInicial, Boolean editavel ) {
		this( nome, classe, nome, new Lista<T>( opcoes ), selecaoInicial, editavel );
	}
	
	public SelecaoMultipla( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, Boolean editavel ) {
		this( nome, classe, atributo, opcoes, null, editavel );
	}
	
	public SelecaoMultipla( String nome, Class<? extends Object> classe, List<T> opcoes, Boolean editavel ) {
		this( nome, classe, nome, opcoes, null, editavel );
	}
	
	public SelecaoMultipla( String nome, Class<? extends Object> classe, T[] opcoes, Boolean editavel ) {
		this( nome, classe, nome, new Lista<T>( opcoes ), null, editavel );
	}
	
	/**
	 * @return {@link #getSelecionados()}
	 */
	public Object getConteudo() {
		return getSelecionados();
	}
	
	/**
	 * @return lista vazia caso não haja objeto selecionado.
	 */
	public List<T> getSelecionados() {
		if( ! selecionadosValidados ) validarSelecionados();
		return selecionados;
	}
	
	/**
	 * Determina os objetos selecionados, os quais serão validados apenas quando deles necessitarem.<br>
	 * A validação consiste em garantir que os selecionados estejam presentes entre as {@link #getOpcoes()}. Portanto, os ausentes serão removidos.
	 * @param selecionados Pode ser <code>null</code>.
	 */
	public void setSelecionados( List<T> selecionados ) {
		this.selecionados = selecionados != null ? selecionados : new Lista<T>();
		this.selecionadosValidados = false;
	}
	
	private void validarSelecionados() {
		Iterator<T> i = this.selecionados.iterator();
		while( i.hasNext() ){
			T t = i.next();
			if( ! this.opcoes.contains( t ) ) i.remove();
		}
		this.selecionadosValidados = true;
	}
	
	public boolean isSelecionado( int indice ) {
		return getSelecionados().contains( opcoes.get( indice ) );
	}
	
	@Override
	public SelecaoMultipla<T> setOpcoes( List<T> opcoes ) {
		this.opcoes = opcoes != null ? opcoes : new Lista<T>();
		this.selecionadosValidados = false;
		return this;
	}
	
	@Override
	public SelecaoMultipla<T> setOpcoes( T cabeca, T... cauda ) {
		return setOpcoes( new Lista<T>( cabeca, cauda ) );
	}
	
	@Override
	public SelecaoMultipla<T> setOpcoes( T cabeca, List<T> cauda ) {
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
		return opcoes.get( indice );
	}
	
	public String getTexto( int indice ) {
		T obj = opcoes.get( indice );
		return aparencia != null ? aparencia.texto( obj ) : ( obj != null ? obj.toString() : "" );
	}
	
	public String getImagem( int indice ) {
		return aparencia != null ? aparencia.imagem( opcoes.get( indice ) ) : null;
	}
	
	public Aparencia<T> getAparencia() {
		return aparencia;
	}
	
	public SelecaoMultipla<T> setAparencia( Aparencia<T> aparencia ) {
		this.aparencia = aparencia;
		return this;
	}
	
	public void setDisposicaoHorizontal( boolean disposicaoHorizontal ) {
		this.disposicaoHorizontal = disposicaoHorizontal;
	}
	
	public boolean isDisposicaoHorizontal() {
		return disposicaoHorizontal;
	}
	
	public SelecaoMultipla<T> maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public SelecaoMultipla<T> maisNaoVazio( int tipo, String mensagem ) {
		mais( new NaoVazioValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public SelecaoMultipla<T> maisNaoVazio( String mensagem ) {
		return maisNaoVazio( ValidacaoException.ERRO, mensagem );
	}
	
	/**
	 * @see JFValidacaoNaoVazio
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFValidacaoNaoVazio jfValidacaoNaoVazio = atributo.getAnnotation( JFValidacaoNaoVazio.class );

		if( jfValidacaoNaoVazio != null ) maisNaoVazio( jfValidacaoNaoVazio.erro() );
		
		return this;
		
	}
	
}
