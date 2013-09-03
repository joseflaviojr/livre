
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

import com.joseflavio.modelo.JFReal;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.modelo.JFValidacaoValorLimite;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.VistaTextual;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.RealLimiteValidacao;
import com.joseflavio.validacao.TextoLimite;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Número real.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Real extends ComplexoDado implements VistaTextual {
	
	private Double numero;
	
	private int larguraTextual;
	
	private int digitosNaFracao;
	
	/**
	 * @param numero Conteúdo. Pode ser <code>null</code>.
	 * @param digitosNaFracao Quantidade de dígitos (mínimo e máximo) na parte fracionária do número. -1 == desconsiderar.
	 */
	public Real( String nome, Double numero, int digitosNaFracao, int larguraTextual, boolean editavel ) {
		super( nome, editavel );
		this.numero = numero;
		this.digitosNaFracao = digitosNaFracao;
		this.larguraTextual = larguraTextual;
	}
	
	public Real( String nome, double numero, int digitosNaFracao, int larguraTextual, boolean editavel ) {
		this( nome, new Double( numero ), digitosNaFracao, larguraTextual, editavel );
	}
	
	public Real( String nome, Double numero, int larguraTextual, boolean editavel ) {
		this( nome, numero, -1, larguraTextual, editavel );
	}
	
	public Real( String nome, double numero, int larguraTextual, boolean editavel ) {
		this( nome, new Double( numero ), -1, larguraTextual, editavel );
	}
	
	public Real( double numero ) {
		this( null, new Double( numero ), -1, 0, false );
	}
	
	public Real( String nome, Class<? extends Object> classe, String atributo, Double numero, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.numero = numero;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Real( String nome, Class<? extends Object> classe, Double numero, Boolean editavel ) {
		this( nome, classe, nome, numero, editavel );
	}
	
	public Object getConteudo() {
		return numero;
	}
	
	public Real setNumero( Double numero ) {
		this.numero = numero;
		return this;
	}
	
	public Real setNumero( double numero ) {
		this.numero = new Double( numero );
		return this;
	}

	public Double getNumeroValidado() throws ValidacaoException {
		validar();
		return numero;
	}
	
	public Double getNumero() {
		return numero;
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
	
	public int getDigitosNaFracao() {
		return digitosNaFracao;
	}
	
	public Real setDigitosNaFracao( int digitosNaFracao ) {
		this.digitosNaFracao = digitosNaFracao;
		return this;
	}
	
	public Real maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Real maisTextoLimite( int maxCaracteres, int tipo, String mensagem ) {
		mais( new TextoLimite( getNome(), maxCaracteres, tipo, mensagem ) );
		return this;
	}
	
	public Real maisTextoLimite( int maxCaracteres, String mensagem ) {
		return maisTextoLimite( maxCaracteres, ValidacaoException.ERRO, mensagem );
	}
	
	public Real maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Real maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
	public Real maisRealLimite( double min, double max, int tipo, String mensagem ) {
		mais( new RealLimiteValidacao( getNome(), min, max, tipo, mensagem ) );
		return this;
	}
	
	public Real maisRealLimite( double min, double max, String mensagem ) {
		return maisRealLimite( min, max, ValidacaoException.ERRO, mensagem );
	}
	
	/**
	 * @see JFReal
	 * @see JFTexto
	 * @see JFValidacaoValorLimite
	 * @see JFValidacaoTamanhoLimite
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFReal                    jfReal                     = atributo.getAnnotation( JFReal.class );
		JFTexto                   jfTexto                    = atributo.getAnnotation( JFTexto.class );
		JFValidacaoValorLimite    jfValidacaoValorLimite     = atributo.getAnnotation( JFValidacaoValorLimite.class );
		JFValidacaoTamanhoLimite  jfValidacaoTamanhoLimite   = atributo.getAnnotation( JFValidacaoTamanhoLimite.class );

		if( jfReal == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " não contém " + JFReal.class.getName() );

		this.digitosNaFracao = jfReal.maxFracao();
		
		if( jfTexto != null ){
			this.larguraTextual = jfTexto.largura();
			if( jfValidacaoTamanhoLimite != null ) maisTextoLimite( jfTexto.max(), jfValidacaoTamanhoLimite.erro() );
		}
		if( jfValidacaoValorLimite != null ) maisRealLimite( jfReal.min(), jfReal.max(), jfValidacaoValorLimite.erro() );
		
		return this;
		
	}
	
}
