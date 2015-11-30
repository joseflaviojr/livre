package com.joseflavio.util;

/**
 * Aceitação ou rejeição de objetos.
 * @author José Flávio de Souza Dias Júnior
 * @version 2015
 */
public interface Filtro<T> {
	
	/**
	 * @param o Objeto a ser aceito ou rejeitado.
	 * @return objeto aceito?
	 */
	boolean aceitar( T o ) throws Exception;

}
