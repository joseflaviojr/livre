
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;

/**
 * Identifica��o do {@link Dado} no contexto da HTML/JavaScript.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class DadoIdentificacaoTag extends TQCTagSupport {

	private static final long serialVersionUID = 1L;

	private String nome;
	
	public int doEndTag() throws JspException {
		
		try{
			
			JspWriter out = pageContext.getOut();
			
			TomaraQueCaia tqc = tqc();
			Viagem viagem = tqc.getViagemAtiva();
			Informacao info = viagem.getAtual();
			
			int indice = info.getIndice( nome );
			if( indice == -1 ) throw new JspException( "Dado desconhecido: " + nome );

			out( TomaraQueCaiaDesktopServlet.PREFIXO_DADO + indice, out );

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
	
}
