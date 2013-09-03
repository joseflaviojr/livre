
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

import java.util.Calendar;
import java.util.Date;

/**
 * {@link Transformacao} de {@link Date}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class DataTransformacao extends Transformacao {

	public static final int COMPLETA = 0;
    public static final int EXTENSA = 1;
    public static final int NORMAL = 2;
    public static final int RESUMIDA = 3;
    public static final int PADRAO = NORMAL;
    
    /**
     * Dia, m�s, ano, horas e minutos definidos numericamente.<br>
     * As horas s�o definidas no formato de 24h.
     */
    public static final int NUMERICA = 4;

    private Cultura cultura;
    
	private int estilo;
	
	private Calendar calendario;
	
	private Sintaxe sintaxe;
	
	DataTransformacao( Cultura cultura, int estilo ) {
		
		this.cultura = cultura;
		this.estilo = estilo;
		this.calendario = cultura.novoCalendar();
		this.sintaxe = cultura.getSintaxe();
		
	}

	public StringBuffer transcrever( Object data, StringBuffer receptor ) throws TransformacaoException {

		calendario.setTime( (Date) data );
		
		return transcreverDataHora( calendario, sintaxe, estilo, receptor );
		
	}
	
	public final String transcreverDataHora( Date data ) throws TransformacaoException {
		
		return transcreverDataHora( data, new StringBuffer( 50 ) ).toString();
		
	}
	
	public final String transcreverDataHoraConfiante( Date data ) {
		
		try{
			
			return transcreverDataHora( data );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
	
	public final StringBuffer transcreverDataHora( Date data, StringBuffer receptor ) throws TransformacaoException {

		calendario.setTime( data );
		
		return transcreverDataHora( calendario, sintaxe, estilo, receptor );
		
	}
	
	public final String transcreverData( Date data ) throws TransformacaoException {
		
		return transcreverData( data, new StringBuffer( 50 ) ).toString();
		
	}
	
	public final String transcreverDataConfiante( Date data ) {
		
		try{
			
			return transcreverData( data );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
	
	public final StringBuffer transcreverData( Date data, StringBuffer receptor ) throws TransformacaoException {

		calendario.setTime( data );
		
		return transcreverData( calendario, sintaxe, estilo, receptor );
		
	}
	
	public final String transcreverHora( Date data ) throws TransformacaoException {
		
		return transcreverHora( data, new StringBuffer( 50 ) ).toString();
		
	}
	
	public final String transcreverHoraConfiante( Date data ) {
		
		try{
			
			return transcreverHora( data );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
	
	public final StringBuffer transcreverHora( Date data, StringBuffer receptor ) throws TransformacaoException {

		calendario.setTime( data );
		
		return transcreverHora( calendario, sintaxe, estilo, receptor );
		
	}
	
	public Object transformar( Object obj ) throws TransformacaoException {

		if( ! ( obj instanceof String ) ) throw new TransformacaoException( "Objeto incompat�vel." );
		
		calendario.clear();
		transformarDataHora( (String) obj, sintaxe, estilo, calendario );
		return calendario.getTime();
		
	}
	
	public Date transformarData( String data ) throws TransformacaoException {
		
		calendario.clear();
		transformarData( data, sintaxe, estilo, calendario );
		return calendario.getTime();
		
	}
	
	public Date transformarDataConfiante( String data ) {
		
		try{
			
			return transformarData( data );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
	
	public Date transformarHora( String hora ) throws TransformacaoException {
		
		calendario.clear();
		transformarHora( hora, sintaxe, estilo, calendario );
		return calendario.getTime();
		
	}
	
	public Date transformarHoraConfiante( String hora ) {
		
		try{
			
			return transformarHora( hora );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}
	
	public Date transformarDataHora( String dataHora ) throws TransformacaoException {
		
		calendario.clear();
		transformarDataHora( dataHora, sintaxe, estilo, calendario );
		return calendario.getTime();
		
	}
	
	public Date transformarDataHoraConfiante( String dataHora ) {
		
		try{
			
			return transformarDataHora( dataHora );
			
		}catch( TransformacaoException e ){
			return null;
		}
		
	}

	/**
	 * Formata a data no estilo especificado.
	 */
	protected abstract StringBuffer transcreverData( Calendar data, Sintaxe sintaxe, int estilo, StringBuffer receptor ) throws TransformacaoException;

	/**
	 * Formata a hora no estilo especificado.
	 */
	protected abstract StringBuffer transcreverHora( Calendar hora, Sintaxe sintaxe, int estilo, StringBuffer receptor ) throws TransformacaoException;

	/**
	 * Formata a data e hora no estilo especificado.
	 */
	protected abstract StringBuffer transcreverDataHora( Calendar dataHora, Sintaxe sintaxe, int estilo, StringBuffer receptor ) throws TransformacaoException;

	protected abstract void transformarData( String data, Sintaxe sintaxe, int estilo, Calendar receptor ) throws TransformacaoException;
	
	protected abstract void transformarHora( String hora, Sintaxe sintaxe, int estilo, Calendar receptor ) throws TransformacaoException;
	
	protected abstract void transformarDataHora( String dataHora, Sintaxe sintaxe, int estilo, Calendar receptor ) throws TransformacaoException;
	
	public Cultura getCultura() {
		return cultura;
	}

	public void setEstilo( int estilo ) {
		this.estilo = estilo;
	}
	
	public int getEstilo() {
		return estilo;
	}
	
	protected String doisDigitos( int n ) {
		
		return n < 10 ? "0" + n : "" + n;
		
	}
	
	protected String quatroDigitos( int n ) {
		
		if( n < 10 ) return "000" + n;
		if( n < 100 ) return "00" + n;
		if( n < 1000 ) return "0" + n;
		
		return "" + n;
		
	}
	
	/**
	 * Exemplo: S�culo base do ano 2359: 2300.
	 */
	protected int seculoBase() {
		
		calendario.setTime( new Date() );
		
		int seculoBase = calendario.get( Calendar.YEAR );
		for( ; ( seculoBase % 100 ) != 0; seculoBase-- );
		
		return seculoBase;
		
	}
	
}
