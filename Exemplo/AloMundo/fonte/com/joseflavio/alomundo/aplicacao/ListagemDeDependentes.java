
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

package com.joseflavio.alomundo.aplicacao;

import javax.persistence.Entity;

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class ListagemDeDependentes<O, DEPENDENCIA> extends Listagem<O> {

	protected DEPENDENCIA dependencia;
	
	protected ListagemDeDependentes( AloMundo aplicacao, Class<? extends Informacao> cadastrador, DEPENDENCIA dependencia, String titulo, String subtitulo, boolean construir ) throws TomaraQueCaiaException {

		super( aplicacao, cadastrador, titulo, subtitulo, false );
		
		if( dependencia.getClass().getAnnotation( Entity.class ) != null && aplicacao.getEntityManager().contains( dependencia ) ){ //TODO JPAUtil
			this.dependencia = aplicacao.atualizar( dependencia );			
		}else{
			this.dependencia = dependencia;
		}
		
		if( construir ) construir();
		
	}
	
	protected ListagemDeDependentes( AloMundo aplicacao, Class<? extends Informacao> cadastrador, DEPENDENCIA dependencia, String titulo, boolean construir ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, dependencia, titulo, titulo, construir );
	}
	
	protected ListagemDeDependentes( AloMundo aplicacao, Class<? extends Informacao> cadastrador, DEPENDENCIA dependencia, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, dependencia, titulo, subtitulo, true );
	}
	
	protected ListagemDeDependentes( AloMundo aplicacao, Class<? extends Informacao> cadastrador, DEPENDENCIA dependencia, String titulo ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, dependencia, titulo, titulo, true );
	}	
	
	@Override
	public void adicionar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		try{
			
			viagem.visitar( cadastrador.getConstructor( AloMundo.class, dependencia.getClass() ).newInstance( aplicacao, dependencia ) );
			
		}catch( Exception e ){
			if( e instanceof ValidacaoException ) throw (ValidacaoException) e;
			if( e instanceof TomaraQueCaiaException ) throw (TomaraQueCaiaException) e;
			throw new TomaraQueCaiaException( e );
		}
		
	}
	
}
