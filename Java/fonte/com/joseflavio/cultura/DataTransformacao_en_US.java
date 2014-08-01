
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

package com.joseflavio.cultura;

import java.util.Calendar;

import com.joseflavio.util.SeparadorTextual;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
class DataTransformacao_en_US extends DataTransformacao {

	private SeparadorTextual separadorDataResumida = new SeparadorTextual( new char[]{ '/', '/' } );
	private SeparadorTextual separadorDataExtensa = new SeparadorTextual( new String[]{ " ", ", " } );
	private SeparadorTextual separadorDataCompleta = new SeparadorTextual( new String[]{ ", ", " ", ", " } );
	private SeparadorTextual separadorDataNormal = new SeparadorTextual( new String[]{ " ", ", " } );
	private SeparadorTextual separadorDataNumerica = new SeparadorTextual( new char[]{ '/', '/' } );
	
	private SeparadorTextual separadorHoraResumida = new SeparadorTextual( new char[]{ ':', ' ' } );
	private SeparadorTextual separadorHoraNormal = new SeparadorTextual( new char[]{ ':', ':', ' ' } );
	private SeparadorTextual separadorHoraNumerica = new SeparadorTextual( new char[]{ ':' } );
	
	private SeparadorTextual separadorDataHoraResumida = new SeparadorTextual( new char[]{ '/', '/', ' ', ':', ' ' } );
	private SeparadorTextual separadorDataHoraExtensa = new SeparadorTextual( new String[]{ " ", ", ", " ", ":", ":", " " } );
	private SeparadorTextual separadorDataHoraCompleta = new SeparadorTextual( new String[]{ ", ", " ", ", ", " ", ":", ":", " " } );
	private SeparadorTextual separadorDataHoraNormal = new SeparadorTextual( new String[]{ " ", ", ", " ", ":", ":", " " } );
	private SeparadorTextual separadorDataHoraNumerica = new SeparadorTextual( new char[]{ '/', '/', ' ', ':' } );
	
	DataTransformacao_en_US( Cultura cultura, int estilo ) {
		
		super( cultura, estilo );
		
	}

	protected StringBuffer transcreverData( Calendar data, Sintaxe sintaxe, int estilo, StringBuffer receptor ) throws TransformacaoException {
		
		try{
			
			int dia = data.get( Calendar.DAY_OF_MONTH );
			int mes = data.get( Calendar.MONTH ) + 1;
			int ano = data.get( Calendar.YEAR );
			
			switch( estilo ){
				
				case DataTransformacao.NUMERICA :
					return receptor.append( doisDigitos( mes ) + "/" + doisDigitos( dia ) + "/" + quatroDigitos( ano ) );
				
				case DataTransformacao.RESUMIDA :
					return receptor.append( mes + "/" + dia + "/" + quatroDigitos( ano ).substring( 2, 4 ) );

				case DataTransformacao.EXTENSA :
					return receptor.append( sintaxe.getMesNome( mes - 1 ) + " " + dia + ", " + ano );
					
				case DataTransformacao.COMPLETA :
					return receptor.append( sintaxe.getDiaDaSemanaNome( data.get( Calendar.DAY_OF_WEEK ) ) + ", " + sintaxe.getMesNome( mes - 1 ) + " " + dia + ", " + ano );
					
				default :
					return receptor.append( sintaxe.getMesNomeAbreviado( mes - 1 ) + " " + dia + ", " + ano );
				
			}
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() );
		}
		
	}

	protected StringBuffer transcreverHora( Calendar hora, Sintaxe sintaxe, int estilo, StringBuffer receptor ) throws TransformacaoException {

		try{
			
			int h = hora.get( Calendar.HOUR_OF_DAY );
			int m = hora.get( Calendar.MINUTE );
			int s = hora.get( Calendar.SECOND );
			String periodo;
			
			switch( estilo ){
				
				case DataTransformacao.NUMERICA :
					return receptor.append( doisDigitos( h ) + ":" + doisDigitos( m ) );
				
				case DataTransformacao.RESUMIDA :
					
					if( h > 12 ) h -= 12;
					periodo = ( hora.get( Calendar.AM_PM ) == Calendar.AM ) ? " AM" : " PM";
					
					return receptor.append( h + ":" + doisDigitos( m ) + periodo );
					
				default :
					
					if( h > 12 ) h -= 12;
					periodo = ( hora.get( Calendar.AM_PM ) == Calendar.AM ) ? " AM" : " PM";
					
					return receptor.append( h + ":" + doisDigitos( m ) + ":" + doisDigitos( s ) + periodo );
				
			}
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() );
		}
		
	}

	protected StringBuffer transcreverDataHora( Calendar dataHora, Sintaxe sintaxe, int estilo, StringBuffer receptor ) throws TransformacaoException {
		
		transcreverData( dataHora, sintaxe, estilo, receptor );
		receptor.append( " " );
		transcreverHora( dataHora, sintaxe, estilo, receptor );
		
		return receptor;
		
	}
	
	protected void transformarData( String data, Sintaxe sintaxe, int estilo, Calendar receptor ) throws TransformacaoException {

		try{
			
			int dia = 0, mes = 0, ano = 0, semana = 0;
			SeparadorTextual sp;
			
			switch( estilo ){
				
				case DataTransformacao.NUMERICA :
					
					sp = separadorDataNumerica;
					sp.executar( data );
					
					mes = Integer.parseInt( sp.getParte( 0 ) ) - 1;
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) );
					
					break;
				
				case DataTransformacao.RESUMIDA :
					
					sp = separadorDataResumida;
					sp.executar( data );
					
					mes = Integer.parseInt( sp.getParte( 0 ) ) - 1;
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) ) + seculoBase();
					
					break;

				case DataTransformacao.EXTENSA :
					
					sp = separadorDataExtensa;
					sp.executar( data );
					
					mes = sintaxe.getMes( sp.getParte( 0 ) );
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) );
					
					break;
					
				case DataTransformacao.COMPLETA :
					
					sp = separadorDataCompleta;
					sp.executar( data );
					
					semana = sintaxe.getDiaDaSemana( sp.getParte( 0 ) );
					mes = sintaxe.getMes( sp.getParte( 1 ) );
					dia = Integer.parseInt( sp.getParte( 2 ) );
					ano = Integer.parseInt( sp.getParte( 3 ) );
					
					break;
					
				default :
					
					sp = separadorDataNormal;
					sp.executar( data );
					
					mes = sintaxe.getMesAbreviado( sp.getParte( 0 ) );
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) );
						
					break;
				
			}

			if( estilo == DataTransformacao.COMPLETA ) receptor.set( Calendar.DAY_OF_WEEK, semana );
			receptor.set( Calendar.DAY_OF_MONTH, dia );
			receptor.set( Calendar.MONTH, mes );
			receptor.set( Calendar.YEAR, ano );
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() );
		}
		
	}

	protected void transformarHora( String hora, Sintaxe sintaxe, int estilo, Calendar receptor ) throws TransformacaoException {

		try{
			
			int h = 0, m = 0, s = 0;
			boolean manha = true;
			SeparadorTextual sp;
			
			switch( estilo ){
				
				case DataTransformacao.NUMERICA :
					
					sp = separadorHoraNumerica;
					sp.executar( hora );
					
					h = Integer.parseInt( sp.getParte( 0 ) );
					m = Integer.parseInt( sp.getParte( 1 ) );
					
					break;
				
				case DataTransformacao.RESUMIDA :
					
					sp = separadorHoraResumida;
					sp.executar( hora );
					
					h = Integer.parseInt( sp.getParte( 0 ) );
					m = Integer.parseInt( sp.getParte( 1 ) );
					manha = sp.getParte( 2 ).equals( "AM" );
					
					break;

				default :
					
					sp = separadorHoraNormal;
					sp.executar( hora );
					
					h = Integer.parseInt( sp.getParte( 0 ) );
					m = Integer.parseInt( sp.getParte( 1 ) );
					s = Integer.parseInt( sp.getParte( 2 ) );
					manha = sp.getParte( 3 ).equals( "AM" );
					
					break;
				
			}
			
			if( ! manha ) h += 12;
			
			receptor.set( Calendar.HOUR_OF_DAY, h );
			receptor.set( Calendar.MINUTE, m );
			receptor.set( Calendar.SECOND, s );
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() );
		}
		
	}

	protected void transformarDataHora( String dataHora, Sintaxe sintaxe, int estilo, Calendar receptor ) throws TransformacaoException {
		
		try{
			
			int dia = 0, mes = 0, ano = 0, semana = 0, h = 0, m = 0, s = 0;
			boolean manha = true;
			SeparadorTextual sp;
			
			switch( estilo ){
				
				case DataTransformacao.NUMERICA :
					
					sp = separadorDataHoraNumerica;
					sp.executar( dataHora );
					
					mes = Integer.parseInt( sp.getParte( 0 ) ) - 1;
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) );
					
					h = Integer.parseInt( sp.getParte( 3 ) );
					m = Integer.parseInt( sp.getParte( 4 ) );
					
					break;
				
				case DataTransformacao.RESUMIDA :
					
					sp = separadorDataHoraResumida;
					sp.executar( dataHora );
					
					mes = Integer.parseInt( sp.getParte( 0 ) ) - 1;
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) ) + seculoBase();
					
					h = Integer.parseInt( sp.getParte( 3 ) );
					m = Integer.parseInt( sp.getParte( 4 ) );
					manha = sp.getParte( 5 ).equals( "AM" );
					
					break;

				case DataTransformacao.EXTENSA :
					
					sp = separadorDataHoraExtensa;
					sp.executar( dataHora );
					
					mes = sintaxe.getMes( sp.getParte( 0 ) );
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) );
					
					h = Integer.parseInt( sp.getParte( 3 ) );
					m = Integer.parseInt( sp.getParte( 4 ) );
					s = Integer.parseInt( sp.getParte( 5 ) );
					manha = sp.getParte( 6 ).equals( "AM" );
					
					break;
					
				case DataTransformacao.COMPLETA :
					
					sp = separadorDataHoraCompleta;
					sp.executar( dataHora );
					
					semana = sintaxe.getDiaDaSemana( sp.getParte( 0 ) );
					mes = sintaxe.getMes( sp.getParte( 1 ) );
					dia = Integer.parseInt( sp.getParte( 2 ) );
					ano = Integer.parseInt( sp.getParte( 3 ) );
					
					h = Integer.parseInt( sp.getParte( 4 ) );
					m = Integer.parseInt( sp.getParte( 5 ) );
					s = Integer.parseInt( sp.getParte( 6 ) );
					manha = sp.getParte( 7 ).equals( "AM" );
					
					break;
					
				default :
					
					sp = separadorDataHoraNormal;
					sp.executar( dataHora );
					
					mes = sintaxe.getMesAbreviado( sp.getParte( 0 ) );
					dia = Integer.parseInt( sp.getParte( 1 ) );
					ano = Integer.parseInt( sp.getParte( 2 ) );
					
					h = Integer.parseInt( sp.getParte( 3 ) );
					m = Integer.parseInt( sp.getParte( 4 ) );
					s = Integer.parseInt( sp.getParte( 5 ) );
					manha = sp.getParte( 6 ).equals( "AM" );
						
					break;
				
			}

			if( ! manha ) h += 12;
			
			if( estilo == DataTransformacao.COMPLETA ) receptor.set( Calendar.DAY_OF_WEEK, semana );
			receptor.set( Calendar.DAY_OF_MONTH, dia );
			receptor.set( Calendar.MONTH, mes );
			receptor.set( Calendar.YEAR, ano );
			receptor.set( Calendar.HOUR_OF_DAY, h );
			receptor.set( Calendar.MINUTE, m );
			receptor.set( Calendar.SECOND, s );
			
		}catch( Exception e ){
			throw new TransformacaoException( e.getMessage() );
		}
		
	}

}
