
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

	private long conta;
	
	private long limiteInferior;
	
	private long limiteSuperior;
	
	private boolean ciclico;
	
	private ReentrantLock trava;
	
	/**
	 * Inicia em 0, limitado a {@link Long#MIN_VALUE}..{@link Long#MAX_VALUE}, sem ciclicidade e sem sincroniza��o.
	 * @see Contador#Contador(long, long, long, boolean, boolean)
	 */
	public Contador() {
		this( 0, Long.MIN_VALUE, Long.MAX_VALUE, false, false );
	}
	
	/**
	 * Inicia em <code>contaInicial</code>, limitado a {@link Long#MIN_VALUE}..{@link Long#MAX_VALUE}, sem ciclicidade e sem sincroniza��o.
	 * @see Contador#Contador(long, long, long, boolean, boolean)
	 */
	public Contador( long contaInicial ) {
		this( contaInicial, Long.MIN_VALUE, Long.MAX_VALUE, false, false );
	}
	
	/**
	 * Limitado a {@link Long#MIN_VALUE}..{@link Long#MAX_VALUE} sem ciclicidade.
	 * @see Contador#Contador(long, long, long, boolean, boolean)
	 */
	public Contador( long contaInicial, boolean sincronizado ) {
		this( contaInicial, Long.MIN_VALUE, Long.MAX_VALUE, false, sincronizado );
	}
	
	/**
	 * Limitado a {@link Long#MIN_VALUE}..{@link Long#MAX_VALUE}.
	 * @see Contador#Contador(long, long, long, boolean, boolean)
	 */
	public Contador( long contaInicial, boolean ciclico, boolean sincronizado ) {
		this( contaInicial, Long.MIN_VALUE, Long.MAX_VALUE, ciclico, sincronizado );
	}
	
	/**
	 * @param contaInicial Valor inicial.
	 * @param limiteInferior Valor m�nimo.
	 * @param limiteSuperior Valor m�ximo.
	 * @param ciclico Fechar um ciclo de evolu��o entre o valor inferior e o superior.
	 * @param sincronizado {@link ReentrantLock}?
	 */
	public Contador( long contaInicial, long limiteInferior, long limiteSuperior, boolean ciclico, boolean sincronizado ) {
		if( limiteInferior > limiteSuperior ) throw new IllegalArgumentException( "Limite inferior maior que o superior." );
		if( contaInicial < limiteInferior || contaInicial > limiteSuperior ) throw new IllegalArgumentException( "Conta inicial fora do limite." );
		this.conta = contaInicial;
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
		this.ciclico = ciclico;
		this.trava = sincronizado ? new ReentrantLock() : null;
	}

	/**
	 * Incrementa a contagem, estagnando em {@link #getLimiteSuperior()}.
	 * @return {@link #getConta()} ap�s o incremento.
	 */
	public long incrementar() {
		if( trava != null ) trava.lock();
		try{
			if( conta == limiteSuperior ) return ciclico ? conta = limiteInferior : conta;
			return ++conta;	
		}finally{
			if( trava != null ) trava.unlock();
		}
	}
	
	/**
	 * Decrementa a contagem, estagnando em {@link #getLimiteInferior()}.
	 * @return {@link #getConta()} ap�s o decremento.
	 */
	public long decrementar() {
		if( trava != null ) trava.lock();
		try{
			if( conta == limiteInferior ) return ciclico ? conta = limiteSuperior : conta;
			return --conta;	
		}finally{
			if( trava != null ) trava.unlock();
		}
	}
	
	/**
	 * Valor atual de contagem.
	 */
	public long getConta() {
		return conta;
	}
	
	/**
	 * Altera o valor atual de contagem.<br>
	 * Deve-se evitar utilizar este m�todo, pois quebra a l�gica funcional do {@link Contador} (sequ�ncia num�rica).
	 */
	public void setConta( long conta ) {
		if( trava != null ) trava.lock();
		try{
			if( conta < limiteInferior || conta > limiteSuperior ) throw new IllegalArgumentException( "Conta fora do limite." );
			this.conta = conta;
		}finally{
			if( trava != null ) trava.unlock();
		}
	}
	
	public long getLimiteInferior() {
		return limiteInferior;
	}
	
	public long getLimiteSuperior() {
		return limiteSuperior;
	}
	
}
