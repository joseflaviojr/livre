
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

	private long conta;
	
	private long limiteInferior;
	
	private long limiteSuperior;
	
	private boolean ciclico;
	
	private ReentrantLock trava;
	
	/**
	 * Inicia em 0, limitado a {@link Long#MIN_VALUE}..{@link Long#MAX_VALUE}, sem ciclicidade e sem sincronização.
	 * @see Contador#Contador(long, long, long, boolean, boolean)
	 */
	public Contador() {
		this( 0, Long.MIN_VALUE, Long.MAX_VALUE, false, false );
	}
	
	/**
	 * Inicia em <code>contaInicial</code>, limitado a {@link Long#MIN_VALUE}..{@link Long#MAX_VALUE}, sem ciclicidade e sem sincronização.
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
	 * @param limiteInferior Valor mínimo.
	 * @param limiteSuperior Valor máximo.
	 * @param ciclico Fechar um ciclo de evolução entre o valor inferior e o superior.
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
	 * @return {@link #getConta()} após o incremento.
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
	 * @return {@link #getConta()} após o decremento.
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
	 * Deve-se evitar utilizar este método, pois quebra a lógica funcional do {@link Contador} (sequência numérica).
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
