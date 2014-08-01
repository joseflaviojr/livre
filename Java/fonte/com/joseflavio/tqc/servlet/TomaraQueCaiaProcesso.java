
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.joseflavio.tqc.AnexoControleDeFoco;
import com.joseflavio.tqc.AnexoRolagem;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Edicao;
import com.joseflavio.tqc.EspacoLateral;
import com.joseflavio.tqc.EspacoTextual;
import com.joseflavio.tqc.EstiloFonte;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.Menu;
import com.joseflavio.tqc.OpcaoDeMenu;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.aplicacao.ComandoImplementado;
import com.joseflavio.tqc.aplicacao.Formulario;
import com.joseflavio.tqc.aplicacao.Listagem;
import com.joseflavio.tqc.dado.Arquivo;
import com.joseflavio.tqc.dado.Binario;
import com.joseflavio.tqc.dado.Bruto;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.tqc.dado.Data;
import com.joseflavio.tqc.dado.Imagem;
import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.tqc.dado.Linha;
import com.joseflavio.tqc.dado.LinhaFim;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.dado.Nativo;
import com.joseflavio.tqc.dado.Objeto;
import com.joseflavio.tqc.dado.QuebraDeLinha;
import com.joseflavio.tqc.dado.Real;
import com.joseflavio.tqc.dado.Selecao;
import com.joseflavio.tqc.dado.SelecaoMultipla;
import com.joseflavio.tqc.dado.SelecionavelTexto;
import com.joseflavio.tqc.dado.Senha;
import com.joseflavio.tqc.dado.Tabela;
import com.joseflavio.tqc.dado.TabelaColuna;
import com.joseflavio.tqc.dado.TabelaColunaFim;
import com.joseflavio.tqc.dado.TabelaFim;
import com.joseflavio.tqc.dado.TabelaLinha;
import com.joseflavio.tqc.dado.TabelaLinhaFim;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.util.Lista;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
@SuppressWarnings("deprecation")
class TomaraQueCaiaProcesso { //TODO Revisar sincronização
	
	Semaphore semaforo = new Semaphore( 1 );
	
	private Viagem viagem;
	private Informacao info;
	private Dado dado;
	private Comando comando;
	private DadoControle controle;
	private int totalViagens;
	private int totalDados;
	
	private TextoControle textoControle;
	private SelecionavelTextoControle selecionavelTextoControle;
	private InteiroControle inteiroControle;
	private RealControle realControle;
	private DataControle dataControle;
	private SelecaoControle selecaoControle;
	private SelecaoMultiplaControle selecaoMultiplaControle;
	private ComandoControle comandoControle;
	private ObjetoControle objetoControle;
	private ImagemControle imagemControle;
	private SenhaControle senhaControle;
	private BinarioControle binarioControle;
	private BrutoControle brutoControle;
	private ArquivoControle arquivoControle;
	private NativoControle nativoControle;
	
	void processar( TomaraQueCaia tqc, HttpServletRequest req, HttpServletResponse resp, HttpSession sessao, ServletContext servletContext, PrintWriter saida ) throws TomaraQueCaiaException, ValidacaoException, ServletException, IOException {
		
		/*------------------------------*/
		
		if( isEncerrada( tqc ) ){
			encerrar( tqc, resp, sessao, saida, true );
			return;
		}
		
		/*------------------------------*/

		if( ! filtrar( tqc, resp, sessao, saida ) ) return;
		
		/*------------------------------*/
		
		String informacaoNome = req.getParameter( "informacaoTQC" );
		
		if( ( viagem.getNome() + "@" + info.getClass().getName() + "@" + viagem.getPasso() ).equals( informacaoNome ) ){
			
			//Rolagem
			
			try{
				AnexoRolagem rolagem = info.getAnexo( AnexoRolagem.class, true );
				rolagem.setHorizontal( new Integer( req.getParameter( "rolagemHorizontal" ) ) );
				rolagem.setVertical( new Integer( req.getParameter( "rolagemVertical" ) ) );
			}catch( Exception e ){
			}
			
			//Foco
			
			try{
				AnexoControleDeFoco foco = info.getAnexo( AnexoControleDeFoco.class, true );
				foco.setElemento( req.getParameter( "focoDado" ) );
			}catch( Exception e ){
			}
			
			//Dados
			
			comando = null;
		
			totalDados = info.getTotalDados();
			String paramId;
			Object paramValor;
			
			for( int d = 0; d < totalDados; d++ ){
				
				dado = info.getDado( d );
				controle = getControle( dado );
				
				if( controle == null ) continue;
				
				paramId = TomaraQueCaiaDesktopServlet.PREFIXO_DADO + d;
				
				if( controle.isTransformacaoMultipla() ){
					
					String[] paramArray = req.getParameterValues( paramId );
					
					if( paramArray == null || paramArray.length == 0 ){
						paramValor = req.getParameter( paramId );
						if( paramValor != null ) paramValor = paramValor.toString().split( "_///_" );
						
					}else if( paramArray[0].equals( "TQC#SELECAOMULTIPLA" ) ){
						int totalValores = Integer.parseInt( req.getParameter( paramId + "_total" ) );
						List<String> valores = new Lista<String>( totalValores );
						for( int i = 0; i < totalValores; i++ ){
							String valor = req.getParameter( paramId + "_" + i );
							if( valor != null && valor.equals( "v" ) ) valores.add( "" + i );
						}
						paramValor = valores.toArray( new String[ valores.size() ] );
						
					}else{
						paramValor = paramArray;
					}
					
				}else{
					
					paramValor = req.getParameter( paramId );
					if( paramValor == null ) paramValor = req.getParameter( paramId + ".x" );
					
				}
				
				controle.redirecionar( req, resp, tqc, viagem, info, dado, paramId );
				
				if( dado instanceof Comando ){
					
					if( paramValor != null ) comando = (Comando) dado;
				
				}else if( paramValor != null || ( dado instanceof Edicao && ((Edicao)dado).isEditavel() ) ){
					
					if( controle.isTransformacaoMultipla() ){
						controle.transformar( req, tqc, (String[]) paramValor, dado );
					}else{
						controle.transformar( req, tqc, (String) paramValor, dado );
					}
					
				}
			
			}
			
			if( comando != null && comando.isVisivel() ) info.acao( tqc, viagem, comando );
				
		}
		
		if( resp.isCommitted() ) return;
		
		/*------------------------------*/
		
		if( isEncerrada( tqc ) ){
			encerrar( tqc, resp, sessao, saida, true );
			return;
		}
		
		/*------------------------------*/

		String comandoViagem = req.getParameter( "comandoViagem" );
		if( comandoViagem == null ) comandoViagem = "";
		
		/*------------------------------*/
		
		if( comandoViagem.startsWith( "confirmar" ) ){
			
			boolean buscarComando = true;
			
			int alvoIndice = -1;
			int indiceE = comandoViagem.indexOf( '@' );
			if( indiceE > -1 && indiceE < ( comandoViagem.length() - 1 ) ){
				try{
					alvoIndice = Integer.parseInt( comandoViagem.substring( indiceE + 1 + TomaraQueCaiaDesktopServlet.PREFIXO_DADO.length() ) );
				}catch( NumberFormatException e ){
					alvoIndice = -1;
				}
			}
			
			Dado alvo = alvoIndice != -1 ? info.getDado( alvoIndice ) : null;
			
			if( alvo != null ){
				
				if( alvo instanceof Comando && alvo.isVisivel() ){
					
					buscarComando = false;
					info.acao( tqc, viagem, (Comando) alvo );
					
				}else if( alvo instanceof ComplexoDado && ((ComplexoDado)alvo).getComando() != null ){
					
					Comando comandoAuxiliar = ((ComplexoDado)alvo).getComando();
					if( comandoAuxiliar.isVisivel() ){
						if( info.contem( comandoAuxiliar ) ){
							buscarComando = false;
							info.acao( tqc, viagem, comandoAuxiliar );
						}else if( comandoAuxiliar instanceof ComandoImplementado ){
							buscarComando = false;
							((ComandoImplementado)comandoAuxiliar).acao( viagem );
						}
					}
					
				}

			}
			
			if( buscarComando ){
				
				Dado dado;
				Comando comando = null, primeiro = null;
				int buscaI = 0, buscaF = info.getTotalDados() - 1;
				
				if( alvo != null ) buscaI = info.getIndice( alvo ) + 1;
				
				for( ; buscaI <= buscaF; buscaI++ ){
					
					dado = info.getDado( buscaI );
					if( !( dado instanceof Comando ) ) continue;
					
					comando = (Comando) dado;
					if( primeiro == null ) primeiro = comando;
					if( comando.getFuncaoTipo() == Funcao.CONFIRMAR && comando.isVisivel() ) break;
					comando = null;
					
				}
				
				if( comando != null ) info.acao( tqc, viagem, comando );
				else if( primeiro != null && primeiro.isVisivel() ) info.acao( tqc, viagem, primeiro );
			
			}
			
		}else if( comandoViagem.startsWith( "cancelar" ) ){
			
			boolean cancelada = false;
			
			for( Comando comando : info.getDados( Comando.class ) ){
				if( comando.getFuncaoTipo() == Funcao.CANCELAR && comando.isVisivel() ){
					cancelada = true;
					info.acao( tqc, viagem, comando );
					break;
				}
			}
			
			if( ! cancelada ){
				info.sair( viagem );
			}
			
		}else if( comandoViagem.startsWith( "avancar" ) ){
			
			for( Comando comando : info.getDados( Comando.class ) ){
				if( comando.getFuncaoTipo() == Funcao.AVANCAR && comando.isVisivel() ){
					info.acao( tqc, viagem, comando );
					break;
				}
			}
			
		}else if( comandoViagem.startsWith( "voltar" ) ){
			
			for( Comando comando : info.getDados( Comando.class ) ){
				if( comando.getFuncaoTipo() == Funcao.VOLTAR && comando.isVisivel() ){
					info.acao( tqc, viagem, comando );
					break;
				}
			}
			
		}else if( comandoViagem.startsWith( "comando" ) ){
			
			String comandoNome = null;
			int indiceE = comandoViagem.indexOf( '@' );
			if( indiceE > -1 && indiceE < ( comandoViagem.length() - 1 ) ){
				comandoNome = comandoViagem.substring( indiceE + 1 );
			}
			
			if( comandoNome != null ){
				Comando comando = info.getComando( comandoNome ); 
				if( comando != null && comando.isVisivel() ){
					info.acao( tqc, viagem, comando );
				}
			}
			
		}else if( comandoViagem.startsWith( "menu" ) ){
			
			String identificacao = null;
			int indiceE = comandoViagem.indexOf( '@' );
			if( indiceE > -1 && indiceE < ( comandoViagem.length() - 1 ) ){
				identificacao = comandoViagem.substring( indiceE + 1 );
			}
			
			if( identificacao != null ){
				Menu menu = info.getMenu();
				if( menu != null ){
					OpcaoDeMenu om = menu.getOpcaoDeMenu( identificacao );
					if( om != null && om instanceof Comando ) info.acao( tqc, viagem, (Comando) om );
				}
			}
			
		}else if( comandoViagem.equals( "encerrarAplicacao" ) ){
			
			encerrar( tqc, resp, sessao, saida, true );
			return;
			
		}else if( comandoViagem.equals( "mostrarAjuda" ) ){
			
			Informacao ajuda = info.criarAjuda( tqc );
			if( ajuda != null ) viagem.visitar( ajuda );
			
		}
		
		/*------------------------------*/
		
		if( isEncerrada( tqc ) ){
			encerrar( tqc, resp, sessao, saida, true );
			return;
		}
		
		/*------------------------------*/
		
		totalViagens = tqc.getTotalViagens();
		
		for( int i = 0; i < totalViagens; i++ ){
			
			viagem = tqc.getViagem( i );
			String comandoEsperado = "fechar_" + viagem.getNome();
			
			if( req.getParameter( comandoEsperado ) != null || comandoViagem.equals( comandoEsperado ) ){
				
				viagem.encerrar();
				
				if( totalViagens == 1 ){
					encerrar( tqc, resp, sessao, saida, true );
					return;
				}
					
				break;
				
			}
			
		}
		
		/*------------------------------*/
		
		totalViagens = tqc.getTotalViagens();
		
		for( int i = 0; i < totalViagens; i++ ){

			viagem = tqc.getViagem( i );
			String comandoEsperado = "irpara_" + viagem.getNome();
			
			if( req.getParameter( comandoEsperado ) != null || comandoViagem.equals( comandoEsperado ) ){
				viagem.ativar();
				break;
			}
			
		}
		
		/*------------------------------*/
		
		while( true ){
			
			if( ! filtrar( tqc, resp, sessao, saida ) ) return;
			
			Informacao tmpInfo = info;
			
			if( ! antesDeMostrar( tqc, resp, sessao, saida ) ) return;
			
			if( tmpInfo == info ) break;
		
		}
		
		/*------------------------------*/
		
		if( isEncerrada( tqc ) ){
			encerrar( tqc, resp, sessao, saida, true );
			return;
		}
		
		/*------------------------------*/
		
		if( info.isPersonalizada() ){
			
			String jsp = info.getPersonalizadaInterface();
			if( jsp == null ){
				String pacote = info.getClass().getPackage().getName();
				jsp = ( pacote != null && pacote.length() > 0 ? pacote + "/" : "" ) + info.getClass().getSimpleName();
			}
			jsp = "jsp/" + jsp + ".jsp";
			
			if( servletContext.getResource( "/" + jsp ) != null ){
				
				resp.sendRedirect( jsp + "?tqc=" + tqc.getClass().getName() );
				
				tqc.informacaoMostrada( viagem, info ); //TODO Colocar em filtro web
				return;
				
			}
			
		}
		
		/*------------------------------*/
		
		String pele = info.getPele() != null ? info.getPele() : tqc.getPele();
		
		if( info instanceof Formulario<?> && servletContext.getResource( "/" + pele + "/Formulario.jsp" ) != null ){
			
			resp.sendRedirect( pele + "/Formulario.jsp?tqc=" + tqc.getClass().getName() );
			
			tqc.informacaoMostrada( viagem, info ); //TODO Colocar em filtro web
			return;
			
		}else if( info instanceof Listagem<?,?> && servletContext.getResource( "/" + pele + "/Listagem.jsp" ) != null ){
			
			resp.sendRedirect( pele + "/Listagem.jsp?tqc=" + tqc.getClass().getName() );
			
			tqc.informacaoMostrada( viagem, info ); //TODO Colocar em filtro web
			return;
		
		}else if( servletContext.getResource( "/" + pele + "/Informacao.jsp" ) != null ){
			
			resp.sendRedirect( pele + "/Informacao.jsp?tqc=" + tqc.getClass().getName() );
			
			tqc.informacaoMostrada( viagem, info ); //TODO Colocar em filtro web
			return;
			
		}
		
		/*------------------------------*/
		
		saida.write( "<!DOCTYPE html>\n" );
		saida.write( "<html>\n" );
		
		saida.write( "<head>\n" );
		saida.write( "<title>" + tqc.getTitulo() + "</title>\n" );
		saida.write( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n" );
		
		/*------------------------------*/
		
		saida.write( "<link href=\"css/tqc.css\" rel=\"stylesheet\" type=\"text/css\" />\n" );
		
		EstiloFonte[] estiloFontes = tqc.listarEstiloFontes();
		for( int i = 0; i < estiloFontes.length; i++ ){
			saida.write( "<link href=\"css/" + estiloFontes[i].getNome() + ".css\" rel=\"stylesheet\" type=\"text/css\" />\n" );
		}
		
		/*------------------------------*/
		
		saida.write( "<script type=\"text/javascript\" src=\"script/dado.js\"></script>\n" );
		saida.write( "</head>\n" );
		
		/*------------------------------*/
		
		AlertaImpressao.imprimir( viagem.removerAlerta(), saida );
		
		/*------------------------------*/
		
		saida.write( "<body class=\"fundoGeral\">\n" );

		saida.write( "<form id=\"formTQC\" name=\"formTQC\" method=\"POST\">\n" );

		IdentificacaoImpressao.imprimir( viagem, info, saida );
		saida.write( "\n" );
		
		/*------------------------------*/
		
		ControleImpressao.imprimir( tqc, saida );
		
		/*------------------------------*/
		
		saida.write( "<div class=\"fundoAplicacao\">\n" );
		
		totalDados = info.getTotalDados();
		Dado ultimoDado = null;
		
		for( int d = 0; d < totalDados; d++ ){
			
			dado = info.getDado( d );
			
			if( ! dado.isVisivel() ) continue;
			
			if( dado instanceof Linha ){

				imprimir( (Linha) dado, ultimoDado, saida );
				
			}else if( dado instanceof LinhaFim ){

				saida.write( "</div>\n" );
				
			}else if( dado instanceof QuebraDeLinha ){

				saida.write( "<br>" );
				
			}else if( dado instanceof Tabela ){

				imprimir( (Tabela) dado, saida );
				
			}else if( dado instanceof TabelaLinha ){

				imprimir( (TabelaLinha) dado, saida );
				
			}else if( dado instanceof TabelaColuna ){

				imprimir( (TabelaColuna) dado, saida );
				
			}else if( dado instanceof TabelaColunaFim ){

				saida.write( "</td>\n" );
				
			}else if( dado instanceof TabelaLinhaFim ){

				saida.write( "</tr>\n" );
				
			}else if( dado instanceof TabelaFim ){

				saida.write( "</table>\n" );
				
			}else if( dado instanceof Marcador ){
				
			}else if( dado instanceof EspacoTextual ){
				
				int quant = ((EspacoTextual)dado).getQuantidade();
				for( int i = 0; i < quant; i++ ) saida.write( "&nbsp;" );
			
			}else{

				controle = getControle( dado );
				if( controle != null ) controle.imprimir( req, tqc, viagem, info, dado, TomaraQueCaiaDesktopServlet.PREFIXO_DADO + d, saida );
				
			}
			
			if( dado instanceof EspacoLateral && ((EspacoLateral)dado).isEspacoTextualPosterior() ) saida.write( "&nbsp;" );
			
			if( ! ( dado instanceof Marcador ) ) ultimoDado = dado;
			
		}
			
		saida.write( "</div>\n" );

		/*------------------------------*/
		
		saida.write( "</form>\n" );
		
		saida.write( "</body>\n" );
		saida.write( "</html>\n" );
		
		/*------------------------------*/
		
		tqc.informacaoMostrada( viagem, info );
		
		/*------------------------------*/
		
	}

	private boolean filtrar( TomaraQueCaia tqc, HttpServletResponse resp, HttpSession sessao, PrintWriter saida ) throws TomaraQueCaiaException, ServletException, IOException {
		return processarEventos( tqc, resp, sessao, saida, true, false );
	}
	
	private boolean antesDeMostrar( TomaraQueCaia tqc, HttpServletResponse resp, HttpSession sessao, PrintWriter saida ) throws TomaraQueCaiaException, ServletException, IOException {
		return processarEventos( tqc, resp, sessao, saida, false, true );
	}
	
	private boolean processarEventos( TomaraQueCaia tqc, HttpServletResponse resp, HttpSession sessao, PrintWriter saida, boolean filtragem, boolean antesDeMostrar ) throws TomaraQueCaiaException, ServletException, IOException {
		
		if( tqc.isAutoEncerrarViagensVazias() ) tqc.encerrarViagensVazias();
		
		viagem = tqc.getViagemAtiva();
		if( viagem == null ){
			encerrar( tqc, resp, sessao, saida, true );
			return false;
		}
		
		do{
			
			info = viagem.getAtual();
			if( info == null ) return false;
			
			if( filtragem ){
				if( ! tqc.filtrar( viagem ) ) tqc.permissaoNegada( viagem, info );
			}
			
			if( antesDeMostrar ){
				info.antesDeMostrar( tqc, viagem );
			}
			
			if( filtragem || antesDeMostrar ){
				
				if( tqc.isAutoEncerrarViagensVazias() ) tqc.encerrarViagensVazias();
			
				viagem = tqc.getViagemAtiva();
				if( viagem == null ){
					encerrar( tqc, resp, sessao, saida, true );
					return false;
				}
			
			}
		
		}while( info != viagem.getAtual() );
		
		return true;
		
	}
	
	private boolean isEncerrada( TomaraQueCaia tqc ) {
		return tqc.isEncerrada() || tqc.getTotalViagens() == 0 || tqc.getViagemAtiva() == null;
	}
	
	void encerrar( TomaraQueCaia tqc, HttpServletResponse resp, HttpSession sessao, PrintWriter saida, boolean imprimirPaginaFinal ) throws ServletException, IOException {
		
		String tqcClassName = tqc.getClass().getName();
		String pele = tqc.getPele();
		
		sessao.removeAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_SESSAO + tqcClassName );
		sessao.removeAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_URL + tqcClassName );
		sessao.removeAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_QUERY + tqcClassName );
		sessao.removeAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_PROCESSO + tqcClassName );
		
		if( ! sessao.getAttributeNames().hasMoreElements() ) sessao.invalidate();
		
		if( ! isEncerrada( tqc ) ) tqc.encerrar();
		
		if( imprimirPaginaFinal ) resp.sendRedirect( ( pele != null ? pele : "padrao" ) + "/Fim.jsp?tqc=" + tqcClassName );
		
	}
	
	private boolean fimDeLinha( Dado dado ) {
		
		if( dado == null ) return false;
		
		return dado instanceof QuebraDeLinha || dado instanceof LinhaFim;
		
	}
	
	private void imprimir( Linha dado, Dado anterior, PrintWriter saida ) throws ValidacaoException, IOException {

		if( anterior != null && ! fimDeLinha( anterior ) ) saida.write( "<br>\n" );
		
		saida.write( "<div" );
		
		TomaraQueCaiaDesktopServlet.imprimirEstilo( dado, null, saida );
		
		saida.write( ">" );

	}
	
	private void imprimir( Tabela dado, PrintWriter saida ) throws ValidacaoException, IOException {
		
		saida.write( "<table cellspacing=\"2\" cellpadding=\"2\"" );
		
		TomaraQueCaiaDesktopServlet.imprimirEstilo( dado, "dadoTabela", saida );
		
		saida.write( ">\n" );
		
	}
	
	private void imprimir( TabelaLinha dado, PrintWriter saida ) throws ValidacaoException, IOException {
		
		saida.write( "<tr" );

		TomaraQueCaiaDesktopServlet.imprimirEstilo( dado, null, saida );
			
		saida.write( ">\n" );
		
	}
	
	private void imprimir( TabelaColuna dado, PrintWriter saida ) throws ValidacaoException, IOException {

		saida.write( "<td" );

		TomaraQueCaiaDesktopServlet.imprimirEstilo( dado, null, saida );
			
		saida.write( ">" );
		
	}
	
	DadoControle getControle( Dado dado ) {
		
		if( dado instanceof Texto ){
			if( textoControle == null ) textoControle = new TextoControle();
			return textoControle;
			
		}else if( dado instanceof SelecionavelTexto ){
			if( selecionavelTextoControle == null ) selecionavelTextoControle = new SelecionavelTextoControle();
			return selecionavelTextoControle;
			
		}else if( dado instanceof Comando ){
			if( comandoControle == null ) comandoControle = new ComandoControle();
			return comandoControle;
			
		}else if( dado instanceof Inteiro ){
			if( inteiroControle == null ) inteiroControle = new InteiroControle();
			return inteiroControle;

		}else if( dado instanceof Real ){
			if( realControle == null ) realControle = new RealControle();
			return realControle;

		}else if( dado instanceof Data ){
			if( dataControle == null ) dataControle = new DataControle();
			return dataControle;

		}else if( dado instanceof Selecao<?> ){
			if( selecaoControle == null ) selecaoControle = new SelecaoControle();
			return selecaoControle;
			
		}else if( dado instanceof SelecaoMultipla<?> ){
			if( selecaoMultiplaControle == null ) selecaoMultiplaControle = new SelecaoMultiplaControle();
			return selecaoMultiplaControle;

		}else if( dado instanceof Objeto ){
			if( objetoControle == null ) objetoControle = new ObjetoControle();
			return objetoControle;

		}else if( dado instanceof Imagem ){
			if( imagemControle == null ) imagemControle = new ImagemControle();
			return imagemControle;

		}else if( dado instanceof Senha ){
			if( senhaControle == null ) senhaControle = new SenhaControle();
			return senhaControle;
			
		}else if( dado instanceof Binario ){
			if( binarioControle == null ) binarioControle = new BinarioControle();
			return binarioControle;
			
		}else if( dado instanceof Bruto ){
			if( brutoControle == null ) brutoControle = new BrutoControle();
			return brutoControle;
			
		}else if( dado instanceof Arquivo ){
			if( arquivoControle == null ) arquivoControle = new ArquivoControle();
			return arquivoControle;
			
		}else if( dado instanceof Nativo ){
			if( nativoControle == null ) nativoControle = new NativoControle();
			return nativoControle;
		}
		
		return null;
		
	}

}
