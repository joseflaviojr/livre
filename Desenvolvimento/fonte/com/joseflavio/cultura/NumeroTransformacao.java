
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

package com.joseflavio.cultura;

/**
 * {@link Transformacao} de números.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public final class NumeroTransformacao extends Transformacao {
/*------------------------------------------------------------*/
	private Cultura cultura;
	
	private StringBuffer buffer = new StringBuffer( 60 );
	
	private int maximoDigitosNaFracao = 10;

	private int maximoDigitosNoInteiro = 1000;
	
	private int minimoDigitosNaFracao = 0;
	
	private int minimoDigitosNoInteiro = 0;
	
	private boolean usarMilha = true;
	
	private char separadorMilha;
	
	private char separadorDecimal;
	
	private boolean preferenciaReal;
/*------------------------------------------------------------*/
    NumeroTransformacao( Cultura cultura, boolean preferenciaReal ){
		
		this.cultura = cultura;
		this.preferenciaReal = preferenciaReal;

		Sintaxe sintaxe = cultura.getSintaxe();
		this.separadorMilha = sintaxe.getSeparadorMilha();
		this.separadorDecimal = sintaxe.getSeparadorDecimal();
		
	}
/*------------------------------------------------------------*/
	public String transcrever( double numero ) throws TransformacaoException {
		
		return transcrever( numero, new StringBuffer( 20 ) ).toString();
		
	}
/*------------------------------------------------------------*/
	public String transcreverConfiante( double numero ) {
		
		try{
			
			return transcrever( numero );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
/*------------------------------------------------------------*/
	public String transcrever( float numero ) throws TransformacaoException {
		
		return transcrever( numero, new StringBuffer( 20 ) ).toString();
		
	}
/*------------------------------------------------------------*/
	public String transcreverConfiante( float numero ) {
		
		try{
			
			return transcrever( numero );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
/*------------------------------------------------------------*/
	public String transcrever( double numero, int maximoDigitosNaFracao, boolean usarMilha ) throws TransformacaoException {
		
		return transcreverReal( numero, new StringBuffer( 20 ), maximoDigitosNaFracao, 1000, 0, 0, usarMilha ).toString();
		
	}
/*------------------------------------------------------------*/
	public String transcrever( float numero, int maximoDigitosNaFracao, boolean usarMilha ) throws TransformacaoException {
		
		return transcreverReal( numero, new StringBuffer( 20 ), maximoDigitosNaFracao, 1000, 0, 0, usarMilha ).toString();
		
	}
/*------------------------------------------------------------*/
	public String transcrever( long numero ) throws TransformacaoException {
		
		return transcrever( numero, new StringBuffer( 20 ) ).toString();
		
	}
/*------------------------------------------------------------*/
	public String transcreverConfiante( long numero ) {
		
		try{
			
			return transcrever( numero );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
/*------------------------------------------------------------*/
	public StringBuffer transcrever( double numero, StringBuffer receptor ) throws TransformacaoException {
		
		return transcreverReal( numero, receptor, maximoDigitosNaFracao, maximoDigitosNoInteiro, minimoDigitosNaFracao, minimoDigitosNoInteiro, usarMilha );
		
	}
/*------------------------------------------------------------*/
	public StringBuffer transcrever( float numero, StringBuffer receptor ) throws TransformacaoException {
		
		return transcreverReal( numero, receptor, maximoDigitosNaFracao, maximoDigitosNoInteiro, minimoDigitosNaFracao, minimoDigitosNoInteiro, usarMilha );
		
	}
/*------------------------------------------------------------*/
	private StringBuffer transcreverReal( double numero, StringBuffer receptor, int maxFracao, int maxInteiro, int minFracao, int minInteiro, boolean milha ) throws TransformacaoException {
		
		return transcreverReal( String.valueOf( numero ), receptor, maxFracao, maxInteiro, minFracao, minInteiro, milha );
		
	}
/*------------------------------------------------------------*/
	private StringBuffer transcreverReal( float numero, StringBuffer receptor, int maxFracao, int maxInteiro, int minFracao, int minInteiro, boolean milha ) throws TransformacaoException {
		
		return transcreverReal( String.valueOf( numero ), receptor, maxFracao, maxInteiro, minFracao, minInteiro, milha );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @param numeroStr Número no formato Java.
	 */
	private StringBuffer transcreverReal( String numeroStr, StringBuffer receptor, int maxFracao, int maxInteiro, int minFracao, int minInteiro, boolean milha ) throws TransformacaoException {
		
		try{
			
			buffer.delete( 0, buffer.length() );
			if( numeroStr.endsWith( ".0" ) ) numeroStr = numeroStr.substring( 0, numeroStr.length() - 2 );
			
			int decimal = -1; //Posição do separador decimal
			
			//Enxugando o número
			int E = numeroStr.indexOf( 'E' );			
			if( E != -1 ){ //Formato científico	
				
				int exp = Integer.parseInt( numeroStr.substring( E + 1, numeroStr.length() ) );
				buffer.append( numeroStr.substring( 0, E ) );
				buffer.deleteCharAt( 1 );
				
				if( exp >= ( buffer.length() - 1 ) )
					inserirZeros( buffer, buffer.length(), exp - buffer.length() + 1 );

				decimal = exp + 1;
				
			}else{ //Formato normal 
				
				buffer.append( numeroStr );
				decimal = numeroStr.indexOf( '.' );
				if( decimal != -1  )
					buffer.deleteCharAt( decimal );
				else
					decimal = buffer.length();
				
			}

			//Determinando o tamanho da parte inteira
			int tamanho = decimal;
			if( tamanho < minInteiro ){ 
				
				inserirZeros( buffer, 0, minInteiro - tamanho );
				decimal += minInteiro - tamanho;
				
			}else if( tamanho > maxInteiro ){
				
				buffer.delete( 0, decimal - maxInteiro );
				decimal = maxInteiro;
				
			}
			
			//Determinando o tamanho da parte fracionária
			tamanho = buffer.length() - decimal;
			if( tamanho < minFracao ){
				
				inserirZeros( buffer, buffer.length(), minFracao - tamanho );
				
			}else if( tamanho > maxFracao ){
				
				buffer.delete( decimal + maxFracao, buffer.length() );
				
			}
			
			//Inserindo separadores
			if( decimal < buffer.length() )
				buffer.insert( decimal, separadorDecimal );
			
			if( milha ){
				int fim = 0;
				char sinal = buffer.charAt( 0 );
				if( sinal == '-' || sinal == '+' ) fim++;
				for( int i = decimal - 3; i > fim; i -= 3 ){
					buffer.insert( i, separadorMilha );
				}
			}
			
			//Copiando o conteúdo de buffer para receptor
			tamanho = buffer.length();
			for( E = 0; E < tamanho; E++ )
				receptor.append( buffer.charAt( E ) );
			
			return receptor;
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() ); 
		}
		
	}
/*------------------------------------------------------------*/
	public StringBuffer transcrever( long numero, StringBuffer receptor ) throws TransformacaoException {
		
		return transcreverReal( (double) numero, receptor, 0, maximoDigitosNoInteiro, 0, minimoDigitosNoInteiro, usarMilha );
		
	}
/*------------------------------------------------------------*/
	public StringBuffer transcrever( Object numero,  StringBuffer receptor ) throws TransformacaoException {
		
		if( numero instanceof Double )
			return transcrever( ((Double)numero).doubleValue(), receptor );
		
		else if( numero instanceof Float )
			return transcrever( ((Float)numero).doubleValue(), receptor );
		
		else if( numero instanceof Long )
			return transcrever( ((Long)numero).longValue(), receptor );
		
		else if( numero instanceof Integer )
			return transcrever( ((Integer)numero).longValue(), receptor );
		
		else if( numero instanceof Short )
			return transcrever( ((Short)numero).longValue(), receptor );
		
		else if( numero instanceof Byte )
			return transcrever( ((Byte)numero).longValue(), receptor );
		
		else
			throw new TransformacaoException( "O objeto não corresponde a um número." );
		
	}
/*------------------------------------------------------------*/
	/**
	 * Insere <code>n</code> zeros na posição <code>indice</code> de <code>sb</code>.
	 */
	private void inserirZeros( StringBuffer sb, int indice, int n ){			
		for( ; n > 0; n-- ){
			sb.insert( indice, '0' );
		}			
	}
/*------------------------------------------------------------*/
	public Object transformar( Object obj ) throws TransformacaoException {
		
		if( ! ( obj instanceof String ) ) throw new TransformacaoException( "Objeto incompatível." );

		try{
			
			if( preferenciaReal ) return Double.valueOf( enxugar( (String) obj ) );
			
			return Long.valueOf( enxugar( (String) obj ) );
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() ); 
		}
		
	}
/*------------------------------------------------------------*/
	public double transformarReal( String str ) throws TransformacaoException {
	
		try{
			
			return Double.parseDouble( enxugar( str ) );
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() ); 
		}
		
	}
/*------------------------------------------------------------*/
	public double transformarRealConfiante( String str ) {
		
		try{
			
			return transformarReal( str );
			
		}catch( TransformacaoException e ){
			return 0;
		}
		
	}
/*------------------------------------------------------------*/
	public long transformarInteiro( String str ) throws TransformacaoException {
		
		try{
			
			return Long.parseLong( enxugar( str ) );
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() ); 
		}
		
	}
/*------------------------------------------------------------*/
	public long transformarInteiroConfiante( String str ) {
		
		try{
			
			return transformarInteiro( str );
			
		}catch( TransformacaoException e ){
			return 0;
		}
		
	}
/*------------------------------------------------------------*/
	private String enxugar( String str ) {
		
		int len = str.length();
		StringBuffer sb = new StringBuffer( len );
		char ch;
		
		for( int i = 0; i < len; i++ ){
			ch = str.charAt( i );
			if( ch == separadorDecimal ) sb.append( '.' );
			else if( ch != separadorMilha ) sb.append( ch );
		}
		
		return sb.toString();
		
	}
/*------------------------------------------------------------*/
	public Cultura getCultura() {
		return cultura;
	}
/*------------------------------------------------------------*/
	public void setUsarMilha( boolean usarMilha ) {
		this.usarMilha = usarMilha;
	}
/*------------------------------------------------------------*/
	public boolean isUsarMilha() {
		return usarMilha;
	}
/*------------------------------------------------------------*/
	public int getMaximoDigitosNaFracao() {
		return maximoDigitosNaFracao;
	}
/*------------------------------------------------------------*/
	public void setMaximoDigitosNaFracao( int digitos ) {
		
		maximoDigitosNaFracao = digitos;
		
		if( digitos < minimoDigitosNaFracao )
			minimoDigitosNaFracao = digitos;
		
	}
/*------------------------------------------------------------*/
	public int getMaximoDigitosNoInteiro() {
		return maximoDigitosNoInteiro;
	}
/*------------------------------------------------------------*/
	public void setMaximoDigitosNoInteiro( int digitos ) {
		
		maximoDigitosNoInteiro = digitos;
		
		if( digitos < minimoDigitosNoInteiro )
			minimoDigitosNoInteiro = digitos;
		
	}
/*------------------------------------------------------------*/
	public int getMinimoDigitosNaFracao() {
		return minimoDigitosNaFracao;
	}
/*------------------------------------------------------------*/
	public void setMinimoDigitosNaFracao( int digitos ) {
		
		minimoDigitosNaFracao = digitos;
		
		if( digitos > maximoDigitosNaFracao )
			maximoDigitosNaFracao = digitos;
		
	}
/*------------------------------------------------------------*/
	public int getMinimoDigitosNoInteiro() {
		return minimoDigitosNoInteiro;
	}
/*------------------------------------------------------------*/
	public void setMinimoDigitosNoInteiro( int digitos ) {
		
		minimoDigitosNoInteiro = digitos;
		
		if( digitos > maximoDigitosNoInteiro )
			maximoDigitosNoInteiro = digitos;

		
	}
/*------------------------------------------------------------*/
	public boolean isPreferenciaReal() {
		return preferenciaReal;
	}
/*------------------------------------------------------------*/
}
