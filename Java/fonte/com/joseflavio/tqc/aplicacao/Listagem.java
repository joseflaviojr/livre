
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

package com.joseflavio.tqc.aplicacao;

import java.util.ArrayList;
import java.util.List;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.molde.ListaMolde;
import com.joseflavio.tqc.molde.ListaMolde.ListaColuna;
import com.joseflavio.tqc.molde.ListaMolde.ListaFiltro;
import com.joseflavio.util.Lista;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class Listagem<TQC extends AplicacaoTQC, ELEMENTO> extends BaseInformacao<TQC> implements Rodape {
	
	private ListaMolde<ELEMENTO> listaMolde;
	
	private boolean primeiraAmostragem = true;
	
	private boolean autoAtualizar = true;
	
	private int totalDeComandos = 0;
	
	/**
	 * @see #construir()
	 */
	public Listagem( TQC aplicacao, String titulo, String banner, String subtitulo, boolean subtituloCentral ) throws TomaraQueCaiaException {
		
		super( aplicacao, titulo, banner, subtitulo, subtituloCentral );
		
		listaMolde = new ListaMolde<ELEMENTO>();
		listaMolde.setMarcadorInicial( "tqc_listagem_i" ).setMarcadorFinal( "tqc_listagem_f" );
		listaMolde.setColunas( new ArrayList<ListaColuna<ELEMENTO>>() );
		listaMolde.setLista( new ArrayList<ELEMENTO>() );
		
	}
	
	public Listagem( TQC aplicacao, String titulo, String subtitulo, boolean subtituloCentral ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, subtitulo, subtituloCentral );
	}

	public Listagem( TQC aplicacao, String titulo, String banner, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, banner, subtitulo, false );
	}

	public Listagem( TQC aplicacao, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, subtitulo, false );
	}
	
	protected void construir() throws TomaraQueCaiaException {
		
		super.construir();
		
		/*----------------------*/
		
		listaMolde.getColunas().clear();
		colunas();
		
		listaMolde.getLista().clear();
		objetos( listaMolde.getLista() );
		
		listaMolde.construir( this );
		
		maisQuebraDeLinha();
		
		/*----------------------*/
		
		comandosPrincipais();

		maisQuebraDeLinha();
		
		/*----------------------*/
		
		rodapeDaInformacao();
		
		/*----------------------*/
		
	}
	
	/**
	 * Deve executar {@link #maisColuna(ListaMolde.ListaColuna)} para cada {@link ListaColuna} desejada.
	 */
	protected abstract void colunas() throws TomaraQueCaiaException;
	
	/**
	 * Deve preencher a {@link Lista} com os objetos a serem mostrados.
	 */
	protected abstract void objetos( List<ELEMENTO> lista ) throws TomaraQueCaiaException;
	
	@Override
	public final void rodapeDaInformacao() throws TomaraQueCaiaException {
		maisMarcador( "tqc_rodape_i" );
		rodape();
		maisMarcador( "tqc_rodape_f" );
	}
	
	/**
	 * Equivalente ao m�todo {@link #rodapeDaInformacao()}, por�m sem os {@link Marcador}es.
	 */
	protected void rodape() throws TomaraQueCaiaException {
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColuna( ListaColuna<ELEMENTO> coluna ){
		listaMolde.maisColuna( coluna );
		return this;
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColunaTexto( Class<? extends Object> classe, String atributo ){
		listaMolde.maisColunaTexto( classe, atributo );
		return this;
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColunaData( Class<? extends Object> classe, String atributo ){
		listaMolde.maisColunaData( classe, atributo );
		return this;		
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColunaInteiro( Class<? extends Object> classe, String atributo ){
		listaMolde.maisColunaInteiro( classe, atributo );
		return this;		
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColunaReal( Class<? extends Object> classe, String atributo ){
		listaMolde.maisColunaReal( classe, atributo );
		return this;		
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColunaBinario( Class<? extends Object> classe, String atributo, String valorVerdadeiro, String valorFalso ){
		listaMolde.maisColunaBinario( classe, atributo, valorVerdadeiro, valorFalso );
		return this;		
	}
	
	protected final Listagem<TQC, ELEMENTO> maisColunaConversao( Class<? extends Object> classe, String atributo, Object[] lista, String original, String convertido ){
		listaMolde.maisColunaConversao( classe, atributo, lista, original, convertido );
		return this;		
	}
	
	protected final Listagem<TQC, ELEMENTO> mais( Comando comando ){
		
		totalDeComandos++;
		
		comando.setEspacoTextualPosterior( false ); //Na vers�o 2009 = true
		
		maisMarcador( "tqc_comando_" + totalDeComandos + "_i" );
		mais( (Dado) comando );
		maisMarcador( "tqc_comando_" + totalDeComandos + "_f" );
		
		return this;
		
	}
	
	/**
	 * Remove as colunas e os objetos e reinvoca {@link #colunas()} e {@link #objetos(List)}. 
	 */
	public void atualizar() throws TomaraQueCaiaException {
		
		listaMolde.getColunas().clear();
		colunas();
		
		listaMolde.getLista().clear();
		objetos( listaMolde.getLista() );
		
		listaMolde.atualizar( this );
		
	}
	
	public ELEMENTO getElemento( int indice ) {
		
		return listaMolde.getLista().get( indice );
		
	}
	
	public Listagem<TQC, ELEMENTO> setFiltro( ListaFiltro<ELEMENTO> filtro ) {
		listaMolde.setFiltro( filtro );
		return this;
	}
	
	public int getTotalDeLinhas() {
		return listaMolde.getLista().size();
	}
	
	/**
	 * @see #isAutoAtualizar()
	 */
	protected final void antesDeMostrar( Viagem viagem ) throws TomaraQueCaiaException {
		
		if( primeiraAmostragem ){
			primeiraAmostragem = false;
		}else{
			if( autoAtualizar ) atualizar();
		}
		
		antesDeMostrarListagem( viagem );
		
	}

	protected void antesDeMostrarListagem( Viagem viagem ) throws TomaraQueCaiaException {
	}
	
	public void setAutoAtualizar( boolean autoAtualizar ) {
		this.autoAtualizar = autoAtualizar;
	}
	
	public boolean isAutoAtualizar() {
		return autoAtualizar;
	}
	
}
