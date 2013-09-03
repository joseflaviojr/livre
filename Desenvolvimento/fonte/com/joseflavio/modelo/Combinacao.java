
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

package com.joseflavio.modelo;

import com.joseflavio.util.Conversor;

/**
 * Combinação de dois objetos.<br>
 * Para fins de ordenação, {@link #getObjeto1()} < {@link #getObjeto2()}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see Conversor
 */
public class Combinacao <A,B> {
	
	/**
	 * true, "Verdadeiro", false, "Falso"
	 */
	public static final Combinacao<Boolean,String>[] LOGICA = lista( true, new Object[]{ true, "Verdadeiro", false, "Falso" } );
	
	/**
	 * true, "Sim", false, "Não"
	 */
	public static Combinacao<Boolean,String>[] LOGICA_SIM = lista( true, new Object[]{ true, "Sim", false, "Não" } );
	
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
	 * Na ausência de {@link String}, prioriza-se o {@link StringBuffer}.<br>
	 * Na ausência de {@link String} e {@link StringBuffer}, retornar-se-á {@link #getObjeto1()}.{@link Object#toString() toString()}.
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
	 * Retorna {@link #getObjeto1()} se o parâmetro for igual a {@link #getObjeto2()}, ou vice-versa.
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
	 * Cria uma lista de {@link Combinacao combinações}.
	 * @param objetos Pares sequenciais: comb1 = obj1,obj2 / comb2 = obj3,obj4 / comb[N] = obj[X],obj[X+1]
	 */
	public static <A,B> Combinacao<A,B>[] lista( Object... objetos ) {
		return lista( false, objetos );
	}
	
	/**
	 * Busca por {@link Combinacao} com específico {@link #getObjeto1()}.
	 * @return <code>null</code> caso não seja encontrado.
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
	 * Busca por {@link Combinacao} com específico {@link #getObjeto2()}.
	 * @return <code>null</code> caso não seja encontrado.
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
