
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

package com.joseflavio.memoria;

import java.util.Enumeration;



/**
 * {@link DepositoDeBytes} implementado como uma lista de {@link Registro}s.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface DepositoDeRegistros extends DepositoDeBytes, AlteracaoExclusiva, SomenteLeitura {
	
	/**
	 * Vers�o de implementa��o do {@link DepositoDeRegistros}.<br>
	 * O {@link DepositoDeRegistros} dever� sempre ter retrocompatibilidade.<br>
	 * Vers�o inicial: 1.
	 */
	public int getVersao();
	
	/**
	 * Quantidade de {@link Registro}s {@link Registro#ESTADO_USADO usados}.
	 */
	@Override
	public long getTamanhoAtual();
	
	/**
	 * Enumera os {@link Registro}s {@link Registro#ESTADO_USADO usados}.<br>
	 * Aten��o: os {@link Registro}s poder�o repentinamente mudar de {@link Registro#getEstado() estado}. Veja {@link #setSomenteLeitura(boolean)}.<br>
	 * {@link Enumeration#nextElement()} retornar� <code>null</code> caso o fim da {@link Enumeration} tenha sido alcan�ado.
	 */
	public Enumeration<Registro> enumerar() throws DepositoException;
	
	/**
	 * {@link Registro} armazenado neste {@link DepositoDeRegistros} e identificado pelo id.<br>
	 * Aten��o: o {@link Registro} poder� repentinamente mudar de {@link Registro#getEstado() estado}. Veja {@link #setAlteracaoExclusiva(boolean)}.
	 * @see #obter(long, byte[], int)
	 * @see DepositoException#RAZAO_INEXISTENCIA
	 */
	public Registro getRegistro( long id ) throws DepositoException;
	
	/**
	 * Indica se o {@link DepositoDeRegistros} est� utilizando mecanismos de otimiza��o de espa�o f�sico. Padr�o: false.
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
	 * Espa�o livre inicial reservado para cada {@link Registro} poder crescer sem a necessidade de aloca��o de mais espa�o.<br>
	 * Esta sobra � garantida apenas na primeira {@link #armazenar(byte[], int, int, long) armazenagem}.<br>
	 * Valor expresso em percentagem. Exemplo: 0.3f = 30% de sobra = ( {@link Registro#getTamanhoMaximo()} - {@link Registro#getTamanhoAtual()} ) / {@link Registro#getTamanhoAtual()}
	 */
	public void setSobra( float sobra );
	
}
