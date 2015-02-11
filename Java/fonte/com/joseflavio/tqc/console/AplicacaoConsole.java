
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

import java.io.IOException;

import com.joseflavio.cultura.Cultura;

/**
 * Aplicação baseada em {@link Console}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see #inicio(String[])
 */
public abstract class AplicacaoConsole extends Console {

	private Console console;
	
	private Argumentos argumentos;
	
	private int corTextoInicial;
	
	private int corTextoFundoInicial;
	
	/**
	 * @param console {@link Console} a ser utilizado por esta aplicação, a qual será um proxy dele. <code>null</code> == {@link Console#novoConsole()}.
	 */
	protected AplicacaoConsole( Console console ) {
		
		super( console != null ? console.getCultura() : Cultura.getPadrao() );
		
		this.console = console != null ? console : Console.novoConsole();
		
	}

	/**
	 * {@link Console#novoConsole(Cultura)}
	 */
	protected AplicacaoConsole( Cultura cultura ) {
		this( Console.novoConsole( cultura ) );
	}
	
	/**
	 * {@link AplicacaoConsole#AplicacaoConsole(Console)} com <code>null</code>.
	 */
	protected AplicacaoConsole() {
		this( (Console) null );
	}
	
	/**
	 * Método inicial, o qual deve ser invocado pelo método <code>main</code>.<br>
	 * Sequência: Guardar cores, {@link #processarArgumentos(String[])}, {@link #principal()}, restaurar cores(¹), {@link #fim()}(¹).<br>
	 * (¹) {@link Runtime#addShutdownHook(Thread)}
	 */
	public final void inicio( String[] args ) {
		
		corTextoInicial = getCorTexto();
		corTextoFundoInicial = getCorTextoFundo();
		
		Runtime.getRuntime().addShutdownHook( new SaidaThread() );
		
		argumentos = processarArgumentos( args );
		if( argumentos == null ) throw new IllegalArgumentException( "Argumentos == null" );
		
		principal();
		
	}
	
	/**
	 * Processa todos os argumentos puros recebidos, convertendo-os para {@link Argumentos}, validando-os e inicializando os componentes internos relacionados.
	 * @return {@link Argumentos}, mesmo vazio.
	 * @throws IllegalArgumentException caso haja argumentos inválidos.
	 */
	protected abstract Argumentos processarArgumentos( String[] args );
	
	/**
	 * Método principal da {@link AplicacaoConsole}, executado após {@link #processarArgumentos(String[])} e responsável pelo ciclo de vida da aplicação.
	 */
	protected abstract void principal();
	
	/**
	 * Método final da {@link AplicacaoConsole}, responsável pela liberação dos recursos em utilização.
	 */
	protected abstract void fim();
	
	public void setConsole( Console console ) {
		this.console = console;
	}
	
	public Console getConsole() {
		return console;
	}
	
	public Argumentos getArgumentos() {
		return argumentos;
	}
	
	@Override
	public int getTotalColunas() {
		return console.getTotalColunas();
	}

	@Override
	public int getTotalLinhas() {
		return console.getTotalLinhas();
	}

	@Override
	public void setCorTexto( int cor ) {
		console.setCorTexto( cor );
	}

	@Override
	public int getCorTexto() {
		return console.getCorTexto();
	}

	@Override
	public void setCorTextoFundo( int cor ) {
		console.setCorTextoFundo( cor );
	}

	@Override
	public int getCorTextoFundo() {
		return console.getCorTextoFundo();
	}

	@Override
	public void limpar() {
		console.limpar();
	}

	@Override
	public char esperar( boolean mostrar ) throws IOException {
		return console.esperar( mostrar );
	}

	@Override
	public void setTelaCheia( boolean cheia ) {
		console.setTelaCheia( cheia );
	}

	@Override
	public String receber() throws IOException {
		return console.receber();
	}

	@Override
	public void enviar( String texto ) {
		console.enviar( texto );
	}

	@Override
	public void enviarln( String texto ) {
		console.enviarln( texto );
	}

	@Override
	public void enviarln() {
		console.enviarln();
	}

	@Override
	public void enviar( String formato, Object... args ) {
		console.enviar( formato, args );
	}
	
	private class SaidaThread extends Thread {
		
		@Override
		public void run() {
			
			setCorTexto( corTextoInicial );
			setCorTextoFundo( corTextoFundoInicial );
			
			fim();
			
		}
		
	}

}
