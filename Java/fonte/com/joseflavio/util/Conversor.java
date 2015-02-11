
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

import java.util.HashMap;
import java.util.Map;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.Combinacao;

/**
 * Gera um mapa de convers�o do tipo ATRIBUTO_A -> ATRIBUTO_B e ATRIBUTO_B -> ATRIBUTO_A.<br>
 * Este mapa � constru�do atrav�s de uma lista de objetos do tipo MAPA, os quais cont�m um ATRIBUTO_A e um ATRIBUTO_B.
 * @author Jos� Fl�vio de Souza Dias J�nior
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