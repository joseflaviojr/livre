
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
 * {@link Dado} que possui a {@link Edicao} natural do {@link Texto} e que permite a escolha de seu conteúdo através de várias opções de texto.
 * @author José Flávio de Souza Dias Júnior
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

		if( jfTexto == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " não contém " + JFTexto.class.getName() );
		
		this.larguraTextual = jfTexto.largura();
		
		if( jfValidacaoNaoVazio != null ) maisNaoVazio( jfValidacaoNaoVazio.erro() );
		if( jfValidacaoTamanhoLimite != null ) maisTextoLimite( jfTexto.max(), jfValidacaoTamanhoLimite.erro() );
		
		return this;
		
	}
	
}
