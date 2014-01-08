
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

import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.anotacao.TQCComando;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class ListagemPaginada<O> extends Listagem<O> {
	
	private int totalDeObjetos;
	
	private int pagina = 1;
	
	private int totalDePaginas;
	
	private int totalPorPagina = 15;
	
	protected ListagemPaginada( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo, String subtitulo, boolean construir ) throws TomaraQueCaiaException {
		
		super( aplicacao, cadastrador, titulo, subtitulo, false );
		
		if( construir ) construir();
		
	}
	
	protected ListagemPaginada( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo, boolean construir ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, titulo, titulo, construir );
	}

	protected ListagemPaginada( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, titulo, subtitulo, true );
	}
	
	protected ListagemPaginada( AloMundo aplicacao, Class<? extends Informacao> cadastrador, String titulo ) throws TomaraQueCaiaException {
		this( aplicacao, cadastrador, titulo, titulo, true );
	}
	
	@Override
	protected void construir() throws com.joseflavio.tqc.TomaraQueCaiaException {
		calcularPagina();
		super.construir();
	}

	@Override
	protected void antesDeMostrarListagem( Viagem viagem ) throws TomaraQueCaiaException {
		
		super.antesDeMostrarListagem( viagem );
		
		this.<Texto>getDado( "totalDeObjetos" ).setTexto( "Total: " + totalDeObjetos );
		this.<Texto>getDado( "pagina" ).setTexto( "Página: " + pagina + "/" + totalDePaginas );
		
	}
	
	/**
	 * @see AplicacaoTQC_JPA#listarLimitada(String, int, int)
	 * @see #getResultadoInicial()
	 * @see #getMaximoResultados()
	 */
	@Override
	protected abstract void objetos( List<O> lista ) throws TomaraQueCaiaException;
	
	/**
	 * Quantifica o total de objetos.
	 * @see AplicacaoTQC_JPA#obterQuantidade(String, Object...)
	 */
	protected abstract int contarTotal();
	
	protected int getResultadoInicial() {
		return ( pagina - 1 ) * totalPorPagina;
	}
	
	protected int getMaximoResultados() {
		return totalPorPagina;
	}
	
	private void calcularPagina() {
		
		totalDeObjetos = contarTotal();
		totalDePaginas = (int) Math.ceil( (double) totalDeObjetos / totalPorPagina );
		
		if( totalDePaginas == 0 ) totalDePaginas = 1;
		
		if( pagina > totalDePaginas ) pagina = totalDePaginas;
		else if( pagina <= 0 ) pagina = 1;
		
	}
	
	/**
	 * Vai para a primeira página.
	 */
	public void primeiraPagina() {
		pagina = 1;
		calcularPagina();
	}
	
	@Override
	protected void rodape() throws TomaraQueCaiaException {
		
		maisQuebraDeLinha();
		mais( new Texto( "totalDeObjetos", "" ) );
		maisQuebraDeLinha();
		mais( new Texto( "pagina", "" ) );
		
	}
	
	@TQCComando( rotulo="<<", nome="voltar", ordem=3, imagem="img/iconep/recuar.png", funcao=Funcao.VOLTAR )
	public void voltar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		pagina--;
		calcularPagina();
	}
	
	@TQCComando( rotulo=">>", nome="avancar", ordem=4, imagem="img/iconep/avancar.png", funcao=Funcao.AVANCAR )
	public void avancar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		pagina++;
		calcularPagina();
	}
	
}
