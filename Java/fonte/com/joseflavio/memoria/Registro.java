
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

import java.io.IOException;
import java.io.OutputStream;

import com.joseflavio.Tamanho;



/**
 * Registro de bytes.<br>
 * <pre>
 * Formato físico:
 * 
 * [Sinal] 1 byte
 * [Estado] 1 byte
 * [TamanhoMaximo] 4 bytes
 * [TamanhoAtual] 4 bytes
 * [Identificacao] 8 bytes
 * [Conteudo] tamanhoMaximo bytes
 * [Sinal] 1 byte
 * </pre>
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface Registro extends Tamanho {
	
	/**
	 * Disponível para armazenamento de bytes.
	 */
	public static final byte ESTADO_DISPONIVEL = 0;
	
	/**
	 * Contém bytes armazenados.
	 */
	public static final byte ESTADO_USADO = 1;
	
	/**
	 * Estado.
	 * @see #ESTADO_DISPONIVEL
	 */
	public byte getEstado();
	
	/**
	 * Espaço reservado para conteúdo.
	 * @return 32 bits.
	 */
	@Override
	public long getTamanhoMaximo();
	
	/**
	 * Tamanho do conteúdo atual.
	 * @return 32 bits.
	 */
	@Override
	public long getTamanhoAtual();
	
	/**
	 * Identificação do {@link Registro}.
	 * @return <code>0</code>, caso {@link #getEstado()} == {@link #ESTADO_DISPONIVEL}.
	 */
	public long getIdentificacao();
	
	/**
	 * Conteúdo (sequência de bytes) armazenado neste {@link Registro}.<br>
	 * Retorna um array com tamanho = {@link #getTamanhoAtual()}.
	 * @return <code>null</code>, caso {@link #getEstado()} == {@link #ESTADO_DISPONIVEL} ou o {@link Registro} inexiste.
	 * @see DepositoDeBytes#obter(long, byte[], int)
	 */
	public byte[] getConteudo() throws DepositoException;
	
	/**
	 * Conteúdo (sequência de bytes) armazenado neste {@link Registro}.<br>
	 * Escreve em {@link OutputStream} {@link #getTamanhoAtual()} bytes.
	 * @param out Destino do conteúdo do registro.
	 * @return {@link #getTamanhoAtual()}. <code>-1</code>, caso {@link #getEstado()} == {@link #ESTADO_DISPONIVEL} ou o {@link Registro} inexiste.
	 * @throws IOException proveniente de {@link OutputStream}.
	 * @see DepositoDeBytes#obter(long, OutputStream)
	 */
	public int getConteudo( OutputStream out ) throws DepositoException, IOException;
	
	/**
	 * Valor que fisicamente inicia e finaliza o registro, sinalizando armazenamento consistente.
	 */
	public byte getSinal();
	
}
