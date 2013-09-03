
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
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joseflavio.tqc.Aparencia;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Arquivo;
import com.joseflavio.tqc.dado.Bruto;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
class UploadControle implements DadoControle {
	
	public static final String PAGINA_UPLOAD = "Upload.jsp";
	public static final String PAGINA_DOWNLOAD = "tqcdownload";
	
	@Override
	public boolean isTransformacaoMultipla() {
		return false;
	}
	
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String conteudo, Dado destino ) {
	}
	
	@Override
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String[] conteudo, Dado destino ) {
	}

	public String transcrever( HttpServletRequest request, TomaraQueCaia tqc, Dado dado ) {
		
		String texto = null;
		
		if( dado instanceof Bruto ){
			Bruto brutoDado = (Bruto) dado;
			texto = brutoDado.getValor() != null ? brutoDado.getValorRotulo() : null;	
		}else{
			Arquivo arquivoDado = (Arquivo) dado;
			texto = arquivoDado.getArquivo() != null ? arquivoDado.getArquivo().getName() : null;
		}
		
		return texto != null ? texto : "";
		
	}

	/**
	 * @deprecated devido à HTML embutida.
	 */
	public void imprimir( HttpServletRequest request, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id, Writer saida ) throws IOException {
		
		ComplexoDado complexoDado = (ComplexoDado) dado;
		String texto = null;
		
		if( dado instanceof Bruto ){
			Bruto d = (Bruto) dado;
			Aparencia<Bruto> aparencia = d.getAparencia();
			texto = aparencia != null ? aparencia.texto( d ) : transcrever( request, tqc, d );
		}else{
			Arquivo d = (Arquivo) dado;
			Aparencia<Arquivo> aparencia = d.getAparencia();
			texto = aparencia != null ? aparencia.texto( d ) : transcrever( request, tqc, d );
		}

		if( complexoDado.isEditavel() ){
			
			if( texto.length() == 0 ) texto = "            ";
			
			saida.write( "<input type=\"submit\" id=\"" + id + "\" name=\"" + id + "\" value=\"" + texto + "\" class=\"dadoComando\" />" );
			
			if( complexoDado.getConteudo() != null ){
				String link = request.getContextPath() + "/" + PAGINA_DOWNLOAD + "?tqc=" + tqc.getClass().getName() + "&viagem=" + viagem.getNome() + "&passo=" + viagem.getPasso() + "&dado=" + informacao.getIndice( dado );
				saida.write( "&nbsp;<a href=\"" + link + "\" target=\"_blank\">Abrir</a>" );	
			}
			
		}else if( complexoDado.getConteudo() != null ){
			
			String link = request.getContextPath() + "/" + PAGINA_DOWNLOAD + "?tqc=" + tqc.getClass().getName() + "&viagem=" + viagem.getNome() + "&passo=" + viagem.getPasso() + "&dado=" + informacao.getIndice( dado );
			saida.write( "<a href=\"" + link + "\" target=\"_blank\">" + texto + "</a>" );
			
		}
		
	}
	
	public void redirecionar( HttpServletRequest request, HttpServletResponse response, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id ) throws IOException {
		
		if( request.getParameter( id ) != null ){
			String pele = informacao.getPele() != null ? informacao.getPele() : tqc.getPele();
			response.sendRedirect( pele + "/" + PAGINA_UPLOAD + "?tqc=" + tqc.getClass().getName() + "&viagem=" + viagem.getNome() + "&passo=" + viagem.getPasso() + "&dado=" + informacao.getIndice( dado ) );
		}
		
	}
	
}
