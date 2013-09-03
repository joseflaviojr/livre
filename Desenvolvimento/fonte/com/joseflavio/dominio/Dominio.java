
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
 * José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 * sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a versão 3 da Licença, como
 * (a seu critério) qualquer versão posterior.
 * 
 * José Flávio Livre é distribuído na expectativa de que seja útil,
 * porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 * COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 * Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 * junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.dominio;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class Dominio {
	
	protected Dominio() throws DominioException {
	}

	/**
	 * Nome do domínio. Ex.: "joseflavio.com.br"
	 */
	public abstract String getNome();
	
	/**
	 * Endereço IP ou nome do servidor/controlador do domínio. Ex.: "192.168.0.2"
	 */
	public abstract String getEndereco();
	
	/**
	 * Usuário através do qual o domínio está sendo efetivamente acessado. Normalmente um administrador de rede.
	 */
	public abstract DominioUsuario getUsuario();
	
	public abstract boolean isDisponivel();
	
	/**
	 * @param identificacao {@link DominioUsuario#getIdentificacao() Identificação} do {@link DominioUsuario} desejado. Ex.: "jose21"
	 * @param baseBusca Base, no formato LDAP, para a busca do usuário. Ex.: "dc=joseflavio, dc=com, dc=br"
	 * @throws DominioException caso o usuário não exista ou haja uma falha de rede/autenticação/autorização.
	 */
	public abstract DominioUsuario buscarUsuario( String identificacao, String baseBusca ) throws DominioException;
	
	/**
	 * @see #buscarUsuario(String, String)
	 */
	public abstract DominioUsuario buscarUsuario( String identificacao ) throws DominioException;
	
	/**
	 * @return o {@link DominioUsuario} correspondente à <code>identificacao</code> caso a sua <code>senha</code> esteja correta.
	 * @throws DominioException caso o usuário não exista ou haja uma falha de rede/autenticação/autorização.
	 */
	public abstract DominioUsuario autenticarUsuario( String identificacao, String senha ) throws DominioException;
	
}
