
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
 * Microsoft(R) Active Directory através de LDAP.
 * @author José Flávio de Souza Dias Júnior
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
