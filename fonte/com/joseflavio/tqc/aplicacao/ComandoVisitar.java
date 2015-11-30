
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

package com.joseflavio.tqc.aplicacao;

import java.lang.reflect.InvocationTargetException;

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.util.Instanciador;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
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
