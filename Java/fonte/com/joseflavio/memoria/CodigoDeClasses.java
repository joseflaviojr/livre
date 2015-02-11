
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

/**
 * Mapeamento que relaciona uma {@link Class} a um código numérico único.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface CodigoDeClasses {
	
	/**
	 * Retorna o código identificador da {@link Class} especificada.<br>
	 * Caso a {@link Class} ainda não o possua, um novo código será criado.
	 */
	public short getCodigo( Class<?> classe ) throws DepositoException;
	
	/**
	 * Retorna a {@link Class} correspondente ao código especificado.
	 * @return <code>null</code>, caso o código seja inválido.
	 */
	public Class<?> getClasse( short codigo ) throws DepositoException;
	
	/**
	 * Associa uma {@link Class} a um código identificador.
	 * @return <code>false</code>, caso a {@link Class} e/ou o código já existam na base.
	 */
	public boolean associar( Class<?> classe, short codigo ) throws DepositoException;

}
