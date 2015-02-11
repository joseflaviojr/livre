
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

import java.io.IOException;
import java.io.OutputStream;

import com.joseflavio.Tamanho;


/**
 * Armazenamento simplificado de bytes.<br>
 * A unidade de armazenamento básica é uma sequência de bytes denominada registro.<br>
 * O {@link DepositoDeBytes} é considerado um sistema de persistência primitivo, podendo ser utilizado como base para a implementação de {@link DepositoDeObjetos}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface DepositoDeBytes extends Tamanho {
	
	/**
	 * Quantidade máxima de registros suportada.
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
	 * Caso a id já exista no {@link DepositoDeBytes}, a operação será considerada uma substituição.
	 * @param registro Conteúdo do registro. Sequência de bytes a armazenar.
	 * @param inicio Índice inicial do array a ser considerado como conteúdo do registro.
	 * @param total Total de bytes, a partir do valor de inicio, a ser considerado como conteúdo do registro.
	 * @param id Identificação do registro no {@link DepositoDeBytes}. Caso <= 0, será utilizado automaticamente {@link #gerarId()}.
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
	 * Obtém o conteúdo de um registro armazenado.
	 * @param id Identificação do registro no {@link DepositoDeBytes}.
	 * @param registro Array que receberá o conteúdo do registro.
	 * @param inicio Índice inicial do array a ser considerado como conteúdo do registro.
	 * @return o tamanho do registro, em bytes.
	 * @throws DepositoException {@link DepositoException#RAZAO_INEXISTENCIA}, caso id inválido.
	 */
	public int obter( long id, byte[] registro, int inicio ) throws DepositoException;
	
	/**
	 * Obtém o conteúdo de um registro armazenado.
	 * @param id Identificação do registro no {@link DepositoDeBytes}.
	 * @param out Destino do conteúdo do registro.
	 * @return o tamanho do registro, em bytes.
	 * @throws DepositoException {@link DepositoException#RAZAO_INEXISTENCIA}, caso id inválido.
	 * @throws IOException proveniente de {@link OutputStream}.
	 */
	public int obter( long id, OutputStream out ) throws DepositoException, IOException;
	
	/**
	 * Verifica a existência de um registro identificado pela id especificada.
	 */
	public boolean existe( long id ) throws DepositoException;
	
	/**
	 * Remove um registro armazenado.
	 * @param id Identificação do registro no {@link DepositoDeBytes}.
	 * @see DepositoException#RAZAO_INEXISTENCIA
	 */
	public void remover( long id ) throws DepositoException;
	
	/**
	 * Lista a identificação de todos os registros armazenados.
	 * @param id Array que receberá as identificações dos registros.
	 * @return quantidade de registros listados.
	 */
	public long listar( long[] id ) throws DepositoException;
	
	/**
	 * Mínimo valor de identificação aceitável.
	 */
	public long getIdMinimo();
	
	/**
	 * Máximo valor de identificação aceitável.
	 */
	public long getIdMaximo();
	
	/**
	 * Gera identificação para um novo registro na faixa de {@link #getIdMinimo()} a {@link #getIdMaximo()}.<br>
	 * Subssequentes chamadas não gerarão a mesma identificação mesmo que a anterior ainda inexista no {@link DepositoDeBytes}.
	 * @return um valor de identificação único no {@link DepositoDeBytes}.
	 * @throws DepositoException {@link DepositoException#RAZAO_INCONSISTENCIA}, caso a identificação gerada tenha ultrapassado o limite estabelecido.
	 */
	public long gerarId() throws DepositoException;

}
