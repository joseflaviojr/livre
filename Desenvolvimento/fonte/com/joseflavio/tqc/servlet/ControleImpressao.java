
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

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.util.TextoUtil;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @deprecated devido à HTML embutida.
 */
class ControleImpressao implements Impressao {

	/**
	 * @deprecated devido à HTML embutida.
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
