
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

import java.util.List;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.anotacao.TQCComando;
import com.joseflavio.tqc.aplicacao.ComandoQuestionamento;
import com.joseflavio.tqc.aplicacao.ComandoVisitar;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.tqc.molde.ListaMolde.ListaColunaPadrao;
import com.joseflavio.util.Lista;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class Listagem<O> extends ListagemBase<O> {
	
	protected Class<? extends Informacao> cadastrador;
	
	protected boolean elementoEmNovaViagem = false;
	
	protected boolean mostrarComandoAdicionar = true;
	
	protected boolean mostrarComandoEditar = true;
	
	protected boolean mostrarComandoExcluir = true;
	
	protected Listagem( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo, String subtitulo, boolean construir ) throws TomaraQueCaiaException {
		
		super( aplicacao, titulo, subtitulo );
		
		this.cadastrador = cadastrador;
		
		if( construir ) construir();
		
	}
	
	protected Listagem( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo, boolean construir ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, titulo, titulo, construir );
	}
	
	protected Listagem( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, titulo, subtitulo, true );
	}
	
	protected Listagem( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, titulo, titulo, true );
	}
	
	@Override
	protected void antesDeMostrarListagem( Viagem viagem ) throws TomaraQueCaiaException {
		
		super.antesDeMostrarListagem( viagem );
		
		getComando( "adicionar" ).setVisivel( mostrarComandoAdicionar && cadastrador != null );
		
	}
	
	@TQCComando( rotulo="Adicionar", nome="adicionar", ordem=1, funcao=Funcao.CONFIRMAR )
	public void adicionar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		try{
			
			viagem.visitar( cadastrador.getConstructor( AloMundo.class ).newInstance( aplicacao ) );
			
		}catch( Exception e ){
			if( e instanceof ValidacaoException ) throw (ValidacaoException) e;
			if( e instanceof TomaraQueCaiaException ) throw (TomaraQueCaiaException) e;
			throw new TomaraQueCaiaException( e );
		}
		
	}
	
	@TQCComando( rotulo="Fechar", nome="fechar", funcao=Funcao.CANCELAR )
	public void fechar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		sair( viagem );
	}
	
	/**
	 * @see ColunaAcoes
	 * @see ColunaAcoes#getDados(Object)
	 */
	protected Lista<Dado> acoesPara( O elemento ) {
		
		Comando comandoEditar = null;
		
		if( mostrarComandoEditar && cadastrador != null ){
		
			Object dependencia = null;
			
			if( this instanceof ListagemDeDependentes<?,?> ) dependencia = ((ListagemDeDependentes<?,?>)this).dependencia;
			else if( this instanceof ListagemDeDependentesPaginada<?,?> ) dependencia = ((ListagemDeDependentesPaginada<?,?>)this).dependencia;
			
			if( dependencia != null ){
				try{
					cadastrador.getConstructor( aplicacao.getClass(), dependencia.getClass(), elemento.getClass() );
					comandoEditar = new ComandoVisitar( "Editar", elementoEmNovaViagem, cadastrador, aplicacao, dependencia, elemento );
				}catch( Exception e ){
					comandoEditar = new ComandoVisitar( "Editar", elementoEmNovaViagem, cadastrador, aplicacao, elemento );
				}
			}else{
				comandoEditar = new ComandoVisitar( "Editar", elementoEmNovaViagem, cadastrador, aplicacao, elemento );
			}
		
		}
		
		Lista<Dado> lista = new Lista<Dado>();
		if( comandoEditar != null ) lista.mais( comandoEditar.setImagem( "img/iconep/editar.png" ) );
		if( mostrarComandoExcluir ) lista.mais( new ComandoApagarQuestionando( elemento ).setImagem( "img/iconep/remover.png" ) );
		
		return lista;
		
	}
	
	/**
	 * @see ComandoApagarQuestionando
	 */
	protected void remover( O elemento ) throws ValidacaoException, TomaraQueCaiaException {
		aplicacao.remover( elemento, "Não foi possível apagar \"" + elemento.toString() + "\".", true );
	}
	
	/**
	 * @deprecated {@link #elementoEmNovaViagem} acessado diretamente pelas subclasses.
	 */
	public boolean isElementoEmNovaViagem() {
		return elementoEmNovaViagem;
	}
	
	/**
	 * @deprecated {@link #elementoEmNovaViagem} acessado diretamente pelas subclasses.
	 */
	public void setElementoEmNovaViagem( boolean elementoEmNovaViagem ) {
		this.elementoEmNovaViagem = elementoEmNovaViagem;
	}
	
	protected class ColunaAcoes extends ListaColunaPadrao<O> {
		
		public ColunaAcoes() {
		}
		
		public String getTitulo() {
			return "Ações";
		}
		
		public List<Dado> getDados( O elemento ) {
			return acoesPara( elemento );
		}
		
	}
	
	protected class ComandoApagarQuestionando extends ComandoQuestionamento<AloMundo> {

		private O elemento;
		
		public ComandoApagarQuestionando( O elemento ) {
			super( aplicacao, "Apagar", "Deseja realmente apagar \"" + elemento.toString() + "\"?" );
			this.elemento = elemento;
		}

		@Override
		public void sim( AloMundo aplicacao, Viagem viagem ) throws TomaraQueCaiaException {
			try{
				remover( elemento );
			}catch( ValidacaoException e ){
				throw new TomaraQueCaiaException( e );
			}
		}
		
	}
	
}
