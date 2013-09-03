
/*
 *  Copyright (C) 2009-2013 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2013 José Flávio de Souza Dias Júnior
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

/**
 * Repositório de {@link Arquivo}s.<br>
 * Corresponde a um diretório em sistema hierárquico de armazenamento.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see Arquivo
 */
public interface Repositorio {
	
	/**
	 * Identificação do repositório.<br>
	 * Pode ser <code>null</code> caso este repositório corresponda ao raiz.
	 */
	public String getNome() throws DepositoException;
	
	/**
	 * Localização do repositório, isto é, o repositório-pai.
	 * @return <code>null</code>, caso este repositório corresponda ao raiz.
	 */
	public Repositorio getRepositorio() throws DepositoException;
	
	/**
	 * Lista todos os {@link Arquivo}s existentes neste repositório.
	 */
	public Arquivo[] listarArquivos() throws DepositoException;
	
	/**
	 * Lista todos os {@link Repositorio repositórios-filhos}.
	 */
	public Repositorio[] listarRepositorios() throws DepositoException;
	
	/**
	 * Retorna o {@link Arquivo} que corresponde ao nome e tipo especificados.
	 * @param criarSeNecessario Criar o {@link Arquivo} caso ele não exista?
	 * @return <code>null</code>, caso não seja encontrado.
	 */
	public Arquivo getArquivo( String nome, String tipo, boolean criarSeNecessario ) throws DepositoException;
	
	/**
	 * Retorna o {@link Repositorio repositório-filho} que corresponde ao nome especificado.
	 * @param criarSeNecessario Criar o {@link Repositorio} caso ele não exista?
	 * @return <code>null</code>, caso não seja encontrado.
	 */
	public Repositorio getRepositorio( String nome, boolean criarSeNecessario ) throws DepositoException;
	
	/**
	 * Verifica a existência de um {@link Arquivo}.
	 */
	public boolean existeArquivo( String nome, String tipo ) throws DepositoException;
	
	/**
	 * Verifica a existência de um {@link Repositorio}.
	 */
	public boolean existeRepositorio( String nome ) throws DepositoException;
	
	/**
	 * Apaga definitivamente um {@link Arquivo} contido neste {@link Repositorio}.
	 * @return <code>false</code>, caso o {@link Arquivo} não exista ou não seja possível apagá-lo.
	 * @see Arquivo#apagar()
	 */
	public boolean apagarArquivo( String nome, String tipo ) throws DepositoException;
	
	/**
	 * Apaga definitivamente um {@link Repositorio} contido neste {@link Repositorio}.
	 * @return <code>false</code>, caso o {@link Repositorio} não exista ou não seja possível apagá-lo por completo.
	 * @see #apagar()
	 */
	public boolean apagarRepositorio( String nome ) throws DepositoException;
	
	/**
	 * Apaga definitivamente este {@link Repositorio} juntamente com seus {@link Arquivo}s e {@link Repositorio}s internos.<br>
	 * Este objeto {@link Repositorio} se tornará inutilizável.
	 * @return <code>false</code>, caso não seja possível apagá-lo por completo.
	 */
	public boolean apagar() throws DepositoException;
	
}
