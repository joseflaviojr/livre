
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

package com.joseflavio.tqc.console;

import java.awt.Color;

/**
 * Associação das cores do {@link Console} a suas respectivas {@link Color}s.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public enum Cor {

	PRETA            (  0, Color.BLACK ),
	AZUL             (  1, new Color( 0x00, 0x00, 0x8B ) ),
	VERDE            (  2, new Color( 0x00, 0x64, 0x00 ) ),
	CIANO            (  3, new Color( 0x00, 0x8B, 0x8B ) ),
	VERMELHA         (  4, new Color( 0x8B, 0x00, 0x00 ) ),
	MAGENTA          (  5, new Color( 0x8B, 0x00, 0x8B ) ),
	MARRON           (  6, new Color( 0xA5, 0x2A, 0x2A ) ),
	CINZA_INTENSA    (  7, new Color( 0xD3, 0xD3, 0xD3 ) ),
	CINZA_ESCURA     (  8, Color.GRAY ),
	AZUL_INTENSA     (  9, Color.BLUE ),
	VERDE_INTENSA    ( 10, Color.GREEN ),
	CIANO_INTENSA 	 ( 11, Color.CYAN ),
	VERMELHA_INTENSA ( 12, Color.RED ),
	MAGENTA_INTENSA  ( 13, Color.MAGENTA ),
	AMARELA          ( 14, Color.YELLOW ),
	BRANCA           ( 15, Color.WHITE );
	
	private int cor;
	
	private Color color;
	
	private static final Cor[] COR = {
		PRETA, AZUL, VERDE,
		CIANO, VERMELHA, MAGENTA,
		MARRON, CINZA_INTENSA, CINZA_ESCURA,
		AZUL_INTENSA, VERDE_INTENSA, CIANO_INTENSA,
		VERMELHA_INTENSA, MAGENTA_INTENSA, AMARELA,
		BRANCA
		};
	
	private Cor( int cor, Color color ) {
		this.cor = cor;
		this.color = color;
	}
	
	/**
	 * @see Console#COR_PRETA
	 */
	public static Cor getCor( int cor ){
		return COR[ cor ];
	}

	public int getCor() {
		return cor;
	}
	
	public Color getColor() {
		return color;
	}
	
}
