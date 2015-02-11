
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

package com.joseflavio.util;

import java.util.Date;
import java.util.TimeZone;


/**
 * Representação simples de data gregoriana.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class DataSimples {
	
	private static final long ANO_365_MS = 365 * 24 * 60 * 60 * 1000L;
	private static final long ANO_366_MS = 366 * 24 * 60 * 60 * 1000L;
	
	private static final long MES_28_MS = 28 * 24 * 60 * 60 * 1000L;
	private static final long MES_29_MS = 29 * 24 * 60 * 60 * 1000L;
	private static final long MES_30_MS = 30 * 24 * 60 * 60 * 1000L;
	private static final long MES_31_MS = 31 * 24 * 60 * 60 * 1000L;
	
	private static final long[] MES_MS = { MES_31_MS, 0, MES_31_MS, MES_30_MS, MES_31_MS, MES_30_MS, MES_31_MS,
											MES_31_MS, MES_30_MS, MES_31_MS, MES_30_MS, MES_31_MS };

	private static final long DIA_MS = 24 * 60 * 60 * 1000L;
	
	private static final long HORA_MS = 60 * 60 * 1000L;
	
	private static final long MINUTO_MS = 60 * 1000L;
	
	private long timestamp;
	private TimeZone tz;
	
	private int dia;
	private int mes;
	private int ano;
	private int h;
	private int m;
	private int s;
	private int ms;
	
	private Date date;
	
	public DataSimples( long timestamp, TimeZone tz ){
		this.tz = tz;
		setTimestamp( timestamp );
	}
	
	/**
	 * @see TimeZone#getDefault()
	 */
	public DataSimples( long timestamp ){
		this( timestamp, TimeZone.getDefault() );
	}
	
	public DataSimples(){
		this( System.currentTimeMillis(), TimeZone.getDefault() );
	}
	
	public DataSimples setTimestamp( long timestamp ) {
		
		long dif = timestamp - this.timestamp;
		if( dif == 0 ) return this;
		
		this.timestamp = timestamp;
		this.date = null;
		
		if( dif > 0 ){

			if( ( ms + dif ) < 1000L ){
				ms += dif;
				return this;
			}
			
			long hoje = s * 1000L + ms;
			
			if( ( hoje + dif ) < MINUTO_MS ){
				dif += hoje;
				s = (int)( dif / 1000L );
				dif -= s * 1000L;
				ms = (int) dif;
				return this;
			}
			
			hoje += h * HORA_MS + m * MINUTO_MS;
			
			if( ( hoje + dif ) < DIA_MS ){
			
				dif += hoje;
				
				h = (int)( dif / HORA_MS );
				dif -= h * HORA_MS;
				
				m = (int)( dif / MINUTO_MS );
				dif -= m * MINUTO_MS;
				
				s = (int)( dif / 1000L );
				dif -= s * 1000L;
				
				ms = (int) dif;
				
				return this;
				
			}
			
		}
		
		mes = 0;
		ano = 1970;
		
		timestamp += tz.getRawOffset();
		
		while( timestamp >= ANO_365_MS ){
			ano++;
			timestamp -= ano % 400 == 0 ? ANO_366_MS : ano % 100 == 0 ? ANO_365_MS : ano % 4 == 0 ? ANO_366_MS : ANO_365_MS;
		}
		
		MES_MS[1] = ano % 400 == 0 ? MES_29_MS : ano % 100 == 0 ? MES_28_MS : ano % 4 == 0 ? MES_29_MS : MES_28_MS;
		
		long tstmp;
		for( int i = 0; i < 12; i++ ){
			mes++;
			tstmp = MES_MS[i];
			if( timestamp < tstmp ) break;
			timestamp -= tstmp;
		}

		dia = (int)( timestamp / DIA_MS );
		timestamp -= dia * DIA_MS;
		dia++;
		
		h = (int)( timestamp / HORA_MS );
		timestamp -= h * HORA_MS;
		
		m = (int)( timestamp / MINUTO_MS );
		timestamp -= m * MINUTO_MS;
		
		s = (int)( timestamp / 1000L );
		timestamp -= s * 1000L;
		
		ms = (int) timestamp;
		
		return this;
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder( 22 );
		
		sb.append( dia < 10 ? "0" + dia : dia );
		sb.append( '/' );
		sb.append( mes < 10 ? "0" + mes : mes );
		sb.append( '/' );
		sb.append( ano < 10 ? "0" + ano : ano );
		
		sb.append( ' ' );
		
		sb.append( h < 10 ? "0" + h : h );
		sb.append( ':' );
		sb.append( m < 10 ? "0" + m : m );
		sb.append( ':' );
		sb.append( s < 10 ? "0" + s : s );
		sb.append( ':' );
		sb.append( ms < 10 ? "0" + ms : ms );
		
		return sb.toString();
		
	}
	
	public Date getDate() {
		if( date == null ) date = new Date( timestamp );
		return date;
	}
	
	/**
	 * @return 1 a 31
	 */
	public int getDia() {
		return dia;
	}
	
	/**
	 * @return 1 a 12
	 */
	public int getMes() {
		return mes;
	}
	
	public int getAno() {
		return ano;
	}
	
	public int getHora() {
		return h;
	}
	
	public int getMinuto() {
		return m;
	}
	
	public int getSegundo() {
		return s;
	}
	
	public int getMilissegundo() {
		return ms;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public TimeZone getTimeZone() {
		return tz;
	}
	
}
