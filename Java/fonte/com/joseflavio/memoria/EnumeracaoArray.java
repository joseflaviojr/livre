
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

package com.joseflavio.memoria;

import java.util.Iterator;

/**
 * {@link Enumeracao} de um array de {@link Objeto}s.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class EnumeracaoArray <T extends Objeto> implements Enumeracao<T> {

	private T[] array;
	
	private int pos;
	
	private int total;
	
	public EnumeracaoArray( T[] array ) {
		this.array = array;
		this.pos = 0;
		this.total = array.length;
	}
	
	public EnumeracaoArray( T[] array, int inicio, int total ) {
		this.array = array;
		this.pos = inicio;
		this.total = total;
	}
	
	/**
	 * {@link Enumeracao} vazia.
	 */
	public EnumeracaoArray() {
		this.array = null;
		this.pos = 0;
		this.total = 0;
	}
	
	@Override
	public boolean temProximo() {
		return total > 0;
	}
	
	@Override
	public T proximo() {
		if( total == 0 ) throw new IndexOutOfBoundsException();
		total--;
		return array[pos++];
	}
	
	@Override
	public long proximaIdentidade() {
		if( total == 0 ) throw new IndexOutOfBoundsException();
		total--;
		return array[pos++].getIdentidade();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new IteratorImpl();
	}
	
	private class IteratorImpl implements Iterator<T> {
		
		@Override
		public boolean hasNext() {
			return total > 0;
		}
		
		@Override
		public T next() {
			return proximo();
		}
		
		@Override
		public void remove() {
		}
		
	}
	
}
