
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Instanciador<T> {

	private Constructor<?> construtor;
	
	private Object[] parametros;
	
	public Instanciador( Class<? extends T> classe, Object... parametros ) {
		
		this.construtor = getConstrutor( classe, parametros );
		this.parametros = parametros;
		
	}
	
	public static Constructor<?> getConstrutor( Class<?> classe, Object... parametros ) throws IllegalArgumentException {
		
		int i;
		boolean ok;
		
		Class<?> a[] = new Class<?>[ parametros.length ];
		for( i = 0; i < a.length; i++ ){
			a[i] = parametros[i].getClass();
		}
		
		for( Constructor<?> c : classe.getDeclaredConstructors() ){
			
			Class<?> b[] = c.getParameterTypes();

			if( a.length != b.length ) continue;
			
			ok = true;
			for( i = 0; i < b.length; i++ ){
				if( ! ClassUtil.getInvolucro( b[i] ).isAssignableFrom( a[i] ) ){
					ok = false;
					break;
				}
			}
			
			if( ok ) return c;

		}
		
		throw new IllegalArgumentException();
			
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T instanciar( Class<? extends T> classe, Object... parametros ) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		
		return (T) getConstrutor( classe, parametros ).newInstance( parametros );
			
	}
	
	@SuppressWarnings("unchecked")
	public T instanciar() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		
		return (T) construtor.newInstance( parametros );
			
	}
	
}
