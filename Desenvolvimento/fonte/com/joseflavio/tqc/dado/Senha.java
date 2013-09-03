
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
 * Senha definida textualmente.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Senha extends ComplexoDado implements VistaTextual {
	
	private String senha;
	
	private int larguraTextual;
	
	/**
	 * @param senha Conteúdo. Pode ser <code>null</code>.
	 */
	public Senha( String nome, String senha, int larguraTextual, boolean editavel ) {
		super( nome, editavel );
		this.senha = senha;
		this.larguraTextual = larguraTextual;
	}
	
	public Senha( String senha ) {
		this( null, senha, 0, false );
	}
	
	public Senha( String nome, Class<? extends Object> classe, String atributo, String senha, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.senha = senha;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Senha( String nome, Class<? extends Object> classe, String senha, Boolean editavel ) {
		this( nome, classe, nome, senha, editavel );
	}
	
	public Object getConteudo() {
		return senha;
	}
	
	public Senha setSenha( String senha ) {
		this.senha = senha;
		return this;
	}
	
	public String getSenhaValidada() throws ValidacaoException {
		validar();
		return senha;
	}
	
	public String getSenha() {
		return senha;
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
	
	public boolean isVazio() {
		return getConteudoInvalido() == null && ( senha == null || senha.length() == 0 );
	}
	
	public Senha maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Senha maisTextoLimite( int maxCaracteres, int tipo, String mensagem ) {
		mais( new TextoLimite( getNome(), maxCaracteres, tipo, mensagem ) );
		return this;
	}
	
	public Senha maisTextoLimite( int maxCaracteres, String mensagem ) {
		return maisTextoLimite( maxCaracteres, ValidacaoException.ERRO, mensagem );
	}
	
	public Senha maisNaoVazio( int tipo, String mensagem ) {
		mais( new NaoVazioValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Senha maisNaoVazio( String mensagem ) {
		return maisNaoVazio( ValidacaoException.ERRO, mensagem );
	}
	
	public Senha maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Senha maisNaoNulo( String mensagem ) {
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

		if( jfTexto == null ) throw new IllegalArgumentException( "O atributo " + atributo.getType().getSimpleName() + "." + atributo.getName() + " não contém " + JFTexto.class.getName() );
		
		this.larguraTextual = jfTexto.largura();
		
		if( jfValidacaoNaoVazio != null ) maisNaoVazio( jfValidacaoNaoVazio.erro() );
		if( jfValidacaoTamanhoLimite != null ) maisTextoLimite( jfTexto.max(), jfValidacaoTamanhoLimite.erro() );
		
		return this;
		
	}
	
}
