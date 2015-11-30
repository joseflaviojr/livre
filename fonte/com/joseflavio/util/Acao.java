package com.joseflavio.util;

/**
 * Ação sobre objeto.
 * @author José Flávio de Souza Dias Júnior
 * @version 2015
 */
public interface Acao<T> {
	
	/**
	 * @param o Objeto sobre o qual a ação será realizada.
	 */
	void acao( T o ) throws Exception;

}
