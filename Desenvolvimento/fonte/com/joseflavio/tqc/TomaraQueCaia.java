
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

package com.joseflavio.tqc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.joseflavio.cultura.Cultura;
import com.joseflavio.cultura.DataTransformacao;
import com.joseflavio.cultura.NumeroTransformacao;
import com.joseflavio.util.Calendario;
import com.joseflavio.util.ListaUtil;

/**
 * Base das aplicações da arquitetura Tomara Que Caia.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class TomaraQueCaia {
	
	private Properties propriedades;
	
	private String titulo;
	
	private Cultura cultura = Cultura.getPadrao();
	
	/**
	 * Mapa de {@link Filtro}'s.
	 */
	private Map<String, FiltroMapa> filtros = new HashMap<String, FiltroMapa>();
	
	/**
	 * @see EstiloFonte
	 */
	private List<EstiloFonte> estiloFontes = new ArrayList<EstiloFonte>();
	
	private List<Viagem> viagemLista = new ArrayList<Viagem>();
	private Map<String, Viagem> viagemMapa = new HashMap<String, Viagem>();
	private Viagem viagemAtiva = null;
	private int geradorNomeViagem = 1;
	private List<String> historicoDeAtivacao = new ArrayList<String>();
	
	private File raiz;
	
	private String direitosAutorais;
	
	private String pele = "padrao";
	
	private boolean autoEncerrarViagensVazias = true;
	
	/**
	 * Construtor padrão. As subclasses de {@link TomaraQueCaia} deverão manter construtor sem parâmetro.
	 */
	protected TomaraQueCaia() {
		
		try{
			propriedades = new Properties();
			propriedades.loadFromXML( TomaraQueCaia.class.getResourceAsStream( "/META-INF/tqc.xml" ) );
		}catch( Exception e ){
			propriedades = null;
		}
		
		if( propriedades != null ){
			
			String tqc_titulo = propriedades.getProperty( "tqc.titulo" );
			String tqc_cultura = propriedades.getProperty( "tqc.cultura" );
			String tqc_pele = propriedades.getProperty( "tqc.pele" );
			
			if( tqc_titulo != null ) this.titulo = tqc_titulo;
			if( tqc_cultura != null ) this.cultura = Cultura.getCultura( tqc_cultura );
			if( tqc_pele != null ) this.pele = tqc_pele;
			
		}
		
		direitosAutorais = "joseflavio.com &copy; 2009-" + new Calendario( this.cultura ).getAno() + "\nSoftware Livre";
		
	}
	
	/**
	 * @param titulo {@link #getTitulo() Título} utilizado na ausência da {@link #getPropriedade(String) propriedade} "tqc.titulo".
	 * @param cultura {@link #getCultura() Cultura} utilizada na ausência da {@link #getPropriedade(String) propriedade} "tqc.cultura".
	 * @deprecated Definições externas. Ver {@link #getPropriedade(String)}.
	 */
	protected TomaraQueCaia( String titulo, Cultura cultura ) {
		
		this();
		
		if( this.titulo == null && titulo != null ) this.titulo = titulo;
		if( this.cultura == null && cultura != null ) this.cultura = cultura;
		
	}
	
	/**
	 * Retorna o valor da propriedade definida em "/META-INF/tqc.xml".
	 * @return <code>null</code> caso a propriedade ou o arquivo não sejam encontrados.
	 */
	public String getPropriedade( String chave ) {
		return propriedades != null ? propriedades.getProperty( chave ) : null;
	}
	
	/**
	 * {@link Properties#loadFromXML(java.io.InputStream)} com {@link Class#getResourceAsStream(String)}.
	 * @param endereco <code>null</code> = "/META-INF/tqc.xml".
	 * @return <code>null</code> caso o arquivo de propriedades não seja encontrado ou seja inválido.
	 */
	public static Properties carregarPropriedades( String endereco ) {
		try{
			Properties propriedades = new Properties();
			propriedades.loadFromXML( TomaraQueCaia.class.getResourceAsStream( endereco != null ? endereco : "/META-INF/tqc.xml" ) );
			return propriedades;
		}catch( Exception e ){
			return null;
		}
	}
	
	/**
	 * Cria uma {@link Viagem} {@link Viagem#isVazia() vazia}.
	 * @param nome {@link Viagem#getNome()}
	 */
	public final Viagem novaViagem( String nome ) {
		
		if( viagemMapa.containsKey( nome ) ) throw new IllegalArgumentException( nome );
		
		Viagem v = new Viagem( this, nome );
		
		viagemLista.add( v );
		viagemMapa.put( nome, v );
		
		if( viagemAtiva == null ) viagemAtiva = v;
		
		return v;
		
	}
	
	/**
	 * Cria uma {@link Viagem} {@link Viagem#isVazia() vazia}.<br>
	 * Seu {@link Viagem#getNome() nome} será gerado automaticamente.
	 */
	public final Viagem novaViagem() {
		
		return novaViagem( "tqc_viagem_" + ( geradorNomeViagem++ ) );
		
	}

	/**
	 * {@link Viagem#encerrar() Encerra} todas as {@link Viagem}'s {@link Viagem#isVazia() vazias}.
	 */
	public final void encerrarViagensVazias() {
		int total = viagemLista.size();
		while( viagemLista.size() > 0 && total > 0 ){
			Viagem v = viagemLista.get( 0 );
			if( v.isVazia() ) v.encerrar();
			total--;
		}
	}
	
	public final Viagem getViagem( String nome ) {
		return (Viagem) viagemMapa.get( nome );
	}
	
	public final Viagem getViagem( int index ) {
		return (Viagem) viagemLista.get( index );
	}
	
	public final Viagem getViagemAtiva() {
		return viagemAtiva;
	}
	
	public final int getTotalViagens() {
		return viagemLista.size();
	}
	
	public Viagem[] getViagens() {
		return viagemLista.toArray( new Viagem[ viagemLista.size() ] );
	}
	
	final void ativar( Viagem viagem ){
		
		if( viagemLista.contains( viagem ) ){
			
			if( viagemAtiva != null ){
				historicoDeAtivacao.add( viagemAtiva.getNome() );
			}
			
			viagemAtiva = viagem;
			
		}
		
	}
	
	final void remover( Viagem viagem ){
		
		viagemLista.remove( viagem );
		viagemMapa.remove( viagem.getNome() );
		
		if( viagemAtiva == viagem ){
			
			do{
				
				String nome = ListaUtil.menosUltimo( historicoDeAtivacao );
				
				if( nome != null ){
					Viagem v = getViagem( nome );
					if( v != null ){
						viagemAtiva = v;
						return;
					}
				}else{
					break;
				}
				
			}while( true );
			
			viagemAtiva = getTotalViagens() > 0 ? getViagem( 0 ) : null;
			
		}
		
	}
	
	/**
	 * Encerra todas as {@link Viagem viagens} abertas, desencadeando a finalização da aplicação.<br>
	 * Assim que o gerenciador da {@link TomaraQueCaia} detecta a ausência de {@link Viagem viagens}, o método {@link #encerrar()} é chamado.
	 */
	public final void encerrarViagens() {
		
		while( viagemLista.size() > 0 ){
			viagemLista.get( viagemLista.size() - 1 ).encerrar();
		}
		
	}
	
	/**
	 * Chama o método {@link #fim()} e encerra devidamente toda a aplicação.
	 * @see #isEncerrada()
	 */
	public final void encerrar() {
		
		fim();
		
		encerrarViagens();
		
		//titulo = null;
		//cultura = null;
		filtros = null;
		//estiloFontes = null;
		viagemLista = null;
		viagemMapa = null;
		viagemAtiva = null;
		historicoDeAtivacao = null;
		//raiz = null;
		//direitosAutorais = null;
		
	}
	
	/**
	 * Verifica se a {@link TomaraQueCaia} está {@link #encerrar() encerrada}.
	 * @see #encerrar()
	 */
	public final boolean isEncerrada() {
		return viagemLista == null;
	}
	
	/**
	 * Método inicial da {@link TomaraQueCaia}, responsável pelo ciclo de vida da aplicação. 
	 */
	public abstract void inicio( java.util.Map<String, String> parametros ) throws TomaraQueCaiaException;
	
	/**
	 * Método final da {@link TomaraQueCaia}, responsável pela liberação dos recursos em utilização.
	 * @see #encerrar()
	 */
	public abstract void fim();
	
	public void mais( EstiloFonte fonte ) {
		estiloFontes.add( fonte );
	}
	
	public EstiloFonte[] listarEstiloFontes() {
		
		int total = estiloFontes.size();
		
		EstiloFonte[] lista = new EstiloFonte[ total ];
		
		for( int i = 0; i < total; i++ ){
			lista[i] = estiloFontes.get( i );
		}
		
		return lista;
		
	}
	
	/**
	 * A {@link Informacao} foi processada e mostrada ao usuário.<br>
	 * Esta implementação nada faz.
	 */
	public void informacaoMostrada( Viagem viagem, Informacao informacao ) throws TomaraQueCaiaException {
	}
	
	/**
	 * O acesso à {@link Informacao} foi negada por algum {@link Filtro} ou recurso extra.
	 */
	public abstract void permissaoNegada( Viagem viagem, Informacao informacao ) throws TomaraQueCaiaException;
	
	/**
	 * A {@link TomaraQueCaia} deve negar acesso a qualquer {@link Informacao} que não possua {@link Filtro} associado?
	 * @see #filtrar(Viagem)
	 */
	public abstract boolean negarAcessoSemFiltro();
	
	/**
	 * Aplica todos os {@link Filtro}'s associados à {@link Viagem#getAtual()}.<br>
	 * Caso algum dos {@link Filtro}'s retorne <code>false</code>, este método retornará <code>false</code> imediatamente.
	 * @see Filtro#antesDeFiltrar()
	 * @see Filtro#filtrar(TomaraQueCaia, Viagem, Informacao)
	 */
	public final boolean filtrar( Viagem viagem ) throws TomaraQueCaiaException {
		
		Informacao informacao = viagem.getAtual();
		if( informacao == null ) return false;
		
		FiltroMapa mapaClasse = filtros.get( informacao.getClass().getName() );
		FiltroMapa mapaPacote = filtros.get( informacao.getClass().getPackage().getName() );

		if( mapaClasse == null && mapaPacote == null ){
			return ! negarAcessoSemFiltro();
		}
		
		return filtrar( informacao, mapaClasse ) && filtrar( informacao, mapaPacote );
			
	}
	
	/**
	 * Aplica à {@link Viagem#getAtual()} todos os {@link Filtro}'s associados à {@link Viagem#getAnterior()}.
	 * @see #filtrar(Viagem)
	 */
	public final boolean filtrarRetroativo( Viagem viagem ) throws TomaraQueCaiaException {
		
		Informacao informacaoAnterior = viagem.getAnterior();
		if( informacaoAnterior == null ) return false;
		
		Informacao informacaoAtual = viagem.getAtual();
		
		FiltroMapa mapaClasse = filtros.get( informacaoAnterior.getClass().getName() );
		FiltroMapa mapaPacote = filtros.get( informacaoAnterior.getClass().getPackage().getName() );

		if( mapaClasse == null && mapaPacote == null ){
			return ! negarAcessoSemFiltro();
		}
		
		return filtrar( informacaoAtual, mapaClasse ) && filtrar( informacaoAtual, mapaPacote );
			
	}
	
	private boolean filtrar( Informacao informacao, FiltroMapa lista ) throws TomaraQueCaiaException {
		
		while( lista != null ){
			
			lista.filtro.antesDeFiltrar();
			if( ! lista.filtro.filtrar( this, viagemAtiva, informacao ) ) return false;
			
			lista = lista.proximo;
				
		}
		
		return true;
			
	}
	
	/**
	 * Adiciona um {@link Filtro} para uma específica {@link Informacao}.
	 * @param informacao Classe da {@link Informacao} que deve ser filtrada.
	 * @param filtro {@link Filtro} que deve filtrar a {@link Informacao}.
	 */
	public TomaraQueCaia mais( Class<? extends Informacao> informacao, Filtro filtro ){
		
		return maisFiltroMapa( informacao.getName(), filtro );
		
	}
	
	/**
	 * Adiciona um {@link Filtro} para as {@link Informacao}'s localizadas no pacote especificado.<br>
	 * OBS.: Não inclue os subpacotes.
	 * @param pacote Nome completo do pacote. null || "" == pacote raiz. Exemplo: "com.joseflavio"
	 * @param filtro {@link Filtro} que deve filtrar as {@link Informacao}'s do pacote.
	 */
	public TomaraQueCaia mais( String pacote, Filtro filtro ){

		return maisFiltroMapa( pacote != null ? pacote : "", filtro );
		
	}

	private TomaraQueCaia maisFiltroMapa( String chave, Filtro filtro ){
		
		FiltroMapa atual = filtros.get( chave );
		FiltroMapa novo = new FiltroMapa( filtro );
		
		filtros.put( chave, atual == null ? novo : atual.maisUltimo( novo ) );
		
		return this;
		
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo( String titulo ) {
		this.titulo = titulo;
	}
	
	public void setRaiz( File raiz ) {
		this.raiz = raiz;
	}
	
	/**
	 * Diretório-raiz da aplicação.
	 */
	public File getRaiz() {
		return raiz;
	}
	
	/**
	 * Texto que especifica o "Copyright".
	 */
	public String getDireitosAutorais() {
		return direitosAutorais;
	}
	
	public void setDireitosAutorais( String direitosAutorais ) {
		this.direitosAutorais = direitosAutorais;
	}
	
	/**
	 * @see #setPele(String)
	 */
	public String getPele() {
		return pele;
	}
	
	/**
	 * Determina o modelo visual geral padrão da {@link TomaraQueCaia}. Padrão: "padrao".
	 * @param pele <code>null</code> = "padrao".
	 * @see Informacao#setPele(String)
	 */
	public void setPele( String pele ) {
		this.pele = pele != null ? pele : "padrao";
	}
	
	public Cultura getCultura() {
		return cultura;
	}

	/**
	 * Determina o {@link #encerrarViagensVazias() encerramento automático} de todas as {@link Viagem}'s {@link Viagem#isVazia() vazias} periodicamente.
	 */
	public void setAutoEncerrarViagensVazias( boolean autoEncerrarViagensVazias ) {
		this.autoEncerrarViagensVazias = autoEncerrarViagensVazias;
	}
	
	/**
	 * @see #setAutoEncerrarViagensVazias(boolean)
	 */
	public boolean isAutoEncerrarViagensVazias() {
		return autoEncerrarViagensVazias;
	}
	
	public NumeroTransformacao novaRealTransformacao() {
		
		return cultura.novaRealTransformacao();
		
	}
	
	public NumeroTransformacao novaInteiroTransformacao() {
		
		return cultura.novaInteiroTransformacao();
		
	}
	
	public NumeroTransformacao novaMoedaTransformacao() {
		
		return cultura.novaMoedaTransformacao();
		
	}
	
	public DataTransformacao novaDataTransformacao() {
		
		return cultura.novaDataTransformacao();
		
	}
	
	private static class FiltroMapa {
		
		private Filtro filtro;
		
		private FiltroMapa proximo;

		public FiltroMapa( Filtro filtro ) {
			this.filtro = filtro;
		}
		
		public FiltroMapa maisUltimo( FiltroMapa filtroMapa ) {
			
			FiltroMapa fm = this;
			
			while( fm.proximo != null ) fm = fm.proximo;
			
			fm.proximo = filtroMapa;
			
			return this;
			
		}
		
	}
	
	public static final String ENDERECO_ICONE_PEQUENO = "img/iconep/";
	
	public static final String ICONE_PEQUENO_ABRIR = ENDERECO_ICONE_PEQUENO + "abrir.png";
	public static final String ICONE_PEQUENO_ANEXO = ENDERECO_ICONE_PEQUENO + "anexo.png";
	public static final String ICONE_PEQUENO_ATUALIZAR = ENDERECO_ICONE_PEQUENO + "atualizar.png";
	public static final String ICONE_PEQUENO_AVANCAR = ENDERECO_ICONE_PEQUENO + "avancar.png";
	public static final String ICONE_PEQUENO_CARTA = ENDERECO_ICONE_PEQUENO + "carta.png";
	public static final String ICONE_PEQUENO_CHECADO = ENDERECO_ICONE_PEQUENO + "checado.png";
	public static final String ICONE_PEQUENO_COLAR = ENDERECO_ICONE_PEQUENO + "colar.png";
	public static final String ICONE_PEQUENO_COPIAR = ENDERECO_ICONE_PEQUENO + "copiar.png";
	public static final String ICONE_PEQUENO_DESFAZER = ENDERECO_ICONE_PEQUENO + "desfazer.png";
	public static final String ICONE_PEQUENO_DUVIDA = ENDERECO_ICONE_PEQUENO + "duvida.png";
	public static final String ICONE_PEQUENO_EDITAR = ENDERECO_ICONE_PEQUENO + "editar.png";
	public static final String ICONE_PEQUENO_IMPRIMIR = ENDERECO_ICONE_PEQUENO + "imprimir.png";
	public static final String ICONE_PEQUENO_INFORMACAO = ENDERECO_ICONE_PEQUENO + "informacao.png";
	public static final String ICONE_PEQUENO_MAIS = ENDERECO_ICONE_PEQUENO + "mais.png";
	public static final String ICONE_PEQUENO_MENOS = ENDERECO_ICONE_PEQUENO + "menos.png";
	public static final String ICONE_PEQUENO_NAO_CHECADO = ENDERECO_ICONE_PEQUENO + "nao_checado.png";
	public static final String ICONE_PEQUENO_NOVO = ENDERECO_ICONE_PEQUENO + "novo.png";
	public static final String ICONE_PEQUENO_RECORTAR = ENDERECO_ICONE_PEQUENO + "recortar.png";
	public static final String ICONE_PEQUENO_RECUAR = ENDERECO_ICONE_PEQUENO + "recuar.png";
	public static final String ICONE_PEQUENO_REMOVER = ENDERECO_ICONE_PEQUENO + "remover.png";
	public static final String ICONE_PEQUENO_REPETIR = ENDERECO_ICONE_PEQUENO + "repetir.png";
	public static final String ICONE_PEQUENO_SALVAR = ENDERECO_ICONE_PEQUENO + "salvar.png";
	
}
