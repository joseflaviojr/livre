
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

package com.joseflavio.tqc.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;

/**
 * Especifica um {@link ServletRequest#setAttribute(String, Object) atributo} com o conteúdo de um {@link Dado}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class AtributoTag extends TQCTagSupport {

	private static final long serialVersionUID = 1L;

	private String nome;
	
	private String tipo;
	
	public int doEndTag() throws JspException {
		
		try{
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			
			TomaraQueCaia tqc = tqc();
			Viagem viagem = tqc.getViagemAtiva();
			Informacao info = viagem.getAtual();
			
			Dado dado = info.getDado( nome );
			if( dado == null ) throw new JspException( "Dado desconhecido: " + nome );
			
			DadoControle controle = proc().getControle( dado );
			
			String str = controle.transcrever( request, tqc, dado );
			Object obj;
			
			if( tipo == null || tipo.equals( "string" ) ) obj = str;
			else if( tipo.equals( "int" ) ) obj = new Integer( str );
			else if( tipo.equals( "float" ) ) obj = new Float( str );
			else if( tipo.equals( "boolean" ) ) obj = new Boolean( str );
			else if( tipo.equals( "long" ) ) obj = new Long( str );
			else if( tipo.equals( "double" ) ) obj = new Double( str );
			else if( tipo.equals( "short" ) ) obj = new Short( str );
			else if( tipo.equals( "byte" ) ) obj = new Byte( str );
			else if( tipo.equals( "char" ) ) obj = new Character( str.charAt( 0 ) );
			else obj = str;
			
			request.setAttribute( nome, obj );
			
			return EVAL_PAGE;
		
		}catch( JspException e ){
			throw e;
		}catch( Exception e ){
			throw new JspException( e );
		}
		
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setTipo( String tipo ) {
		this.tipo = tipo;
	}

	/**
	 * string, char, boolean, byte, short, int, long, float ou double.<br>
	 * <code>null</code> == string
	 */
	public String getTipo() {
		return tipo;
	}
	
}
