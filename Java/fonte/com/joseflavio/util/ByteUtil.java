
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

package com.joseflavio.util;


/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2014
 */
public class ByteUtil {

	private static final char[] HEXDIGITO = "0123456789ABCDEF".toCharArray();
	
	/**
	 * @return Valor hexadecimal no formato {@link String} com quantidade par de d�gitos.
	 * @see #converterHexadecimalParaBytes(String)
	 */
	public static String converterBytesParaHexadecimal( byte[] bytes ) {
		return converterBytesParaHexadecimal( bytes, 0, bytes.length );
	}

	/**
	 * @return Valor hexadecimal no formato {@link String} com quantidade par de d�gitos.
	 * @see #converterHexadecimalParaBytes(String)
	 */
	public static String converterBytesParaHexadecimal( byte[] bytes, int inicio, int total ) {

		StringBuilder hexadecimal = new StringBuilder( total * 2 );
		
		for( int i = inicio; total > 0; i++, total-- ){
			byte b = bytes[i];
			hexadecimal.append( HEXDIGITO[ ( b >> 4 ) & 0xF ] );
			hexadecimal.append( HEXDIGITO[ b & 0xF ] );
		}
		
		return hexadecimal.toString();
		
	}

	/**
	 * @param hexadecimal Valor hexadecimal com quantidade par de d�gitos.
	 * @return Bytes correspondentes ao valor hexadecimal.
	 * @see #converterBytesParaHexadecimal(byte[])
	 */
	public static byte[] converterHexadecimalParaBytes( String hexadecimal ) {
		
		hexadecimal = hexadecimal.toUpperCase();
		
		int total = hexadecimal.length();
		if( total % 2 != 0 ) throw new IllegalArgumentException(); 

		byte[] bytes = new byte[ total / 2 ];

		for( int i = 0; i < total; i += 2 ){
			
			char c1 = hexadecimal.charAt( i );
			char c2 = hexadecimal.charAt( i + 1 );
			
			int b1 = '0' <= c1 && c1 <= '9' ? c1 - '0' : 'A' <= c1 && c1 <= 'F' ? c1 - 'A' + 10 : -1;
			int b2 = '0' <= c2 && c2 <= '9' ? c2 - '0' : 'A' <= c2 && c2 <= 'F' ? c2 - 'A' + 10 : -1;
			
			if( b1 == -1 || b2 == -1 ) throw new IllegalArgumentException();
			
			bytes[ i / 2 ] = (byte) ( b1 * 16 + b2 );
			
		}

		return bytes;
	}

}
