
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
 * José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 * sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a versão 3 da Licença, como
 * (a seu critério) qualquer versão posterior.
 * 
 * José Flávio Livre é distribuído na expectativa de que seja útil,
 * porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 * COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 * Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 * junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Os argumentos puros (<code>String[] args</code>) são interpretados e assim convertidos para {@link Argumento}'s.<br>
 * Cada elemento de <code>String[] args</code> será interpretado sequencialmente dessa forma:<br>
 * - caso seja uma {@link Chave} sem {@link Chave#isRequerValor() necessidade de valor}, obtém-se um {@link Argumento} com {@link Chave} e sem {@link Argumento#getValor() valor};<br>
 * - caso seja uma {@link Chave} com {@link Chave#isRequerValor() necessidade de valor}, obtém-se um {@link Argumento} com {@link Chave} e com {@link Argumento#getValor() valor}, este sendo o próximo elemento puro e imediato;<br>
 * - senão obtém-se um {@link Argumento} sem {@link Chave} e com {@link Argumento#getValor() valor}.<br>
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ChaveadosArgumentos implements Argumentos {

	private String[] args;
	
	private Map<String, Chave> chaves = new HashMap<String, Chave>();
	
	private Map<String, List<Argumento>> argumentos = new HashMap<String, List<Argumento>>();
	
	public ChaveadosArgumentos( String[] args, List<Chave> chaves ) {
		this.args = args;
		for( Chave c : chaves ){
			String k = c.getToken();
			this.chaves.put( k, c );
			this.argumentos.put( k, new ArrayList<Argumento>() );
		}
		this.argumentos.put( null, new ArrayList<Argumento>() );
		interpretar();
	}
	
	public ChaveadosArgumentos( String[] args, Chave... chaves ) {
		this.args = args;
		for( Chave c : chaves ){
			String k = c.getToken();
			this.chaves.put( k, c );
			this.argumentos.put( k, new ArrayList<Argumento>() );
		}
		this.argumentos.put( null, new ArrayList<Argumento>() );
		interpretar();
	}
	
	private void interpretar() {
		
		Chave chave;
		String token, valor;
		int total = args.length;
		
		for( int i = 0; i < total; i++ ){
			
			token = args[i];
			valor = null;
			chave = chaves.get( token );
			
			if( chave == null ){
				valor = token;
				token = null;
			}else if( chave.requerValor ){
				valor = ++i < total ? args[i] : null;
			}
			
			argumentos.get( token ).add( new Argumento( chave, valor ) );
			
		}
		
	}
	
	/**
	 * Retorna todos os {@link Argumento}'s declarados com a {@link Chave} em questão.
	 * @param chave {@link Chave#getToken()}. <code>null</code> == sem chave.
	 * @return lista vazia caso não haja {@link Argumento}'s.
	 */
	public List<Argumento> getArgumentos( String chave ) {
		return argumentos.get( chave );
	}
	
	/**
	 * Retorna todos os {@link Argumento}'s declarados.
	 * @return lista vazia caso não haja {@link Argumento}'s.
	 */
	public List<Argumento> getArgumentos() {
		
		Collection<List<Argumento>> listas = argumentos.values();
		List<Argumento> todos = new ArrayList<Argumento>( listas.size() );
		
		for( List<Argumento> lista : listas ){
			for( Argumento arg : lista ){
				todos.add( arg );
			}
		}
		
		return todos;
		
	}
	
	/**
	 * {@link #getArgumentos(String)}.size()
	 */
	public int getTotalArgumentos( String chave ) {
		return argumentos.get( chave ).size();
	}
	
	/**
	 * Retorna o total de {@link Argumento}'s.
	 * @see #getArgumentos()
	 */
	public int getTotalArgumentos() {
		int total = 0;
		for( List<Argumento> lista : argumentos.values() ){
			total += lista.size();
		}
		return total;
	}
	
	@Override
	public String[] getArgs() {
		return args;
	}
	
	/**
	 * @param argumentoSemChave {@link ArgumentoProcessador} para {@link Argumento}'s sem {@link Chave}. Pode ser <code>null</code>.
	 * @see ArgumentoProcessador
	 * @see Chave#getProcessador()
	 */
	public ChaveadosArgumentos processarArgumentos( ArgumentoProcessador argumentoSemChave ) throws IllegalArgumentException {
		Chave chave;
		ArgumentoProcessador proc;
		for( List<Argumento> lista : argumentos.values() ){
			for( Argumento arg : lista ){
				chave = arg.chave;
				proc = chave != null ? chave.processador : argumentoSemChave;
				if( proc != null ) proc.processar( arg );
			}
		}
		return this;
	}

	public static class Chave {
		
		private String token;
		
		private boolean requerValor;
		
		private ArgumentoProcessador processador;
		
		private String explicacao;

		/**
		 * @param token Símbolo que será efetivamente utilizado para identificar esta {@link Chave} entre os argumentos puros (<code>String[] args</code>). Normalmente tem o hífen como primeiro caractere. Exemplo: -o
		 * @param requerValor Significa que toda vez que o token for encontrado, o próximo argumento puro e imediato será o valor correspondente.
		 * @param processador Pode ser <code>null</code>. 
		 * @param explicacao Breve explicação sobre esta {@link Chave}. Pode ser <code>null</code>.
		 */
		public Chave( String token, boolean requerValor, ArgumentoProcessador processador, String explicacao ) {
			if( token == null ) throw new IllegalArgumentException( "token == null" );
			this.token = token;
			this.requerValor = requerValor;
			this.processador = processador;
			this.explicacao = explicacao;
		}
		
		public Chave( String token, boolean requerValor, ArgumentoProcessador processador ) {
			this( token, requerValor, processador, null );
		}
		
		public Chave( String token, boolean requerValor ) {
			this( token, requerValor, null, null );
		}
		
		public String getToken() {
			return token;
		}

		public boolean isRequerValor() {
			return requerValor;
		}
		
		public ArgumentoProcessador getProcessador() {
			return processador;
		}

		public String getExplicacao() {
			return explicacao;
		}
		
	}
	
	/**
	 * Argumento obtido após interpretação dos argumentos puros (<code>String[] args</code>).
	 */
	public static class Argumento {
		
		private Chave chave;
		
		private String valor;

		Argumento( Chave chave, String valor ) {
			this.chave = chave;
			this.valor = valor;
		}
		
		/**
		 * @return <code>null</code> == sem chave.
		 */
		public Chave getChave() {
			return chave;
		}
		
		/**
		 * @return <code>null</code> == sem valor.
		 */
		public String getValor() {
			return valor;
		}
		
	}
	
	/**
	 * Processador de {@link Argumento}'s.
	 * @see AdaptadoArgumentoProcessador
	 */
	public static interface ArgumentoProcessador {
		
		/**
		 * @throws IllegalArgumentException caso o {@link Argumento} seja inválido.
		 */
		public void processar( Argumento arg ) throws IllegalArgumentException;
		
	}
	
	public static abstract class AdaptadoArgumentoProcessador implements ArgumentoProcessador {
		
		@Override
		public final void processar( Argumento arg ) throws IllegalArgumentException {
			processar( arg.chave, arg.valor );
		}
		
		/**
		 * @see Argumento#getChave()
		 * @see Argumento#getValor()
		 */
		public abstract void processar( Chave chave, String valor ) throws IllegalArgumentException;
		
	}
	
}
