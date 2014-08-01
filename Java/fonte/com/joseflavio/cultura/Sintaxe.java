
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

import java.util.Calendar;

/**
 * Informa��es sobre a sintaxe da l�ngua de uma determinada {@link Cultura}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class Sintaxe {
	
	public static final int PALAVRA_OK = 1;	
	public static final int PALAVRA_CANCELAR = 2;	
	public static final int PALAVRA_SIM = 3;	
	public static final int PALAVRA_NAO = 4;
	public static final int PALAVRA_ATENCAO = 5;
	public static final int PALAVRA_INFORMACAO = 6;
	public static final int PALAVRA_ERRO = 7;
	public static final int PALAVRA_AJUDA = 8;
	public static final int PALAVRA_ACAO = 9;
	public static final int PALAVRA_PAGINA = 10;
	public static final int PALAVRA_PRIMEIRO = 11;
	public static final int PALAVRA_ULTIMO = 12;
	public static final int PALAVRA_UNICO = 13;
	
	Sintaxe() {
	}
	
	/**
	 * Retorna o nome do m�s.
	 * @param mes {@link Calendar#MONTH}
	 */
	public abstract String getMesNome( int mes );
	
	public abstract int getMes( String nome ) throws IllegalArgumentException;
	
	/**
	 * Retorna o nome do m�s abreviado.
	 * @param mes {@link Calendar#MONTH}
	 */
	public abstract String getMesNomeAbreviado( int mes );
	
	public abstract int getMesAbreviado( String abreviado ) throws IllegalArgumentException;
	
	/**
	 * Retorna o nome do dia da semana.
	 * @param diaDaSemana {@link Calendar#DAY_OF_WEEK}
	 */
	public abstract String getDiaDaSemanaNome( int diaDaSemana );
	
	public abstract int getDiaDaSemana( String nome ) throws IllegalArgumentException;
	
	/**
	 * Retorna o nome de todos os meses do ano.
	 */
	public abstract String[] getMesesNomes();
	
	/**
	 * Retorna o nome abreviado de todos os meses do ano.
	 */
	public abstract String[] getMesesNomesAbreviados();
	
	/**
	 * Retorna o nome de todos os dias da semana.
	 */
	public abstract String[] getDiasDaSemanaNomes();
	
	/**
	 * Retorna a forma escrita nesta {@link Cultura} de uma palavra comum entre as {@link Cultura}'s.
	 * @param genero Especifica o g�nero da palavra. <code>true</code> == masculino.
	 * @throws IllegalArgumentException caso a palavra n�o seja reconhecida.
	 * @see #PALAVRA_OK
	 */
	public abstract String getEscrita( int palavra, boolean genero ) throws IllegalArgumentException;
	
	/**
	 * Retorna o separador de milha de n�meros.
	 */
	public abstract char getSeparadorMilha();
	
	/**
	 * Retorna o separador de parte fracion�ria de n�meros.
	 */
	public abstract char getSeparadorDecimal();
	
}
