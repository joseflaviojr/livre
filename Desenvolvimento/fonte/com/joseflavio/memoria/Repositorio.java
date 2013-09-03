
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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

/**
 * Reposit�rio de {@link Arquivo}s.<br>
 * Corresponde a um diret�rio em sistema hier�rquico de armazenamento.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see Arquivo
 */
public interface Repositorio {
	
	/**
	 * Identifica��o do reposit�rio.<br>
	 * Pode ser <code>null</code> caso este reposit�rio corresponda ao raiz.
	 */
	public String getNome() throws DepositoException;
	
	/**
	 * Localiza��o do reposit�rio, isto �, o reposit�rio-pai.
	 * @return <code>null</code>, caso este reposit�rio corresponda ao raiz.
	 */
	public Repositorio getRepositorio() throws DepositoException;
	
	/**
	 * Lista todos os {@link Arquivo}s existentes neste reposit�rio.
	 */
	public Arquivo[] listarArquivos() throws DepositoException;
	
	/**
	 * Lista todos os {@link Repositorio reposit�rios-filhos}.
	 */
	public Repositorio[] listarRepositorios() throws DepositoException;
	
	/**
	 * Retorna o {@link Arquivo} que corresponde ao nome e tipo especificados.
	 * @param criarSeNecessario Criar o {@link Arquivo} caso ele n�o exista?
	 * @return <code>null</code>, caso n�o seja encontrado.
	 */
	public Arquivo getArquivo( String nome, String tipo, boolean criarSeNecessario ) throws DepositoException;
	
	/**
	 * Retorna o {@link Repositorio reposit�rio-filho} que corresponde ao nome especificado.
	 * @param criarSeNecessario Criar o {@link Repositorio} caso ele n�o exista?
	 * @return <code>null</code>, caso n�o seja encontrado.
	 */
	public Repositorio getRepositorio( String nome, boolean criarSeNecessario ) throws DepositoException;
	
	/**
	 * Verifica a exist�ncia de um {@link Arquivo}.
	 */
	public boolean existeArquivo( String nome, String tipo ) throws DepositoException;
	
	/**
	 * Verifica a exist�ncia de um {@link Repositorio}.
	 */
	public boolean existeRepositorio( String nome ) throws DepositoException;
	
	/**
	 * Apaga definitivamente um {@link Arquivo} contido neste {@link Repositorio}.
	 * @return <code>false</code>, caso o {@link Arquivo} n�o exista ou n�o seja poss�vel apag�-lo.
	 * @see Arquivo#apagar()
	 */
	public boolean apagarArquivo( String nome, String tipo ) throws DepositoException;
	
	/**
	 * Apaga definitivamente um {@link Repositorio} contido neste {@link Repositorio}.
	 * @return <code>false</code>, caso o {@link Repositorio} n�o exista ou n�o seja poss�vel apag�-lo por completo.
	 * @see #apagar()
	 */
	public boolean apagarRepositorio( String nome ) throws DepositoException;
	
	/**
	 * Apaga definitivamente este {@link Repositorio} juntamente com seus {@link Arquivo}s e {@link Repositorio}s internos.<br>
	 * Este objeto {@link Repositorio} se tornar� inutiliz�vel.
	 * @return <code>false</code>, caso n�o seja poss�vel apag�-lo por completo.
	 */
	public boolean apagar() throws DepositoException;
	
}
