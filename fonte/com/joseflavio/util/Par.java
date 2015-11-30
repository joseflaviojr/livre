package com.joseflavio.util;

/**
 * Par de objetos.
 * @author José Flávio de Souza Dias Júnior
 * @version 2015
 */
public class Par<A,B> {

	private A objeto1;
	
	private B objeto2;

	public Par( A objeto1, B objeto2 ) {
		this.objeto1 = objeto1;
		this.objeto2 = objeto2;
	}

	public A getObjeto1() {
		return objeto1;
	}

	public void setObjeto1( A objeto1 ) {
		this.objeto1 = objeto1;
	}

	public B getObjeto2() {
		return objeto2;
	}

	public void setObjeto2( B objeto2 ) {
		this.objeto2 = objeto2;
	}
	
}
