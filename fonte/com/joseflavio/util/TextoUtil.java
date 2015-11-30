
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2014
 */
public class TextoUtil {

	public static String limitarComprimento( String texto, int comprimentoMaximo, boolean usarReticencias ) {
		
		if( texto == null ) return "";
		
		if( comprimentoMaximo <= 0 ) return texto;
		
		int len = texto.length();
		
		if( len > comprimentoMaximo ){
			
			if( usarReticencias && comprimentoMaximo >= 4 ) {
				return texto.substring( 0, comprimentoMaximo - 3 ) + "...";
			}else{
				return texto.substring( 0, comprimentoMaximo );
			}
			
		}
		
		return texto;
		
	}
	
	public static String fixarComprimento( String texto, int comprimento, boolean usarReticencias ) {

		if( texto == null ) return "";
		
		int len = texto.length();
		
		if( len < comprimento ){
			
			StringBuilder sb = new StringBuilder( comprimento );
			sb.append( texto );

			len = comprimento - len;
			
			for( int i = 0; i < len; i++ ){
				sb.append( ' ' );
			}
			
			return sb.toString(); 
			
		}
		
		return limitarComprimento( texto, comprimento, usarReticencias );
		
	}
	
	/**
	 * {@link Object#toString()} ou ""
	 */
	public static String toString( Object objeto ) {
		return toString( objeto, false );
	}
	
	/**
	 * {@link Object#toString()}
	 */
	public static String toString( Object objeto, boolean retornarNulo ) {
		return objeto != null ? objeto.toString() : retornarNulo ? null : "";
	}
	
	/**
	 * {@link Object#toString()} ou ""
	 */
	public static List<String> toString( List<?> objetos ) {
		return toString( objetos, false );
	}
	
	/**
	 * {@link Object#toString()} ou ""
	 */
	public static String[] toString( Object[] objetos ) {
		return toString( objetos, false );
	}
	
	/**
	 * {@link Object#toString()}
	 */
	public static List<String> toString( List<?> objetos, boolean retornarNulo ) {
		if( objetos == null ) return retornarNulo ? null : new ArrayList<String>();
		List<String> lista = new ArrayList<String>( objetos.size() );
		for( Object o : objetos ) lista.add( o != null ? o.toString() : retornarNulo ? null : "" );
		return lista;
	}
	
	/**
	 * {@link Object#toString()}
	 */
	public static String[] toString( Object[] objetos, boolean retornarNulo ) {
		if( objetos == null ) return retornarNulo ? null : new String[0];
		int total = objetos.length;
		String[] lista = new String[ total ];
		for( int i = 0; i < total; i++ ){
			Object o = objetos[i];
			lista[i] = o != null ? o.toString() : retornarNulo ? null : "";	
		}
		return lista;
	}
	
}
