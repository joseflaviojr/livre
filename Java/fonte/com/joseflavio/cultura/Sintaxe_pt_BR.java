
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


/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
class Sintaxe_pt_BR extends Sintaxe {

	/**
	 * Singleton.
	 */
	public static final Sintaxe_pt_BR instancia = new Sintaxe_pt_BR();
	
	private Sintaxe_pt_BR() {
	}
	
	public static final String[] MES_COMPLETO = {
		"Janeiro",
		"Fevereiro",
		"Março",
		"Abril",
		"Maio",
		"Junho",
		"Julho",
		"Agosto",
		"Setembro",
		"Outubro",
		"Novembro",
		"Dezembro"
	};
	
	public static final String[] MES_ABREVIADO = {
		"Jan",
		"Fev",
		"Mar",
		"Abr",
		"Mai",
		"Jun",
		"Jul",
		"Ago",
		"Set",
		"Out",
		"Nov",
		"Dez"
	};
	
	public static final String[] DIA_SEMANA_COMPLETO = {
		"Domingo",
		"Segunda-feira",
		"Terça-feira",
		"Quarta-feira",
		"Quinta-feira",
		"Sexta-feira",
		"Sábado"
	};
	
	public String getMesNome( int mes ) {
		return MES_COMPLETO[ mes ];		
	}
	
	public int getMes( String nome ) throws IllegalArgumentException {

		int total = MES_COMPLETO.length;
		
		for( int i = 0; i < total; i++ ){
			
			if( MES_COMPLETO[i].equals( nome ) ) return i;
			
		}
		
		throw new IllegalArgumentException( nome );
		
	}

	public String getMesNomeAbreviado( int mes ) {
		return MES_ABREVIADO[ mes ];		
	}
	
	public int getMesAbreviado( String abreviado ) throws IllegalArgumentException {
		
		int total = MES_ABREVIADO.length;
		
		for( int i = 0; i < total; i++ ){
			
			if( MES_ABREVIADO[i].equals( abreviado ) ) return i;
			
		}
		
		throw new IllegalArgumentException( abreviado );
		
	}

	public String getDiaDaSemanaNome( int diaDaSemana ) {
		return DIA_SEMANA_COMPLETO[ diaDaSemana - 1 ];		
	}
	
	public int getDiaDaSemana( String nome ) throws IllegalArgumentException {
		
		int total = DIA_SEMANA_COMPLETO.length;
		
		for( int i = 0; i < total; i++ ){
			
			if( DIA_SEMANA_COMPLETO[i].equals( nome ) ) return i + 1;
			
		}
		
		throw new IllegalArgumentException( nome );
		
	}
	
	public String[] getMesesNomes() {
		return MES_COMPLETO;
	}
	
	public String[] getMesesNomesAbreviados() {
		return MES_ABREVIADO;
	}
	
	public String[] getDiasDaSemanaNomes() {
		return DIA_SEMANA_COMPLETO;
	}

	public String getEscrita( int palavra, boolean genero ) throws IllegalArgumentException {

		switch( palavra ){
			
			case PALAVRA_OK :
				return "Ok";
				
			case PALAVRA_CANCELAR :
				return "Cancelar";
				
			case PALAVRA_SIM :
				return "Sim";
				
			case PALAVRA_NAO :
				return "Não";
				
			case PALAVRA_ATENCAO :
				return "Atenção";
				
			case PALAVRA_INFORMACAO :
				return "Informação";
				
			case PALAVRA_ERRO :
				return "Erro";
				
			case PALAVRA_AJUDA :
				return "Ajuda";
				
			case PALAVRA_ACAO :
				return "Ação";
				
			case PALAVRA_PAGINA :
				return "Página";
				
			case PALAVRA_PRIMEIRO :
				return genero ? "Primeiro" : "Primeira";
				
			case PALAVRA_ULTIMO :
				return genero ? "Último" : "Última";
				
			case PALAVRA_UNICO :
				return genero ? "Único" : "Única";
			
		}
		
		throw new IllegalArgumentException( "Palavra desconhecida!" );
		
	}
	
	public char getSeparadorMilha() {
		return '.';
	}
	
	public char getSeparadorDecimal() {
		return ',';
	}
	
}
