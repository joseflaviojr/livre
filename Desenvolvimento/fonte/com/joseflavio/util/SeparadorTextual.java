
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

package com.joseflavio.util;

/**
 * Particionador de texto atrav�s de separadores pr�-fixados.<br>
 * Exemplo: "1,2,3:4ponto5" -> [ "," / "," / ":" / "ponto" ] = 1 2 3 4 5
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public final class SeparadorTextual {

	private String texto;
	
	private String[] sepStr;
	
	private char[] sepChar;
	
	private String[] partes;
	
	private int totalPartes = 0;
	
	private SeparadorTextual( String[] sepStr, char[] sepChar ) {
		
		this.sepStr = sepStr;
		this.sepChar = sepChar;
		this.partes = new String[ ( sepStr != null ? sepStr.length : sepChar.length ) + 1 ];
		
	}
	
	public SeparadorTextual( String[] separadores ) {
		this( separadores, null );
	}
	
	public SeparadorTextual( char[] separadores ) {
		this( null, separadores );
	}
	
	public SeparadorTextual( String separador ) {
		this( new String[]{ separador } );
	}
	
	public SeparadorTextual( char separador ) {
		this( new char[]{ separador } );
	}
	
	public void executar( String texto ) {
		
		this.texto = texto;
		
		for( int i = 0; i < totalPartes; i++ ) partes[i] = null;
		
		if( sepStr != null ){
			executarSepStr();
		}else{
			executarSepChar();
		}
		
	}
	
	private void executarSepStr() {
		
		int letras = texto.length();
		int pos = 0, posAux;
		
		String separador;
		int totalSep = sepStr.length;
		int sep = 0;
		
		int parte = 0;
		
		while( pos < letras ){
			
			if( sep == totalSep ){
				partes[ parte++ ] = texto.substring( pos, letras );
				break;
			}
			
			separador = sepStr[sep++];
			
			posAux = texto.indexOf( separador, pos );
			if( posAux == -1 ) posAux = letras;
			partes[ parte++ ] = texto.substring( pos, posAux );
			
			pos = posAux + separador.length();
			
		}
		
		totalPartes = parte;
		
	}
	
	private void executarSepChar() {
		
		int letras = texto.length();
		int pos = 0, posAux;
		
		char separador;
		int totalSep = sepChar.length;
		int sep = 0;
		
		int parte = 0;
		
		while( pos < letras ){
			
			if( sep == totalSep ){
				partes[ parte++ ] = texto.substring( pos, letras );
				break;
			}
			
			separador = sepChar[sep++];
			
			posAux = texto.indexOf( separador, pos );
			if( posAux == -1 ) posAux = letras;
			partes[ parte++ ] = texto.substring( pos, posAux );
			
			pos = posAux + 1;
			
		}
		
		totalPartes = parte;
		
	}
	
	public String getTexto() {
		return texto;
	}
	
	public int getTotalPartes() {
		return totalPartes;
	}
	
	public String getParte( int indice ) {
		
		if( indice >= totalPartes ) throw new IndexOutOfBoundsException( "" + indice );
		
		return partes[ indice ];
		
	}
	
}
