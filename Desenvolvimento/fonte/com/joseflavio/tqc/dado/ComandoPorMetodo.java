
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
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.dado;

import java.lang.reflect.Method;

import com.joseflavio.tqc.Identificacao;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.anotacao.TQCComando;

/**
 * Especifica��o de {@link Comando} atrav�s de {@link Method M�todo}.<br>
 * Anotar os {@link Method M�todos} {@link Comando comandados} com {@link TQCComando}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see TQCComando
 */
public interface ComandoPorMetodo {
	
	/**
	 * {@link Method M�todos} anotados com {@link TQCComando}.
	 */
	Method[] getMetodosComandados();
	
	/**
	 * {@link Comando} equivalente � {@link TQCComando anota��o} do {@link Method m�todo}.<br>
	 * N�o h� restri��o quanto ao controle de inst�ncias.
	 * @throws IllegalArgumentException se {@link TQCComando} ausente.
	 * @throws IllegalArgumentException se {@link TQCComando#nome()} inv�lido.
	 */
	Comando getComando( Method metodoComandado );
	
	/**
	 * Retorna o {@link Comando} {@link Identificacao#getNome() denominado} conforme par�metro.<br>
	 * Caso o {@link Comando} n�o esteja contido na {@link Informacao}, ele ser� instanciado de acordo com {@link #getComando(Method)}.
	 * @throws IllegalArgumentException se inexiste o {@link Comando} e se inexiste um {@link #getMetodosComandados() m�todo comandado} correspondente.
	 */
	Comando getComando( String nome );

}
