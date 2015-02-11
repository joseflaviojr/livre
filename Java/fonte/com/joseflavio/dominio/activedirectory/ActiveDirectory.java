
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

package com.joseflavio.dominio.activedirectory;

import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.joseflavio.dominio.Dominio;
import com.joseflavio.dominio.DominioException;
import com.joseflavio.dominio.DominioUsuario;

/**
 * Microsoft(R) Active Directory atrav�s de LDAP.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class ActiveDirectory extends Dominio {

	private String nome;
	
	private String endereco;
	
	private ActiveDirectoryUsuario usuario;
	
	private String senha;
	
	public ActiveDirectory( String nome, String endereco, String usuario, String senha ) throws DominioException {

		this.nome = nome;
		this.endereco = endereco;
		this.usuario = buscarUsuario( usuario, nome, endereco, usuario, senha );
		this.senha = senha;
		
	}
	
	public boolean isDisponivel() {
		
		try{
			
			return buscarUsuario( usuario.getIdentificacao() ) != null;
			
		}catch( Exception e ){
			
			return false;
			
		}
		
	}
	
	private static ActiveDirectoryUsuario buscarUsuario( String usuario, String dominio, String endereco, String baseBusca, String administrador, String senha ) throws DominioException {

		ActiveDirectoryUsuario adUsuario = null;
		
		try{
		
			SearchControls controles = new SearchControls();
			controles.setReturningAttributes( new String[]{ "displayName", "mail" } );
			controles.setSearchScope( SearchControls.SUBTREE_SCOPE );
	
			Hashtable<String, String> ambiente = new Hashtable<String, String>( 5 );
			ambiente.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
			ambiente.put( Context.PROVIDER_URL, "ldap://" + endereco );
			ambiente.put( Context.SECURITY_AUTHENTICATION, "simple" );
			ambiente.put( Context.SECURITY_PRINCIPAL, administrador + "@" + dominio );
			ambiente.put( Context.SECURITY_CREDENTIALS, senha );
	
			LdapContext ldapContext = new InitialLdapContext( ambiente, null );

			NamingEnumeration<SearchResult> answer = ldapContext.search( baseBusca, "(&(objectClass=user)(sAMAccountName=" + usuario + "))", controles );
			if( answer.hasMoreElements() ){
				
				Attributes attributes = answer.next().getAttributes();

				if( attributes != null ){
					
					String nomeCompleto = null;
					String email = null;
					
					NamingEnumeration<? extends Attribute> ne = attributes.getAll();
					while( ne.hasMore() ){
						
						Attribute attr = ne.next();
						String id = attr.getID();
						
						if( id.equals( "displayName" ) ) nomeCompleto = (String) attr.get();
						else if( id.equals( "mail" ) ) email = (String) attr.get();
						
					}
					ne.close();
					
					if( nomeCompleto != null && email != null ){
						adUsuario = new ActiveDirectoryUsuario( usuario, nomeCompleto, email );
					}
					
				}
				
			}
			answer.close();
			
		}catch( Exception e ){
			throw new DominioException( e );
		}

		if( adUsuario == null ) throw new DominioException( usuario );
		return adUsuario;
		
	}
	
	private static ActiveDirectoryUsuario buscarUsuario( String usuario, String dominio, String endereco, String administrador, String senha ) throws DominioException {
		
		StringBuilder baseBusca = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer( dominio, "." );
		boolean primeiro = true;
		while( st.hasMoreTokens() ){
			if( primeiro ){
				baseBusca.append( "dc=" + st.nextToken() );
				primeiro = false;
			}else{
				baseBusca.append( ", dc=" + st.nextToken() );
			}
		}
		
		return buscarUsuario( usuario, dominio, endereco, baseBusca.toString(), administrador, senha );
		
	}

	public DominioUsuario buscarUsuario( String identificacao, String baseBusca ) throws DominioException {
		return buscarUsuario( identificacao, nome, endereco, baseBusca, this.usuario.getIdentificacao(), senha );
	}
	
	public DominioUsuario buscarUsuario( String identificacao ) throws DominioException {
		return buscarUsuario( identificacao, nome, endereco, this.usuario.getIdentificacao(), senha );
	}
	
	public DominioUsuario autenticarUsuario( String identificacao, String senha ) throws DominioException {
		return buscarUsuario( identificacao, nome, endereco, identificacao, senha );
	}
	
	public String getEndereco() {
		return endereco;
	}

	public String getNome() {
		return nome;
	}

	public DominioUsuario getUsuario() {
		return usuario;
	}

}
