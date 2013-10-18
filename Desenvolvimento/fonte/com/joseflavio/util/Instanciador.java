
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

package com.joseflavio.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
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
