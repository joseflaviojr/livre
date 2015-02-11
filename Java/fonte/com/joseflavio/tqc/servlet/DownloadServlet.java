
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Arquivo;
import com.joseflavio.tqc.dado.Bruto;
import com.joseflavio.util.ArquivoUtil;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class DownloadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

		try{
		
			TomaraQueCaia tqc = (TomaraQueCaia) req.getSession().getAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_SESSAO + req.getParameter( "tqc" ) );
			if( tqc == null ) return;
			
			Viagem viagem = tqc.getViagem( req.getParameter( "viagem" ) );
			long passo = Long.parseLong( req.getParameter( "passo" ) );
			if( viagem.getPasso() != passo ) return;
	
			Informacao info = viagem.getAtual();
			int indice = Integer.parseInt( req.getParameter( "dado" ) );
			if( info == null || indice >= info.getTotalDados() ) return;
	
			Dado dado = info.getDado( indice );
			
			if( dado instanceof Bruto ){
			
				Bruto bruto = (Bruto) dado;
				byte[] valor = bruto.getValor();
				
				resp.setContentType( ArquivoUtil.getHTMLContentType( bruto.getValorRotulo() ) );
				resp.setHeader( "Content-Disposition", "attachment;filename=" + bruto.getValorRotulo() );
				resp.setContentLength( valor.length );
				
				OutputStream out = resp.getOutputStream();
				out.write( valor );
				out.flush();
			
			}else if( dado instanceof Arquivo ){
				
				Arquivo arquivo = (Arquivo) dado;
				File f = arquivo.getArquivo();
				
				resp.setContentType( ArquivoUtil.getHTMLContentType( f.getName() ) );
				resp.setHeader( "Content-Disposition", "attachment;filename=" + f.getName() );
				resp.setContentLength( (int) f.length() );
				
				FileInputStream in = new FileInputStream( f );
				OutputStream out = resp.getOutputStream();
				
				int ch;
				while( ( ch = in.read() ) != -1 ) out.write( ch );
				
				out.flush();
				in.close();
				
			}
		
		}catch( IOException e ){
			throw e;
		}catch( Exception e ){
			throw new ServletException( e );
		}
		
	}
	
	public void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		doGet( req, resp );
		
	}
	
}
