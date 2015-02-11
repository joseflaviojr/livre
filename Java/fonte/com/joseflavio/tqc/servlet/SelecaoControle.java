
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

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Selecao;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
class SelecaoControle implements DadoControle {
	
	@Override
	public boolean isTransformacaoMultipla() {
		return false;
	}
	
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String conteudo, Dado destino ) {
		
		Selecao<?> selecaoDado = (Selecao<?>) destino;
		
		selecaoDado.setConteudoInvalido( null );
		
		if( conteudo == null || conteudo.length() == 0 ){
			selecaoDado.setIndiceSelecionado( -1 );
			return;
		}
		
		try{
			
			int indice = Integer.parseInt( conteudo );
			
			selecaoDado.setIndiceSelecionado( indice );
			
		}catch( Exception e ){
			selecaoDado.setConteudoInvalido( conteudo );
		}
		
	}
	
	@Override
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String[] conteudo, Dado destino ) {
	}

	public String transcrever( HttpServletRequest request, TomaraQueCaia tqc, Dado dado ) {
		return null;
	}

	/**
	 * @deprecated devido � HTML embutida.
	 */
	public void imprimir( HttpServletRequest request, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id, Writer saida ) throws IOException {
		
		Selecao<?> selecaoDado = (Selecao<?>) dado;
		
		if( selecaoDado.isEditavel() ){
			
			saida.write( "<select id=\"" + id + "\" name=\"" + id + "\"" );
			
			TomaraQueCaiaDesktopServlet.imprimirEstilo( selecaoDado, "dadoSelecaoEditavel", saida );
				
			saida.write( ">\n" );
			
			int total = selecaoDado.getTotalOpcoes();
			int selecionado = selecaoDado.getIndiceSelecionado();
			
			for( int i = 0; i < total; i++ ){
				
				saida.write( "<option value=\"" + i + "\"" );
				if( i == selecionado ) saida.write( " selected" );
				saida.write( ">" );
				
				imprimir( i, selecaoDado, saida );
				
				saida.write( "</option>\n" );
				
			}
			
			saida.write( "</select>" );
		
		}else{
			
			saida.write( "<span" );
			TomaraQueCaiaDesktopServlet.imprimirEstilo( selecaoDado, "dadoSelecao", saida );
			saida.write( ">" );
			
			imprimir( selecaoDado.getIndiceSelecionado(), selecaoDado, saida );
			
			saida.write( "</span>" );
			
		}

	}
	
	private void imprimir( int i, Selecao<?> selecaoDado, Writer saida ) throws IOException {
		
		if( i < 0 ) return;
		
		String texto = selecaoDado.getTexto( i );
		
		if( texto != null ){
			saida.write( texto );
			return;
		}
		
		Object opcao = selecaoDado.getOpcao( i );
		
		if( opcao != null ){
			saida.write( opcao.toString() );
			return;
		}
		
	}
	
	public void redirecionar( HttpServletRequest request, HttpServletResponse response, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id ) throws IOException {
	}

}
