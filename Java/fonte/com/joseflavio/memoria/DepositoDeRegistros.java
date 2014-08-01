
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

package com.joseflavio.memoria;

import java.util.Enumeration;



/**
 * {@link DepositoDeBytes} implementado como uma lista de {@link Registro}s.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface DepositoDeRegistros extends DepositoDeBytes, AlteracaoExclusiva, SomenteLeitura {
	
	/**
	 * Versão de implementação do {@link DepositoDeRegistros}.<br>
	 * O {@link DepositoDeRegistros} deverá sempre ter retrocompatibilidade.<br>
	 * Versão inicial: 1.
	 */
	public int getVersao();
	
	/**
	 * Quantidade de {@link Registro}s {@link Registro#ESTADO_USADO usados}.
	 */
	@Override
	public long getTamanhoAtual();
	
	/**
	 * Enumera os {@link Registro}s {@link Registro#ESTADO_USADO usados}.<br>
	 * Atenção: os {@link Registro}s poderão repentinamente mudar de {@link Registro#getEstado() estado}. Veja {@link #setSomenteLeitura(boolean)}.<br>
	 * {@link Enumeration#nextElement()} retornará <code>null</code> caso o fim da {@link Enumeration} tenha sido alcançado.
	 */
	public Enumeration<Registro> enumerar() throws DepositoException;
	
	/**
	 * {@link Registro} armazenado neste {@link DepositoDeRegistros} e identificado pelo id.<br>
	 * Atenção: o {@link Registro} poderá repentinamente mudar de {@link Registro#getEstado() estado}. Veja {@link #setAlteracaoExclusiva(boolean)}.
	 * @see #obter(long, byte[], int)
	 * @see DepositoException#RAZAO_INEXISTENCIA
	 */
	public Registro getRegistro( long id ) throws DepositoException;
	
	/**
	 * Indica se o {@link DepositoDeRegistros} está utilizando mecanismos de otimização de espaço físico. Padrão: false.
	 * @see #setOtimizarEspaco(boolean)
	 */
	public boolean isOtimizarEspaco();
	
	/**
	 * @see #isOtimizarEspaco()
	 */
	public void setOtimizarEspaco( boolean otimizar ) throws DepositoException;
	
	/**
	 * @see #setSobra(float)
	 */
	public float getSobra();
	
	/**
	 * Espaço livre inicial reservado para cada {@link Registro} poder crescer sem a necessidade de alocação de mais espaço.<br>
	 * Esta sobra é garantida apenas na primeira {@link #armazenar(byte[], int, int, long) armazenagem}.<br>
	 * Valor expresso em percentagem. Exemplo: 0.3f = 30% de sobra = ( {@link Registro#getTamanhoMaximo()} - {@link Registro#getTamanhoAtual()} ) / {@link Registro#getTamanhoAtual()}
	 */
	public void setSobra( float sobra );
	
}
