
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

package com.joseflavio.tqc.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.aplicacao.Formulario;

/**
 * Enumera os {@link Dado}s do campo atual do {@link Formulario}.<br>
 * Cada {@link Dado} poderá ser obtido através do método {@link #getDadoCorrente()} ou do {@link ServletRequest#getAttribute(String) atributo} "TQC_Dado_Corrente".
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see FormularioCamposTag#getDados()
 */
public class FormularioCampoDadosTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private Dado[] dados;
	
	private int pos;
	
	public int doStartTag() throws JspException {
		
		Tag tagMae = getParent();
		if( !( tagMae instanceof FormularioCamposTag ) ) throw new JspException( "Escopo inválido de tag." );
		
		dados = ((FormularioCamposTag)tagMae).getDados();
		pos = 0;
		
		if( dados.length > 0 ){
			
			((HttpServletRequest)pageContext.getRequest()).setAttribute( "TQC_Dado_Corrente", dados[0] );
			
			return EVAL_BODY_INCLUDE;
			
		}
		
		return SKIP_BODY;
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		
		pos++;
		
		if( pos < dados.length ){
			
			((HttpServletRequest)pageContext.getRequest()).setAttribute( "TQC_Dado_Corrente", dados[pos] );
			
			return EVAL_BODY_AGAIN;
			
		}
		
		return SKIP_BODY;
		
	}
	
	@Override
	public int doEndTag() throws JspException {
		((HttpServletRequest)pageContext.getRequest()).setAttribute( "TQC_Dado_Corrente", null );
		release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		dados = null;
	}
	
	public Dado getDadoCorrente() {
		return dados[pos];
	}
	
}
