
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

package com.joseflavio.tqc.aplicacao;

import java.lang.reflect.InvocationTargetException;

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.util.Instanciador;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class ComandoVisitar extends ComandoImplementado {

	private Instanciador<Informacao> instanciador;
	
	private boolean novaViagem;
	
	public ComandoVisitar( String nome, String rotulo, boolean novaViagem, Class<? extends Informacao> destino, Object... parametros ) {
		
		super( nome, rotulo );
		
		this.novaViagem = novaViagem;
		this.instanciador = new Instanciador<Informacao>( destino, parametros );
		
	}
	
	public ComandoVisitar( String rotulo, boolean novaViagem, Class<? extends Informacao> destino, Object... parametros ) {
		this( null, rotulo, novaViagem, destino, parametros );
	}
	
	public ComandoVisitar( String rotulo, Class<? extends Informacao> destino, Object... parametros ) {
		this( rotulo, false, destino, parametros );
	}
	
	public void acao( Viagem viagem ) throws ValidacaoException, TomaraQueCaiaException {
		
		try{
			
			if( novaViagem ){
				viagem.getTomaraQueCaia().novaViagem().visitar( instanciador.instanciar() ).ativar();
			}else{
				viagem.visitar( instanciador.instanciar() );
			}
			
		}catch( IllegalArgumentException e ){
			throw new TomaraQueCaiaException( e );
		}catch( InstantiationException e ){
			throw new TomaraQueCaiaException( e );
		}catch( IllegalAccessException e ){
			throw new TomaraQueCaiaException( e );
		}catch( InvocationTargetException e ){
			throw new TomaraQueCaiaException( e );
		}
		
	}

}
