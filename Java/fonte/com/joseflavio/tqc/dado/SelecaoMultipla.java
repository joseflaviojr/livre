
/*
 *  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
 *  
 *  This file is part of Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 *  
 *  Jos� Fl�vio Livre is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Jos� Fl�vio Livre is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Jos� Fl�vio Livre. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
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
 * {@link Dado} que permite a escolha de um ou mais objetos entre v�rios opcionais.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * @param editavel Sele��o permitida?
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
	 * @return lista vazia caso n�o haja objeto selecionado.
	 */
	public List<T> getSelecionados() {
		if( ! selecionadosValidados ) validarSelecionados();
		return selecionados;
	}
	
	/**
	 * Determina os objetos selecionados, os quais ser�o validados apenas quando deles necessitarem.<br>
	 * A valida��o consiste em garantir que os selecionados estejam presentes entre as {@link #getOpcoes()}. Portanto, os ausentes ser�o removidos.
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
