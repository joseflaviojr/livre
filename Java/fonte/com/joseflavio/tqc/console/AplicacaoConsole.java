
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
 * Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 * sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 * (a seu crit�rio) qualquer vers�o posterior.
 * 
 * Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 * por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 * COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 * Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 * junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.console;

import java.io.IOException;

import com.joseflavio.cultura.Cultura;

/**
 * Aplica��o baseada em {@link Console}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see #inicio(String[])
 */
public abstract class AplicacaoConsole extends Console {

	private Console console;
	
	private Argumentos argumentos;
	
	private int corTextoInicial;
	
	private int corTextoFundoInicial;
	
	/**
	 * @param console {@link Console} a ser utilizado por esta aplica��o, a qual ser� um proxy dele. <code>null</code> == {@link Console#novoConsole()}.
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
	 * M�todo inicial, o qual deve ser invocado pelo m�todo <code>main</code>.<br>
	 * Sequ�ncia: Guardar cores, {@link #processarArgumentos(String[])}, {@link #principal()}, restaurar cores(�), {@link #fim()}(�).<br>
	 * (�) {@link Runtime#addShutdownHook(Thread)}
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
	 * @throws IllegalArgumentException caso haja argumentos inv�lidos.
	 */
	protected abstract Argumentos processarArgumentos( String[] args );
	
	/**
	 * M�todo principal da {@link AplicacaoConsole}, executado ap�s {@link #processarArgumentos(String[])} e respons�vel pelo ciclo de vida da aplica��o.
	 */
	protected abstract void principal();
	
	/**
	 * M�todo final da {@link AplicacaoConsole}, respons�vel pela libera��o dos recursos em utiliza��o.
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
