
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

import javax.servlet.jsp.JspException;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.aplicacao.Formulario;

/**
 * Laço para cada campo {@link Formulario#getTotalDeCamposVisiveis() visível} de {@link Formulario}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see FormularioCampoRotuloTag
 * @see FormularioCampoDadosTag
 */
public class FormularioCamposTag extends TQCBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private Formulario<?> formulario;
	
	private int total, totalSelecionado;
	
	private int campo;
	
	private String rotulo;
	
	private Dado[] dados;
	
	public int doStartTag() throws JspException {
		
		TomaraQueCaia tqc = tqc();
		Viagem viagem = tqc.getViagemAtiva();
		Informacao info = viagem.getAtual();
		
		if( info instanceof Formulario<?> ){
			
			formulario = (Formulario<?>) info;
			total = formulario.getTotalDeCamposVisiveis();
			totalSelecionado = 0;
			campo = 0;
			
			if( total > 0 ){
				if( ! proximo() ) return SKIP_BODY;
				return EVAL_BODY_INCLUDE;
			}
			
		}
			
		return SKIP_BODY;
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		if( totalSelecionado < total ){
			if( ! proximo() ) return SKIP_BODY;
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}
	
	private boolean proximo() {
		
		campo++;
		
		Dado rotuloDado = formulario.getDado( "tqc_formulario_rotulo_" + campo );
		if( rotuloDado == null ) return false;
		
		if( ! rotuloDado.isVisivel() ) return proximo();
		
		totalSelecionado++;
		
		Object rotuloObj = rotuloDado.getConteudo();
		rotulo = rotuloObj != null ? rotuloObj.toString() : "";
		
		int i = formulario.getIndice( "tqc_formulario_campo_" + campo + "_i" );
		int f = formulario.getIndice( "tqc_formulario_campo_" + campo + "_f" );
		
		int x = f - i - 1;
		dados = new Dado[ x > 0 ? x : 0 ];

		for( x = 0, i++; i < f; x++, i++ ){
			dados[x] = formulario.getDado( i );
		}
		
		return true;
		
	}
	
	@Override
	public int doEndTag() throws JspException {
		release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		formulario = null;
		rotulo = null;
		dados = null;
	}
	
	/**
	 * {@link Formulario} em processamento.
	 */
	public Formulario<?> getFormulario() {
		return formulario;
	}

	/**
	 * Rótulo do campo atual do laço.
	 */
	public String getRotulo() {
		return rotulo;
	}
	
	/**
	 * {@link Dado}s do campo atual do laço.
	 */
	public Dado[] getDados() {
		return dados;
	}
	
}
