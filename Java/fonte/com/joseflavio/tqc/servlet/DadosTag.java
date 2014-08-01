
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
import com.joseflavio.tqc.Identificacao;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;

/**
 * Enumera {@link Dado}s da {@link Informacao} atual, disponibilizando-os no {@link ServletRequest#setAttribute(String, Object) atributo} "TQC_Dado_Corrente".<br>
 * Uma faixa de enumeração poderá ser especificada. Ver {@link #getNomeInicial()} e {@link #getNomeFinal()}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class DadosTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private Informacao informacao;
	
	private String nomeInicial;
	
	private String nomeFinal;
	
	private boolean inclusive = false;
	
	private int passo = 1;
	
	private int posI, posF;
	
	public int doStartTag() throws JspException {
		
		TomaraQueCaia tqc = tqc();
		Viagem viagem = tqc.getViagemAtiva();
		informacao = viagem.getAtual();
		
		int total = informacao.getTotalDados();
		if( total == 0 ) return SKIP_BODY;
		
		if( nomeInicial != null ){
			posI = informacao.getIndice( nomeInicial );
			if( posI < 0 ) posI = 0;
			else if( ! inclusive ) posI++;
		}else{
			posI = 0;
		}
		
		if( nomeFinal != null ){
			posF = informacao.getIndice( nomeFinal );
			if( posF < 0 ) posF = total - 1;
			else if( ! inclusive ) posF--;
		}else{
			posF = total - 1;
		}
		
		if( posI <= posF ){
			proximo();
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		if( posI <= posF ){
			proximo();
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}
	
	private void proximo() {
		((HttpServletRequest)pageContext.getRequest()).setAttribute( "TQC_Dado_Corrente", informacao.getDado( posI ) );
		posI += passo;
	}
	
	@Override
	public int doEndTag() throws JspException {
		release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		informacao = null;
		nomeInicial = null;
		nomeFinal = null;
	}
	
	/**
	 * {@link Identificacao#getNome() Nome} do {@link Dado} desejado inicial da {@link Informacao}.<br>
	 * <code>null</code> = primeiro {@link Dado} da {@link Informacao}.
	 */
	public String getNomeInicial() {
		return nomeInicial;
	}
	
	public void setNomeInicial( String nomeInicial ) {
		this.nomeInicial = nomeInicial;
	}
	
	/**
	 * {@link Identificacao#getNome() Nome} do {@link Dado} desejado final da {@link Informacao}.<br>
	 * <code>null</code> = último {@link Dado} da {@link Informacao}.
	 */
	public String getNomeFinal() {
		return nomeFinal;
	}

	public void setNomeFinal( String nomeFinal ) {
		this.nomeFinal = nomeFinal;
	}

	/**
	 * O {@link Dado} {@link #getNomeInicial() inicial} e o {@link #getNomeFinal() final} estão incluídos na enumeração?<br>
	 * Padrão: false.
	 */
	public boolean isInclusive() {
		return inclusive;
	}
	
	public void setInclusive( boolean inclusive ) {
		this.inclusive = inclusive;
	}

	/**
	 * Salto elementar da enumeração. Padrão: 1.
	 */
	public int getPasso() {
		return passo;
	}
	
	public void setPasso( int passo ) {
		this.passo = passo;
	}
	
}
