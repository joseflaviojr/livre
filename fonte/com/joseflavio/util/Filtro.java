package com.joseflavio.util;

/**
 * Aceita��o ou rejei��o de objetos.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2015
 */
public interface Filtro<T> {
	
	/**
	 * @param o Objeto a ser aceito ou rejeitado.
	 * @return objeto aceito?
	 */
	boolean aceitar( T o ) throws Exception;

}
