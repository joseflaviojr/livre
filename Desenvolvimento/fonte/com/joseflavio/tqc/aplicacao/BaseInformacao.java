
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

package com.joseflavio.tqc.aplicacao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joseflavio.CapturaDeExcecao;
import com.joseflavio.modelo.JFDica;
import com.joseflavio.tqc.Alerta;
import com.joseflavio.tqc.AnexoAtencao;
import com.joseflavio.tqc.AnexoDica;
import com.joseflavio.tqc.AnexoErro;
import com.joseflavio.tqc.AnexoInformacao;
import com.joseflavio.tqc.AnexoMensagem;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.Menu;
import com.joseflavio.tqc.OpcaoDeMenu;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.anotacao.TQCComando;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.tqc.dado.ComandoPorMetodo;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.dado.Opcoes;
import com.joseflavio.tqc.dado.Selecao;
import com.joseflavio.util.ClassUtil;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Base de {@link Informacao} para {@link AplicacaoTQC}.<br>
 * Os {@link Comando}s poderão ser implementados através de {@link ComandoImplementado}, {@link TQCComando}, {@link Method} homônimo ao {@link Comando} e {@link #acao(Viagem, Comando)}.<br>
 * Todas as implementações de {@link Comando} serão executadas na sequência acima, caso coexistam e não disparem exceção. Porém, qualquer {@link Method} será executado uma única vez.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class BaseInformacao<A extends AplicacaoTQC> extends Informacao implements Cabecalho, ComandoPrincipal, ComandoPorMetodo, CapturaDeExcecao {

	public static final String ESTILO_SUBTITULO = "formSubtitulo";
	public static final String ESTILO_MENSAGEM_ERRO = "formMensagemErro";
	public static final String ESTILO_MENSAGEM_INFORMACAO = "formMensagemInformacao";
	
	protected A aplicacao;
	
	private String banner;
	
	private String subtitulo;
	
	private boolean autoRetirarMensagens = true;
	
	private boolean autoValidarTudo = false;
	
	private boolean autoCriarAlerta = false;
	
	private boolean alertaMostrado = false;
	
	private boolean subtituloCentral = false;
	
	private Map<String, Method> metodosComandados = new HashMap<String, Method>();
	
	/**
	 * @see #construir()
	 */
	public BaseInformacao( A aplicacao, String titulo, String banner, String subtitulo, boolean subtituloCentral ) throws TomaraQueCaiaException {
		
		super( titulo );
		
		this.aplicacao = aplicacao;
		
		this.subtitulo = subtitulo;
		this.subtituloCentral = subtituloCentral;

		this.banner = banner != null ? banner : aplicacao.getBanner();
		
		retirarMensagens(); //Adiciona as mensagens
		
		for( Method metodo : getMetodosComandados() ){
			TQCComando anotacao = metodo.getAnnotation( TQCComando.class );
			String nome = anotacao.nome();
			if( nome != null && nome.length() > 0 ) metodosComandados.put( nome, metodo );
		}
		
	}
	
	public BaseInformacao( A aplicacao, String titulo, String banner, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, banner, subtitulo, false );
	}
	
	public BaseInformacao( A aplicacao, String titulo, String subtitulo, boolean subtituloCentral ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, subtitulo, subtituloCentral );
	}
	
	public BaseInformacao( A aplicacao, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, subtitulo, false );
	}
	
	/**
	 * Efetua a construção da {@link Informacao}.
	 * @see #cabecalhoDaInformacao()
	 */
	protected void construir() throws TomaraQueCaiaException {
		
		cabecalhoDaInformacao();
		
	}
	
	@Override
	public final void cabecalhoDaInformacao() throws TomaraQueCaiaException {
		maisMarcador( "tqc_cabecalho_i" );
		cabecalho();
		maisMarcador( "tqc_cabecalho_f" );
	}
	
	/**
	 * Equivalente ao método {@link #cabecalhoDaInformacao()}, porém sem os {@link Marcador}es.
	 */
	protected void cabecalho() throws TomaraQueCaiaException {
	}
	
	@Override
	public final void comandosPrincipais() throws TomaraQueCaiaException {
		
		maisMarcador( "tqc_comandos_i" );
		
		comandos();
		
		Method[] metodosComandados = getMetodosComandados();
		Arrays.sort( metodosComandados, new Comparator<Method>() {
			public int compare( Method o1, Method o2 ) {
				int ordem1 = o1.getAnnotation( TQCComando.class ).ordem();
				int ordem2 = o2.getAnnotation( TQCComando.class ).ordem();
				return ordem1 < ordem2 ? -1 : ordem1 > ordem2 ? 1 : 0;
			}
		} );
		
		for( Method metodo : metodosComandados ){
			TQCComando anotacao = metodo.getAnnotation( TQCComando.class );
			if( anotacao.adicionar() ) mais( getComando( metodo ) );
		}
		
		maisMarcador( "tqc_comandos_f" );
		
	}
	
	@Override
	public Method[] getMetodosComandados() {
		
		Class<?> classe = this.getClass();
		List<Method> metodosComandados = new ArrayList<Method>();
		Map<String, Boolean> mapaDeInsercao = new HashMap<String, Boolean>();
		
		for( Method metodo : ClassUtil.listarMetodos( classe ) ){
			
			TQCComando anotacao = metodo.getAnnotation( TQCComando.class );
			
			if( anotacao != null ){
				
				Boolean inserido = mapaDeInsercao.get( anotacao.nome() );
				if( inserido != null && inserido ) continue;
				
				Class<?>[] parametros = metodo.getParameterTypes();
				if( parametros.length != 2 || parametros[0] != Viagem.class || parametros[1] != Comando.class ) continue;
				
				metodosComandados.add( metodo );
				mapaDeInsercao.put( anotacao.nome(), true );
				
			}
			
		}
		
		return metodosComandados.toArray( new Method[ metodosComandados.size() ] );
		
	}
	
	@Override
	public Comando getComando( Method metodoComandado ) {
		
		TQCComando anotacao = metodoComandado.getAnnotation( TQCComando.class );
		if( anotacao == null ) throw new IllegalArgumentException( "TQCComando ausente." );
		
		String nome = anotacao.nome();
		if( nome == null || nome.length() == 0 ) throw new IllegalArgumentException( "TQCComando: nome inválido." );
		
		Dado dado = getDado( nome );
		if( dado != null && dado instanceof Comando ) return (Comando) dado;
		
		String rotulo = anotacao.rotulo();
		String imagem = anotacao.imagem();
		Funcao funcao = anotacao.funcao();
		
		Comando comando = new Comando( nome, rotulo, imagem, funcao );
		
		JFDica jfDica = metodoComandado.getAnnotation( JFDica.class );
		if( jfDica != null ) comando.mais( new AnexoDica( jfDica.value() ) );
		
		return comando;
		
	}
	
	@Override
	public Comando getComando( String nome ) {
		
		Dado dado = getDado( nome );
		if( dado != null && dado instanceof Comando ) return (Comando) dado;
		
		Method metodo = metodosComandados.get( nome );
		if( metodo != null ) return getComando( metodo );
		
		throw new IllegalArgumentException( nome );
		
	}
	
	/**
	 * Deve executar {@link #maisOpcaoDeMenu(OpcaoDeMenu)} ou {@link #getMenu()}.{@link Menu#mais(OpcaoDeMenu) mais(OpcaoDeMenu)} para cada {@link OpcaoDeMenu} desejada.
	 */
	protected void menu() throws TomaraQueCaiaException {
	}
	
	public BaseInformacao<A> maisOpcaoDeMenu( OpcaoDeMenu opcao ) {
		Menu menu = getMenu();
		if( menu == null ) setMenu( menu = new Menu( "Menu" ) );
		menu.mais( opcao );
		return this;
	}
	
	/**
	 * Equivalente ao método {@link #comandosPrincipais()}, porém sem os {@link Marcador}es.<br>
	 * Os {@link Comando}s definidos através de {@link TQCComando} serão {@link #mais(Comando) adicionados} automaticamente pelo método {@link #comandosPrincipais()}.
	 */
	protected void comandos() throws TomaraQueCaiaException {
	}
	
	/**
	 * @see Selecao#atualizarOpcoes()
	 * @see #antesDeMostrar(Viagem)
	 * @see #criarAlerta()
	 */
	public final void antesDeMostrar( TomaraQueCaia tqc, Viagem viagem ) throws TomaraQueCaiaException {
		
		if( getMenu() == null ) setMenu( new Menu( "Menu" ) );
		if( getMenu().getTotal() == 0 ) menu();
		
		for( Opcoes<?> opcoes : getDados( Opcoes.class ) ){
			try{
				opcoes.atualizarOpcoes();
			}catch( Exception e ){
				throw new TomaraQueCaiaException( e );
			}
		}

		this.antesDeMostrar( viagem );
		
		if( autoCriarAlerta && ! alertaMostrado ){
			alertaMostrado = true;
			Alerta alerta = criarAlerta();
			if( alerta != null ) viagem.mostrar( alerta );
		}
		
	}
	
	protected void antesDeMostrar( Viagem viagem ) throws TomaraQueCaiaException {
	}

	public final void acao( TomaraQueCaia tqc, Viagem viagem, Comando comando ) throws TomaraQueCaiaException {

		if( autoRetirarMensagens ) retirarMensagens();
		
		try{
			
			if( autoValidarTudo ) validarTudo();
			
			String comandoNome = comando.getNome();
			
			if( comando instanceof ComandoImplementado ) ((ComandoImplementado)comando).acao( viagem );
			
			try{
			
				Method metodoAnotado = metodosComandados.get( comandoNome );
				if( metodoAnotado != null ) metodoAnotado.invoke( this, viagem, comando );
				
				if( metodoAnotado == null || ! metodoAnotado.getName().equals( comandoNome ) ){
					try{
						Method metodoHomonimo = this.getClass().getDeclaredMethod( comandoNome, Viagem.class, Comando.class );
						metodoHomonimo.invoke( this, viagem, comando );
					}catch( NoSuchMethodException e ){
					}
				}
				
			}catch( InvocationTargetException e ){
				Throwable t = e.getCause();
				if( t != null ) throw (Exception) t;
			}
			
			acao( viagem, comando );
			
		}catch( ValidacaoException e ){
			
			processarValidacaoException( e );
		
		}catch( TomaraQueCaiaException e ){
			
			if( e.getCause() instanceof ValidacaoException ) processarValidacaoException( (ValidacaoException) e.getCause() );
			else throw e;
			
		}catch( BancoDeDadosException e ){
			
			e.printStackTrace();
			setMensagemErro( "Erro de Banco de Dados: " + e.getMessage() );
		
		}catch( Exception e ){
			
			e.printStackTrace();
			setMensagemErro( "Erro desconhecido: " + e.getMessage() );
			
		}
		
	}
	
	/**
	 * @see #acao(TomaraQueCaia, Viagem, Comando)
	 * @throws ValidacaoException será convertida em {@link AnexoMensagem}.
	 * @throws TomaraQueCaiaException será propagada.
	 */
	protected void acao( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
	}
	
	private void processarValidacaoException( ValidacaoException e ) {
		
		switch( e.getTipo() ){
			
			case ValidacaoException.INFORMACAO :
				setMensagemInformacao( e.getMensagem() );
				break;
				
			case ValidacaoException.ATENCAO :
				setMensagemAtencao( e.getMensagem() );
				break;
				
			default :
				setMensagemErro( e.getMensagem() );
				break;
			
		}
		
	}
	
	/**
	 * @see #setMensagemErro(String)
	 */
	@Override
	public void capturar( Exception e ) {
		if( e instanceof ValidacaoException ) processarValidacaoException( (ValidacaoException) e );
		else if( e.getCause() instanceof ValidacaoException ) processarValidacaoException( (ValidacaoException) e.getCause() );
		else setMensagemErro( e.getMessage() );
	}
	
	public void setSubtitulo( String subtitulo ) {
		this.subtitulo = subtitulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}
	
	public boolean isSubtituloCentral() {
		return subtituloCentral;
	}
	
	public void setSubtituloCentral( boolean subtituloCentral ) {
		this.subtituloCentral = subtituloCentral;
	}
	
	public void setBanner( String imagem ) {
		this.banner = imagem;
	}
	
	public String getBanner() {
		return banner;
	}
	
	@Override
	public Informacao setMensagemErro( String mensagem ) {
		alertaMostrado = false;
		return super.setMensagemErro( mensagem );
	}
	
	@Override
	public Informacao setMensagemAtencao( String mensagem ) {
		alertaMostrado = false;
		return super.setMensagemAtencao( mensagem );
	}
	
	@Override
	public Informacao setMensagemInformacao( String mensagem ) {
		alertaMostrado = false;
		return super.setMensagemInformacao( mensagem );
	}
	
	/**
	 * Executar #retirarMensagens() em {@link #acao(TomaraQueCaia, Viagem, Comando)}?
	 */
	public boolean isAutoRetirarMensagens() {
		return autoRetirarMensagens;
	}
	
	/**
	 * Executar #retirarMensagens() em {@link #acao(TomaraQueCaia, Viagem, Comando)}?
	 */
	public void setAutoRetirarMensagens( boolean autoRetirarMensagens ) {
		this.autoRetirarMensagens = autoRetirarMensagens;
	}
	
	public boolean isAutoValidarTudo() {
		return autoValidarTudo;
	}
	
	public void setAutoValidarTudo( boolean autoValidarTudo ) {
		this.autoValidarTudo = autoValidarTudo;
	}
	
	public boolean isAutoCriarAlerta() {
		return autoCriarAlerta;
	}
	
	/**
	 * Determina que se deve {@link #criarAlerta()} e {@link Viagem#mostrar(Alerta) mostrá-lo} {@link #antesDeMostrar(TomaraQueCaia, Viagem)}.<br>
	 * Cada {@link Alerta} será mostrado uma única vez.
	 */
	public void setAutoCriarAlerta( boolean autoCriarAlerta ) {
		this.autoCriarAlerta = autoCriarAlerta;
	}
	
	/**
	 * Gera uma {@link Alerta} compatível com uma das {@link AnexoMensagem mensagens}. Ordem: {@link AnexoErro}, {@link AnexoAtencao}, {@link AnexoInformacao}.
	 * @return <code>null</code> caso não haja qualquer {@link AnexoMensagem}.
	 */
	public Alerta criarAlerta() {
		
		if( getMensagemErro() != null ){
			
			return Alerta.novoErro( getTitulo(), getMensagemErro() );
			
		}else if( getMensagemAtencao() != null ){
			
			return Alerta.novaAtencao( getTitulo(), getMensagemAtencao() );
			
		}else if( getMensagemInformacao() != null ){
			
			return Alerta.novaInformacao( getTitulo(), getMensagemInformacao() );
			
		}
		
		return null;
		
	}
	
	@Override
	public final Informacao criarAjuda( TomaraQueCaia tqc ) throws TomaraQueCaiaException {
		return criarAjuda( aplicacao );
	}
	
	/**
	 * Adaptação de {@link #criarAjuda(TomaraQueCaia)} para {@link BaseInformacao}.
	 */
	public BaseInformacao<A> criarAjuda( A aplicacao ) throws TomaraQueCaiaException {
		return null;
	}
	
}
