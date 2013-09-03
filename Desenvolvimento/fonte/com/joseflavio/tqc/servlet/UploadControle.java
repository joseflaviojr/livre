
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
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
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
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * @deprecated devido � HTML embutida.
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
