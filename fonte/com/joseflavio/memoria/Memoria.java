
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

package com.joseflavio.memoria;

import com.joseflavio.Tamanho;


/**
 * Mem�ria de bytes.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface Memoria extends Tamanho {

	/**
	 * Indica se a {@link Memoria} � modific�vel. Caso contr�rio, ser� de apenas leitura.
	 */
	public boolean isModificavel();
	
	/**
	 * Posi��o corrente para leitura e escrita.<br>
	 * Posi��o inicial: 0.
	 * @return 0 a {@link #getTamanhoAtual()}
	 */
	public long getPosicao() throws DepositoException;
	
	/**
	 * Determina a posi��o corrente para leitura e escrita.
	 * @param posicao Valor de 0 a {@link #getTamanhoAtual()}
	 */
	public void setPosicao( long posicao ) throws DepositoException;
	
	/**
	 * Determina o tamanho atual da {@link Memoria}, truncando se necess�rio.
	 */
	public void setTamanhoAtual( long tamanho ) throws DepositoException;
	
	/**
	 * Escreve o byte na {@link #getPosicao()}.<br>
	 * Avan�a devidamente a {@link #setPosicao(long) posi��o}.
	 */
	public void escrever( byte b ) throws DepositoException;
	
	/**
	 * Escreve a sequ�ncia de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avan�a devidamente a {@link #setPosicao(long) posi��o}.
	 */
	public void escrever( byte[] b ) throws DepositoException;
	
	/**
	 * Escreve a subsequ�ncia de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avan�a devidamente a {@link #setPosicao(long) posi��o}.
	 * @param inicio �ndice do array a ser considerado como inicial.
	 * @param total Quantidade de bytes a ser escrita.
	 */
	public void escrever( byte[] b, int inicio, int total ) throws DepositoException;
	
	/**
	 * Obt�m o byte da {@link #getPosicao()}.<br>
	 * Avan�a devidamente a {@link #setPosicao(long) posi��o}.
	 * @return -1, caso o fim tenha sido alcan�ado.
	 */
	public byte ler() throws DepositoException;
	
	/**
	 * Obt�m uma sequ�ncia de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avan�a devidamente a {@link #setPosicao(long) posi��o}.
	 * @param b Destino dos bytes lidos.
	 * @return quantidade de bytes lidos. Retorna -1 caso o arquivo todo j� tenha sido lido.
	 */
	public int ler( byte[] b ) throws DepositoException;
	
	/**
	 * Obt�m uma sequ�ncia de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avan�a devidamente a {@link #setPosicao(long) posi��o}.
	 * @param b Destino dos bytes lidos.
	 * @param inicio �ndice do array a ser considerado como destino inicial.
	 * @param total Quantidade de bytes a ser lida.
	 * @return quantidade de bytes lidos.
	 */
	public int ler( byte[] b, int inicio, int total ) throws DepositoException;
	
	/**
	 * Caso a {@link Memoria} seja persistente, descarregar os dados em cache.
	 */
	public void persistirPendencias() throws DepositoException;
	
	/**
	 * Fecha a {@link Memoria} para utiliza��o.
	 * @throws DepositoException fechamento concluso por�m com erro.
	 */
	public void fechar() throws DepositoException;
	
}
