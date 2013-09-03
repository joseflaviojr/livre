
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

package com.joseflavio.modelo;

import com.joseflavio.util.Conversor;

/**
 * Combina��o de dois objetos.<br>
 * Para fins de ordena��o, {@link #getObjeto1()} < {@link #getObjeto2()}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see Conversor
 */
public class Combinacao <A,B> {
	
	/**
	 * true, "Verdadeiro", false, "Falso"
	 */
	public static final Combinacao<Boolean,String>[] LOGICA = lista( true, new Object[]{ true, "Verdadeiro", false, "Falso" } );
	
	/**
	 * true, "Sim", false, "N�o"
	 */
	public static Combinacao<Boolean,String>[] LOGICA_SIM = lista( true, new Object[]{ true, "Sim", false, "N�o" } );
	
	/**
	 * true, "Positivo", false, "Negativo"
	 */
	public static Combinacao<Boolean,String>[] LOGICA_POSITIVO = lista( true, new Object[]{ true, "Positivo", false, "Negativo" } );
	
	private A objeto1;
	
	private B objeto2;
	
	private boolean estatica;
	
	private Combinacao( A objeto1, B objeto2, boolean estatica ) {
		this.objeto1 = objeto1;
		this.objeto2 = objeto2;
		this.estatica = estatica;
	}

	public Combinacao( A objeto1, B objeto2 ) {
		this( objeto1, objeto2, false );
	}

	/**
	 * Prioriza-se entre os objetos o do tipo {@link String}.<br>
	 * Na aus�ncia de {@link String}, prioriza-se o {@link StringBuffer}.<br>
	 * Na aus�ncia de {@link String} e {@link StringBuffer}, retornar-se-� {@link #getObjeto1()}.{@link Object#toString() toString()}.
	 */
	@Override
	public String toString() {
		if( objeto1 instanceof String ) return (String) objeto1;
		if( objeto2 instanceof String ) return (String) objeto2;
		if( objeto1 instanceof StringBuffer ) return objeto1.toString();
		if( objeto2 instanceof StringBuffer ) return objeto2.toString();
		return objeto1.toString();
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null ) return false;
		if( ! ( obj instanceof Combinacao ) ) return false;
		Combinacao<?,?> comb = (Combinacao<?,?>) obj;
		if( objeto1 == comb.objeto1 && objeto2 == comb.objeto2 ) return true;
		if( objeto1 != null && comb.objeto1 != null && ! objeto1.equals( comb.objeto1 ) ) return false;
		if( objeto1 != comb.objeto1 ) return false;
		if( objeto2 != null && comb.objeto2 != null && ! objeto2.equals( comb.objeto2 ) ) return false;
		if( objeto2 != comb.objeto2 ) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return ( objeto1 != null ? objeto1.hashCode() : 0 ) + ( objeto2 != null ? objeto2.hashCode() : 0 );
	}

	public A getObjeto1() {
		return objeto1;
	}
	
	public void setObjeto1( A objeto1 ) {
		if( estatica ) throw new IllegalStateException();
		this.objeto1 = objeto1;
	}
	
	public B getObjeto2() {
		return objeto2;
	}
	
	public void setObjeto2( B objeto2 ) {
		if( estatica ) throw new IllegalStateException();
		this.objeto2 = objeto2;
	}

	/**
	 * Retorna {@link #getObjeto1()} se o par�metro for igual a {@link #getObjeto2()}, ou vice-versa.
	 */
	public Object par( Object objeto ) {
		if( objeto == objeto1 ) return objeto2;
		if( objeto == objeto2 ) return objeto1;
		if( objeto != null ){
			if( objeto1 != null && objeto.equals( objeto1 ) ) return objeto2;
			if( objeto2 != null && objeto.equals( objeto2 ) ) return objeto1;
		}
		throw new IllegalArgumentException();
	}
	
	@SuppressWarnings("unchecked")
	private static <A,B> Combinacao<A,B>[] lista( boolean estatica, Object[] objetos ) {
		
		if( objetos == null ) throw new IllegalArgumentException();
		
		int total = objetos.length;
		if( total == 0 || ( total % 2 ) != 0 ) throw new IllegalArgumentException();
		total /= 2;
		
		Combinacao<A,B>[] lista = new Combinacao[ total ];
		
		for( int i = 0, o = 0; total > 0; i++, o += 2, total-- ){
			Object obj1 = objetos[o];
			Object obj2 = objetos[o+1];
			lista[i] = new Combinacao<A,B>( (A) obj1, (B) obj2, estatica );
		}
		
		return lista;
		
	}
	
	/**
	 * Cria uma lista de {@link Combinacao combina��es}.
	 * @param objetos Pares sequenciais: comb1 = obj1,obj2 / comb2 = obj3,obj4 / comb[N] = obj[X],obj[X+1]
	 */
	public static <A,B> Combinacao<A,B>[] lista( Object... objetos ) {
		return lista( false, objetos );
	}
	
	/**
	 * Busca por {@link Combinacao} com espec�fico {@link #getObjeto1()}.
	 * @return <code>null</code> caso n�o seja encontrado.
	 */
	public static <A> Combinacao<A,?> buscar1( Combinacao<A,?>[] combinacoes, A alvo ){
		for( Combinacao<A,?> comb : combinacoes ){
			A obj = comb.getObjeto1();
			if( obj == alvo ) return comb;
			if( obj != null && alvo != null && obj.equals( alvo ) ) return comb;
		}
		return null;
	}
	
	/**
	 * Busca por {@link Combinacao} com espec�fico {@link #getObjeto2()}.
	 * @return <code>null</code> caso n�o seja encontrado.
	 */
	public static <B> Combinacao<?,B> buscar2( Combinacao<?,B>[] combinacoes, B alvo ){
		for( Combinacao<?,B> comb : combinacoes ){
			B obj = comb.getObjeto2();
			if( obj == alvo ) return comb;
			if( obj != null && alvo != null && obj.equals( alvo ) ) return comb;
		}
		return null;
	}
	
	/**
	 * null, nulo, true, verdadeiro, false, falso
	 */
	public static Combinacao<Boolean,String>[] listaLogica( String nulo, String verdadeiro, String falso ) {
		return lista( null, nulo, true, verdadeiro, false, falso );
	}
	
	/**
	 * true, verdadeiro, false, falso
	 */
	public static Combinacao<Boolean,String>[] listaLogica( String verdadeiro, String falso ) {
		return lista( true, verdadeiro, false, falso );
	}
	
}
