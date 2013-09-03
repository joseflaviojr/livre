
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
 * Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 * sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 * (a seu crit�rio) qualquer vers�o posterior.
 * 
 * Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 * por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 * COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 * Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 * junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.dominio;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class Dominio {
	
	protected Dominio() throws DominioException {
	}

	/**
	 * Nome do dom�nio. Ex.: "joseflavio.com.br"
	 */
	public abstract String getNome();
	
	/**
	 * Endere�o IP ou nome do servidor/controlador do dom�nio. Ex.: "192.168.0.2"
	 */
	public abstract String getEndereco();
	
	/**
	 * Usu�rio atrav�s do qual o dom�nio est� sendo efetivamente acessado. Normalmente um administrador de rede.
	 */
	public abstract DominioUsuario getUsuario();
	
	public abstract boolean isDisponivel();
	
	/**
	 * @param identificacao {@link DominioUsuario#getIdentificacao() Identifica��o} do {@link DominioUsuario} desejado. Ex.: "jose21"
	 * @param baseBusca Base, no formato LDAP, para a busca do usu�rio. Ex.: "dc=joseflavio, dc=com, dc=br"
	 * @throws DominioException caso o usu�rio n�o exista ou haja uma falha de rede/autentica��o/autoriza��o.
	 */
	public abstract DominioUsuario buscarUsuario( String identificacao, String baseBusca ) throws DominioException;
	
	/**
	 * @see #buscarUsuario(String, String)
	 */
	public abstract DominioUsuario buscarUsuario( String identificacao ) throws DominioException;
	
	/**
	 * @return o {@link DominioUsuario} correspondente � <code>identificacao</code> caso a sua <code>senha</code> esteja correta.
	 * @throws DominioException caso o usu�rio n�o exista ou haja uma falha de rede/autentica��o/autoriza��o.
	 */
	public abstract DominioUsuario autenticarUsuario( String identificacao, String senha ) throws DominioException;
	
}
