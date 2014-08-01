
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

/**
 * Informações sobre a sintaxe da língua de uma determinada {@link Cultura}.
 * @author José Flávio de Souza Dias Júnior
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
	 * Retorna o nome do mês.
	 * @param mes {@link Calendar#MONTH}
	 */
	public abstract String getMesNome( int mes );
	
	public abstract int getMes( String nome ) throws IllegalArgumentException;
	
	/**
	 * Retorna o nome do mês abreviado.
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
	 * @param genero Especifica o gênero da palavra. <code>true</code> == masculino.
	 * @throws IllegalArgumentException caso a palavra não seja reconhecida.
	 * @see #PALAVRA_OK
	 */
	public abstract String getEscrita( int palavra, boolean genero ) throws IllegalArgumentException;
	
	/**
	 * Retorna o separador de milha de números.
	 */
	public abstract char getSeparadorMilha();
	
	/**
	 * Retorna o separador de parte fracionária de números.
	 */
	public abstract char getSeparadorDecimal();
	
}
