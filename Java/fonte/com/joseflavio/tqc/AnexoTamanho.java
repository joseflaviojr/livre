
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

package com.joseflavio.tqc;

import com.joseflavio.modelo.JFTamanho;
import com.joseflavio.modelo.JFTamanho.Unidade;

/**
 * Tamanho em espaço bidimensional.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see JFTamanho
 */
public class AnexoTamanho extends Anexo {

	private int largura = -1;
	
	private int altura = -1;
	
	private int larguraMaxima = -1;
	
	private int alturaMaxima = -1;
	
	private int larguraMinima = -1;
	
	private int alturaMinima = -1;
	
	private Unidade unidade = Unidade.PIXEL;
	
	public AnexoTamanho() {
	}

	/**
	 * @param largura Largura desejada. -1 == indefinida.
	 * @param altura Altura desejada. -1 == indefinida.
	 */
	public AnexoTamanho( int largura, int altura ) {
		this.largura = largura;
		this.altura = altura;
	}
	
	/**
	 * @param largura Largura desejada. -1 == indefinida.
	 * @param altura Altura desejada. -1 == indefinida.
	 */
	public AnexoTamanho( int largura, int altura, int larguraMaxima, int alturaMaxima, int larguraMinima, int alturaMinima, Unidade unidade ) {
		this.largura = largura;
		this.altura = altura;
		this.larguraMaxima = larguraMaxima;
		this.alturaMaxima = alturaMaxima;
		this.larguraMinima = larguraMinima;
		this.alturaMinima = alturaMinima;
		this.unidade = unidade;
	}

	/**
	 * this( largura, -1 );
	 */
	public AnexoTamanho( int largura ) {
		this( largura, -1 );
	}
	
	public void setTamanho( int largura, int altura ) {
		this.largura = largura;
		this.altura = altura;
	}
	
	public int getLargura() {
		return largura;
	}
	
	public void setLargura( int largura ) {
		this.largura = largura;
	}
	
	public int getAltura() {
		return altura;
	}
	
	public void setAltura( int altura ) {
		this.altura = altura;
	}
	
	public int getLarguraMaxima() {
		return larguraMaxima;
	}
	
	public void setLarguraMaxima( int larguraMaxima ) {
		this.larguraMaxima = larguraMaxima;
	}
	
	public int getAlturaMaxima() {
		return alturaMaxima;
	}
	
	public void setAlturaMaxima( int alturaMaxima ) {
		this.alturaMaxima = alturaMaxima;
	}
	
	public int getLarguraMinima() {
		return larguraMinima;
	}
	
	public void setLarguraMinima( int larguraMinima ) {
		this.larguraMinima = larguraMinima;
	}
	
	public int getAlturaMinima() {
		return alturaMinima;
	}
	
	public void setAlturaMinima( int alturaMinima ) {
		this.alturaMinima = alturaMinima;
	}
	
	public Unidade getUnidade() {
		return unidade;
	}
	
	public void setUnidade( Unidade unidade ) {
		this.unidade = unidade;
	}
	
}
