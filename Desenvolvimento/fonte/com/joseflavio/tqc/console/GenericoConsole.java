
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
import java.util.Formatter;
import java.util.Scanner;

import com.joseflavio.cultura.Cultura;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class GenericoConsole extends Console {

	private java.io.Console console;
	
	private Scanner scanner;
	
	private Formatter formatter;
	
	private int corTexto = COR_BRANCA;
	private int corTextoFundo = COR_PRETA;
	
	public GenericoConsole( Cultura cultura ) {
		
		super( cultura );

		console = System.console();
		scanner = new Scanner( System.in );
		formatter = new Formatter( System.out );
		
	}

	public int getTotalColunas(){
		return 80;
	}
	
	public int getTotalLinhas(){
		return 50;
	}
	
	public void setCorTexto( int cor ){
		this.corTexto = cor;
	}
	
	public int getCorTexto() {
		return corTexto;
	}
	
	public void setCorTextoFundo( int cor ){
		this.corTextoFundo = cor;
	}
	
	public int getCorTextoFundo() {
		return corTextoFundo;
	}
	
	public void limpar(){
	}
	
	public char esperar( boolean mostrar ) throws IOException {
		
		if( console != null ){
			
			if( mostrar ){
				String s;
				do s = console.readLine(); while( s != null && s.length() == 0 );
				if( s != null ) return s.charAt( 0 );
			}else{
				char[] s;
				do s = console.readPassword(); while( s != null && s.length == 0 );
				if( s != null ) return s[ 0 ];
			}
			
		}
			
		String s;
		do s = scanner.nextLine(); while( s != null && s.length() == 0 );
		if( s != null ) return s.charAt( 0 );
		
		throw new IOException( "Recurso indispon�vel" );
		
	}
	
	public void setTelaCheia( boolean cheia ){
	}
	
	public String receber() throws IOException {
		
		if( console != null ){
			String s = console.readLine();
			if( s != null ) return s;
		}
			
		String s = scanner.nextLine();
		if( s != null ) return s;
		
		throw new IOException( "Recurso indispon�vel" );
		
	}
	
	public void enviar( String texto ) {
		System.out.print( texto );
	}
	
	public void enviarln( String texto ) {
		System.out.println( texto );
	}
	
	public void enviarln() {
		System.out.println();
	}
	
	public void enviar( String formato, Object... args ) {
		formatter.format( formato, args );
	}

}
