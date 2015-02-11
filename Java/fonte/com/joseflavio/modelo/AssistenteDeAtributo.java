
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

package com.joseflavio.modelo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.joseflavio.util.ClassUtil;

/**
 * Ferramenta para manipular os recursos inerentes a um {@link Field}: anotações, métodos get/is e set (Padrão JavaBeans).
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see ClassUtil
 */
public class AssistenteDeAtributo {
	
	private Class<? extends Object> classe;
	
	private String atributo;
	
	private Field campo;
	
	private Method metodoGet;
	
	private Method metodoSet;
	
	public AssistenteDeAtributo( Class<? extends Object> classe, String atributo ) {
		this.classe = classe;
		this.atributo = atributo;
	}
	
	public Class<? extends Object> getClasse() {
		return classe;
	}
	
	public String getAtributo() {
		return atributo;
	}

	/**
	 * Retorna um {@link Field campo} de uma {@link Class classe}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 * @throws IllegalArgumentException caso o {@link Field campo} não seja encontrado. 
	 */
	public static Field getCampo( Class<? extends Object> classe, String nome ) {
		
		Class<? extends Object> c = classe;
		Field f = null;
		
		while( c != null ){
			
			try{
				f = c.getDeclaredField( nome );
				if( f != null ) return f;
			}catch( Exception e ){
			}
			
			c = c.getSuperclass();
			
		}

		throw new IllegalArgumentException( "Atributo " + classe.getName() + "." + nome + " desconhecido." );
		
	}
	
	/**
	 * @see #getCampo(Class, String)
	 * @see #getClasse()
	 * @see #getAtributo()
	 */
	public Field getCampo() {
		if( campo == null ) campo = getCampo( classe, atributo );
		return campo;
	}
	
	/**
	 * Retorna todos os {@link Field campos} de uma {@link Class classe}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 */
	public static Field[] getCampos( Class<? extends Object> classe ) {
		
		Class<? extends Object> c = classe;
		List<Field> campos = new ArrayList<Field>();
		
		while( c != null ){
			for( Field f : c.getDeclaredFields() ) campos.add( f );
			c = c.getSuperclass();
		}
		
		return campos.toArray( new Field[ campos.size() ] );
		
	}
	
	/**
	 * @see #getMetodoGet(Class, String)
	 * @see #getClasse()
	 * @see #getAtributo()
	 */
	public Method getMetodoGet() {
		if( metodoGet == null ) metodoGet = getMetodoGet( classe, atributo );
		return metodoGet;
	}
	
	public Object invocarGet( Object objeto ) {
		
		try{
			
			return getMetodoGet().invoke( objeto );
			
		}catch( IllegalAccessException e ){
			throw new IllegalArgumentException( "Método GET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "Método GET para " + classe.getName() + "." + atributo + " desconhecido." );
		}
		
	}
	
	/**
	 * Retorna o {@link Method método} GET de um {@link Field atributo}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 * @throws IllegalArgumentException caso o {@link Method método} não seja encontrado. 
	 */
	public static Method getMetodoGet( Class<? extends Object> classe, String atributo ) {

		Class<?> tipo = getCampo( classe, atributo ).getType();
		String prefixo = tipo == boolean.class || tipo == Boolean.class ? "is" : "get";
		String metodoNome = prefixo + Character.toUpperCase( atributo.charAt( 0 ) ) + atributo.substring( 1 );
		
		Class<? extends Object> c = classe;
		Method m = null;
		
		while( c != null ){
			
			try{
				m = c.getDeclaredMethod( metodoNome );
				if( m != null ) return m;
			}catch( Exception e ){
			}
			
			c = c.getSuperclass();
			
		}

		throw new IllegalArgumentException( "Método GET para " + classe.getName() + "." + atributo + " desconhecido." );
		
	}
	
	public static Object invocarGet( Object objeto, String atributo ) {
		
		Class<? extends Object> classe = objeto.getClass();
		
		try{
			
			return getMetodoGet( classe, atributo ).invoke( objeto );
			
		}catch( IllegalAccessException e ){
			throw new IllegalArgumentException( "Método GET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "Método GET para " + classe.getName() + "." + atributo + " desconhecido." );
		}
		
	}
	
	/**
	 * @see #getMetodoSet(Class, String)
	 * @see #getClasse()
	 * @see #getAtributo()
	 */
	public Method getMetodoSet() {
		if( metodoSet == null ) metodoSet = getMetodoSet( classe, atributo );
		return metodoSet;
	}
	
	public Object invocarSet( Object objeto, Object valor ) {
		
		try{
			
			return getMetodoSet().invoke( objeto, valor );
			
		}catch( IllegalAccessException e ){
			throw new IllegalArgumentException( "Método SET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "Método SET para " + classe.getName() + "." + atributo + " desconhecido." );
		}
		
	}
	
	/**
	 * Retorna o {@link Method método} SET de um {@link Field atributo}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 * @throws IllegalArgumentException caso o {@link Method método} não seja encontrado. 
	 */
	public static Method getMetodoSet( Class<? extends Object> classe, String atributo ) {

		Class<?> tipo = getCampo( classe, atributo ).getType();
		String metodoNome = "set" + Character.toUpperCase( atributo.charAt( 0 ) ) + atributo.substring( 1 );
		
		Class<? extends Object> c = classe;
		Method m = null;
		
		while( c != null ){
			
			try{
				m = c.getDeclaredMethod( metodoNome, tipo );
				if( m != null ) return m;
			}catch( Exception e ){
			}
			
			c = c.getSuperclass();
			
		}

		throw new IllegalArgumentException( "Método SET para " + classe.getName() + "." + atributo + " desconhecido." );
		
	}
	
	public static Object invocarSet( Object objeto, String atributo, Object valor ) {
		
		Class<? extends Object> classe = objeto.getClass();
		
		try{
			
			return getMetodoSet( classe, atributo ).invoke( objeto, valor );
			
		}catch( IllegalAccessException e ){
			throw new IllegalArgumentException( "Método SET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "Método SET para " + classe.getName() + "." + atributo + " desconhecido." );
		}
		
	}
	
	/**
	 * Captura uma {@link Annotation} de um {@link Field}.
	 */
	public static <T extends Annotation> T getAnotacao( Class<? extends Object> classe, String atributo, Class<T> tipo, boolean obrigatorio ) {
		T jf = getCampo( classe, atributo ).getAnnotation( tipo );
		if( obrigatorio && jf == null ) throw new IllegalArgumentException( tipo.getName() + " desconhecido." );
		return jf;
	}
	
	public static JFApresentacao getJFApresentacao( Class<? extends Object> classe, String atributo, boolean obrigatorio ) {
		Class<JFApresentacao> tipo = JFApresentacao.class;
		JFApresentacao jf = getCampo( classe, atributo ).getAnnotation( tipo );
		if( obrigatorio && jf == null ) throw new IllegalArgumentException( tipo.getName() + " desconhecido." );
		return jf;
	}
	
	public <T extends Annotation> T getAnotacao( Class<T> tipo, boolean obrigatorio ) {
		T jf = getCampo().getAnnotation( tipo );
		if( obrigatorio && jf == null ) throw new IllegalArgumentException( tipo.getName() + " desconhecido." );
		return jf;
	}
	
	public JFAcesso getJFAcesso( boolean obrigatorio ) {
		return getAnotacao( JFAcesso.class, obrigatorio );
	}
	
	public JFApresentacao getJFApresentacao( boolean obrigatorio ) {
		return getAnotacao( JFApresentacao.class, obrigatorio );
	}
	
	public JFData getJFData( boolean obrigatorio ) {
		return getAnotacao( JFData.class, obrigatorio );
	}
	
	public JFInteiro getJFInteiro( boolean obrigatorio ) {
		return getAnotacao( JFInteiro.class, obrigatorio );
	}
	
	public JFReal getJFReal( boolean obrigatorio ) {
		return getAnotacao( JFReal.class, obrigatorio );
	}
	
	public JFTexto getJFTexto( boolean obrigatorio ) {
		return getAnotacao( JFTexto.class, obrigatorio );
	}
	
	public JFValidacaoNaoNulo getJFValidacaoNaoNulo( boolean obrigatorio ) {
		return getAnotacao( JFValidacaoNaoNulo.class, obrigatorio );
	}
	
	public JFValidacaoNaoVazio getJFValidacaoNaoVazio( boolean obrigatorio ) {
		return getAnotacao( JFValidacaoNaoVazio.class, obrigatorio );
	}
	
	public JFValidacaoPrimitiva getJFValidacaoPrimitiva( boolean obrigatorio ) {
		return getAnotacao( JFValidacaoPrimitiva.class, obrigatorio );
	}
	
	public JFValidacaoTamanhoLimite getJFValidacaoTamanhoLimite( boolean obrigatorio ) {
		return getAnotacao( JFValidacaoTamanhoLimite.class, obrigatorio );
	}
	
	public JFValidacaoValorLimite getJFValidacaoValorLimite( boolean obrigatorio ) {
		return getAnotacao( JFValidacaoValorLimite.class, obrigatorio );
	}

}
