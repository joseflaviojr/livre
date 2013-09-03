
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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

package com.joseflavio.cultura;

/**
 * {@link Transformacao} de n�meros.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * @param numeroStr N�mero no formato Java.
	 */
	private StringBuffer transcreverReal( String numeroStr, StringBuffer receptor, int maxFracao, int maxInteiro, int minFracao, int minInteiro, boolean milha ) throws TransformacaoException {
		
		try{
			
			buffer.delete( 0, buffer.length() );
			if( numeroStr.endsWith( ".0" ) ) numeroStr = numeroStr.substring( 0, numeroStr.length() - 2 );
			
			int decimal = -1; //Posi��o do separador decimal
			
			//Enxugando o n�mero
			int E = numeroStr.indexOf( 'E' );			
			if( E != -1 ){ //Formato cient�fico	
				
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
			
			//Determinando o tamanho da parte fracion�ria
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
			
			//Copiando o conte�do de buffer para receptor
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
			throw new TransformacaoException( "O objeto n�o corresponde a um n�mero." );
		
	}
/*------------------------------------------------------------*/
	/**
	 * Insere <code>n</code> zeros na posi��o <code>indice</code> de <code>sb</code>.
	 */
	private void inserirZeros( StringBuffer sb, int indice, int n ){			
		for( ; n > 0; n-- ){
			sb.insert( indice, '0' );
		}			
	}
/*------------------------------------------------------------*/
	public Object transformar( Object obj ) throws TransformacaoException {
		
		if( ! ( obj instanceof String ) ) throw new TransformacaoException( "Objeto incompat�vel." );

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
