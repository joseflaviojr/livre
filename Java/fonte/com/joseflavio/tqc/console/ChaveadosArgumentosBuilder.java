
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 * Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 * sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 * (a seu crit�rio) qualquer vers�o posterior.
 * 
 * Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 * por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 * COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 * Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 * junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.console;

import java.util.ArrayList;
import java.util.List;

import com.joseflavio.tqc.console.ChaveadosArgumentos.ArgumentoProcessador;
import com.joseflavio.tqc.console.ChaveadosArgumentos.Chave;


/**
 * Builder de {@link ChaveadosArgumentos}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class ChaveadosArgumentosBuilder {

	private String[] args;
	
	private List<Chave> chaves = new ArrayList<ChaveadosArgumentos.Chave>();

	public ChaveadosArgumentosBuilder( String[] args ) {
		this.args = args;
	}
	
	public ChaveadosArgumentosBuilder mais( Chave chave ) {
		this.chaves.add( chave );
		return this;
	}
	
	/**
	 * @see Chave#Chave(String, boolean, ArgumentoProcessador, String)
	 * @see #mais(Chave)
	 */
	public ChaveadosArgumentosBuilder mais( String token, boolean requerValor, ArgumentoProcessador processador, String explicacao ) {
		return mais( new Chave( token, requerValor, processador, explicacao ) );
	}
	
	/**
	 * @see Chave#Chave(String, boolean, ArgumentoProcessador)
	 * @see #mais(Chave)
	 */
	public ChaveadosArgumentosBuilder mais( String token, boolean requerValor, ArgumentoProcessador processador ) {
		return mais( new Chave( token, requerValor, processador ) );
	}
	
	/**
	 * @see Chave#Chave(String, boolean)
	 * @see #mais(Chave)
	 */
	public ChaveadosArgumentosBuilder mais( String token, boolean requerValor ) {
		return mais( new Chave( token, requerValor ) );
	}
	
	public ChaveadosArgumentos getChaveadosArgumentos() {
		return new ChaveadosArgumentos( args, chaves );
	}

}
