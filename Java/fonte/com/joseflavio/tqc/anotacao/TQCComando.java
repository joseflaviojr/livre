
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

package com.joseflavio.tqc.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.ComandoPorMetodo;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Especificação de {@link Comando} através de {@link Method Método}.<br>
 * A {@link Class} deverá implementar {@link ComandoPorMetodo}.<br>
 * O {@link Method} deverá ter dois parâmetros: {@link Viagem}, {@link Comando}.<br>
 * O {@link Method} deverá assinar duas exceções: {@link ValidacaoException}, {@link TomaraQueCaiaException}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see ComandoPorMetodo
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TQCComando {
	
	/**
	 * @see Comando#getRotulo()
	 */
	String rotulo();
	
	/**
	 * @see Comando#getNome()
	 */
	String nome();
	
	/**
	 * @see Comando#getImagem()
	 */
	String imagem() default "";
	
	/**
	 * @see Comando#getFuncaoTipo()
	 */
	Funcao funcao() default Funcao.GERAL;
	
	/**
	 * {@link Informacao#mais(Comando) Adição} automática de {@link Comando} na {@link Informacao}.
	 */
	boolean adicionar() default true;
	
	/**
	 * Ordem preferencial de posicionamento na tela.
	 */
	int ordem() default 99;
	
}
