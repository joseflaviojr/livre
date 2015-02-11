
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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

import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.VistaTextual;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.NaoVazioValidacao;
import com.joseflavio.validacao.TextoLimite;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Texto de uma ou mais linhas.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see String
 */
public class Texto extends ComplexoDado implements VistaTextual {
	
	private String texto;
	
	private int larguraTextual;
	
	private int alturaTextual = 1;
	
	private boolean multiplaLinha = false;
	
	private boolean quebraDeLinhaAutomatica = false;

	/**
	 * @param texto Conte�do. Pode ser <code>null</code>.
	 */
	public Texto( String nome, String texto, int larguraTextual, int alturaTextual, boolean multiplaLinha, boolean editavel ) {
		super( nome, editavel );
		this.texto = texto;
		this.larguraTextual = larguraTextual;
		this.alturaTextual = alturaTextual;
		this.multiplaLinha = multiplaLinha;
	}
	
	public Texto( String nome, String texto, int larguraTextual, boolean editavel ) {
		this( nome, texto, larguraTextual, 1, false, editavel );
	}
	
	public Texto( String nome, String texto ) {
		this( nome, texto, 0, 1, false, false );
	}
	
	public Texto( String texto ) {
		this( null, texto, 0, 0, true, false );
	}
	
	public Texto( String nome, Class<? extends Object> classe, String atributo, String texto, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.texto = texto;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Texto( String nome, Class<? extends Object> classe, String texto, Boolean editavel ) {
		this( nome, classe, nome, texto, editavel );
	}
	
	public Object getConteudo() {
		return texto;
	}
	
	public Texto setTexto( String texto ) {
		this.texto = texto;
		return this;
	}
	
	public String getTextoValidado() throws ValidacaoException {
		validar();
		return texto;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public int getAlturaTextual() {
		return alturaTextual;
	}
	
	public Dado setAlturaTextual( int linhas ) {
		this.alturaTextual = linhas;
		return this;
	}
	
	public int getLarguraTextual() {
		return larguraTextual;
	}
	
	public Dado setLarguraTextual( int caracs ) {
		this.larguraTextual = caracs;
		return this;
	}
	
	public boolean isMultiplaLinha() {
		return multiplaLinha;
	}
	
	public void setQuebraDeLinhaAutomatica( boolean quebraDeLinhaAutomatica ) {
		this.quebraDeLinhaAutomatica = quebraDeLinhaAutomatica;
	}
	
	public boolean isQuebraDeLinhaAutomatica() {
		return quebraDeLinhaAutomatica;
	}
	
	public boolean isVazio() {
		return getConteudoInvalido() == null && ( texto == null || texto.length() == 0 );
	}
	
	public Texto maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Texto maisTextoLimite( int maxCaracteres, int tipo, String mensagem ) {
		mais( new TextoLimite( getNome(), maxCaracteres, tipo, mensagem ) );
		return this;
	}
	
	public Texto maisTextoLimite( int maxCaracteres, String mensagem ) {
		return maisTextoLimite( maxCaracteres, ValidacaoException.ERRO, mensagem );
	}
	
	public Texto maisNaoVazio( int tipo, String mensagem ) {
		mais( new NaoVazioValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Texto maisNaoVazio( String mensagem ) {
		return maisNaoVazio( ValidacaoException.ERRO, mensagem );
	}
	
	public Texto maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Texto maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
	/**
	 * @see JFTexto
	 * @see JFValidacaoNaoVazio
	 * @see JFValidacaoTamanhoLimite
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFTexto                   jfTexto                    = atributo.getAnnotation( JFTexto.class );
		JFValidacaoNaoVazio       jfValidacaoNaoVazio        = atributo.getAnnotation( JFValidacaoNaoVazio.class );
		JFValidacaoTamanhoLimite  jfValidacaoTamanhoLimite   = atributo.getAnnotation( JFValidacaoTamanhoLimite.class );

		if( jfTexto == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " n�o cont�m " + JFTexto.class.getName() );
		
		this.larguraTextual = jfTexto.largura();
		this.alturaTextual = jfTexto.altura();
		this.multiplaLinha = jfTexto.variasLinhas();
		this.quebraDeLinhaAutomatica = jfTexto.quebraDeLinhaAutomatica();
		
		if( jfValidacaoNaoVazio != null ) maisNaoVazio( jfValidacaoNaoVazio.erro() );
		if( jfValidacaoTamanhoLimite != null ) maisTextoLimite( jfTexto.max(), jfValidacaoTamanhoLimite.erro() );
		
		return this;
		
	}
	
}
