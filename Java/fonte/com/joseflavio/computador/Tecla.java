
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

package com.joseflavio.computador;


/**
 * Componente de teclado brasileiro (ABNT2).
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @deprecated Quantidade de teclas limitada.
 */
public enum Tecla {
	
	N0(0), N1(1), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8), N9(9),
	
	A(10), B(11), C(12), D(13), E(14), F(15), G(16), H(17), I(18), J(19), K(20), L(21), M(22), N(23), O(24), P(25), Q(26),
	R(27), S(28), T(29), U(30), V(31), W(32), X(33), Y(34), Z(35), C_CEDILHA(36),
	
	AGUDO(37), TIL(38), ASPAS(39),
	
	PONTO(40), VIRGULA(41), PONTO_VIRGULA(42), BARRA_DIREITA(43), BARRA_ESQUERDA(44), COLCHETE_ESQUERDO(45), COLCHETE_DIREITO(46), MENOS(47), IGUAL(48),
	
	/**
	 * ENTER
	 */
	ENTRAR(49),
	
	/**
	 * ESC
	 */
	CANCELAR(50),
	
	/**
	 * SHIFT
	 */
	MUDAR(51),
	
	/**
	 * ALT
	 */
	ALTERNAR(52),
	
	/**
	 * CONTROL
	 */
	CONTROLAR(53),
	
	/**
	 * BACKSPACE
	 */
	APAGAR_ESQUERDA(54),
	
	/**
	 * DELETE
	 */
	APAGAR_DIREITA(55),
	
	/**
	 * TAB
	 */
	TABULAR(56),
	
	/**
	 * INSERT
	 */
	INSERIR(57),
	
	INICIO(58), FIM(59), ESQUERDA(60), DIREITA(61), CIMA(62), BAIXO(63);
	
	private long valor;

	private Tecla( long posicao ) {
		this.valor = (long) Math.pow( 2, posicao );
	}
	
	/**
	 * Valor inteiro exclusivo, potência de 2, correspondente à {@link Tecla}.<br>
	 * Cada {@link Tecla} corresponde a 1 bit dos 64 disponíveis, permitindo disjunção lógica com 64 bits.
	 */
	public long getValor() {
		return valor;
	}
	
	/**
	 * Verifica se uma {@link Tecla} está presente numa combinação de {@link Tecla}s.
	 * @param tecla {@link Tecla} desejada.
	 * @param combinacao Combinação (disjunção lógica) de {@link Tecla#getValor() valores de teclas}. Exemplo: {@link Tecla#CONTROLAR} | {@link Tecla#A}
	 */
	public static boolean isPresente( Tecla tecla, long combinacao ) {
		return ( combinacao & tecla.valor ) == tecla.valor;
	}
	
}
