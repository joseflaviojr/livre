package com.joseflavio.util;

/**
 * A��o sobre objeto.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2015
 */
public interface Acao<T> {
	
	/**
	 * @param o Objeto sobre o qual a a��o ser� realizada.
	 */
	void acao( T o ) throws Exception;

}
