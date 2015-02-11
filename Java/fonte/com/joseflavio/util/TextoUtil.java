
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

package com.joseflavio.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Flávio de Souza Dias Júnior
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
