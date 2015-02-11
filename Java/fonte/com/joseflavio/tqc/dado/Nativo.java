
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

package com.joseflavio.tqc.dado;

import java.util.HashMap;
import java.util.Map;

import com.joseflavio.tqc.Dado;

/**
 * {@link Dado} que possui conte�do nativo, isto �, implementado especificamente para a plataforma operacional.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * @param propriedades Sequ�ncia de chaves e valores: CHAVE1, VALOR1, CHAVE2, VALOR2, ...
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
