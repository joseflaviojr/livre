
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

package com.joseflavio.util;

import java.lang.reflect.Field;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.JFApresentacao;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class ModeloUtil {
	
	/**
	 * Retorna o valor de {@link JFApresentacao} de uma {@link Class}.
	 * @param plural {@link JFApresentacao#plural()} ou {@link JFApresentacao#value()}?
	 * @return {@link String} vazia caso a {@link Class} n�o tenha uma {@link JFApresentacao} associada.
	 */
	public static String getApresentacao( Class<? extends Object> classe, boolean plural ) {
		return getApresentacao( classe.getAnnotation( JFApresentacao.class ), plural );
	}
	
	/**
	 * Retorna o {@link JFApresentacao#value() valor} de {@link JFApresentacao} de uma {@link Class}.
	 * @return {@link String} vazia caso a {@link Class} n�o tenha uma {@link JFApresentacao} associada.
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
	 * @return {@link String} vazia caso o {@link Field} n�o tenha uma {@link JFApresentacao} associada.
	 */
	public static String getApresentacao( Class<? extends Object> classe, String atributo, boolean plural ) {
		return getApresentacao( AssistenteDeAtributo.getJFApresentacao( classe, atributo, false ), plural );
	}
	
	/**
	 * Retorna o {@link JFApresentacao#value() valor} de {@link JFApresentacao} de um {@link Field}.
	 * @return {@link String} vazia caso o {@link Field} n�o tenha uma {@link JFApresentacao} associada.
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
