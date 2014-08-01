
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

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.EspacoLateral;
import com.joseflavio.tqc.Identificacao;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.OpcaoDeMenu;
import com.joseflavio.tqc.SimplesDado;

/**
 * Um comando induz uma ação, mas não a especifica.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Comando extends SimplesDado implements Identificacao, OpcaoDeMenu, EspacoLateral {
	
	/**
	 * @see Funcao#GERAL
	 */
	public static final int NORMAL = Funcao.GERAL.getValor();
	
	/**
	 * @see Funcao#CONFIRMAR
	 */
	public static final int OK = Funcao.CONFIRMAR.getValor();
	
	/**
	 * @see Funcao#CANCELAR
	 */
	public static final int CANCELAMENTO = Funcao.CANCELAR.getValor();
	
	/**
	 * @see Funcao#AVANCAR
	 */
	public static final int SUCESSAO = Funcao.AVANCAR.getValor();
	
	/**
	 * @see Funcao#VOLTAR
	 */
	public static final int ANTECESSAO = Funcao.VOLTAR.getValor();
	
	private String nome;
	
	private String rotulo;
	
	private String imagem;
	
	private Funcao funcao = Funcao.GERAL;
	
	private boolean espacoTextualPosterior = false;
	
	/**
	 * @param rotulo Rótulo que define o {@link Comando}.
	 * @param funcao Função genérica do {@link Comando}.
	 */
	public Comando( String nome, String rotulo, String imagem, Funcao funcao ) {
		this.nome = nome;
		this.rotulo = rotulo;
		this.imagem = imagem;
		this.funcao = funcao;
	}
	
	public Comando( String nome, String rotulo, String imagem, int funcao ) {
		this( nome, rotulo, imagem, Funcao.getFuncao( funcao ) );
	}
	
	public Comando( String nome, String rotulo, Funcao funcao ) {
		this( nome, rotulo, null, funcao );
	}
	
	public Comando( String nome, String rotulo, int funcao ) {
		this( nome, rotulo, null, funcao );
	}
	
	public Comando( String nome, String rotulo ) {
		this( nome, rotulo, null, NORMAL );
	}
	
	public Comando( String rotulo ) {
		this( null, rotulo, null, NORMAL );
	}
	
	@Override
	public int hashCode() {
		return
				( nome   != null ? nome.hashCode()   : 0 ) +
				( rotulo != null ? rotulo.hashCode() : 0 ) +
				( imagem != null ? imagem.hashCode() : 0 ) +
				( funcao != null ? funcao.getValor() : 0 );
	}
	
	public String getNome() {
		return nome;
	}
	
	public Dado setNome( String nome ) {
		this.nome = nome;
		return this;
	}
	
	public Object getConteudo() {
		return rotulo;
	}
	
	/**
	 * Mesmo que {@link #getNome()}.
	 */
	@Override
	public String getIdentificacao() {
		return nome;
	}
	
	public String getRotulo() {
		return rotulo;
	}
	
	public Comando setRotulo( String rotulo ) {
		this.rotulo = rotulo;
		return this;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public Comando setImagem( String imagem ) {
		this.imagem = imagem;
		return this;
	}
	
	public Funcao getFuncaoTipo() {
		return funcao;
	}
	
	public int getFuncao() {
		return funcao.getValor();
	}
	
	public Comando setFuncao( Funcao funcao ) {
		this.funcao = funcao;
		return this;
	}
	
	public Comando setFuncao( int funcao ) {
		this.funcao = Funcao.getFuncao( funcao );
		return this;
	}
	
	public boolean isEspacoTextualPosterior() {
		return espacoTextualPosterior;
	}
	
	public Dado setEspacoTextualPosterior( boolean sim ) {
		this.espacoTextualPosterior = sim;
		return this;
	}
	
	/**
	 * Tenta buscar um {@link Comando} por {@link ComandoPorMetodo#getComando(String)} ou por {@link Informacao#getDado(String)}.
	 * @return <code>null</code> caso o {@link Comando} não seja encontrado.
	 */
	public static Comando getComando( Informacao informacao, String nome ) {
		if( informacao instanceof ComandoPorMetodo ){
			try{
				return ((ComandoPorMetodo)informacao).getComando( nome );
			}catch( IllegalArgumentException e ){
				return null;
			}
		}else{
			Dado dado = informacao.getDado( nome );
			if( dado instanceof Comando ) return (Comando) dado;
		}
		return null;
	}
	
	public static enum Funcao {
		
		GERAL( 1 ),
		
		CONFIRMAR( 2 ),
		
		CANCELAR( 3 ),
		
		AVANCAR( 4 ),
		
		VOLTAR( 5 );
		
		private int valor;

		private Funcao( int valor ) {
			this.valor = valor;
		}
		
		public int getValor() {
			return valor;
		}
		
		public static Funcao getFuncao( int valor ) {
			switch( valor ){
				case 1 : return GERAL;
				case 2 : return CONFIRMAR;
				case 3 : return CANCELAR;
				case 4 : return AVANCAR;
				case 5 : return VOLTAR;
			}
			throw new IllegalArgumentException( "" + valor );
		}
		
	}
	
}
