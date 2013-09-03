
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

package com.joseflavio.util;

import java.lang.reflect.Field;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.JFApresentacao;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ModeloUtil {
	
	/**
	 * Retorna o valor de {@link JFApresentacao} de uma {@link Class}.
	 * @param plural {@link JFApresentacao#plural()} ou {@link JFApresentacao#value()}?
	 * @return {@link String} vazia caso a {@link Class} não tenha uma {@link JFApresentacao} associada.
	 */
	public static String getApresentacao( Class<? extends Object> classe, boolean plural ) {
		return getApresentacao( classe.getAnnotation( JFApresentacao.class ), plural );
	}
	
	/**
	 * Retorna o {@link JFApresentacao#value() valor} de {@link JFApresentacao} de uma {@link Class}.
	 * @return {@link String} vazia caso a {@link Class} não tenha uma {@link JFApresentacao} associada.
	 */
	public static String getApresentacao( Class<? extends Object> classe ) {
		return getApresentacao( classe.getAnnotation( JFApresentacao.class ), false );
	}
	
	/**
	 * {@link JFApresentacao} de uma {@link Class}.
	 * @return <code>null</code> caso a {@link JFApresentacao} inexista.
	 */
	public static JFApresentacao getJFApresentacao( Class<? extends Object> classe ) {
		return classe.getAnnotation( JFApresentacao.class );
	}
	
	/**
	 * Retorna o valor de {@link JFApresentacao} de um {@link Field}.
	 * @param plural {@link JFApresentacao#plural()} ou {@link JFApresentacao#value()}?
	 * @return {@link String} vazia caso o {@link Field} não tenha uma {@link JFApresentacao} associada.
	 */
	public static String getApresentacao( Class<? extends Object> classe, String atributo, boolean plural ) {
		return getApresentacao( AssistenteDeAtributo.getJFApresentacao( classe, atributo, false ), plural );
	}
	
	/**
	 * Retorna o {@link JFApresentacao#value() valor} de {@link JFApresentacao} de um {@link Field}.
	 * @return {@link String} vazia caso o {@link Field} não tenha uma {@link JFApresentacao} associada.
	 */
	public static String getApresentacao( Class<? extends Object> classe, String atributo ) {
		return getApresentacao( AssistenteDeAtributo.getJFApresentacao( classe, atributo, false ), false );
	}
	
	/**
	 * {@link JFApresentacao} de um {@link Field}.
	 * @return <code>null</code> caso a {@link JFApresentacao} inexista.
	 */
	public static JFApresentacao getJFApresentacao( Class<? extends Object> classe, String atributo ) {
		return AssistenteDeAtributo.getJFApresentacao( classe, atributo, false );
	}
	
	private static String getApresentacao( JFApresentacao jfa, boolean plural ) {
		
		if( jfa == null ) return "";
		
		String txtS = jfa.value();
		if( ! plural ) return txtS;
		
		String txtP = jfa.plural();
		return txtP != null && txtP.length() > 0 ? txtP : txtS;
		
	}

}
