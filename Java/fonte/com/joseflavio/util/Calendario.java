
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

package com.joseflavio.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.joseflavio.cultura.Cultura;

/**
 * {@link Calendar} adaptado para {@link Cultura}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Calendario {

	private Calendar cal;
	
	public Calendario( Cultura cultura ) {
		Locale locale = null;
		if( cultura.equals( Cultura.NORTE_AMERICANA ) ) locale = Locale.US;
		cal = Calendar.getInstance( locale != null ? locale : new Locale( "pt", "BR" ) );
	}
	
	public Calendario() {
		this( Cultura.getPadrao() );
	}
	
	public Calendario setData( Date data ) {
		cal.setTime( data );
		return this;
	}
	
	public Calendario setTimestamp( long ms ) {
		cal.setTimeInMillis( ms );
		return this;
	}
	
	public Date getData() {
		return cal.getTime();
	}
	
	public long getTimestamp() {
		return cal.getTimeInMillis();
	}
	
	public int getDia() {
		return cal.get( Calendar.DAY_OF_MONTH );
	}
	
	public int getMes() {
		return cal.get( Calendar.MONTH );
	}
	
	public int getMesReal() {
		return cal.get( Calendar.MONTH ) + 1;
	}
	
	public int getAno() {
		return cal.get( Calendar.YEAR );
	}
	
	public int getHora() {
		return cal.get( Calendar.HOUR_OF_DAY );
	}
	
	public int getMinuto() {
		return cal.get( Calendar.MINUTE );
	}
	
	public int getSegundo() {
		return cal.get( Calendar.SECOND );
	}
	
	public Calendario setDia( int v ) {
		cal.set( Calendar.DAY_OF_MONTH, v );
		return this;
	}
	
	public Calendario setMes( int v ) {
		cal.set( Calendar.MONTH, v );
		return this;
	}
	
	public Calendario setMesReal( int v ) {
		cal.set( Calendar.MONTH, v - 1 );
		return this;
	}
	
	public Calendario setAno( int v ) {
		cal.set( Calendar.YEAR, v );
		return this;
	}
	
	public Calendario setHora( int v ) {
		cal.set( Calendar.HOUR_OF_DAY, v );
		return this;
	}
	
	public Calendario setMinuto( int v ) {
		cal.set( Calendar.MINUTE, v );
		return this;
	}
	
	public Calendario setSegundo( int v ) {
		cal.set( Calendar.SECOND, v );
		return this;
	}
	
	public Calendario setMilissegundo( int v ) {
		cal.set( Calendar.MILLISECOND, v );
		return this;
	}
	
	public Calendario limparHorario() {
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );
		return this;
	}
	
	public Calendario maisUmMes() {
		int mes = getMes() + 1;
		int ano = getAno();
		if( mes == 12 ){
			mes = 0;
			ano++;
		}
		setMes( mes );
		setAno( ano );
		return this;
	}
	
	public Calendario menosUmMes() {
		int mes = getMes() - 1;
		int ano = getAno();
		if( mes == -1 ){
			mes = 11;
			ano--;
		}
		setMes( mes );
		setAno( ano );
		return this;
	}
	
	public Calendario limpar() {
		cal.clear();
		return this;
	}

}
