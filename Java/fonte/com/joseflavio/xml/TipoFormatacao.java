
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

package com.joseflavio.xml;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Utilitário de formatação conforme {@link Tipo}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2014
 */
public class TipoFormatacao {
	
	private NumberFormat     formatoReal;
	private SimpleDateFormat formatoData;
	private SimpleDateFormat formatoHora1;
	private SimpleDateFormat formatoHora2;
	private SimpleDateFormat formatoDataHora1;
	private SimpleDateFormat formatoDataHora2;
	
	private Pattern padraoInteiro;
	private Pattern padraoReal;
	private Pattern padraoData;
	private Pattern padraoHora;
	private Pattern padraoDataHora;
	
	public TipoFormatacao() {
		
		formatoReal = NumberFormat.getInstance( new Locale( "pt", "BR" ) );
		formatoReal.setGroupingUsed( false );
		formatoReal.setMinimumIntegerDigits( 1 );
		formatoReal.setMinimumFractionDigits( 1 );
		
		formatoData = new SimpleDateFormat( "dd/MM/yyyy" );
		formatoHora1 = new SimpleDateFormat( "HH:mm:ss" );
		formatoHora2 = new SimpleDateFormat( "HH:mm:ss:SSS" );
		formatoDataHora1 = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
		formatoDataHora2 = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss:SSS" );
		
		padraoInteiro = Pattern.compile( "[+|-]?[0-9]+" );
		padraoReal = Pattern.compile( "[+|-]?[0-9]+,[0-9]+" );
		padraoData = Pattern.compile( "\\d{2}/\\d{2}/\\d{4}" );
		padraoHora = Pattern.compile( "\\d{2}:\\d{2}:\\d{2}(:\\d{3})?" );
		padraoDataHora = Pattern.compile( "\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}(:\\d{3})?" );
		
	}
	
	/**
	 * Representa textualmente o {@link Object} no formato do {@link Tipo} especificado.
	 * @param tipo <code>null</code> == tipo padrão para a {@link Class} do {@link Object}.
	 */
	public synchronized String transcrever( Object objeto, Tipo tipo ) {
		
		if( objeto == null ){
			return "";
			
		}else if( objeto instanceof String ){
			if( tipo == null ) tipo = Tipo.TEXTO;
			switch( tipo ){
				case TEXTO :
				case TEXTO_MULTILINHA :
					return objeto.toString();
				default : throw new IllegalArgumentException( "Tipo deve ser " + Tipo.TEXTO.toString() + " ou " + Tipo.TEXTO_MULTILINHA.toString() );
			}
			
		}else if( objeto instanceof Number ){
			if( tipo == null ) tipo = Tipo.REAL;
			switch( tipo ){
				case INTEIRO : return "" + ((Number)objeto).longValue();
				case REAL    : return formatoReal.format( ((Number)objeto).doubleValue() );
				default : throw new IllegalArgumentException( "Tipo deve ser " + Tipo.INTEIRO.toString() + " ou " + Tipo.REAL.toString() );
			}
			
		}else if( objeto instanceof Character ){
			if( tipo == null ) tipo = Tipo.LETRA;
			if( tipo != Tipo.LETRA ) throw new IllegalArgumentException( "Tipo deve ser " + Tipo.LETRA.toString() );
			return objeto.toString();
			
		}else if( objeto instanceof Boolean ){
			if( tipo == null ) tipo = Tipo.LOGICO;
			if( tipo != Tipo.LOGICO ) throw new IllegalArgumentException( "Tipo deve ser " + Tipo.LOGICO.toString() );
			return (Boolean) objeto ? "S" : "N";
			
		}else if( objeto instanceof Date ){
			if( tipo == null ) tipo = Tipo.DATA_HORA;
			switch( tipo ){
				case DATA      : return formatoData.format( (Date) objeto );
				case HORA      : return formatoHora2.format( (Date) objeto );
				case DATA_HORA : return formatoDataHora2.format( (Date) objeto );
				default : throw new IllegalArgumentException( "Tipo deve ser " + Tipo.DATA.toString() + ", " + Tipo.HORA.toString() + " ou " + Tipo.DATA_HORA.toString() );
			}
		}
		
		throw new IllegalArgumentException( "Objeto incompatível." );
		
	}
	
	/**
	 * Transforma a {@link String} num {@link Object} compatível com o {@link Tipo} especificado.<br>
	 * Ex.: {@link Tipo#DATA} exigerá uma {@link String} no formato "dd/MM/yyyy" a fim de gerar uma {@link Date}.
	 * @param tipo <code>null</code> == detecção automática do {@link Tipo}. Veja {@link #reconhecerTipo(String)}.
	 */
	public synchronized Object transformar( String texto, Tipo tipo ) {
		
		if( texto == null ) return null;
		if( tipo == null ) tipo = reconhecerTipo( texto );
		if( texto.length() == 0 && tipo != Tipo.TEXTO && tipo != Tipo.TEXTO_MULTILINHA ) return null;
		
		switch( tipo ){
			
			case LOGICO :
				return new Boolean( texto.equals( "S" ) );
				
			case LETRA :
				return new Character( texto.charAt( 0 ) );
				
			case INTEIRO :
				return new Long( texto );
				
			case REAL :
				try{
					return formatoReal.parse( texto );
				}catch( ParseException e ){
					throw new IllegalArgumentException( e );
				}
				
			case DATA :
				try{
					return formatoData.parse( texto.substring( 0, 10 ) );
				}catch( ParseException e ){
					throw new IllegalArgumentException( e );
				}
				
			case HORA :
				try{
					return texto.length() == 8 ? formatoHora1.parse( texto ) : formatoHora2.parse( texto );
				}catch( ParseException e ){
					throw new IllegalArgumentException( e );
				}
				
			case DATA_HORA :
				try{
					return texto.length() == 19 ? formatoDataHora1.parse( texto ) : formatoDataHora2.parse( texto );
				}catch( ParseException e ){
					throw new IllegalArgumentException( e );
				}
				
			default :
				return texto;
				
		}
		
	}
	
	/**
	 * Detecção automática de {@link Tipo} conforme formato textual do <code>valor</code>.<br>
	 * Ordem dos testes: <code>null</code>, {@link Tipo#INTEIRO}, {@link Tipo#REAL}, {@link Tipo#DATA}, {@link Tipo#HORA}, {@link Tipo#DATA_HORA}, {@link Tipo#LETRA}, {@link Tipo#TEXTO}.
	 */
	public synchronized Tipo reconhecerTipo( String valor ) {
		
		if( valor == null || valor.length() == 0 ) return null;
		
		if( padraoInteiro.matcher( valor ).matches() ){
			return Tipo.INTEIRO;
		}else if( padraoReal.matcher( valor ).matches() ){
			return Tipo.REAL;
		}else if( padraoData.matcher( valor ).matches() ){
			return Tipo.DATA;
		}else if( padraoHora.matcher( valor ).matches() ){
			return Tipo.HORA;
		}else if( padraoDataHora.matcher( valor ).matches() ){
			return Tipo.DATA_HORA;
		}else if( valor.length() == 1 ){
			return Tipo.LETRA;
		//TODO TEXTO_MULTILINHA contém \n
		}
		
		return Tipo.TEXTO;
		
	}
	
}
