
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

package com.joseflavio.tqc;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joseflavio.modelo.Combinacao;
import com.joseflavio.tqc.dado.Arquivo;
import com.joseflavio.tqc.dado.Binario;
import com.joseflavio.tqc.dado.Bruto;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.ComandoPorMetodo;
import com.joseflavio.tqc.dado.Data;
import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.tqc.dado.Linha;
import com.joseflavio.tqc.dado.LinhaFim;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.dado.QuebraDeLinha;
import com.joseflavio.tqc.dado.Real;
import com.joseflavio.tqc.dado.Selecao;
import com.joseflavio.tqc.dado.SelecaoMultipla;
import com.joseflavio.tqc.dado.SelecionavelTexto;
import com.joseflavio.tqc.dado.Senha;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.util.Lista;
import com.joseflavio.util.TipoUtil;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Informação que pode ser visitada por {@link TomaraQueCaia} através de {@link Viagem}.<br>
 * Uma {@link Informacao} possui {@link Dado}'s.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see AnexoMensagem
 * @see AnexoRolagem
 * @see AnexoControleDeFoco
 */
public abstract class Informacao implements AcionamentoDeComando {
	
	private String titulo;
	
	/**
	 * {@link Dado}'s na sequência de inserção.
	 */
	private List<Dado> dados = new ArrayList<Dado>( 10 );
	
	private Menu menu;
	
	private AnexoGrupo anexoGrupo = new AnexoGrupo();
	
	private boolean personalizada = true;
	
	private String personalizadaInterface;
	
	private String pele;
	
	private Map<String, Integer> indiceCache = new HashMap<String, Integer>( 50 );
	
	private int contadorDadosAnonimos = 0;
	
	/**
	 * @param titulo Título que resume o contéudo da {@link Informacao}.
	 * @throws TomaraQueCaiaException impossibilidade de construção da {@link Informacao}.
	 */
	protected Informacao( String titulo ) throws TomaraQueCaiaException {
		setTitulo( titulo );
	}
	
	/**
	 * Método invocado antes de a {@link Informacao} ser mostrada.
	 * @param viagem {@link Viagem} que está visitando esta {@link Informacao}.
	 * @throws TomaraQueCaiaException impossibilidade de uso da {@link Informacao}.
	 */
	public abstract void antesDeMostrar( TomaraQueCaia tqc, Viagem viagem ) throws TomaraQueCaiaException;
	
	/**
	 * Constroi uma {@link Informacao} que explica o conteúdo desta {@link Informacao}. Padrão: <code>null</code>.
	 */
	public Informacao criarAjuda( TomaraQueCaia tqc ) throws TomaraQueCaiaException {
		return null;
	}
	
	/**
	 * Indica se esta {@link Informacao} possui {@link #criarAjuda(TomaraQueCaia) ajuda}. Padrão: <code>false</code>.
	 */
	public boolean possuiAjuda() {
		return false;
	}
	
	/**
	 * Valida todos os {@link ValidavelDado}'s.
	 * @see ValidavelDado#validar()
	 */
	public void validarTudo() throws ValidacaoException {
		
		int total = dados.size();
		Dado dado;
		
		for( int i = 0; i < total; i++ ){
			dado = dados.get( i );
			if( dado instanceof ValidavelDado ) ((ValidavelDado)dado).validar();
		}
		
	}
	
	public void setTitulo( String titulo ) {
		this.titulo = titulo;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	/**
	 * Determina um {@link Menu}. Padrão: <code>null</code>.
	 */
	public void setMenu( Menu menu ) {
		this.menu = menu;
	}
	
	/**
	 * Determina que a {@link Informacao} será mostrada através de uma JSP exclusiva, desconsiderando a {@link #getPele()}.
	 * @see #setPersonalizadaInterface(String)
	 */
	public void setPersonalizada( boolean personalizada ) {
		this.personalizada = personalizada;
	}
	
	/**
	 * @see #setPersonalizada(boolean)
	 */
	public boolean isPersonalizada() {
		return personalizada;
	}
	
	/**
	 * @see #setPersonalizadaInterface(String)
	 */
	public String getPersonalizadaInterface() {
		return personalizadaInterface;
	}
	
	/**
	 * Interface gráfica personalizada a ser utilizada para esta {@link Informacao} caso {@link #isPersonalizada()}. Endereço relativo a diretório reservado para tal finalidade.<br>
	 * Pode ser <code>null</code>, indicando que seguirá o seguinte padrão: <br>
	 * PACOTE/CLASSE<br>
	 * Exemplo: com.joseflavio.tqc.teste/TesteTQCFormulario<br><br>
	 * Após a definição da interface, {@link #setPersonalizada(boolean)} será automaticamente ativada.
	 */
	public void setPersonalizadaInterface( String personalizadaInterface ) {
		this.personalizadaInterface = personalizadaInterface;
		setPersonalizada( true );
	}
	
	/**
	 * @see #setPele(String)
	 * @return <code>null</code> para prevalecer {@link TomaraQueCaia#getPele()}.
	 */
	public String getPele() {
		return pele;
	}
	
	/**
	 * Determina o modelo visual da {@link Informacao}. Padrão: {@link TomaraQueCaia#setPele(String)}
	 * @param pele <code>null</code> = prevalecer {@link TomaraQueCaia#getPele()}.
	 */
	public void setPele( String pele ) {
		this.pele = pele;
	}
	
	/**
	 * Retorna o {@link Identificacao#getNome() nome} dos {@link Dado}s {@link Identificacao identificáveis}.
	 */
	public List<String> getNomesDeDados() {
		
		List<String> nomes = new ArrayList<String>();
		
		for( Dado dado : dados ){
			if( dado instanceof Identificacao ){
				String n = ((Identificacao)dado).getNome();
				if( n != null && n.length() > 0 ) nomes.add( n );
			}
		}
		
		return nomes;
		
	}
	
	/**
	 * Gera um nome exclusivo para {@link Identificacao identificar} um {@link Dado} dentro desta {@link Informacao}.
	 * @see Identificacao#setNome(String)
	 */
	public String gerarNome() {
		return "anonimo" + (++contadorDadosAnonimos);
	}
	
	/**
	 * Adiciona um {@link Dado} ao final da {@link Informacao}.
	 */
	public <T extends Dado> T mais( T dado ){
		
		if( dado instanceof Identificacao ){
			String nome = ((Identificacao)dado).getNome();
			if( nome == null || nome.length() == 0 ) ((Identificacao)dado).setNome( gerarNome() );
		}
		
		dados.add( dado );
		
		if( dado instanceof ComplexoDado ) ((ComplexoDado)dado).setInformacao( this );
		atualizarIndiceCache();
		
		return dado;
		
	}
	
	/**
	 * Adiciona um {@link Dado} numa determinada posição dentro da {@link Informacao}.
	 * @param indice Posição do {@link Dado} dentro da {@link Informacao}. Inicia em zero.
	 */
	public <T extends Dado> T mais( T dado, int indice ){
		
		if( dado instanceof Identificacao ){
			String nome = ((Identificacao)dado).getNome();
			if( nome == null || nome.length() == 0 ) ((Identificacao)dado).setNome( gerarNome() );
		}
		
		dados.add( indice, dado );
		
		if( dado instanceof ComplexoDado ) ((ComplexoDado)dado).setInformacao( this );
		atualizarIndiceCache();
		
		return dado;
		
	}
	
	private <T extends Dado> T menos( T dado, int indice ) {
		
		if( dado == null ) throw new IllegalArgumentException();
		
		dados.remove( indice );
		
		if( dado instanceof ComplexoDado ) ((ComplexoDado)dado).setInformacao( null );
		atualizarIndiceCache();
		
		return dado;
		
	}
	
	public <T extends Dado> T menos( T dado ) {
		
		return menos( dado, getIndice( dado ) );
		
	}
	
	public Dado menos( int indice ) {
		
		return menos( getDado( indice ), indice );
		
	}
	
	/**
	 * Remove todos os {@link Dado}s delimitados por uma faixa.
	 * @param inicio {@link Dado} inicial.
	 * @param fim {@link Dado} final.
	 * @param inclusive É para remover também o <code>inicio</code> e o <code>fim</code>?
	 * @return índice do {@link Dado} inicial.
	 */
	public int menos( Dado inicio, Dado fim, boolean inclusive ) {
		
		int i = getIndice( inicio );
		int f = getIndice( fim );
		
		menos( inclusive ? i : i + 1, inclusive ? f : f - 1 );
		
		return i;
		
	}
	
	/**
	 * {@link #menos(Dado, Dado, boolean)} inclusive.
	 */
	public int menos( Dado inicio, Dado fim ) {
		
		return menos( inicio, fim, true );
		
	}
	
	/**
	 * Remove todos os {@link Dado}s delimitados por uma faixa.
	 * @param inicio Índice do {@link Dado} inicial, inclusive.
	 * @param fim Índice do {@link Dado} final, inclusive.
	 */
	public void menos( int inicio, int fim ) {
		
		if( inicio < 0 || inicio >= dados.size() ) throw new IndexOutOfBoundsException( "" + inicio );
		if( fim < 0 || fim >= dados.size() ) throw new IndexOutOfBoundsException( "" + fim );
		
		for( int t = ( fim - inicio + 1 ); t > 0; t-- ){
			menos( fim-- );
		}
		
	}
	
	/**
	 * Aplica {@link Dado#setVisivel(boolean)} para toda ocorrência da faixa inicio-fim.
	 */
	public void setVisivel( boolean visivel, Dado inicio, Dado fim ) {

		int total = dados.size();
		
		for( int i = 0; i < total; i++ ){
			if( dados.get( i ) == inicio ){
				for( int f = i + 1; f < total; f++ ){
					if( dados.get( f ) == fim ){
						for( int k = i; k <= f; k++ ){
							dados.get( k ).setVisivel( visivel );
						}
						i = f;
						break;
					}
				}
			}
		}
		
	}
	
	/**
	 * O mesmo que {@link #setVisivel(boolean, Dado, Dado)}, porém encontrando o início e o fim através da {@link Identificacao}.
	 */
	public void setVisivel( boolean visivel, String inicio, String fim ) {
		
		int total = dados.size();
		Dado dado;
		
		for( int i = 0; i < total; i++ ){
			dado = dados.get( i );
			if( dado instanceof Identificacao && nomeIgual( (Identificacao) dado, inicio ) ){
				for( int f = i + 1; f < total; f++ ){
					dado = dados.get( f );
					if( dado instanceof Identificacao && nomeIgual( (Identificacao) dado, fim ) ){
						for( int k = i; k <= f; k++ ){
							dados.get( k ).setVisivel( visivel );
						}
						i = f;
						break;
					}
				}
			}
		}
		
	}
	
	/**
	 * @param nome {@link Identificacao#getNome()} do {@link Dado} desejado.
	 * @return {@link Edicao#isEditavel()}
	 * @see Edicao
	 */
	public boolean isEditavel( String nome ){
		Dado dado = getDado( nome );
		return dado != null && dado instanceof Edicao ? ((Edicao)dado).isEditavel() : false;
	}
	
	/**
	 * Aplica {@link Edicao#setEditavel(boolean)} para toda ocorrência da faixa inicio-fim.
	 */
	public void setEditavel( boolean editavel, Dado inicio, Dado fim ) {

		int total = dados.size();
		Dado dado;
		
		for( int i = 0; i < total; i++ ){
			if( dados.get( i ) == inicio ){
				for( int f = i + 1; f < total; f++ ){
					if( dados.get( f ) == fim ){
						for( int k = i; k <= f; k++ ){
							dado = dados.get( k );
							if( dado instanceof Edicao ) ((Edicao)dado).setEditavel( editavel );
						}
						i = f;
						break;
					}
				}
			}
		}
		
	}
	
	/**
	 * O mesmo que {@link #setEditavel(boolean, Dado, Dado)}, porém encontrando o início e o fim através da {@link Identificacao}.
	 */
	public void setEditavel( boolean editavel, String inicio, String fim ) {
		
		int total = dados.size();
		Dado dado;
		
		for( int i = 0; i < total; i++ ){
			dado = dados.get( i );
			if( dado instanceof Identificacao && nomeIgual( (Identificacao) dado, inicio ) ){
				for( int f = i + 1; f < total; f++ ){
					dado = dados.get( f );
					if( dado instanceof Identificacao && nomeIgual( (Identificacao) dado, fim ) ){
						for( int k = i; k <= f; k++ ){
							dado = dados.get( k );
							if( dado instanceof Edicao ) ((Edicao)dado).setEditavel( editavel );
						}
						i = f;
						break;
					}
				}
			}
		}
		
	}
	
	/**
	 * Altera a propriedade de {@link Edicao} de todos os {@link Dado}s compatíveis.
	 */
	public void setEditavel( boolean editavel ) {
		
		for( Dado dado : dados ){
			if( dado instanceof Edicao ) ((Edicao)dado).setEditavel( editavel );
		}
		
	}
	
	/**
	 * Altera a propriedade de {@link Edicao} de todos os {@link Dado}s, com exceção das classes especificadas.
	 * @param excecao Lista de classes a desconsiderar. A comparação será feita diretamente, sem a utilização de {@link Class#isAssignableFrom(Class)}.
	 */
	public void setEditavel( boolean editavel, Class<? extends Edicao>... excecao ) {
		
		for( Dado dado : dados ){
			if( dado instanceof Edicao ){
				
				Edicao edicao = (Edicao) dado;
				if( edicao.isEditavel() == editavel ) continue;
				
				Class<?> classe = dado.getClass();
				boolean alterar = true;
				
				for( Class<? extends Edicao> ex : excecao ){
					if( classe == ex ){
						alterar = false;
						break;
					}
				}
				
				if( alterar ) edicao.setEditavel( editavel );
				
			}
		}
		
	}
	
	public void setEditavel( String nomeDado, boolean editavel ) {
		
		Dado dado = getDado( nomeDado );
		
		if( ! ( dado instanceof Edicao ) ) throw new IllegalArgumentException( nomeDado + " : não editável" );
		
		((Edicao)dado).setEditavel( editavel );
		
	}
	
	public Texto maisTexto( String texto ){
		Texto dado = new Texto( texto );
		mais( dado );
		return dado;
	}
	
	public QuebraDeLinha maisQuebraDeLinha(){
		QuebraDeLinha dado = new QuebraDeLinha();
		mais( dado );
		return dado;
	}
	
	public EspacoTextual maisEspacoTextual( int quantidade ){
		EspacoTextual dado = new EspacoTextual( quantidade );
		mais( dado );
		return dado;
	}
	
	public EspacoTextual maisEspacoTextual(){
		return maisEspacoTextual( 1 );
	}
	
	public Linha maisLinha(){
		Linha dado = new Linha();
		mais( dado );
		return dado;
	}
	
	public Linha maisLinha( String estilo ){
		Linha dado = new Linha( estilo );
		mais( dado );
		return dado;
	}
	
	public Linha maisLinhaCentral(){
		Linha dado = new Linha( Estilo.centroH );
		mais( dado );
		return dado;
	}
	
	public LinhaFim maisLinhaFim(){
		LinhaFim dado = new LinhaFim();
		mais( dado );
		return dado;
	}
	
	public <T extends Dado> T maisDadoEmLinha( T dado ) {
		maisLinha();
		mais( dado );
		maisLinhaFim();
		return dado;
	}
	
	public <T extends Dado> T maisDadoEmLinhaCentral( T dado ) {
		maisLinhaCentral();
		mais( dado );
		maisLinhaFim();
		return dado;
	}
	
	public <T extends Dado> void maisDadosEmLinhaCentral( T... dados ) {
		maisLinhaCentral();
		for( T dado : dados ) mais( dado );
		maisLinhaFim();
	}
	
	public Informacao maisMarcador( String nome ) {
		mais( new Marcador( nome ) );
		return this;
	}
	
	/**
	 * Retorna todos os {@link Dado}s contidos nesta {@link Informacao}.
	 */
	@SuppressWarnings("unchecked")
	public List<Dado> getDados() {
		return (List<Dado>) ((ArrayList<Dado>)dados).clone();
	}
	
	/**
	 * Retorna a {@link List lista} de {@link Dado}s da {@link Informacao} sem {@link Object#clone() cloná-la}.
	 */
	protected List<Dado> getDadosPuros() {
		return dados;
	}
	
	/**
	 * Retorna todos os {@link Dado}s do {@link Class tipo} especificado.<br>
	 * A busca leva em consideração a {@link Class#isAssignableFrom(Class) generalização}.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Dado> List<T> getDados( Class<? extends T> tipo ) {
		List<T> lista = new ArrayList<T>();
		for( Dado dado : dados ){
			if( tipo.isAssignableFrom( dado.getClass() ) ) lista.add( (T) dado );
		}
		return lista;
	}
	
	/**
	 * Busca pelo primeiro {@link Dado} {@link Identificacao identificado} pelo nome passado por parâmetro.
	 * @param nome {@link Identificacao#getNome()} do {@link Dado} desejado.
	 * @return <code>null</code> caso a {@link Informacao} não contenha o {@link Dado} desejado.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Dado> T getDado( String nome ){
		Integer i = indiceCache.get( nome );
		return (T) ( i != null ? dados.get( i ) : null );
	}
	
	public Dado getDado( int indice ){
		return dados.get( indice );
	}
	
	/**
	 * Busca por um {@link Dado} que inclusive não esteja contido na {@link Informacao}.
	 * @return <code>null</code> caso o {@link Dado} não seja encontrado.
	 * @see #getDado(String)
	 * @see #getComando(String)
	 */
	public Dado getDadoVirtual( String nome ){
		Dado dado = getDado( nome );
		if( dado == null ) dado = getComando( nome );
		return dado;
	}
	
	/**
	 * @return -1 caso o {@link Dado} não esteja nesta {@link Informacao}.
	 */
	public int getIndice( Dado dado ) {
		
		if( dado instanceof Identificacao ){
			String nome = ((Identificacao)dado).getNome();
			if( nome != null ) return getIndice( nome );
		}
		
		return dados.indexOf( dado );
		
	}
	
	/**
	 * Retorna o índice de um {@link Dado} através de sua {@link Identificacao}.
	 * @return -1 caso o {@link Dado} não esteja nesta {@link Informacao}.
	 * @see #getIndice(Dado)
	 */
	public int getIndice( String nome ) {
		Integer i = indiceCache.get( nome );
		return i != null ? i : -1;
	}
	
	/**
	 * Procura por um {@link Dado} através de sua {@link Identificacao} a partir de um índice especificado.
	 * @param aPartir Índice inicial para a busca, inclusive.
	 * @return -1 caso o {@link Dado} não seja encontrado.
	 * @see #getIndice(String)
	 */
	public int getIndice( String nome, int aPartir ) {

		int total = dados.size();
		
		for( int i = aPartir; i < total; i++ ){
			Dado dado = dados.get( i );
			if( dado instanceof Identificacao && nomeIgual( (Identificacao) dado, nome ) ) return i;
		}
		
		return -1;
		
	}
	
	/**
	 * @see ComandoPorMetodo#getComando(String)
	 * @see Comando#getComando(Informacao, String)
	 */
	public Comando getComando( String nome ) {
		return Comando.getComando( this, nome );
	}
	
	public boolean contem( Dado dado ) {
		return getIndice( dado ) != -1;
	}
	
	/**
	 * Substitui um determinado {@link Dado} por outro.
	 */
	public void substituir( Dado atual, Dado novo ) {
		
		if( atual == null || novo == null ) throw new IllegalArgumentException();
		
		int i = dados.indexOf( atual );
		
		dados.set( i, novo );
		
		if( novo instanceof ComplexoDado ) ((ComplexoDado)novo).setInformacao( this );
		if( atual instanceof ComplexoDado ) ((ComplexoDado)atual).setInformacao( null );
		atualizarIndiceCache();
		
	}
	
	public int getTotalDados() {
		return dados.size();
	}
	
	private static boolean nomeIgual( Identificacao identificacao, String nome ) {
		String n = identificacao.getNome();
		return ( n == nome ) || ( n != null && nome != null && n.equals( nome ) );
	}
	
	/**
	 * @see Dado#getConteudo()
	 */
	@SuppressWarnings("unchecked")
	public <O extends Object> O objeto( String dadoNome ) {
		
		Dado dado = getDado( dadoNome );
		
		if( dado != null ) return (O) dado.getConteudo();
		
		throw new IllegalArgumentException( dadoNome );
		
	}
	
	/**
	 * @see #objeto(String)
	 */
	@SuppressWarnings("unchecked")
	public <O extends Object> List<O> objetos( String dadoNome ) {
		
		Object o = objeto( dadoNome );
		
		if( o == null ) return null;
		if( o instanceof List ) return (List<O>) o;
		if( o instanceof Object[] ) return new Lista<O>( (O[]) o );
		
		throw new IllegalArgumentException( dadoNome );
		
	}
	
	/**
	 * @see #objeto(String)
	 */
	public String texto( String dadoNome ) {
		Object o = objeto( dadoNome );
		if( o == null ) return null;
		return o.toString();
	}
	
	/**
	 * @see #objeto(String)
	 * @see Selecao#getSelecionado()
	 * @see Selecao#getIndiceSelecionado()
	 */
	public Long inteiro( String dadoNome ) {
		
		Dado dado = getDado( dadoNome );
		if( dado == null ) throw new IllegalArgumentException( dadoNome );
		Object o = dado.getConteudo();
		
		if( o == null ) return null;
		if( o instanceof Number ) return TipoUtil.converterParaLong( (Number) o );
		if( dado instanceof Selecao<?> ) return new Long( ((Selecao<?>)dado).getIndiceSelecionado() );
		
		throw new IllegalArgumentException( dadoNome );
		
	}
	
	/**
	 * @see #objeto(String)
	 */
	public Double real( String dadoNome ) {
		Object o = objeto( dadoNome );
		if( o == null ) return null;
		if( o instanceof Number ) return TipoUtil.converterParaDouble( (Number) o );
		throw new IllegalArgumentException( dadoNome );
	}
	
	/**
	 * @see #objeto(String)
	 */
	public Date data( String dadoNome ) {
		Object o = objeto( dadoNome );
		if( o == null ) return null;
		if( o instanceof Date ) return (Date) o;
		throw new IllegalArgumentException( dadoNome );
	}
	
	/**
	 * @see #logico(String)
	 */
	public boolean binario( String dadoNome ) {
		Object o = objeto( dadoNome );
		if( o == null ) return false;
		if( o instanceof Boolean ) return (Boolean) o;
		throw new IllegalArgumentException( dadoNome );
	}
	
	/**
	 * Valor {@link Boolean booleano} obtido de acordo com o {@link Selecao#getIndiceSelecionado()}.
	 * @param indiceNulo Índice da opção que representa <code>null</code>. -1 == desconsiderar.
	 * @param indiceVerdadeiro Índice da opção que representa <code>true</code>.
	 */
	public Boolean binarioSelecao( String dadoNome, int indiceNulo, int indiceVerdadeiro ) {
		
		Dado dado = getDado( dadoNome );
		
		if( dado instanceof Selecao ){
			int indice = ((Selecao<?>)dado).getIndiceSelecionado();
			return indice == indiceVerdadeiro ? true : indice != indiceNulo ? false : null;
		}
		
		throw new IllegalArgumentException( dadoNome );
		
	}
	
	/**
	 * Valor primitivo booleano obtido de acordo com o {@link Selecao#getIndiceSelecionado()}.
	 * @param indiceVerdadeiro Índice da opção que representa <code>true</code>.
	 */
	public boolean binarioSelecao( String dadoNome, int indiceVerdadeiro ) {
		
		Dado dado = getDado( dadoNome );
		
		if( dado instanceof Selecao ){
			return ((Selecao<?>)dado).getIndiceSelecionado() == indiceVerdadeiro;
		}
		
		throw new IllegalArgumentException( dadoNome );
		
	}
	
	/**
	 * Valor primitivo booleano obtido de acordo com o {@link Selecao#getIndiceSelecionado()}, no qual a opção de índice 0 representa o valor verdadeiro.
	 */
	public boolean binarioSelecao( String dadoNome ) {
		return binarioSelecao( dadoNome, 0 );
	}
	
	/**
	 * Extrai o {@link Boolean} de uma {@link Combinacao}.<br>
	 * {@link #combinacao(String, Class)} com ( dadoNome, Boolean.class )
	 */
	public Boolean binarioCombinacao( String dadoNome ) {
		return combinacao( dadoNome, Boolean.class );
	}
	
	/**
	 * {@link Combinacao#getObjeto1()} ou {@link Combinacao#getObjeto2()}, dependendo da classe especificada.
	 * @return <code>null</code> caso a classe não seja encontrada.
	 * @see Combinacao
	 * @see #objeto(String)
	 */
	@SuppressWarnings("unchecked")
	public <O> O combinacao( String dadoNome, Class<O> classe ) {
		
		Object obj = objeto( dadoNome );
		
		if( obj instanceof Combinacao ){
			
			Combinacao<?,?> comb = (Combinacao<?,?>) obj;
			
			Object o1 = comb.getObjeto1();
			if( o1 != null && o1.getClass() == classe ) return (O) o1;
			
			Object o2 = comb.getObjeto2();
			if( o2 != null && o2.getClass() == classe ) return (O) o2;
			
			return null;
			
		}
		
		throw new IllegalArgumentException( dadoNome );
		
	}
	
	/**
	 * @see #inteiro(String)
	 * @see Long#shortValue()
	 */
	public short primitivoShort( String dadoNome ) {
		return inteiro( dadoNome ).shortValue();
	}

	/**
	 * @see #inteiro(String)
	 * @see Long#intValue()
	 */
	public int primitivoInt( String dadoNome ) {
		return inteiro( dadoNome ).intValue();
	}
	
	/**
	 * @see #inteiro(String)
	 * @see Long#longValue()
	 */
	public long primitivoLong( String dadoNome ) {
		return inteiro( dadoNome ).longValue();
	}
	
	/**
	 * @see #real(String)
	 * @see Double#floatValue()
	 */
	public float primitivoFloat( String dadoNome ) {
		return real( dadoNome ).floatValue();
	}
	
	/**
	 * @see #real(String)
	 * @see Double#doubleValue()
	 */
	public double primitivoDouble( String dadoNome ) {
		return real( dadoNome ).doubleValue();
	}
	
	public Byte inteiro8( String dadoNome ) {
		return TipoUtil.converterParaByte( inteiro( dadoNome ) );
	}
	
	public Short inteiro16( String dadoNome ) {
		return TipoUtil.converterParaShort( inteiro( dadoNome ) );
	}
	
	public Integer inteiro32( String dadoNome ) {
		return TipoUtil.converterParaInteger( inteiro( dadoNome ) );
	}
	
	public Long inteiro64( String dadoNome ) {
		return inteiro( dadoNome );
	}
	
	public Float real32( String dadoNome ) {
		return TipoUtil.converterParaFloat( real( dadoNome ) );
	}
	
	public Double real64( String dadoNome ) {
		return real( dadoNome );
	}
	
	/**
	 * @see #objeto(String)
	 */
	public Boolean logico( String dadoNome ) {
		Object o = objeto( dadoNome );
		if( o == null ) return null;
		if( o instanceof Boolean ) return (Boolean) o;
		throw new IllegalArgumentException( dadoNome );
	}
	
	public AnexoGrupo getAnexoGrupo() {
		return anexoGrupo;
	}
	
	public void setAnexoGrupo( AnexoGrupo anexoGrupo ) {
		this.anexoGrupo = anexoGrupo;
	}
	
	/**
	 * @see AnexoGrupo#mais(Anexo)
	 */
	public Informacao mais( Anexo anexo ) {
		anexoGrupo.mais( anexo );
		return this;
	}
	
	/**
	 * @see AnexoGrupo#menos(Anexo)
	 */
	public Informacao menos( Anexo anexo ) {
		anexoGrupo.menos( anexo );
		return this;
	}
	
	/**
	 * @see AnexoGrupo#menos(Class)
	 */
	public <T extends Anexo> Informacao menos( Class<? extends T> tipo ) {
		anexoGrupo.menos( tipo );
		return this;
	}
	
	/**
	 * @see AnexoGrupo#getAnexo(Class)
	 */
	public <T extends Anexo> T getAnexo( Class<? extends T> tipo ) {
		return anexoGrupo.getAnexo( tipo );
	}
	
	/**
	 * @see AnexoGrupo#getAnexo(Class, boolean)
	 */
	public <T extends Anexo> T getAnexo( Class<? extends T> tipo, boolean adicionar ) throws InstantiationException, IllegalAccessException {
		return anexoGrupo.getAnexo( tipo, adicionar );
	}
	
	/**
	 * @see AnexoGrupo#getAnexos(Class)
	 */
	public <T extends Anexo> List<T> getAnexos( Class<? extends T> tipo ) {
		return anexoGrupo.getAnexos( tipo );
	}
	
	/**
	 * Retorna a mensagem do primeiro {@link AnexoErro} encontrado.
	 * @see AnexoErro#getMensagem()
	 */
	public String getMensagemErro() {
		AnexoErro a = getAnexo( AnexoErro.class );
		return a != null ? a.getMensagem() : null;
	}
	
	/**
	 * Define a mensagem do primeiro {@link AnexoErro} encontrado.<br>
	 * O {@link AnexoErro} será {@link #mais(Anexo) adicionado} se inexistente.
	 * @param mensagem Pode ser <code>null</code>.
	 * @see AnexoErro#setMensagem(String)
	 */
	public Informacao setMensagemErro( String mensagem ) {
		
		AnexoErro a = getAnexo( AnexoErro.class );
		
		if( a == null ) return mais( new AnexoErro( mensagem ) );

		a.setMensagem( mensagem );
		return this;
				
	}
	
	/**
	 * Retorna a mensagem do primeiro {@link AnexoAtencao} encontrado.
	 * @see AnexoAtencao#getMensagem()
	 */
	public String getMensagemAtencao() {
		AnexoAtencao a = getAnexo( AnexoAtencao.class );
		return a != null ? a.getMensagem() : null;
	}
	
	/**
	 * Define a mensagem do primeiro {@link AnexoAtencao} encontrado.<br>
	 * O {@link AnexoAtencao} será {@link #mais(Anexo) adicionado} se inexistente.
	 * @param mensagem Pode ser <code>null</code>.
	 * @see AnexoAtencao#setMensagem(String)
	 */
	public Informacao setMensagemAtencao( String mensagem ) {
		
		AnexoAtencao a = getAnexo( AnexoAtencao.class );
		
		if( a == null ) return mais( new AnexoAtencao( mensagem ) );

		a.setMensagem( mensagem );
		return this;
				
	}
	
	/**
	 * Retorna a mensagem do primeiro {@link AnexoInformacao} encontrado.
	 * @see AnexoInformacao#getMensagem()
	 */
	public String getMensagemInformacao() {
		AnexoInformacao a = getAnexo( AnexoInformacao.class );
		return a != null ? a.getMensagem() : null;
	}
	
	/**
	 * Define a mensagem do primeiro {@link AnexoInformacao} encontrado.<br>
	 * O {@link AnexoInformacao} será {@link #mais(Anexo) adicionado} se inexistente.
	 * @param mensagem Pode ser <code>null</code>.
	 * @see AnexoInformacao#setMensagem(String)
	 */
	public Informacao setMensagemInformacao( String mensagem ) {
		
		AnexoInformacao a = getAnexo( AnexoInformacao.class );
		
		if( a == null ) return mais( new AnexoInformacao( mensagem ) );

		a.setMensagem( mensagem );
		return this;
				
	}
	
	/**
	 * Atribui <code>null</code> para {@link #setMensagemErro(String)}, {@link #setMensagemAtencao(String)} e {@link #setMensagemInformacao(String)}.
	 */
	public void retirarMensagens() {
		setMensagemErro( null );
		setMensagemAtencao( null );
		setMensagemInformacao( null );
	}
	
	private void atualizarIndiceCache() {
		
		indiceCache.clear();
		int i = 0;
		
		for( Dado dado : dados ){
			if( dado instanceof Identificacao ){
				String nome = ((Identificacao)dado).getNome();
				if( nome != null && nome.length() > 0 && indiceCache.get( nome ) == null ) indiceCache.put( nome, i );
			}
			i++;
		}
		
	}
	
	public Texto maisTexto( String nome, Class<? extends Object> classe, String atributo, String texto, Boolean editavel ) {
		return mais( new Texto( nome, classe, atributo, texto, editavel ) );
	}
	
	public Texto maisTexto( String nome, Class<? extends Object> classe, String texto, Boolean editavel ) {
		return maisTexto( nome, classe, nome, texto, editavel );
	}
	
	public Senha maisSenha( String nome, Class<? extends Object> classe, String atributo, String senha, Boolean editavel ) {
		return mais( new Senha( nome, classe, atributo, senha, editavel ) );
	}
	
	public Senha maisSenha( String nome, Class<? extends Object> classe, String senha, Boolean editavel ) {
		return maisSenha( nome, classe, nome, senha, editavel );
	}
	
	public Data maisData( String nome, Class<? extends Object> classe, String atributo, Date data, Boolean editavel ) {
		return mais( new Data( nome, classe, atributo, data, editavel ) );
	}
	
	public Data maisData( String nome, Class<? extends Object> classe, Date data, Boolean editavel ) {
		return maisData( nome, classe, nome, data, editavel );
	}
	
	public Real maisReal( String nome, Class<? extends Object> classe, String atributo, Double numero, Boolean editavel ) {
		return mais( new Real( nome, classe, atributo, numero, editavel ) );
	}
	
	public Real maisReal( String nome, Class<? extends Object> classe, Double numero, Boolean editavel ) {
		return maisReal( nome, classe, nome, numero, editavel );
	}
	
	public Inteiro maisInteiro( String nome, Class<? extends Object> classe, String atributo, Long numero, Boolean editavel ) {
		return mais( new Inteiro( nome, classe, atributo, numero, editavel ) );
	}
	
	public Inteiro maisInteiro( String nome, Class<? extends Object> classe, Long numero, Boolean editavel ) {
		return maisInteiro( nome, classe, nome, numero, editavel );
	}
	
	public Inteiro maisInteiro( String nome, Class<? extends Object> classe, Integer numero, Boolean editavel ) {
		return maisInteiro( nome, classe, nome, numero != null ? new Long( numero.longValue() ) : null, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		return mais( new Selecao<T>( nome, classe, atributo, opcoes, selecaoInicial, editavel ) );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		return maisSelecao( nome, classe, nome, opcoes, selecaoInicial, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, List<T> opcoes, Boolean editavel ) {
		return maisSelecao( nome, classe, nome, opcoes, null, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, Boolean editavel ) {
		return maisSelecao( nome, classe, nome, (List<T>) null, null, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, T selecaoInicial, Boolean editavel ) {
		return maisSelecao( nome, classe, nome, (List<T>) null, selecaoInicial, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, String atributo, T[] opcoes, T selecaoInicial, Boolean editavel ) {
		return maisSelecao( nome, classe, atributo, new Lista<T>( opcoes ), selecaoInicial, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, T[] opcoes, T selecaoInicial, Boolean editavel ) {
		return maisSelecao( nome, classe, nome, new Lista<T>( opcoes ), selecaoInicial, editavel );
	}
	
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, T[] opcoes, Boolean editavel ) {
		return maisSelecao( nome, classe, nome, new Lista<T>( opcoes ), null, editavel );
	}
	
	public <T extends Object> SelecaoMultipla<T> maisSelecaoMultipla( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		return mais( new SelecaoMultipla<T>( nome, classe, atributo, opcoes, selecaoInicial, editavel ) );
	}
	
	public <T extends Object> SelecaoMultipla<T> maisSelecaoMultipla( String nome, Class<? extends Object> classe, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		return maisSelecaoMultipla( nome, classe, nome, opcoes, selecaoInicial, editavel );
	}
	
	public <T extends Object> SelecaoMultipla<T> maisSelecaoMultipla( String nome, Class<? extends Object> classe, T[] opcoes, T[] selecaoInicial, Boolean editavel ) {
		return maisSelecaoMultipla( nome, classe, nome, new Lista<T>( opcoes ), new Lista<T>( selecaoInicial ), editavel );
	}
	
	public <T extends Object> SelecaoMultipla<T> maisSelecaoMultipla( String nome, Class<? extends Object> classe, Boolean editavel ) {
		return maisSelecaoMultipla( nome, classe, nome, (List<T>) null, null, editavel );
	}
	
	public SelecionavelTexto maisSelecionavelTexto( String nome, Class<? extends Object> classe, String atributo, String texto, List<String> opcoes, Boolean editavel ) {
		return mais( new SelecionavelTexto( nome, classe, atributo, texto, opcoes, editavel ) );
	}
	
	public SelecionavelTexto maisSelecionavelTexto( String nome, Class<? extends Object> classe, String atributo, String texto, String[] opcoes, Boolean editavel ) {
		return maisSelecionavelTexto( nome, classe, atributo, texto, new Lista<String>( opcoes ), editavel );
	}
	
	public SelecionavelTexto maisSelecionavelTexto( String nome, Class<? extends Object> classe, String texto, List<String> opcoes, Boolean editavel ) {
		return maisSelecionavelTexto( nome, classe, nome, texto, opcoes, editavel );
	}
	
	public SelecionavelTexto maisSelecionavelTexto( String nome, Class<? extends Object> classe, String texto, String[] opcoes, Boolean editavel ) {
		return maisSelecionavelTexto( nome, classe, nome, texto, new Lista<String>( opcoes ), editavel );
	}
	
	public SelecionavelTexto maisSelecionavelTexto( String nome, Class<? extends Object> classe, String texto, Boolean editavel ) {
		return maisSelecionavelTexto( nome, classe, nome, texto, (List<String>) null, editavel );
	}
	
	public Bruto maisBruto( String nome, Class<? extends Object> classe, String atributo, byte[] valor, String valorRotulo, Boolean editavel ) {
		return mais( new Bruto( nome, classe, atributo, valor, valorRotulo, editavel ) );
	}
	
	public Bruto maisBruto( String nome, Class<? extends Object> classe, byte[] valor, String valorRotulo, Boolean editavel ) {
		return maisBruto( nome, classe, nome, valor, valorRotulo, editavel );
	}
	
	public Arquivo maisArquivo( String nome, Class<? extends Object> classe, String atributo, File arquivo, File localPreferido, Boolean editavel ) {
		return mais( new Arquivo( nome, classe, atributo, arquivo, localPreferido, editavel ) );
	}
	
	public Arquivo maisArquivo( String nome, Class<? extends Object> classe, File arquivo, File localPreferido, Boolean editavel ) {
		return maisArquivo( nome, classe, nome, arquivo, localPreferido, editavel );
	}
	
	public Binario maisBinario( String nome, Class<? extends Object> classe, String atributo, Boolean valor, Boolean editavel ) {
		return mais( new Binario( nome, classe, atributo, valor, editavel ) );
	}
	
	public Binario maisBinario( String nome, Class<? extends Object> classe, Boolean valor, Boolean editavel ) {
		return maisBinario( nome, classe, nome, valor, editavel );
	}
	
	/**
	 * Deve {@link Viagem#voltar()} ou {@link Viagem#encerrar()}.<br>
	 * Padrão: {@link Viagem#voltar()}
	 */
	public void sair( Viagem viagem ) {
		
		viagem.voltar();
		
	}
	
}
