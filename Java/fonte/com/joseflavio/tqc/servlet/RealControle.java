
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

import com.joseflavio.cultura.Cultura;
import com.joseflavio.cultura.NumeroTransformacao;
import com.joseflavio.cultura.TransformacaoException;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Real;
import com.joseflavio.util.TextoUtil;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
class RealControle implements DadoControle {

	private NumeroTransformacao transformacao;

	public RealControle() {
		transformacao = Cultura.getPadrao().novoNumeroTransformacao();
	}
	
	@Override
	public boolean isTransformacaoMultipla() {
		return false;
	}
	
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String conteudo, Dado destino ) {
		
		if( ! transformacao.getCultura().equals( tqc.getCultura() ) ){
			transformacao = tqc.getCultura().novoNumeroTransformacao();	
		}
		
		Real realDado = (Real) destino;
		
		realDado.setConteudoInvalido( null );
		
		if( conteudo == null || conteudo.length() == 0 ){
			realDado.setNumero( null );
			return;
		}
		
		try{
			
			preparar( realDado );
			
			double numero = transformacao.transformarReal( conteudo );
			
			realDado.setNumero( numero );
			
		}catch( TransformacaoException e ){
			realDado.setConteudoInvalido( conteudo );
		}
		
	}
	
	@Override
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String[] conteudo, Dado destino ) {
	}

	public String transcrever( HttpServletRequest request, TomaraQueCaia tqc, Dado dado ) {
		
		if( ! transformacao.getCultura().equals( tqc.getCultura() ) ){
			transformacao = tqc.getCultura().novoNumeroTransformacao();	
		}
		
		Real realDado = (Real) dado;
		
		if( realDado.getConteudoInvalido() != null ) return realDado.getConteudoInvalido().toString();
		
		try{
			
			preparar( realDado );
			
			Double numero = realDado.getNumero();
			
			return numero != null ? transformacao.transcrever( numero.doubleValue() ) : "";
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}

	/**
	 * @deprecated devido � HTML embutida.
	 */
	public void imprimir( HttpServletRequest request, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id, Writer saida ) throws IOException {
		
		Real realDado = (Real) dado;
		
		String texto = transcrever( request, tqc, realDado );
		if( texto == null ) texto = "";
		
		if( realDado.isEditavel() ){

			int maxCaracs = TomaraQueCaiaDesktopServlet.maxCaracteres( realDado );
			
			saida.write( "<input type=\"text\" id=\"" + id + "\" name=\"" + id + "\" value=\"" + texto + "\"" );
			
			if( realDado.getLarguraTextual() > 0 ) saida.write( " size=\"" + realDado.getLarguraTextual() + "\"" );
			if( maxCaracs > 0 ) saida.write( " maxlength=\"" + maxCaracs + "\"" );
			
			TomaraQueCaiaDesktopServlet.imprimirEstilo( realDado, "dadoTextoEditavel", saida );
				
			saida.write( " />" );
		
		}else{
			
			saida.write( "<span" );
			TomaraQueCaiaDesktopServlet.imprimirEstilo( realDado, "dadoTexto", saida );
			saida.write( ">" );
			
			saida.write( TextoUtil.limitarComprimento( texto, realDado.getLarguraTextual(), true ) );
			
			saida.write( "</span>" );
			
		}

	}
	
	private void preparar( Real real ) {
		
		int digitosNaFracao = real.getDigitosNaFracao();
		
		if( digitosNaFracao >= 0 ){
			transformacao.setMinimoDigitosNaFracao( digitosNaFracao );
			transformacao.setMaximoDigitosNaFracao( digitosNaFracao );
		}else{
			transformacao.setMinimoDigitosNaFracao( 0 );
			transformacao.setMaximoDigitosNaFracao( 10 );
		}
		
	}
	
	public void redirecionar( HttpServletRequest request, HttpServletResponse response, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id ) throws IOException {
	}
	
}
