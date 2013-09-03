
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

import com.joseflavio.modelo.JFInteiro;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.modelo.JFValidacaoValorLimite;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.VistaTextual;
import com.joseflavio.validacao.InteiroLimiteValidacao;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.TextoLimite;
import com.joseflavio.validacao.ValidacaoException;

/**
 * N�mero inteiro.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Inteiro extends ComplexoDado implements VistaTextual {
	
	private Long numero;
	
	private int larguraTextual;
	
	/**
	 * @param numero Conte�do. Pode ser <code>null</code>.
	 */
	public Inteiro( String nome, Long numero, int larguraTextual, boolean editavel ) {
		super( nome, editavel );
		this.numero = numero;
		this.larguraTextual = larguraTextual;
	}
	
	public Inteiro( String nome, long numero, int larguraTextual, boolean editavel ) {
		this( nome, new Long( numero ), larguraTextual, editavel );
	}
	
	public Inteiro( String nome, Class<? extends Object> classe, String atributo, Long numero, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.numero = numero;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Inteiro( String nome, Class<? extends Object> classe, Long numero, Boolean editavel ) {
		this( nome, classe, nome, numero, editavel );
	}
	
	public Object getConteudo() {
		return numero;
	}
	
	public Inteiro setNumero( Long numero ) {
		this.numero = numero;
		return this;
	}
	
	public Inteiro setNumero( long numero ) {
		this.numero = new Long( numero );
		return this;
	}

	public Long getNumeroValidado() throws ValidacaoException {
		validar();
		return numero;
	}
	
	public Long getNumero() {
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
	
	public Inteiro maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Inteiro maisTextoLimite( int maxCaracteres, int tipo, String mensagem ) {
		mais( new TextoLimite( getNome(), maxCaracteres, tipo, mensagem ) );
		return this;
	}
	
	public Inteiro maisTextoLimite( int maxCaracteres, String mensagem ) {
		return maisTextoLimite( maxCaracteres, ValidacaoException.ERRO, mensagem );
	}
	
	public Inteiro maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Inteiro maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
	public Inteiro maisInteiroLimite( long min, long max, int tipo, String mensagem ) {
		mais( new InteiroLimiteValidacao( getNome(), min, max, tipo, mensagem ) );
		return this;
	}
	
	public Inteiro maisInteiroLimite( long min, long max, String mensagem ) {
		return maisInteiroLimite( min, max, ValidacaoException.ERRO, mensagem );
	}
	
	/**
	 * @see JFInteiro
	 * @see JFTexto
	 * @see JFValidacaoValorLimite
	 * @see JFValidacaoTamanhoLimite
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFInteiro                 jfInteiro                  = atributo.getAnnotation( JFInteiro.class );
		JFTexto                   jfTexto                    = atributo.getAnnotation( JFTexto.class );
		JFValidacaoValorLimite    jfValidacaoValorLimite     = atributo.getAnnotation( JFValidacaoValorLimite.class );
		JFValidacaoTamanhoLimite  jfValidacaoTamanhoLimite   = atributo.getAnnotation( JFValidacaoTamanhoLimite.class );

		if( jfInteiro == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " n�o cont�m " + JFInteiro.class.getName() );

		if( jfTexto != null ){
			this.larguraTextual = jfTexto.largura();
			if( jfValidacaoTamanhoLimite != null ) maisTextoLimite( jfTexto.max(), jfValidacaoTamanhoLimite.erro() );
		}
		
		if( jfValidacaoValorLimite != null ) maisInteiroLimite( jfInteiro.min(), jfInteiro.max(), jfValidacaoValorLimite.erro() );
		
		return this;
		
	}
	
}
