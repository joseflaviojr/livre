
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
 * @author José Flávio de Souza Dias Júnior
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
