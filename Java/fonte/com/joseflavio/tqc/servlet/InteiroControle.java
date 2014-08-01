
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
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
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
import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.util.TextoUtil;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
class InteiroControle implements DadoControle {
	
	private NumeroTransformacao transformacao;

	public InteiroControle() {

		transformacao = Cultura.getPadrao().novoNumeroTransformacao();
		transformacao.setMaximoDigitosNaFracao( 0 );
		transformacao.setMinimoDigitosNaFracao( 0 );
		transformacao.setUsarMilha( false );
		
	}
	
	@Override
	public boolean isTransformacaoMultipla() {
		return false;
	}
	
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String conteudo, Dado destino ) {
		
		Inteiro inteiroDado = (Inteiro) destino;
		
		inteiroDado.setConteudoInvalido( null );
		
		if( conteudo == null || conteudo.length() == 0 ){
			inteiroDado.setNumero( null );
			return;
		}
		
		try{
			
			long numero = transformacao.transformarInteiro( conteudo );
			
			inteiroDado.setNumero( numero );
			
		}catch( TransformacaoException e ){
			inteiroDado.setConteudoInvalido( conteudo );
		}
		
	}
	
	@Override
	public void transformar( HttpServletRequest request, TomaraQueCaia tqc, String[] conteudo, Dado destino ) {
	}

	public String transcrever( HttpServletRequest request, TomaraQueCaia tqc, Dado dado ) {
		
		Inteiro inteiroDado = (Inteiro) dado;
		
		if( inteiroDado.getConteudoInvalido() != null ) return inteiroDado.getConteudoInvalido().toString();
		
		try{
			
			Long numero = inteiroDado.getNumero();
			
			return numero != null ? transformacao.transcrever( numero.longValue() ) : "";
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}

	/**
	 * @deprecated devido à HTML embutida.
	 */
	public void imprimir( HttpServletRequest request, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id, Writer saida ) throws IOException {
		
		Inteiro inteiroDado = (Inteiro) dado;
		
		String texto = transcrever( request, tqc, inteiroDado );
		if( texto == null ) texto = "";
		
		if( inteiroDado.isEditavel() ){
			
			int maxCaracs = TomaraQueCaiaDesktopServlet.maxCaracteres( inteiroDado );
			
			saida.write( "<input type=\"text\" id=\"" + id + "\" name=\"" + id + "\" value=\"" + texto + "\"" );
			
			if( inteiroDado.getLarguraTextual() > 0 ) saida.write( " size=\"" + inteiroDado.getLarguraTextual() + "\"" );
			if( maxCaracs > 0 ) saida.write( " maxlength=\"" + maxCaracs + "\"" );
			
			TomaraQueCaiaDesktopServlet.imprimirEstilo( inteiroDado, "dadoTextoEditavel", saida );
				
			saida.write( " />" );
		
		}else{
			
			saida.write( "<span" );
			TomaraQueCaiaDesktopServlet.imprimirEstilo( inteiroDado, "dadoTexto", saida );
			saida.write( ">" );
			
			saida.write( TextoUtil.limitarComprimento( texto, inteiroDado.getLarguraTextual(), true ) );
			
			saida.write( "</span>" );
			
		}

	}
	
	public void redirecionar( HttpServletRequest request, HttpServletResponse response, TomaraQueCaia tqc, Viagem viagem, Informacao informacao, Dado dado, String id ) throws IOException {
	}

}
