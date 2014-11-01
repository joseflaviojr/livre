
/*
 *  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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

package com.joseflavio.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joseflavio.modelo.AssistenteDeAtributo;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2014
 * @see AssistenteDeAtributo
 */
public class ClassUtil {

	public static Class<?> getInvolucro( Class<?> primitiva ) {
		
		if( primitiva == null ) throw new IllegalArgumentException();
		
		if( ! primitiva.isPrimitive() ) return primitiva;
		
		if( primitiva == byte.class ) return Byte.class;
		if( primitiva == short.class ) return Short.class;
		if( primitiva == int.class ) return Integer.class;
		if( primitiva == long.class ) return Long.class;
		if( primitiva == float.class ) return Float.class;
		if( primitiva == double.class ) return Double.class;
		if( primitiva == char.class ) return Character.class;
		if( primitiva == boolean.class ) return Boolean.class;
		
		throw new IllegalArgumentException();
		
	}
	
	/**
	 * Retorna todos os {@link Method m�todos} de uma {@link Class classe}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 */
	public static List<Method> listarMetodos( Class<? extends Object> classe ) {
		
		Class<? extends Object> c = classe;
		List<Method> metodos = new ArrayList<Method>();
		
		while( c != null ){
			for( Method m : c.getDeclaredMethods() ) metodos.add( m );
			c = c.getSuperclass();
		}
		
		return metodos;
		
	}
	
	/**
	 * Classes at�micas: primitivos, inv�lucros dos primitivos, enumera��es, {@link String}, {@link Date} e {@link Class}.
	 */
	public static boolean isAtomica( Class<?> classe ) {

		if( classe == int.class || classe == Integer.class ) return true;
		if( classe == long.class || classe == Long.class ) return true;
		if( classe == byte.class || classe == Byte.class ) return true;
		if( classe == short.class || classe == Short.class ) return true;
		if( classe == float.class || classe == Float.class ) return true;
		if( classe == double.class || classe == Double.class ) return true;
		if( classe == boolean.class || classe == Boolean.class ) return true;
		if( classe == char.class || classe == Character.class ) return true;
		
		if( classe.isEnum() ) return true;
		
		if( classe == String.class ) return true;
		if( classe == Date.class ) return true;
		if( classe == Class.class ) return true;
		
		return false;
		
	}
	
}
