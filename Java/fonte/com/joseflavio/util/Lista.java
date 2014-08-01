
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
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
import java.util.Collection;

/**
 * @author José Flávio de Souza Dias Júnior
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
	 * Remove o último elemento caso ele exista.
	 * @return elemento removido, ou <code>null</code> caso a lista esteja vazia.
	 */
	public T menosUltimo(){
		
		int total = this.size();
		
		return total > 0 ? this.remove( total - 1 ) : null;
		
	}
	
	/**
	 * Retorna o último elemento caso ele exista.
	 * @return <code>null</code> caso a lista esteja vazia.
	 */
	public T getUltimo(){
		
		int total = this.size();
		
		return total > 0 ? this.get( total - 1 ) : null;
		
	}

}
