
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.aplicacao.ComandoPrincipal;
import com.joseflavio.tqc.dado.Comando;

/**
 * Enumera os principais {@link Comando}s da {@link Informacao} que implementa {@link ComandoPrincipal}.<br>
 * Cada {@link Comando} poderá ser obtido através do método {@link #getComandoCorrente()} ou do {@link ServletRequest#getAttribute(String) atributo} "TQC_Dado_Corrente".
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ComandosTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private Informacao informacao;
	
	private List<Comando> comandos;
	private int pos;
	
	private Comando comando;
	
	public int doStartTag() throws JspException {
		
		TomaraQueCaia tqc = tqc();
		Viagem viagem = tqc.getViagemAtiva();
		informacao = viagem.getAtual();
		
		if( informacao instanceof ComandoPrincipal ){
			
			comandos = new ArrayList<Comando>();
			
			int i = informacao.getIndice( "tqc_comandos_i" );
			int f = informacao.getIndice( "tqc_comandos_f" );
			
			for( i++; i < f; i++ ){
				Dado dado = informacao.getDado( i );
				if( dado instanceof Comando ) comandos.add( (Comando) dado );
			}
			
			pos = 0;
			
			if( comandos.size() > 0 ){
				proximo();
				return EVAL_BODY_INCLUDE;
			}
			
		}
			
		return SKIP_BODY;
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		if( pos < comandos.size() ){
			proximo();
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}
	
	private void proximo() {
		comando = comandos.get( pos++ );
		((HttpServletRequest)pageContext.getRequest()).setAttribute( "TQC_Dado_Corrente", comando );
	}
	
	@Override
	public int doEndTag() throws JspException {
		release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		informacao = null;
		comandos = null;
		comando = null;
	}
	
	public Informacao getInformacao() {
		return informacao;
	}
	
	public List<Comando> getComandos() {
		return comandos;
	}

	public Comando getComandoCorrente() {
		return comando;
	}
	
}
