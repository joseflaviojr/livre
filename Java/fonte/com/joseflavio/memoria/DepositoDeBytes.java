
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

import java.io.IOException;
import java.io.OutputStream;

import com.joseflavio.Tamanho;


/**
 * Armazenamento simplificado de bytes.<br>
 * A unidade de armazenamento b�sica � uma sequ�ncia de bytes denominada registro.<br>
 * O {@link DepositoDeBytes} � considerado um sistema de persist�ncia primitivo, podendo ser utilizado como base para a implementa��o de {@link DepositoDeObjetos}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface DepositoDeBytes extends Tamanho {
	
	/**
	 * Quantidade m�xima de registros suportada.
	 */
	@Override
	public long getTamanhoMaximo();
	
	/**
	 * Quantidade de registros contidos no {@link DepositoDeBytes}.
	 */
	@Override
	public long getTamanhoAtual();
	
	/**
	 * Armazena um registro.<br>
	 * Caso a id j� exista no {@link DepositoDeBytes}, a opera��o ser� considerada uma substitui��o.
	 * @param registro Conte�do do registro. Sequ�ncia de bytes a armazenar.
	 * @param inicio �ndice inicial do array a ser considerado como conte�do do registro.
	 * @param total Total de bytes, a partir do valor de inicio, a ser considerado como conte�do do registro.
	 * @param id Identifica��o do registro no {@link DepositoDeBytes}. Caso <= 0, ser� utilizado automaticamente {@link #gerarId()}.
	 * @return id
	 * @see #gerarId()
	 * @throws DepositoException {@link DepositoException#RAZAO_INCONSISTENCIA}, caso id esteja fora da faixa permitida.
	 */
	public long armazenar( byte[] registro, int inicio, int total, long id ) throws DepositoException;
	
	/**
	 * {@link #armazenar(byte[], int, int, long)} com inicio = 0 e total = registro.length
	 */
	public long armazenar( byte[] registro, long id ) throws DepositoException;
	
	/**
	 * Obt�m o conte�do de um registro armazenado.
	 * @param id Identifica��o do registro no {@link DepositoDeBytes}.
	 * @param registro Array que receber� o conte�do do registro.
	 * @param inicio �ndice inicial do array a ser considerado como conte�do do registro.
	 * @return o tamanho do registro, em bytes.
	 * @throws DepositoException {@link DepositoException#RAZAO_INEXISTENCIA}, caso id inv�lido.
	 */
	public int obter( long id, byte[] registro, int inicio ) throws DepositoException;
	
	/**
	 * Obt�m o conte�do de um registro armazenado.
	 * @param id Identifica��o do registro no {@link DepositoDeBytes}.
	 * @param out Destino do conte�do do registro.
	 * @return o tamanho do registro, em bytes.
	 * @throws DepositoException {@link DepositoException#RAZAO_INEXISTENCIA}, caso id inv�lido.
	 * @throws IOException proveniente de {@link OutputStream}.
	 */
	public int obter( long id, OutputStream out ) throws DepositoException, IOException;
	
	/**
	 * Verifica a exist�ncia de um registro identificado pela id especificada.
	 */
	public boolean existe( long id ) throws DepositoException;
	
	/**
	 * Remove um registro armazenado.
	 * @param id Identifica��o do registro no {@link DepositoDeBytes}.
	 * @see DepositoException#RAZAO_INEXISTENCIA
	 */
	public void remover( long id ) throws DepositoException;
	
	/**
	 * Lista a identifica��o de todos os registros armazenados.
	 * @param id Array que receber� as identifica��es dos registros.
	 * @return quantidade de registros listados.
	 */
	public long listar( long[] id ) throws DepositoException;
	
	/**
	 * M�nimo valor de identifica��o aceit�vel.
	 */
	public long getIdMinimo();
	
	/**
	 * M�ximo valor de identifica��o aceit�vel.
	 */
	public long getIdMaximo();
	
	/**
	 * Gera identifica��o para um novo registro na faixa de {@link #getIdMinimo()} a {@link #getIdMaximo()}.<br>
	 * Subssequentes chamadas n�o gerar�o a mesma identifica��o mesmo que a anterior ainda inexista no {@link DepositoDeBytes}.
	 * @return um valor de identifica��o �nico no {@link DepositoDeBytes}.
	 * @throws DepositoException {@link DepositoException#RAZAO_INCONSISTENCIA}, caso a identifica��o gerada tenha ultrapassado o limite estabelecido.
	 */
	public long gerarId() throws DepositoException;

}
