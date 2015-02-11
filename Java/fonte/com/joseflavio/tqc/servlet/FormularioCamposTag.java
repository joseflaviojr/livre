
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

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.aplicacao.Formulario;

/**
 * La�o para cada campo {@link Formulario#getTotalDeCamposVisiveis() vis�vel} de {@link Formulario}.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * R�tulo do campo atual do la�o.
	 */
	public String getRotulo() {
		return rotulo;
	}
	
	/**
	 * {@link Dado}s do campo atual do la�o.
	 */
	public Dado[] getDados() {
		return dados;
	}
	
}
