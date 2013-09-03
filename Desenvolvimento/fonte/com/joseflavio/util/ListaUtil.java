
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

package com.joseflavio.util;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ListaUtil {
	
	/**
	 * Comportamento de {@link List#toArray()}, porém retornando com o mesmo tipo.
	 * @return <code>null</code> caso a lista seja nula ou esteja vazia.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] array( List<T> lista ) {
		if( lista == null || lista.size() == 0 ) return null;
		return lista.toArray( (T[]) Array.newInstance( lista.get( 0 ).getClass(), lista.size() ) );
	}
	
	/**
	 * Retorna o índice do alvo no array.
	 * @param alvo Objeto-alvo.
	 * @return -1 caso o objeto não seja encontrado.
	 */
	public static <T> int indiceDe( T[] lista, T alvo ){
		
		int len = lista.length;
		
		for( int i = 0; i < len; i++ ){
			if( lista[i] == alvo || ( lista[i] != null && alvo != null && lista[i].equals( alvo ) ) ){
				return i;
			}
		}
		
		return -1;
		
	}
	
	/**
	 * Remove o último elemento caso ele exista.
	 * @return elemento removido, ou <code>null</code> caso a lista esteja vazia.
	 */
	public static <T> T menosUltimo( List<T> lista ){
		
		int total = lista.size();
		
		return total > 0 ? lista.remove( total - 1 ) : null;
		
	}
	
	/**
	 * Retorna o último elemento caso ele exista.
	 * @return <code>null</code> caso a lista esteja vazia.
	 */
	public static <T> T getUltimo( List<T> lista ){
		
		int total = lista.size();
		
		return total > 0 ? lista.get( total - 1 ) : null;
		
	}
	
	public static Boolean[] converterParaInvolucro( boolean[] array ){
		
		int len = array.length;
		
		Boolean[] ret = new Boolean[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Boolean( array[i] );
		}
		
		return ret;
		
	}
	
	public static Character[] converterParaInvolucro( char[] array ){
		
		int len = array.length;
		
		Character[] ret = new Character[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Character( array[i] );
		}
		
		return ret;
		
	}
	
	public static Byte[] converterParaInvolucro( byte[] array ){
		
		int len = array.length;
		
		Byte[] ret = new Byte[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Byte( array[i] );
		}
		
		return ret;
		
	}
	
	public static Short[] converterParaInvolucro( short[] array ){
		
		int len = array.length;
		
		Short[] ret = new Short[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Short( array[i] );
		}
		
		return ret;
		
	}
	
	public static Integer[] converterParaInvolucro( int[] array ){
		
		int len = array.length;
		
		Integer[] ret = new Integer[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Integer( array[i] );
		}
		
		return ret;
		
	}
	
	public static Long[] converterParaInvolucro( long[] array ){
		
		int len = array.length;
		
		Long[] ret = new Long[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Long( array[i] );
		}
		
		return ret;
		
	}
	
	public static Float[] converterParaInvolucro( float[] array ){
		
		int len = array.length;
		
		Float[] ret = new Float[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Float( array[i] );
		}
		
		return ret;
		
	}
	
	public static Double[] converterParaInvolucro( double[] array ){
		
		int len = array.length;
		
		Double[] ret = new Double[ len ];
		
		for( int i = 0; i < len; i++ ){
			ret[i] = new Double( array[i] );
		}
		
		return ret;
		
	}

}
