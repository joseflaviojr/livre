
/*
 *  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
 * @author Jos� Fl�vio de Souza Dias J�nior
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
		"Mar�o",
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
		"Ter�a-feira",
		"Quarta-feira",
		"Quinta-feira",
		"Sexta-feira",
		"S�bado"
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
				return "N�o";
				
			case PALAVRA_ATENCAO :
				return "Aten��o";
				
			case PALAVRA_INFORMACAO :
				return "Informa��o";
				
			case PALAVRA_ERRO :
				return "Erro";
				
			case PALAVRA_AJUDA :
				return "Ajuda";
				
			case PALAVRA_ACAO :
				return "A��o";
				
			case PALAVRA_PAGINA :
				return "P�gina";
				
			case PALAVRA_PRIMEIRO :
				return genero ? "Primeiro" : "Primeira";
				
			case PALAVRA_ULTIMO :
				return genero ? "�ltimo" : "�ltima";
				
			case PALAVRA_UNICO :
				return genero ? "�nico" : "�nica";
			
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
