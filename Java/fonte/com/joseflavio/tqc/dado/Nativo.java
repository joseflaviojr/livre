
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

package com.joseflavio.tqc.dado;

import java.util.HashMap;
import java.util.Map;

import com.joseflavio.tqc.Dado;

/**
 * {@link Dado} que possui conteúdo nativo, isto é, implementado especificamente para a plataforma operacional.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Nativo extends Dado {
	
	private Map<Object, Object> propriedades;

	public Nativo() {
	}
	
	public Nativo( Map<Object, Object> propriedades ) {
		this.propriedades = propriedades;
	}
	
	/**
	 * @param propriedades Sequência de chaves e valores: CHAVE1, VALOR1, CHAVE2, VALOR2, ...
	 */
	public Nativo( Object... propriedades ) {
		
		int total = propriedades.length;
		this.propriedades = new HashMap<Object, Object>( total / 2 );
		
		for( int i = 0; i < total; i += 2 ){
			this.propriedades.put( propriedades[i], propriedades[i+1] );
		}
		
	}

	/**
	 * @see #getPropriedades()
	 */
	public Object getConteudo() {
		return propriedades;
	}
	
	public Map<Object, Object> getPropriedades() {
		return propriedades;
	}
	
	public void setPropriedades( Map<Object, Object> propriedades ) {
		this.propriedades = propriedades;
	}
	
}
