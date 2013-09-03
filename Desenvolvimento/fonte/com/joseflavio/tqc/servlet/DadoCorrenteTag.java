
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

package com.joseflavio.tqc.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;

/**
 * Disponibiliza um {@link Dado} como {@link ServletRequest#setAttribute(String, Object) atributo} "TQC_Dado_Corrente".
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class DadoCorrenteTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public int doStartTag() throws JspException {
		
		try{
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			
			TomaraQueCaia tqc = tqc();
			Viagem viagem = tqc.getViagemAtiva();
			Informacao info = viagem.getAtual();
			
			Dado dado = info.getDado( nome );
			if( dado == null ) throw new JspException( "Dado desconhecido: " + nome );
			
			request.setAttribute( "TQC_Dado_Corrente", dado );
			
			return EVAL_BODY_INCLUDE;
		
		}catch( JspException e ){
			throw e;
		}catch( Exception e ){
			throw new JspException( e );
		}
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
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
		nome = null;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
}
