
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

package com.joseflavio.memoria;

import com.joseflavio.JoseFlavioException;


/**
 * Exce��o de {@link DepositoDeObjetos}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class DepositoException extends JoseFlavioException {

	private static final long serialVersionUID = 1L;

	public static final int RAZAO_INDEFINIDA = 0;
	
	/**
	 * Objeto desejado inexistente.
	 */
	public static final int RAZAO_INEXISTENCIA = 1;
	
	/**
	 * Objeto j� existente.
	 */
	public static final int RAZAO_EXISTENCIA = 2;
	
	/**
	 * Conex�o n�o estabelecida.
	 */
	public static final int RAZAO_CONECTIVIDADE = 3;
	
	/**
	 * Autentica��o negada.
	 */
	public static final int RAZAO_AUTENTICACAO = 4;
	
	/**
	 * Autoriza��o negada.
	 */
	public static final int RAZAO_AUTORIZACAO = 5;
	
	/**
	 * Inconsist�ncia detectada.
	 */
	public static final int RAZAO_INCONSISTENCIA = 6;
	
	/**
	 * Opera��o cancelada por exist�ncia de depend�ncia.
	 */
	public static final int RAZAO_DEPENDENCIA = 7;
	
	private int razao = RAZAO_INDEFINIDA;

	/**
	 * @param razao Veja {@link #RAZAO_INDEFINIDA}.
	 */
	public DepositoException( int razao, String mensagem, Throwable causa ) {
		super( mensagem, causa );
		this.razao = razao;
	}
	
	public DepositoException( int razao, String mensagem ) {
		super( mensagem );
		this.razao = razao;
	}
	
	public DepositoException( int razao, Throwable causa ) {
		super( causa );
		this.razao = razao;
	}

	public DepositoException( String mensagem, Throwable causa ) {
		super( mensagem, causa );
	}

	public DepositoException( String mensagem ) {
		super( mensagem );
	}

	public DepositoException( Throwable causa ) {
		super( causa );
	}
	
	public int getRazao() {
		return razao;
	}

}
