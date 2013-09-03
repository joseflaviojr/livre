
/*
 *  Copyright (C) 2009-2013 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2013 José Flávio de Souza Dias Júnior
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

package com.joseflavio.tqc.dado;

import java.lang.reflect.Method;

import com.joseflavio.tqc.Identificacao;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.anotacao.TQCComando;

/**
 * Especificação de {@link Comando} através de {@link Method Método}.<br>
 * Anotar os {@link Method Métodos} {@link Comando comandados} com {@link TQCComando}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see TQCComando
 */
public interface ComandoPorMetodo {
	
	/**
	 * {@link Method Métodos} anotados com {@link TQCComando}.
	 */
	Method[] getMetodosComandados();
	
	/**
	 * {@link Comando} equivalente à {@link TQCComando anotação} do {@link Method método}.<br>
	 * Não há restrição quanto ao controle de instâncias.
	 * @throws IllegalArgumentException se {@link TQCComando} ausente.
	 * @throws IllegalArgumentException se {@link TQCComando#nome()} inválido.
	 */
	Comando getComando( Method metodoComandado );
	
	/**
	 * Retorna o {@link Comando} {@link Identificacao#getNome() denominado} conforme parâmetro.<br>
	 * Caso o {@link Comando} não esteja contido na {@link Informacao}, ele será instanciado de acordo com {@link #getComando(Method)}.
	 * @throws IllegalArgumentException se inexiste o {@link Comando} e se inexiste um {@link #getMetodosComandados() método comandado} correspondente.
	 */
	Comando getComando( String nome );

}
