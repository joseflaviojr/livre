
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

package com.joseflavio.memoria;

import com.joseflavio.Tamanho;


/**
 * Memória de bytes.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface Memoria extends Tamanho {

	/**
	 * Indica se a {@link Memoria} é modificável. Caso contrário, será de apenas leitura.
	 */
	public boolean isModificavel();
	
	/**
	 * Posição corrente para leitura e escrita.<br>
	 * Posição inicial: 0.
	 * @return 0 a {@link #getTamanhoAtual()}
	 */
	public long getPosicao() throws DepositoException;
	
	/**
	 * Determina a posição corrente para leitura e escrita.
	 * @param posicao Valor de 0 a {@link #getTamanhoAtual()}
	 */
	public void setPosicao( long posicao ) throws DepositoException;
	
	/**
	 * Determina o tamanho atual da {@link Memoria}, truncando se necessário.
	 */
	public void setTamanhoAtual( long tamanho ) throws DepositoException;
	
	/**
	 * Escreve o byte na {@link #getPosicao()}.<br>
	 * Avança devidamente a {@link #setPosicao(long) posição}.
	 */
	public void escrever( byte b ) throws DepositoException;
	
	/**
	 * Escreve a sequência de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avança devidamente a {@link #setPosicao(long) posição}.
	 */
	public void escrever( byte[] b ) throws DepositoException;
	
	/**
	 * Escreve a subsequência de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avança devidamente a {@link #setPosicao(long) posição}.
	 * @param inicio índice do array a ser considerado como inicial.
	 * @param total Quantidade de bytes a ser escrita.
	 */
	public void escrever( byte[] b, int inicio, int total ) throws DepositoException;
	
	/**
	 * Obtém o byte da {@link #getPosicao()}.<br>
	 * Avança devidamente a {@link #setPosicao(long) posição}.
	 * @return -1, caso o fim tenha sido alcançado.
	 */
	public byte ler() throws DepositoException;
	
	/**
	 * Obtém uma sequência de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avança devidamente a {@link #setPosicao(long) posição}.
	 * @param b Destino dos bytes lidos.
	 * @return quantidade de bytes lidos. Retorna -1 caso o arquivo todo já tenha sido lido.
	 */
	public int ler( byte[] b ) throws DepositoException;
	
	/**
	 * Obtém uma sequência de bytes a partir da {@link #getPosicao()}, inclusive.<br>
	 * Avança devidamente a {@link #setPosicao(long) posição}.
	 * @param b Destino dos bytes lidos.
	 * @param inicio índice do array a ser considerado como destino inicial.
	 * @param total Quantidade de bytes a ser lida.
	 * @return quantidade de bytes lidos.
	 */
	public int ler( byte[] b, int inicio, int total ) throws DepositoException;
	
	/**
	 * Caso a {@link Memoria} seja persistente, descarregar os dados em cache.
	 */
	public void persistirPendencias() throws DepositoException;
	
	/**
	 * Fecha a {@link Memoria} para utilização.
	 * @throws DepositoException fechamento concluso porém com erro.
	 */
	public void fechar() throws DepositoException;
	
}
