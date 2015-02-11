
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
import java.util.List;

import com.joseflavio.cultura.Transformacao;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Edicao;
import com.joseflavio.tqc.VistaTextual;
import com.joseflavio.util.Lista;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.NaoVazioValidacao;
import com.joseflavio.validacao.TextoLimite;
import com.joseflavio.validacao.TransformacaoValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Dado} que possui a {@link Edicao} natural do {@link Texto} e que permite a escolha de seu conte�do atrav�s de v�rias op��es de texto.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class SelecionavelTexto extends Opcoes<String> implements VistaTextual {

	private String texto;
	
	private int larguraTextual;
	
	private List<String> opcoes;
	
	public SelecionavelTexto( String nome, String texto, int larguraTextual, List<String> opcoes, boolean editavel ) {
		super( nome, editavel );
		this.texto = texto;
		this.larguraTextual = larguraTextual;
		this.opcoes = opcoes != null ? opcoes : new Lista<String>();
	}
	
	public SelecionavelTexto( String nome, String texto, int larguraTextual, String[] opcoes, boolean editavel ) {
		this( nome, texto, larguraTextual, new Lista<String>( opcoes ), editavel );
	}
	
	public SelecionavelTexto( String nome, Class<? extends Object> classe, String atributo, String texto, List<String> opcoes, Boolean editavel ) {
		super( nome );
		this.texto = texto;
		this.opcoes = opcoes != null ? opcoes : new Lista<String>();
		configurarPor( classe, atributo );
		if( editavel != null ) setEditavel( editavel );
	}
	
	public SelecionavelTexto( String nome, Class<? extends Object> classe, String texto, List<String> opcoes, Boolean editavel ) {
		this( nome, classe, nome, texto, opcoes, editavel );
	}
	
	public SelecionavelTexto( String nome, Class<? extends Object> classe, String texto, String[] opcoes, Boolean editavel ) {
		this( nome, classe, nome, texto, new Lista<String>( opcoes ), editavel );
	}
	
	@Override
	public SelecionavelTexto setOpcoes( List<String> opcoes ) {
		this.opcoes = opcoes != null ? opcoes : new Lista<String>();
		return this;
	}
	
	@Override
	public SelecionavelTexto setOpcoes( String cabeca, String... cauda ) {
		return setOpcoes( new Lista<String>( cabeca, cauda ) );
	}
	
	@Override
	public SelecionavelTexto setOpcoes( String cabeca, List<String> cauda ) {
		return setOpcoes( new Lista<String>( cabeca, cauda ) );
	}
	
	@Override
	public List<String> getOpcoes() {
		return opcoes;
	}
	
	@Override
	public int getTotalOpcoes() {
		return opcoes.size();
	}
	
	@Override
	public String getOpcao( int indice ) {
		return opcoes.get( indice );
	}
	
	public Object getConteudo() {
		return texto;
	}
	
	public SelecionavelTexto setTexto( String texto ) {
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
	
	public SelecionavelTexto maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public SelecionavelTexto maisTextoLimite( int maxCaracteres, int tipo, String mensagem ) {
		mais( new TextoLimite( getNome(), maxCaracteres, tipo, mensagem ) );
		return this;
	}
	
	public SelecionavelTexto maisTextoLimite( int maxCaracteres, String mensagem ) {
		return maisTextoLimite( maxCaracteres, ValidacaoException.ERRO, mensagem );
	}
	
	public SelecionavelTexto maisNaoVazio( int tipo, String mensagem ) {
		mais( new NaoVazioValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public SelecionavelTexto maisNaoVazio( String mensagem ) {
		return maisNaoVazio( ValidacaoException.ERRO, mensagem );
	}
	
	public SelecionavelTexto maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public SelecionavelTexto maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
	public SelecionavelTexto maisTransformacao( Transformacao transformacao, int tipo, String mensagem ) {
		mais( new TransformacaoValidacao( getNome(), transformacao, tipo, mensagem ) );
		return this;
	}
	
	public SelecionavelTexto maisTransformacao( Transformacao transformacao, String mensagem ) {
		return maisTransformacao( transformacao, ValidacaoException.ERRO, mensagem );
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
		
		if( jfValidacaoNaoVazio != null ) maisNaoVazio( jfValidacaoNaoVazio.erro() );
		if( jfValidacaoTamanhoLimite != null ) maisTextoLimite( jfTexto.max(), jfValidacaoTamanhoLimite.erro() );
		
		return this;
		
	}
	
}
