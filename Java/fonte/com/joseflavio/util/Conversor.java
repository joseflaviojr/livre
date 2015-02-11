
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

import java.util.HashMap;
import java.util.Map;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.Combinacao;

/**
 * Gera um mapa de conversão do tipo ATRIBUTO_A -> ATRIBUTO_B e ATRIBUTO_B -> ATRIBUTO_A.<br>
 * Este mapa é construído através de uma lista de objetos do tipo MAPA, os quais contém um ATRIBUTO_A e um ATRIBUTO_B.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see Combinacao
 */
public class Conversor<MAPA, ATRIBUTO_A, ATRIBUTO_B> {

	private Map<ATRIBUTO_A, Ida> ida;
	private Map<ATRIBUTO_B, Volta> volta;
	
	/**
	 * @param lista Lista com objetos que possuem pelo menos dois atributos: um do tipo ATRIBUTO_A e um do tipo ATRIBUTO_B.
	 * @param original Nome do atributo da classe MAPA que possui o valor original.
	 * @param convertido Nome do atributo da classe MAPA que possui o valor convertido.
	 */
	@SuppressWarnings("unchecked")
	public Conversor( MAPA[] lista, String original, String convertido ) {
		
		if( lista == null || lista.length == 0 ) throw new IllegalArgumentException( "Lista vazia." );
		
		this.ida = new HashMap<ATRIBUTO_A, Ida>( lista.length );
		this.volta = new HashMap<ATRIBUTO_B, Volta>( lista.length );
		
		try{
			
			AssistenteDeAtributo aaOriginal = new AssistenteDeAtributo( lista[0].getClass(), original );
			AssistenteDeAtributo aaConvertido = new AssistenteDeAtributo( lista[0].getClass(), convertido );
			
			ATRIBUTO_A a;
			ATRIBUTO_B b;
			
			for( MAPA m : lista ){
				a = (ATRIBUTO_A) aaOriginal.getMetodoGet().invoke( m );
				b = (ATRIBUTO_B) aaConvertido.getMetodoGet().invoke( m );
				this.ida.put( a, new Ida( m, b ) );
				this.volta.put( b, new Volta( m, a ) );
			}
			
		}catch( Exception e ){
			throw new IllegalArgumentException( e );
		}
		
	}
	
	public ATRIBUTO_B converter( ATRIBUTO_A original ) {
		
		return ida.get( original ).atributoB;
		
	}
	
	public ATRIBUTO_A reverter( ATRIBUTO_B convertido ) {
		
		return volta.get( convertido ).atributoA;
		
	}
	
	public MAPA obterMapaPeloOriginal( ATRIBUTO_A original ) {
		
		return ida.get( original ).mapa;
		
	}
	
	public MAPA obterMapaPeloConvertido( ATRIBUTO_B convertido ) {
		
		return volta.get( convertido ).mapa;
		
	}
	
	private class Ida {
		
		private MAPA mapa;
		
		private ATRIBUTO_B atributoB;

		private Ida( MAPA mapa, ATRIBUTO_B atributoB ) {
			this.mapa = mapa;
			this.atributoB = atributoB;
		}

	}
	
	private class Volta {
		
		private MAPA mapa;
		
		private ATRIBUTO_A atributoA;

		private Volta( MAPA mapa, ATRIBUTO_A atributoA ) {
			this.mapa = mapa;
			this.atributoA = atributoA;
		}
		
	}
	
}