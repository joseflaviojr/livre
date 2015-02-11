
/*
 *  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ++
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2014
 */
public class Contador {

	private long conta = 0;
	
	private ReentrantLock trava;
	
	/**
	 * Inicia em 0 e sem sincroniza��o.
	 */
	public Contador() {
		this( 0, false );
	}
	
	/**
	 * Cria um {@link Contador} sem sincroniza��o e com um valor inicial.
	 */
	public Contador( long contaInicial ) {
		this( contaInicial, false );
	}
	
	/**
	 * @param contaInicial Valor inicial do {@link Contador}.
	 * @param sincronizado {@link ReentrantLock}?
	 */
	public Contador( long contaInicial, boolean sincronizado ) {
		this.conta = contaInicial;
		this.trava = sincronizado ? new ReentrantLock() : null;
	}
	
	/**
	 * @return {@link #getConta()}
	 */
	public long incrementar() {
		if( trava != null ){
			trava.lock();
			try{
				return ++conta;	
			}finally{
				trava.unlock();
			}
		}else{
			return ++conta;
		}
	}
	
	/**
	 * @return {@link #getConta()}
	 */
	public long decrementar() {
		if( trava != null ){
			trava.lock();
			try{
				return --conta;	
			}finally{
				trava.unlock();
			}
		}else{
			return --conta;
		}
	}
	
	public long getConta() {
		return conta;
	}
	
	public void setConta( long conta ) {
		if( trava != null ){
			trava.lock();
			try{
				this.conta = conta;	
			}finally{
				trava.unlock();
			}
		}else{
			this.conta = conta;
		}
	}
	
}
