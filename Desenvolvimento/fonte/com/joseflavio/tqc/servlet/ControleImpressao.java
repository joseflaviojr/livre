
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

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.util.TextoUtil;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @deprecated devido � HTML embutida.
 */
class ControleImpressao implements Impressao {

	/**
	 * @deprecated devido � HTML embutida.
	 */
	public static void imprimir( TomaraQueCaia tqc, Writer saida ) throws IOException {
		
		saida.write( "<div align=\"left\" class=\"barraControle\">\n" );
		
		int totalViagens = tqc.getTotalViagens();
		Viagem v;
		
		for( int i = 0; i < totalViagens; i++ ){

			v = tqc.getViagem( i );

			if( v.getAtual() == null ) continue;
			
			saida.write( "<input type=\"submit\" id=\"irpara_" + v.getNome() + "\" name=\"irpara_" + v.getNome() + "\" value=\"" + TextoUtil.fixarComprimento( v.getAtual().getTitulo(), 20, true ) + "\"" );
			TomaraQueCaiaDesktopServlet.imprimirEstilo( null, v == tqc.getViagemAtiva() ? "botaoViagemAtiva" : "botaoViagem", saida );
			saida.write( " />" );
			
			saida.write( "<input type=\"submit\" id=\"fechar_" + v.getNome() + "\" name=\"fechar_" + v.getNome() + "\" value=\"x\"" );
			TomaraQueCaiaDesktopServlet.imprimirEstilo( null, v == tqc.getViagemAtiva() ? "botaoViagemAtiva" : "botaoViagem", saida );
			saida.write( " />" );
			
		}
		
		saida.write( "</div>\n" );
		
	}
	
	public void imprimir( HttpServletRequest request, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Writer saida ) throws IOException {
		
		imprimir( tqc, saida );
		
	}
	
}
