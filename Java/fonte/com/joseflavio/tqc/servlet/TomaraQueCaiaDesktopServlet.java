
/*
 *  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joseflavio.tqc.Estilo;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.ValidavelDado;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC;
import com.joseflavio.validacao.TextoLimite;

/**
 * Implementa��o que executa {@link TomaraQueCaia}'s em Desktops sobre a plataforma {@link Servlet}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class TomaraQueCaiaDesktopServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String PREFIXO_TQC_SESSAO = "tqc_";
	public static final String PREFIXO_TQC_URL = "tqc_url_";
	public static final String PREFIXO_TQC_QUERY = "tqc_query_";
	public static final String PREFIXO_TQC_PROCESSO = "tqc_proc_";
	public static final String PREFIXO_TQC_BILHETE = "tqc_bilhete_";
	public static final String PREFIXO_DADO = "dado";
	public static final String PREFIXO_PARAMETRO = "tqc";
	
	private Class<? extends TomaraQueCaia> tqcClasse;
	private String tqcClasseName;
	
	public TomaraQueCaiaDesktopServlet( Class<? extends TomaraQueCaia> tqcClasse ) {
		
		this.tqcClasse = tqcClasse;
		this.tqcClasseName = tqcClasse.getName();
		
	}
	
	public void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		resp.setContentType( "text/html; charset=iso-8859-1" );
		
		HttpSession sessao = req.getSession( true );
		PrintWriter saida = resp.getWriter();
		
//		sessao.setMaxInactiveInterval( 15 * 60 );
		
		TomaraQueCaia tqc = (TomaraQueCaia) sessao.getAttribute( PREFIXO_TQC_SESSAO + tqcClasseName );
		TomaraQueCaiaProcesso proc = (TomaraQueCaiaProcesso) sessao.getAttribute( PREFIXO_TQC_PROCESSO + tqcClasseName );
		
		String query = (String) sessao.getAttribute( PREFIXO_TQC_QUERY + tqcClasseName );
		String queryAtual = req.getQueryString();
		
		if( tqc == null ){
			
			try{
				
				tqc = tqcClasse.newInstance();
				tqc.setRaiz( new File( getServletContext().getRealPath( "/" ) ) );
				
				Map<String, String> parametros = new HashMap<String, String>();
				Enumeration<?> parameterNames = req.getParameterNames();
				while( parameterNames.hasMoreElements() ){
					String chave = (String) parameterNames.nextElement();
					if( chave.startsWith( PREFIXO_PARAMETRO ) ){
						parametros.put( chave.substring( PREFIXO_PARAMETRO.length() ), req.getParameter( chave ) );
					}
				}
				
				proc = new TomaraQueCaiaProcesso();
				
				tqc.inicio( parametros );
				
				String url = req.getRequestURL() + ( queryAtual != null && queryAtual.length() > 0 ? "?" + queryAtual : "" );
				String banner = tqc instanceof AplicacaoTQC ? ((AplicacaoTQC)tqc).getBanner() : null;
				
				sessao.setAttribute( PREFIXO_TQC_SESSAO + tqcClasseName, tqc );
				sessao.setAttribute( PREFIXO_TQC_URL + tqcClasseName, url );
				sessao.setAttribute( PREFIXO_TQC_QUERY + tqcClasseName, queryAtual );
				sessao.setAttribute( PREFIXO_TQC_PROCESSO + tqcClasseName, proc );
				sessao.setAttribute( PREFIXO_TQC_BILHETE + tqcClasseName, new Bilhete( url, tqc.getTitulo(), banner ) );

			}catch( Exception e ){
				throw new ServletException( e );
			}
			
		}else{
			
			if( ! ( query == queryAtual || ( query != null && queryAtual != null && query.equals( queryAtual ) ) ) ){
				proc.encerrar( tqc, resp, sessao, saida, false );
				doGet( req, resp );
				return;
			}
			
		}
		
		if( proc.semaforo.tryAcquire() ){

			try{

				proc.processar( tqc, req, resp, sessao, getServletContext(), saida );
			
			}catch( ServletException e ){
				throw e;
				
			}catch( IOException e ){
				throw e;
				
			}catch( Exception e ){
				throw new ServletException( e );
				
			}finally{
				proc.semaforo.release();
			}

		}else{
			
			resp.sendRedirect( req.getContextPath() + "/" + tqc.getPele() + "/Espera.jsp?tqc=" + tqcClasseName );
			return;
			
		}
		
	}
	
	public void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		doGet( req, resp );
		
	}
	
	public static void imprimirEstilo( Estilo estilo, String padrao, Writer saida ) throws IOException {
		
		if( estilo == null || estilo.getEstilo() == null ){
			
			if( padrao != null ) saida.write( " class=\"" + padrao + "\"" );
			
		}else{
			
			saida.write( " class=\"" + estilo.getEstilo() + "\"" );
			
		}
		
	}
	
	public static int maxCaracteres( ValidavelDado dado ) {
		
		TextoLimite v = (TextoLimite) dado.getValidacao( TextoLimite.class );
		
		return v != null ? v.getMaxCaracteres() : 0;
		
	}
	
	public static Informacao getInformacaoAtual( HttpSession session, HttpServletRequest request ) {
		String tqcClassName = request.getParameter( "tqc" );
		TomaraQueCaia tqc = (TomaraQueCaia) session.getAttribute( PREFIXO_TQC_SESSAO + tqcClassName );
		return tqc.getViagemAtiva().getAtual();
	}
	
}
