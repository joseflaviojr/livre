
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
import java.util.Collection;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Lista<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	public Lista() {
		super();
	}

	public Lista( Collection<T> c ) {
		super( c );
	}
	
	public Lista( T cabeca, T... cauda ) {
		super( 1 + cauda.length );
		add( cabeca );
		for( T t : cauda ) add( t );
	}
	
	public Lista( T cabeca, Collection<T> cauda ) {
		super( 1 + cauda.size() );
		add( cabeca );
		for( T t : cauda ) add( t );
	}
	
	public Lista( int capacidade ) {
		super( capacidade );
	}
	
	public Lista( T[] lista ) {
		super( lista != null ? lista.length : 10 );
		if( lista != null ) for( T t : lista ) add( t );
	}
	
	public Lista<T> mais( T obj ) {
		this.add( obj );
		return this;
	}
	
	public Lista<T> mais( int indice, T obj ) {
		this.add( indice, obj );
		return this;
	}
	
	/**
	 * Remove o �ltimo elemento caso ele exista.
	 * @return elemento removido, ou <code>null</code> caso a lista esteja vazia.
	 */
	public T menosUltimo(){
		
		int total = this.size();
		
		return total > 0 ? this.remove( total - 1 ) : null;
		
	}
	
	/**
	 * Retorna o �ltimo elemento caso ele exista.
	 * @return <code>null</code> caso a lista esteja vazia.
	 */
	public T getUltimo(){
		
		int total = this.size();
		
		return total > 0 ? this.get( total - 1 ) : null;
		
	}

}
