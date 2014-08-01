
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

import java.io.IOException;
import java.io.OutputStream;

import com.joseflavio.Tamanho;



/**
 * Registro de bytes.<br>
 * <pre>
 * Formato f�sico:
 * 
 * [Sinal] 1 byte
 * [Estado] 1 byte
 * [TamanhoMaximo] 4 bytes
 * [TamanhoAtual] 4 bytes
 * [Identificacao] 8 bytes
 * [Conteudo] tamanhoMaximo bytes
 * [Sinal] 1 byte
 * </pre>
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface Registro extends Tamanho {
	
	/**
	 * Dispon�vel para armazenamento de bytes.
	 */
	public static final byte ESTADO_DISPONIVEL = 0;
	
	/**
	 * Cont�m bytes armazenados.
	 */
	public static final byte ESTADO_USADO = 1;
	
	/**
	 * Estado.
	 * @see #ESTADO_DISPONIVEL
	 */
	public byte getEstado();
	
	/**
	 * Espa�o reservado para conte�do.
	 * @return 32 bits.
	 */
	@Override
	public long getTamanhoMaximo();
	
	/**
	 * Tamanho do conte�do atual.
	 * @return 32 bits.
	 */
	@Override
	public long getTamanhoAtual();
	
	/**
	 * Identifica��o do {@link Registro}.
	 * @return <code>0</code>, caso {@link #getEstado()} == {@link #ESTADO_DISPONIVEL}.
	 */
	public long getIdentificacao();
	
	/**
	 * Conte�do (sequ�ncia de bytes) armazenado neste {@link Registro}.<br>
	 * Retorna um array com tamanho = {@link #getTamanhoAtual()}.
	 * @return <code>null</code>, caso {@link #getEstado()} == {@link #ESTADO_DISPONIVEL} ou o {@link Registro} inexiste.
	 * @see DepositoDeBytes#obter(long, byte[], int)
	 */
	public byte[] getConteudo() throws DepositoException;
	
	/**
	 * Conte�do (sequ�ncia de bytes) armazenado neste {@link Registro}.<br>
	 * Escreve em {@link OutputStream} {@link #getTamanhoAtual()} bytes.
	 * @param out Destino do conte�do do registro.
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
