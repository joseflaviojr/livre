
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

import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

import com.joseflavio.cultura.Cultura;

/**
 * @author José Flávio de Souza Dias Júnior
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
		
		throw new IOException( "Recurso indisponível" );
		
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
		
		throw new IOException( "Recurso indisponível" );
		
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
