
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ++
 * @author José Flávio de Souza Dias Júnior
 * @version 2014
 */
public class Contador {

	private long conta = 0;
	
	private ReentrantLock trava;
	
	/**
	 * Inicia em 0 e sem sincronização.
	 */
	public Contador() {
		this( 0, false );
	}
	
	/**
	 * Cria um {@link Contador} sem sincronização e com um valor inicial.
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
