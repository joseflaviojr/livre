
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

import java.util.Calendar;
import java.util.Locale;

import com.joseflavio.util.Calendario;

/**
 * Padrões culturais utilizados por um povo de uma determinada região do planeta.<br>
 * Consiste na padronização da morfologia textual, principalmente no que diz respeito a datas, números e moedas.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public final class Cultura {
/*------------------------------------------------------------*/
	/**
	 * {@link Cultura} padrão do sistema.
	 */
	private static Cultura culturaPadrao = getPadraoMV();
/*------------------------------------------------------------*/
	/**
	 * pt-BR
	 */
	public static final Cultura	BRASILEIRA			= new Cultura( "pt", "BR", "" );
	
    /**
     * en-US
     */
	public static final Cultura	NORTE_AMERICANA		= new Cultura( "en", "US", "" );
/*------------------------------------------------------------*/
	/**
	 * Duas letras minúsculas do padrão ISO-639.
	 */
	private String lingua;
	
	/**
	 * Duas letras maiúsculas do padrão ISO-3166.
	 */
	private String pais;
	
	/**
	 * Código de especialização.
	 */
	private String variante;
	
	private String toString = null;
	
	private Sintaxe sintaxe;
	
	private Moeda moeda;
/*------------------------------------------------------------*/
	/**
	 * @param lingua Duas letras minúsculas do padrão ISO-639.
	 */
	public Cultura( String lingua ){
		
		this( lingua, "", "" );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @param lingua Duas letras minúsculas do padrão ISO-639.
	 * @param pais Duas letras maiúsculas do padrão ISO-3166.
	 */
	public Cultura( String lingua, String pais ){
		
		this( lingua, pais, "" );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @param lingua Duas letras minúsculas do padrão ISO-639.
	 * @param pais Duas letras maiúsculas do padrão ISO-3166.
	 * @param variante Código de especialização.
	 */
	public Cultura( String lingua, String pais, String variante ){
		
		this.lingua = lingua != null ? lingua : "";
		this.pais = pais != null ? pais : "";
		this.variante = variante != null ? variante : "";
		
	}
/*------------------------------------------------------------*/
	/**
	 * Retorna a {@link Cultura} padrão da máquina virtual.
	 */
	private static final Cultura getPadraoMV(){

		String lingua, pais, variante;
		
		String cultura = System.getProperty( "microedition.locale" );
		
		if( cultura == null ){
			String sysl = System.getProperty( "user.language" ); if( sysl == null ) sysl = "";
			String sysp = System.getProperty( "user.country" ); if( sysp == null ) sysp = "";
			String sysv = System.getProperty( "user.variant" ); if( sysv == null ) sysv = "";
			cultura = sysl + '_' + sysp + '_' + sysv + '_';
		}else{
			cultura += '_';
			cultura = cultura.replace( '-', '_' );
		}
		
		//Contando o número de componentes da cultura
		int total = 0;
		for( int i = 0; i < cultura.length(); i++ )
			if( cultura.charAt( i ) == '_' ) total++;
		
		if( total == 1 ) cultura += "__";
		else if( total == 2 ) cultura += '_';
		
		//Linguagem
		int begin = 0;
		int end = cultura.indexOf( '_', begin );		
		lingua = cultura.substring( begin, end );
		
		//País
		begin = end + 1;
		end = cultura.indexOf( '_', begin );
		pais = cultura.substring( begin, end );
		
		//Variação
		begin = end + 1;
		end = cultura.indexOf( '_', begin );
		variante = cultura.substring( begin, end );
		
		return new Cultura( lingua, pais, variante );
		
		
	}
/*------------------------------------------------------------*/
	/**
	 * {@link Cultura} padrão do sistema.
	 * @see Locale#getDefault()
	 * @see #setPadrao(Cultura)
	 */
	public static Cultura getPadrao(){
		return culturaPadrao;		
	}
/*------------------------------------------------------------*/
	/**
	 * Retorna a {@link Cultura} correspondente ao nome especificado, entre as definidas estaticamente.
	 * @param nome Veja {@link #toString()}.
	 * @return <code>null</code> caso não seja encontrada a {@link Cultura} desejada.
	 * @see #BRASILEIRA
	 * @see #NORTE_AMERICANA
	 */
	public static Cultura getCultura( String nome ) {
		if( nome == null ) return null;
		if( nome.equals( "pt-BR" ) ) return BRASILEIRA;
		if( nome.equals( "en-US" ) ) return NORTE_AMERICANA;
		return null;
	}
/*------------------------------------------------------------*/
	/**
	 * Determina a {@link Cultura} padrão do sistema.<br>
	 * Não será feita qualquer alteração na máquina virtual ou no sistema operacional, e sim apenas uma
	 * manutenção numa variável estática definida para tal fim.
	 */
	public static void setPadrao( Cultura cultura ){
		culturaPadrao = cultura;		
	}
/*------------------------------------------------------------*/
	public boolean equals( Object obj ) {
		if( ! ( obj instanceof Cultura ) ) return false;
		return
			lingua.equals( ((Cultura)obj).lingua ) &&
			pais.equals( ((Cultura)obj).pais ) &&
			variante.equals( ((Cultura)obj).variante );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see Locale#toString()
	 * @author Mark Davis
	 */
	public String toString() {
		
		if( toString != null ) return toString;
		
        boolean l = lingua.length() != 0;
        boolean p = pais.length() != 0;
        boolean v = variante.length() != 0;
        
        StringBuilder result = new StringBuilder( lingua );
        
        if ( p || ( l && v ) ) {
            result.append( '_' ).append( pais );
        }
        
        if ( v && ( l || p ) ) {
            result.append( '_' ).append( variante );
        }
        
        return toString = result.toString();
		
	}
/*------------------------------------------------------------*/
	/**
	 * Duas letras maiúsculas do padrão ISO-3166.
	 */
	public String getPais() {
		return pais;
	}
/*------------------------------------------------------------*/
	/**
	 * Duas letras minúsculas do padrão ISO-639.
	 */
	public String getLingua() {
		return lingua;
	}
/*------------------------------------------------------------*/
	/**
	 * Código de especialização.
	 */
	public String getVariante() {
		return variante;
	}
/*------------------------------------------------------------*/
	public Sintaxe getSintaxe() {
		
		if( sintaxe != null ) return sintaxe;
		
		if( this.equals( NORTE_AMERICANA ) ){
			sintaxe = Sintaxe_en_US.instancia;
		}else{
			sintaxe = Sintaxe_pt_BR.instancia;
		}
		
		return sintaxe;
		
	}
/*------------------------------------------------------------*/
	public Moeda getMoeda() {
		
		if( moeda != null ) return moeda;
		
		if( this.equals( NORTE_AMERICANA ) ){
			moeda = Moeda_US.instancia;
		}else{
			moeda = Moeda_BR.instancia;
		}
		
		return moeda;
		
	}
/*------------------------------------------------------------*/
	/**
	 * Instancia um {@link Calendar} nos padrões desta {@link Cultura}.
	 */
	public Calendar novoCalendar() {
		
		return Calendar.getInstance( this.equals( NORTE_AMERICANA ) ? Locale.US : new Locale( "pt", "BR" ) );
		
	}
/*------------------------------------------------------------*/
	public Calendario novoCalendario() {
		
		return new Calendario( this );
		
	}
/*------------------------------------------------------------*/
	/**
	 * {@link #novoNumeroTransformacao(boolean)} com parâmetros: true.
	 */
	public NumeroTransformacao novoNumeroTransformacao() {
		
		return novoNumeroTransformacao( true );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see NumeroTransformacao#isPreferenciaReal()
	 */
	public NumeroTransformacao novoNumeroTransformacao( boolean preferenciaReal ) {
		
		return new NumeroTransformacao( this, preferenciaReal );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#getEstilo()
	 */
	public DataTransformacao novaDataTransformacao( int estilo ) {
		
		if( this.equals( NORTE_AMERICANA ) ){
			return new DataTransformacao_en_US( this, estilo );
		}else{
			return new DataTransformacao_pt_BR( this, estilo );
		}
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#RESUMIDA
	 */
	public DataTransformacao novaDataTransformacaoResumida() {
		
		return novaDataTransformacao( DataTransformacao.RESUMIDA );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#EXTENSA
	 */
	public DataTransformacao novaDataTransformacaoExtensa() {
		
		return novaDataTransformacao( DataTransformacao.EXTENSA );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#COMPLETA
	 */
	public DataTransformacao novaDataTransformacaoCompleta() {
		
		return novaDataTransformacao( DataTransformacao.COMPLETA );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#NORMAL
	 */
	public DataTransformacao novaDataTransformacaoNormal() {
		
		return novaDataTransformacao( DataTransformacao.NORMAL );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#PADRAO
	 */
	public DataTransformacao novaDataTransformacaoPadrao() {
		
		return novaDataTransformacao( DataTransformacao.PADRAO );
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#NUMERICA
	 */
	public DataTransformacao novaDataTransformacaoNumerica() {
		
		return novaDataTransformacao( DataTransformacao.NUMERICA );
		
	}
/*------------------------------------------------------------*/
	public NumeroTransformacao novaRealTransformacao() {
		
		return new NumeroTransformacao( this, true );
		
	}
/*------------------------------------------------------------*/
	public NumeroTransformacao novaInteiroTransformacao() {
		
		NumeroTransformacao nt = new NumeroTransformacao( this, false );
		nt.setMaximoDigitosNaFracao( 0 );
		nt.setMinimoDigitosNaFracao( 0 );
		
		return nt;
		
	}
/*------------------------------------------------------------*/
	/**
	 * {@link NumeroTransformacao} real com 2 casas decimais.
	 */
	public NumeroTransformacao novaMoedaTransformacao() {
		
		NumeroTransformacao nt = new NumeroTransformacao( this, true );
		nt.setMaximoDigitosNaFracao( 2 );
		nt.setMinimoDigitosNaFracao( 2 );
		
		return nt;
		
	}
/*------------------------------------------------------------*/
	/**
	 * @see DataTransformacao#NUMERICA
	 */
	public DataTransformacao novaDataTransformacao() {
		
		return novaDataTransformacao( DataTransformacao.NUMERICA );
		
	}
/*------------------------------------------------------------*/
}
