
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Data extends ComplexoDado implements VistaTextual { //TODO Internacionaliza��o no HTML
	
	private Date data;
	
	private DataTipo tipo;
	
	private int larguraTextual;
	
	/**
	 * @param data Conte�do. Pode ser <code>null</code>.
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

		if( jfData == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " n�o cont�m " + JFData.class.getName() );

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
