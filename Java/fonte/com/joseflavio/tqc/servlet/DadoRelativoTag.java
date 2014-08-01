
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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;

/**
 * Disponibiliza um {@link Dado} como {@link ServletRequest#setAttribute(String, Object) atributo} "TQC_Dado_Corrente" considerando o
 * {@link Dado} já existente no "TQC_Dado_Corrente".
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class DadoRelativoTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private int distancia = 0;
	
	public int doStartTag() throws JspException {
		
		try{
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			
			TomaraQueCaia tqc = tqc();
			Viagem viagem = tqc.getViagemAtiva();
			Informacao info = viagem.getAtual();
			
			Dado corrente = (Dado) request.getAttribute( "TQC_Dado_Corrente" );
			
			if( corrente == null ) throw new JspException( "Dado corrente indefinido." );
			if( distancia == 0 ) return EVAL_BODY_INCLUDE;
			
			int indice = info.getIndice( corrente );
			int indiceNovo = indice + distancia;
			int total = info.getTotalDados();
			
			if( indiceNovo < 0 || indiceNovo >= total ) throw new JspException( "Distância relativa inválida." );
			
			request.setAttribute( "TQC_Dado_Corrente", info.getDado( indiceNovo ) );
			
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
		release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
	}
	
	/**
	 * Distância relativa ao {@link Dado} corrente. Padrão: 0.
	 */
	public int getDistancia() {
		return distancia;
	}
	
	/**
	 * @param distancia 0 == {@link Dado} corrente; Negativo == {@link Dado} anterior; Positivo == {@link Dado} posterior.
	 */
	public void setDistancia( int distancia ) {
		this.distancia = distancia;
	}
	
}
