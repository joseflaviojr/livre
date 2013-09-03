
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

package com.joseflavio.tqc.dado;

import java.lang.reflect.Field;
import java.util.Date;

import com.joseflavio.modelo.JFData;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.VistaTextual;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Data e/ou hora.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Data extends ComplexoDado implements VistaTextual { //TODO Internacionalização no HTML
	
	private Date data;
	
	private DataTipo tipo;
	
	private int larguraTextual;
	
	/**
	 * @param data Conteúdo. Pode ser <code>null</code>.
	 */
	public Data( String nome, Date data, DataTipo tipo, int larguraTextual, boolean editavel ) {
		super( nome, editavel );
		this.data = data;
		this.tipo = tipo;
		this.larguraTextual = larguraTextual;
	}
	
	public Data( String nome, Date data, DataTipo tipo, boolean editavel ) {
		this( nome, data, tipo, 0, editavel );
	}
	
	public Data( Date data, DataTipo tipo ) {
		this( null, data, tipo, 0, false );
	}
	
	public Data( String nome, Class<? extends Object> classe, String atributo, Date data, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.data = data;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Data( String nome, Class<? extends Object> classe, Date data, Boolean editavel ) {
		this( nome, classe, nome, data, editavel );
	}
	
	public Object getConteudo() {
		return data;
	}
	
	public DataTipo getTipo() {
		return tipo;
	}

	public Data setTipo( DataTipo tipo ) {
		this.tipo = tipo;
		return this;
	}
	
	public Date getDataValidada() throws ValidacaoException {
		validar();
		return data;
	}
	
	public Date getData() {
		return data;
	}
	
	public Data setData( Date data ) {
		this.data = data;
		return this;
	}
	
	/**
	 * @return 1
	 */
	public int getAlturaTextual() {
		return 1;
	}
	
	/**
	 * Sem efeito.
	 */
	public Dado setAlturaTextual( int linhas ) {
		return this;
	}
	
	public int getLarguraTextual() {
		return larguraTextual;
	}
	
	public Dado setLarguraTextual( int caracs ) {
		this.larguraTextual = caracs;
		return this;
	}
	
	/**
	 * @return <code>false</code>.
	 */
	public boolean isMultiplaLinha() {
		return false;
	}
	
	public Data maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Data maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Data maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
	/**
	 * @see JFData
	 * @see JFTexto
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFData  jfData  = atributo.getAnnotation( JFData.class );
		JFTexto jfTexto = atributo.getAnnotation( JFTexto.class );

		if( jfData == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " não contém " + JFData.class.getName() );

		if( jfData.tipo() == JFData.DataTipo.DATA_HORA ) this.tipo = DataTipo.DATA_HORA;
		else if( jfData.tipo() == JFData.DataTipo.DATA ) this.tipo = DataTipo.DATA;
		else if( jfData.tipo() == JFData.DataTipo.HORA ) this.tipo = DataTipo.HORA;
		
		if( jfTexto != null ) this.larguraTextual = jfTexto.largura();
		
		return this;
		
	}
	
	public static enum DataTipo {
		
		DATA,
		HORA,
		DATA_HORA;

	}
	
}
