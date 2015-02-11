
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

package com.joseflavio.tqc.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.aplicacao.Formulario;

/**
 * Enumera os {@link Dado}s do campo atual do {@link Formulario}.<br>
 * Cada {@link Dado} poder� ser obtido atrav�s do m�todo {@link #getDadoCorrente()} ou do {@link ServletRequest#getAttribute(String) atributo} "TQC_Dado_Corrente".
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see FormularioCamposTag#getDados()
 */
public class FormularioCampoDadosTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private Dado[] dados;
	
	private int pos;
	
	public int doStartTag() throws JspException {
		
		Tag tagMae = getParent();
		if( !( tagMae instanceof FormularioCamposTag ) ) throw new JspException( "Escopo inv�lido de tag." );
		
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
