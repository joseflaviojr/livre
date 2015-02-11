
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

package com.joseflavio.modelo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.joseflavio.util.ClassUtil;

/**
 * Ferramenta para manipular os recursos inerentes a um {@link Field}: anota��es, m�todos get/is e set (Padr�o JavaBeans).
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * @throws IllegalArgumentException caso o {@link Field campo} n�o seja encontrado. 
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
			throw new IllegalArgumentException( "M�todo GET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "M�todo GET para " + classe.getName() + "." + atributo + " desconhecido." );
		}
		
	}
	
	/**
	 * Retorna o {@link Method m�todo} GET de um {@link Field atributo}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 * @throws IllegalArgumentException caso o {@link Method m�todo} n�o seja encontrado. 
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

		throw new IllegalArgumentException( "M�todo GET para " + classe.getName() + "." + atributo + " desconhecido." );
		
	}
	
	public static Object invocarGet( Object objeto, String atributo ) {
		
		Class<? extends Object> classe = objeto.getClass();
		
		try{
			
			return getMetodoGet( classe, atributo ).invoke( objeto );
			
		}catch( IllegalAccessException e ){
			throw new IllegalArgumentException( "M�todo GET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "M�todo GET para " + classe.getName() + "." + atributo + " desconhecido." );
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
			throw new IllegalArgumentException( "M�todo SET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "M�todo SET para " + classe.getName() + "." + atributo + " desconhecido." );
		}
		
	}
	
	/**
	 * Retorna o {@link Method m�todo} SET de um {@link Field atributo}.<br>
	 * Considera-se toda a hierarquia da {@link Class classe}.
	 * @throws IllegalArgumentException caso o {@link Method m�todo} n�o seja encontrado. 
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

		throw new IllegalArgumentException( "M�todo SET para " + classe.getName() + "." + atributo + " desconhecido." );
		
	}
	
	public static Object invocarSet( Object objeto, String atributo, Object valor ) {
		
		Class<? extends Object> classe = objeto.getClass();
		
		try{
			
			return getMetodoSet( classe, atributo ).invoke( objeto, valor );
			
		}catch( IllegalAccessException e ){
			throw new IllegalArgumentException( "M�todo SET para " + classe.getName() + "." + atributo + " desconhecido." ); 
		}catch( InvocationTargetException e ){
			throw new IllegalArgumentException( "M�todo SET para " + classe.getName() + "." + atributo + " desconhecido." );
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
