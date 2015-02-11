
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
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joseflavio.cultura.Cultura;
import com.joseflavio.cultura.DataTransformacao;
import com.joseflavio.cultura.TransformacaoException;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Data;
import com.joseflavio.util.TextoUtil;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
class DataControle implements DadoControle {
	
	private DataTransformacao transformacao;
	
	private static final int tamanhoData = 10;
	private static final int tamanhoHora = 5;
	private static final int tamanhoDataHora = 16;
	
	public DataControle() {
		transformacao = Cultura.getPadrao().novaDataTransformacaoNumerica();
	}
	
	@Override
	public boolean isTransformacaoMultipla() {
		return false;
	}
	
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String conteudo, Dado destino ) {
		
		if( ! transformacao.getCultura().equals( tqc.getCultura() ) ){
			transformacao = tqc.getCultura().novaDataTransformacaoNumerica();	
		}
		
		Data dataDado = (Data) destino;
		
		dataDado.setConteudoInvalido( null );
		
		if( conteudo == null || conteudo.length() == 0 ){
			dataDado.setData( null );
			return;
		}
		
		try{
			
			Date data;
			
			switch( dataDado.getTipo() ){
				
				case DATA :
					data = transformacao.transformarData( completar( conteudo ) );
					break;
					
				case HORA :
					data = transformacao.transformarHora( completar( conteudo ) );
					break;
					
				default :
					data = transformacao.transformarDataHora( completar( conteudo ) );
					break;
				
			}
			
			dataDado.setData( data );
			
		}catch( TransformacaoException e ){
			dataDado.setConteudoInvalido( conteudo );
		}
			
	}
	
	@Override
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String[] conteudo, Dado destino ) {
	}

	public String transcrever( HttpServletRequest request, TomaraQueCaia tqc, Dado dado ) {
		
		if( ! transformacao.getCultura().equals( tqc.getCultura() ) ){
			transformacao = tqc.getCultura().novaDataTransformacaoNumerica();	
		}
		
		Data dataDado = (Data) dado;

		if( dataDado.getConteudoInvalido() != null ) return dataDado.getConteudoInvalido().toString();
		
		Date data = dataDado.getData();
		if( data == null ) return "";
		
		try{
			
			switch( dataDado.getTipo() ){
				
				case DATA :
					return transformacao.transcreverData( data );
					
				case HORA :
					return transformacao.transcreverHora( data );
					
				default :
					return transformacao.transcreverDataHora( data );
				
			}
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
	
	/**
	 * @deprecated devido � HTML embutida.
	 */
	public void imprimir( HttpServletRequest request, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id, Writer saida ) throws IOException {
		
		Data dataDado = (Data) dado;
		
		String texto = transcrever( request, tqc, dataDado );
		if( texto == null ) texto = "";
		
		if( dataDado.isEditavel() ){
			
			saida.write( "<input type=\"text\" id=\"" + id + "\" name=\"" + id + "\" value=\"" + texto + "\"" );
			
			int tamanho = larguraTextual( dataDado );
			if( dataDado.getLarguraTextual() > tamanho ) tamanho = dataDado.getLarguraTextual();  
			
			saida.write( " size=\"" + tamanho + "\"" );
			saida.write( " maxlength=\"" + tamanho + "\"" );
			
			TomaraQueCaiaDesktopServlet.imprimirEstilo( dataDado, "dadoTextoEditavel", saida );
				
			saida.write( " />" );
		
		}else{
			
			saida.write( "<span" );
			TomaraQueCaiaDesktopServlet.imprimirEstilo( dataDado, "dadoTexto", saida );
			saida.write( ">" );
			
			saida.write( TextoUtil.limitarComprimento( texto, dataDado.getLarguraTextual(), true ) );
			
			saida.write( "</span>" );
			
		}

	}
	
	private int larguraTextual( Data dataDado ) {
		
		switch( dataDado.getTipo() ){
			
			case DATA :
				return tamanhoData;
				
			case HORA :
				return tamanhoHora;
				
			default :
				return tamanhoDataHora;
			
		}
			
	}
	
	public void redirecionar( HttpServletRequest request, HttpServletResponse response, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id ) throws IOException {
	}
	
	private static String completar( String dh ) {
		
		if( dh == null ) return null;
		
		dh = dh.trim();
		int len = dh.length();
		
		for( int i = 0; i < len; i++ ){
			if( ! Character.isDigit( dh.charAt( i ) ) ) return dh;
		}
		
		switch( len ){
			case 4 :
				return new StringBuilder( dh ).insert( 2, ':' ).toString();
			case 6 :
				return new StringBuilder( dh ).insert( 2, '/' ).insert( 5, '/' ).insert( 6, '2' ).insert( 7, '0' ).toString();
			case 8 :
				return new StringBuilder( dh ).insert( 2, '/' ).insert( 5, '/' ).toString();
			case 12 :
				return new StringBuilder( dh ).insert( 2, '/' ).insert( 5, '/' ).insert( 10, ' ' ).insert( 13, ':' ).toString();
			case 13 :
				return new StringBuilder( dh ).insert( 2, '/' ).insert( 5, '/' ).insert( 13, ':' ).toString();
		}
		
		return dh;
		
	}

}
