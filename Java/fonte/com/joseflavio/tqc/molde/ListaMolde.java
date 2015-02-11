
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

package com.joseflavio.tqc.molde;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Estilo;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.Molde;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.dado.Data;
import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.dado.Real;
import com.joseflavio.tqc.dado.Tabela;
import com.joseflavio.tqc.dado.TabelaColuna;
import com.joseflavio.tqc.dado.TabelaColunaFim;
import com.joseflavio.tqc.dado.TabelaFim;
import com.joseflavio.tqc.dado.TabelaLinha;
import com.joseflavio.tqc.dado.TabelaLinhaFim;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.util.ClassUtil;
import com.joseflavio.util.Conversor;
import com.joseflavio.util.Lista;

/**
 * {@link Molde} que mostra uma {@link List} em {@link Informacao} utilizando a estrutura de {@link Tabela}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ListaMolde<ELEMENTO> implements Molde {
	
	private List<ListaColuna<ELEMENTO>> colunas;
	
	private List<ELEMENTO> lista;
	
	private ListaFiltro<ELEMENTO> filtro;
	
	private String estiloLinhaImpar = Estilo.listaLinhaImpar;
	
	private String estiloLinhaPar = Estilo.listaLinhaPar;
	
	private Marcador marcadorInicial = new Marcador();
	
	private Marcador marcadorFinal = new Marcador();
	
	public ListaMolde() {
	}
	
	public void construir( Informacao info ) throws TomaraQueCaiaException {
		
		if( colunas == null || lista == null ) throw new TomaraQueCaiaException();
		
		info.mais( marcadorInicial );
		
		inserir( info, info.getTotalDados() );
		
		info.mais( marcadorFinal );
		
	}
	
	private void inserir( Informacao info, int pos ) throws TomaraQueCaiaException {
		
		int i, j, k, aux1, aux2, aux3;
		ListaColuna<ELEMENTO> coluna;
		ELEMENTO elemento;
		Dado dado;
		List<Dado> dados;
		
		/*----------------------*/
		
		info.mais( new Tabela(), pos++ );
		
		/*----------------------*/
		
		info.mais( new TabelaLinha(), pos++ );

		aux1 = colunas.size();
		
		for( i = 0; i < aux1; i++ ){
			
			coluna = colunas.get( i );
		
			info.mais( new TabelaColuna( coluna.getEstiloColunaTitulo() ), pos++ );
			info.mais( new Texto( "tqc_listagem_coluna_" + (i+1), coluna.getTitulo() ).setEstilo( coluna.getEstiloTitulo() ), pos++ );
			info.mais( new TabelaColunaFim(), pos++ );
			
		}
		
		info.mais( new TabelaLinhaFim(), pos++ );
		
		/*----------------------*/
		
		aux1 = lista.size();
		aux2 = colunas.size();
		boolean par = true;
		
		for( i = 0; i < aux1; i++ ){

			elemento = lista.get( i );
			
			if( filtro != null && ! filtro.filtrar( elemento ) ) continue;
			
			par = !par;
			info.mais( new TabelaLinha().setEstilo( par ? estiloLinhaPar : estiloLinhaImpar ), pos++ );
			
			for( j = 0; j < aux2; j++ ){
				
				coluna = colunas.get( j );
				
				info.mais( new TabelaColuna( coluna.getEstiloColunaValor() ), pos++ );

				dados = coluna.getDados( elemento );
				
				info.mais( new Marcador( "tqc_listagem_elemento_" + (i+1) + "_i" ), pos++ );
				
				if( dados != null ){
					aux3 = dados.size();
					for( k = 0; k < aux3; k++ ){
						info.mais( dados.get( k ), pos++ );
					}
				}else{
					dado = coluna.getDado( elemento );
					if( dado != null ) info.mais( dado, pos++ );
				}
				
				info.mais( new Marcador( "tqc_listagem_elemento_" + (i+1) + "_f" ), pos++ );

				info.mais( new TabelaColunaFim(), pos++ );
				
			}
			
			info.mais( new TabelaLinhaFim(), pos++ );
			
		}
		
		/*----------------------*/
		
		info.mais( new TabelaFim(), pos++ );
		
		/*----------------------*/
		
	}
	
	public void atualizar( Informacao info ) throws TomaraQueCaiaException {
		
		if( colunas == null || lista == null ) throw new TomaraQueCaiaException();
		
		int i = info.getIndice( marcadorInicial );
		int f = info.getIndice( marcadorFinal );
		
		if( i < 0 || f < 0 ) throw new TomaraQueCaiaException();
		
		info.menos( ++i, --f );
		
		inserir( info, i );
		
	}
	
	public void remover( Informacao info ) {

		info.menos( marcadorInicial, marcadorFinal );
		
	}

	public Marcador marcadorInicial() {
		return marcadorInicial;
	}
	
	@Override
	public Molde setMarcadorInicial( String nome ) {
		marcadorInicial.setNome( nome );
		return this;
	}
	
	public Marcador marcadorFinal() {
		return marcadorFinal;
	}
	
	@Override
	public Molde setMarcadorFinal( String nome ) {
		marcadorFinal.setNome( nome );
		return this;
	}
	
	public List<ListaColuna<ELEMENTO>> getColunas() {
		return colunas;
	}
	
	public ListaMolde<ELEMENTO> setColunas( List<ListaColuna<ELEMENTO>> colunas ) {
		this.colunas = colunas;
		return this;
	}
	
	public ListaMolde<ELEMENTO> setColunas( ListaColuna<ELEMENTO>... colunas ) {
		this.colunas = new ArrayList<ListaColuna<ELEMENTO>>( colunas.length );
		for( ListaColuna<ELEMENTO> col : colunas ) this.colunas.add( col );
		return this;
	}
	
	public List<ELEMENTO> getLista() {
		return lista;
	}
	
	public ListaMolde<ELEMENTO> setLista( List<ELEMENTO> lista ) {
		this.lista = new ArrayList<ELEMENTO>( lista );
		return this;
	}
	
	public String getEstiloLinhaImpar() {
		return estiloLinhaImpar;
	}
	
	public ListaMolde<ELEMENTO> setEstiloLinhaImpar( String estiloLinhaImpar ) {
		this.estiloLinhaImpar = estiloLinhaImpar;
		return this;
	}
	
	public String getEstiloLinhaPar() {
		return estiloLinhaPar;
	}

	public ListaMolde<ELEMENTO> setEstiloLinhaPar( String estiloLinhaPar ) {
		this.estiloLinhaPar = estiloLinhaPar;
		return this;
	}
	
	public ListaMolde<ELEMENTO> inverterEstiloLinha() {
		String tmp = estiloLinhaImpar;
		estiloLinhaImpar = estiloLinhaPar;
		estiloLinhaPar = tmp;
		return this;
	}
	
	public ListaMolde<ELEMENTO> setFiltro( ListaFiltro<ELEMENTO> filtro ) {
		this.filtro = filtro;
		return this;
	}
	
	public ListaFiltro<ELEMENTO> getFiltro() {
		return filtro;
	}
	
	public ListaMolde<ELEMENTO> maisColuna( ListaColuna<ELEMENTO> coluna ){
		colunas.add( coluna );
		return this;
	}
	
	public ListaMolde<ELEMENTO> maisColunaTexto( Class<? extends Object> classe, String atributo ){
		return maisColuna( new ListaColunaTexto<ELEMENTO>( classe, atributo ) );
	}
	
	public ListaMolde<ELEMENTO> maisColunaData( Class<? extends Object> classe, String atributo ){
		return maisColuna( new ListaColunaData<ELEMENTO>( classe, atributo ) );
	}
	
	public ListaMolde<ELEMENTO> maisColunaInteiro( Class<? extends Object> classe, String atributo ){
		return maisColuna( new ListaColunaInteiro<ELEMENTO>( classe, atributo ) );
	}
	
	public ListaMolde<ELEMENTO> maisColunaReal( Class<? extends Object> classe, String atributo ){
		return maisColuna( new ListaColunaReal<ELEMENTO>( classe, atributo ) );
	}
	
	public ListaMolde<ELEMENTO> maisColunaBinario( Class<? extends Object> classe, String atributo, String valorVerdadeiro, String valorFalso ){
		return maisColuna( new ListaColunaBinario<ELEMENTO>( classe, atributo, valorVerdadeiro, valorFalso ) );
	}
	
	public ListaMolde<ELEMENTO> maisColunaConversao( Class<? extends Object> classe, String atributo, Object[] lista, String original, String convertido ){
		return maisColuna( new ListaColunaConversao<ELEMENTO>( classe, atributo, lista, original, convertido ) );
	}
	
	public static interface ListaFiltro<ELEMENTO> {
		
		/**
		 * @return <code>false</code> caso o elemento não deva ser incluído na listagem. 
		 */
		public boolean filtrar( ELEMENTO elemento );
		
	}

	public static interface ListaColuna<ELEMENTO> {
		
		public String getTitulo();
		
		public String getEstiloTitulo();
		
		public String getEstiloColunaTitulo();
		
		public String getEstiloColunaValor();
		
		/**
		 * Deve retornar o {@link Dado} correspondente ao elemento da {@link Lista} de acordo com a semântica desta coluna.
		 * @param elemento Elemento da {@link Lista}.
		 * @return <code>null</code> para validar {@link #getDados(Object)}.
		 */
		public Dado getDado( ELEMENTO elemento );
		
		/**
		 * Deve retornar os {@link Dado}s correspondentes ao elemento da {@link Lista} de acordo com a semântica desta coluna.
		 * @param elemento Elemento da {@link Lista}.
		 * @return <code>null</code> para validar {@link #getDado(Object)}.
		 */
		public List<Dado> getDados( ELEMENTO elemento );
		
	}
	
	public static abstract class ListaColunaPadrao<ELEMENTO> implements ListaColuna<ELEMENTO> {
		
		public String getEstiloTitulo() {
			return Estilo.listaTitulo;
		}
		
		public String getEstiloColunaTitulo() {
			return Estilo.listaColunaTitulo;
		}
		
		public String getEstiloColunaValor() {
			return Estilo.listaColunaValor;
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			return null;
		}
		
		public List<Dado> getDados( ELEMENTO elemento ) {
			return null;
		}
		
	}
	
	public static abstract class ListaColunaModelo<ELEMENTO> extends ListaColunaPadrao<ELEMENTO> {
		
		protected AssistenteDeAtributo assistente;
		
		private String titulo;
		
		public ListaColunaModelo( Class<? extends Object> classe, String atributo ) {
			
			this.assistente = new AssistenteDeAtributo( classe, atributo );
			this.titulo = assistente.getJFApresentacao( true ).value();
			
		}

		public String getTitulo() {
			return titulo;
		}
		
	}
	
	public static class ListaColunaTexto<ELEMENTO> extends ListaColunaModelo<ELEMENTO> {

		public ListaColunaTexto( Class<? extends Object> classe, String atributo ) {
			super( classe, atributo );
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			Object resultado = assistente.invocarGet( elemento );
			return new Texto( resultado != null ? resultado.toString() : "" );
		}
		
	}
	
	public static class ListaColunaData<ELEMENTO> extends ListaColunaModelo<ELEMENTO> {

		public ListaColunaData( Class<? extends Object> classe, String atributo ) {
			super( classe, atributo );
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			return new Data( null, assistente.getClasse(), assistente.getAtributo(), (Date) assistente.invocarGet( elemento ), false );
		}
		
	}
	
	public static class ListaColunaInteiro<ELEMENTO> extends ListaColunaModelo<ELEMENTO> {

		public ListaColunaInteiro( Class<? extends Object> classe, String atributo ) {
			super( classe, atributo );
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			return new Inteiro( null, assistente.getClasse(), assistente.getAtributo(), ((Number)assistente.invocarGet( elemento )).longValue(), false );
		}
		
	}
	
	public static class ListaColunaBinario<ELEMENTO> extends ListaColunaModelo<ELEMENTO> {
		
		private String valorVerdadeiro;
		private String valorFalso;

		public ListaColunaBinario( Class<? extends Object> classe, String atributo, String valorVerdadeiro, String valorFalso ) {
			super( classe, atributo );
			this.valorVerdadeiro = valorVerdadeiro;
			this.valorFalso = valorFalso;
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			
			Object valor = (Object) assistente.invocarGet( elemento );
			
			if( valor == null ) return new Texto( valorFalso );
			
			boolean retorno = false;
			
			if( ClassUtil.getInvolucro( valor.getClass() ) == Boolean.class ){
				retorno = valor != null && (Boolean) valor;
			}else{
				retorno = valor != null;
			}
			
			return new Texto( retorno ? valorVerdadeiro : valorFalso );
			
		}
		
	}
	
	public static class ListaColunaReal<ELEMENTO> extends ListaColunaModelo<ELEMENTO> {

		public ListaColunaReal( Class<? extends Object> classe, String atributo ) {
			super( classe, atributo );
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			return new Real( null, assistente.getClasse(), assistente.getAtributo(), ((Number)assistente.invocarGet( elemento )).doubleValue(), false );
		}
		
	}
	
	/**
	 * Converte o conteúdo de um atributo para um outro valor correspondente.
	 */
	@SuppressWarnings("unchecked")
	public static class ListaColunaConversao<ELEMENTO> extends ListaColunaModelo<ELEMENTO> {

		@SuppressWarnings("rawtypes")
		private Conversor conversor;
		
		/**
		 * @param classe Classe à qual o atributo pertence.
		 * @param atributo Atributo a ser convertido.
		 */
		@SuppressWarnings("rawtypes")
		public ListaColunaConversao( Class<? extends Object> classe, String atributo, Conversor conversor ) {
			super( classe, atributo );
			this.conversor = conversor;
		}
		
		@SuppressWarnings("rawtypes")
		public ListaColunaConversao( Class<? extends Object> classe, String atributo, Object[] lista, String original, String convertido ) {
			this( classe, atributo, new Conversor( lista, original, convertido ) );
		}
		
		public Dado getDado( ELEMENTO elemento ) {
			return new Texto( conversor.converter( assistente.invocarGet( elemento ) ).toString() );
		}
		
	}
	
}
