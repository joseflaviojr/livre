
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

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.JFApresentacao;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Estilo;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.dado.Arquivo;
import com.joseflavio.tqc.dado.Binario;
import com.joseflavio.tqc.dado.Bruto;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Data;
import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.dado.Real;
import com.joseflavio.tqc.dado.Selecao;
import com.joseflavio.tqc.dado.SelecaoMultipla;
import com.joseflavio.tqc.dado.SelecionavelTexto;
import com.joseflavio.tqc.dado.Senha;
import com.joseflavio.tqc.dado.Tabela;
import com.joseflavio.tqc.dado.TabelaColuna;
import com.joseflavio.tqc.dado.TabelaColunaFim;
import com.joseflavio.tqc.dado.TabelaFim;
import com.joseflavio.tqc.dado.TabelaLinha;
import com.joseflavio.tqc.dado.TabelaLinhaFim;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.util.Lista;

/**
 * {@link Informacao} com característica de formulário para entrada de {@link Dado}'s. 
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class Formulario<TQC extends AplicacaoTQC> extends BaseInformacao<TQC> implements Rodape {

	private int totalDeCampos = 0;
	
	/**
	 * @see #construir()
	 */
	public Formulario( TQC aplicacao, String titulo, String banner, String subtitulo, boolean subtituloCentral ) throws TomaraQueCaiaException {
		
		super( aplicacao, titulo, banner, subtitulo, subtituloCentral );
		
		setAutoValidarTudo( false );
		
	}
	
	public Formulario( TQC aplicacao, String titulo, String subtitulo, boolean subtituloCentral ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, subtitulo, subtituloCentral );
	}

	public Formulario( TQC aplicacao, String titulo, String banner, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, banner, subtitulo, false );
	}

	public Formulario( TQC aplicacao, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, subtitulo, false );
	}
	
	public Formulario( TQC aplicacao, String titulo_subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo_subtitulo, null, titulo_subtitulo, false );
	}

	protected void construir() throws TomaraQueCaiaException {
		
		super.construir();
		
		/*----------------------*/
		
		mais( new Tabela() );
		
		/*----------------------*/
		
		maisMarcador( "tqc_formulario_campos_i" );
		campos();
		maisMarcador( "tqc_formulario_campos_f" );
		
		/*----------------------*/
		
		mais( new TabelaLinha() );

		mais( new TabelaColuna() );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaColuna() );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaLinhaFim() );
		
		/*----------------------*/
		
		mais( new TabelaLinha() );

		mais( new TabelaColuna() );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaColuna( "esquerda" ) );
		
		comandosPrincipais();
		
		mais( new TabelaColunaFim() );
		
		mais( new TabelaLinhaFim() );
		
		/*----------------------*/
		
		mais( new TabelaFim() );
		
		/*----------------------*/
		
		rodapeDaInformacao();
		
		/*----------------------*/
		
	}
	
	/**
	 * Deve executar {@link #maisCampo(String, Dado)} para cada campo desejado.
	 */
	protected abstract void campos() throws TomaraQueCaiaException;
	
	@Override
	public final void rodapeDaInformacao() throws TomaraQueCaiaException {
		maisMarcador( "tqc_rodape_i" );
		rodape();
		maisMarcador( "tqc_rodape_f" );
	}
	
	/**
	 * Equivalente ao método {@link #rodapeDaInformacao()}, porém sem os {@link Marcador}es.
	 */
	protected void rodape() throws TomaraQueCaiaException {
	}
	
	public int getTotalDeCampos() {
		return totalDeCampos;
	}
	
	/**
	 * Calcula a quantidade de campos {@link Dado#isVisivel() visíveis}.<br>
	 * Um campo é considerado {@link Dado#isVisivel() visível} de acordo com a visibilidade de seu rótulo.
	 */
	public int getTotalDeCamposVisiveis() {
		
		int total = 0;
		
		for( Dado dado : getDadosPuros() ){
			if( dado.isVisivel() && dado instanceof Texto ){
				String nome = ((Texto)dado).getNome();
				if( nome != null && nome.startsWith( "tqc_formulario_rotulo_" ) ) total++;
			}
		}
		
		return total;
		
	}
	
	/**
	 * Adiciona um campo ao {@link Formulario}.
	 * @see #maisCampo(String, Dado...)
	 * @see #maisCampo(String, List)
	 * @see #maisCampo(String, String)
	 */
	protected final Formulario<TQC> maisCampo( String rotulo, Dado campo ){
		
		totalDeCampos++;
		
		mais( new TabelaLinha() );

		mais( new TabelaColuna( Estilo.formColunaRotulo ) );
		mais( new Texto( "tqc_formulario_rotulo_" + totalDeCampos, rotulo ).setEstilo( Estilo.formRotulo ) );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaColuna( Estilo.formColunaValor ) );
		maisMarcador( "tqc_formulario_campo_" + totalDeCampos + "_i" );
		mais( campo );
		maisMarcador( "tqc_formulario_campo_" + totalDeCampos + "_f" );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaLinhaFim() );
		
		return this;
		
	}
	
	protected final Formulario<TQC> maisCampo( String rotulo, Dado... dadosDoCampo ){
		
		totalDeCampos++;
		
		mais( new TabelaLinha() );

		mais( new TabelaColuna( Estilo.formColunaRotulo ) );
		mais( new Texto( "tqc_formulario_rotulo_" + totalDeCampos, rotulo ).setEstilo( Estilo.formRotulo ) );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaColuna( Estilo.formColunaValor ) );
		maisMarcador( "tqc_formulario_campo_" + totalDeCampos + "_i" );
		for( Dado d : dadosDoCampo ) mais( d );
		maisMarcador( "tqc_formulario_campo_" + totalDeCampos + "_f" );
		mais( new TabelaColunaFim() );
		
		mais( new TabelaLinhaFim() );
		
		return this;
		
	}
	
	protected final Formulario<TQC> maisComando( Comando comando ){
		comando.setEspacoTextualPosterior( false ); //Na versão 2009 = true
		mais( (Dado) comando );
		return this;
	}
	
	protected final Formulario<TQC> maisCampo( String rotulo, List<? extends Dado> dadosDoCampo ){
		return maisCampo( rotulo, dadosDoCampo.toArray( new Dado[ dadosDoCampo.size() ] ) );
	}
	
	protected final Formulario<TQC> maisCampo( String rotulo, String valor ){
		return maisCampo( rotulo, new Texto( valor ) );
	}
	
	public static String getApresentacao( Class<? extends Object> classe, String atributo ) {
		JFApresentacao jfApresentacao = AssistenteDeAtributo.getAnotacao( classe, atributo, JFApresentacao.class, false );
		return jfApresentacao != null ? jfApresentacao.value() : atributo;
	}
	
	public static String getRotulo( Class<? extends Object> classe, String atributo ) {
		JFApresentacao jfApresentacao = AssistenteDeAtributo.getAnotacao( classe, atributo, JFApresentacao.class, false );
		return ( jfApresentacao != null ? jfApresentacao.value() : atributo ) + ":";
	}
	
	public static Texto getRotuloFormatado( Class<? extends Object> classe, String atributo ) {
		return (Texto) new Texto( getRotulo( classe, atributo ) ).setEstilo( Estilo.formRotulo );
	}
	
	public static Texto getRotulo( String texto ) {
		return (Texto) new Texto( texto ).setEstilo( Estilo.formRotulo );
	}
	
	/**
	 * @deprecated {@link #maisTexto(String, Class, String, String, Boolean)}
	 */
	protected Texto maisCampoTexto( String nome, Class<? extends Object> classe, String atributo, String texto, Boolean editavel ) {
		Texto dado = new Texto( nome, classe, atributo, texto, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Texto maisTexto( String nome, Class<? extends Object> classe, String atributo, String texto, Boolean editavel ) {
		Texto dado = new Texto( nome, classe, atributo, texto, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisTexto(String, Class, String, Boolean)}
	 */
	protected Texto maisCampoTexto( String nome, Class<? extends Object> classe, String texto, Boolean editavel ) {
		return maisCampoTexto( nome, classe, nome, texto, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSenha(String, Class, String, String, Boolean)}
	 */
	protected Senha maisCampoSenha( String nome, Class<? extends Object> classe, String atributo, String senha, Boolean editavel ) {
		Senha dado = new Senha( nome, classe, atributo, senha, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Senha maisSenha( String nome, Class<? extends Object> classe, String atributo, String senha, Boolean editavel ) {
		Senha dado = new Senha( nome, classe, atributo, senha, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisSenha(String, Class, String, Boolean)}
	 */
	protected Senha maisCampoSenha( String nome, Class<? extends Object> classe, String senha, Boolean editavel ) {
		return maisCampoSenha( nome, classe, nome, senha, editavel );
	}
	
	/**
	 * @deprecated {@link #maisData(String, Class, String, Date, Boolean)}
	 */
	protected Data maisCampoData( String nome, Class<? extends Object> classe, String atributo, Date data, Boolean editavel ) {
		Data dado = new Data( nome, classe, atributo, data, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Data maisData( String nome, Class<? extends Object> classe, String atributo, Date data, Boolean editavel ) {
		Data dado = new Data( nome, classe, atributo, data, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisData(String, Class, Date, Boolean)}
	 */
	protected Data maisCampoData( String nome, Class<? extends Object> classe, Date data, Boolean editavel ) {
		return maisCampoData( nome, classe, nome, data, editavel );
	}
	
	/**
	 * @deprecated {@link #maisReal(String, Class, String, Double, Boolean)}
	 */
	protected Real maisCampoReal( String nome, Class<? extends Object> classe, String atributo, Double numero, Boolean editavel ) {
		Real dado = new Real( nome, classe, atributo, numero, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Real maisReal( String nome, Class<? extends Object> classe, String atributo, Double numero, Boolean editavel ) {
		Real dado = new Real( nome, classe, atributo, numero, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisReal(String, Class, Double, Boolean)}
	 */
	protected Real maisCampoReal( String nome, Class<? extends Object> classe, Double numero, Boolean editavel ) {
		return maisCampoReal( nome, classe, nome, numero, editavel );
	}
	
	/**
	 * @deprecated {@link #maisInteiro(String, Class, String, Long, Boolean)}
	 */
	protected Inteiro maisCampoInteiro( String nome, Class<? extends Object> classe, String atributo, Long numero, Boolean editavel ) {
		Inteiro dado = new Inteiro( nome, classe, atributo, numero, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Inteiro maisInteiro( String nome, Class<? extends Object> classe, String atributo, Long numero, Boolean editavel ) {
		Inteiro dado = new Inteiro( nome, classe, atributo, numero, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisInteiro(String, Class, Long, Boolean)}
	 */
	protected Inteiro maisCampoInteiro( String nome, Class<? extends Object> classe, Long numero, Boolean editavel ) {
		return maisCampoInteiro( nome, classe, nome, numero, editavel );
	}
	
	/**
	 * @deprecated {@link #maisInteiro(String, Class, Integer, Boolean)}
	 */
	protected Inteiro maisCampoInteiro( String nome, Class<? extends Object> classe, Integer numero, Boolean editavel ) {
		return maisCampoInteiro( nome, classe, nome, numero != null ? new Long( numero.longValue() ) : null, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, String, List, Object, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		Selecao<T> dado = new Selecao<T>( nome, classe, atributo, opcoes, selecaoInicial, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public <T extends Object> Selecao<T> maisSelecao( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		Selecao<T> dado = new Selecao<T>( nome, classe, atributo, opcoes, selecaoInicial, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, List, Object, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, List<T> opcoes, T selecaoInicial, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, nome, opcoes, selecaoInicial, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, List, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, List<T> opcoes, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, nome, opcoes, null, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, nome, (List<T>) null, null, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, Object, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, T selecaoInicial, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, nome, (List<T>) null, selecaoInicial, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, String, Object[], Object, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, String atributo, T[] opcoes, T selecaoInicial, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, atributo, Arrays.asList( opcoes ), selecaoInicial, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, Object[], Object, Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, T[] opcoes, T selecaoInicial, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, nome, Arrays.asList( opcoes ), selecaoInicial, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecao(String, Class, Object[], Boolean)}
	 */
	protected <T extends Object> Selecao<T> maisCampoSelecao( String nome, Class<? extends Object> classe, T[] opcoes, Boolean editavel ) {
		return maisCampoSelecao( nome, classe, nome, Arrays.asList( opcoes ), null, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecaoMultipla(String, Class, String, List, List, Boolean)}
	 */
	protected <T extends Object> SelecaoMultipla<T> maisCampoSelecaoMultipla( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		SelecaoMultipla<T> dado = new SelecaoMultipla<T>( nome, classe, atributo, opcoes, selecaoInicial, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public <T extends Object> SelecaoMultipla<T> maisSelecaoMultipla( String nome, Class<? extends Object> classe, String atributo, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		SelecaoMultipla<T> dado = new SelecaoMultipla<T>( nome, classe, atributo, opcoes, selecaoInicial, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisSelecaoMultipla(String, Class, List, List, Boolean)}
	 */
	protected <T extends Object> SelecaoMultipla<T> maisCampoSelecaoMultipla( String nome, Class<? extends Object> classe, List<T> opcoes, List<T> selecaoInicial, Boolean editavel ) {
		return maisCampoSelecaoMultipla( nome, classe, nome, opcoes, selecaoInicial, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecaoMultipla(String, Class, Object[], Object[], Boolean)}
	 */
	protected <T extends Object> SelecaoMultipla<T> maisCampoSelecaoMultipla( String nome, Class<? extends Object> classe, T[] opcoes, T[] selecaoInicial, Boolean editavel ) {
		return maisCampoSelecaoMultipla( nome, classe, nome, new Lista<T>( opcoes ), new Lista<T>( selecaoInicial ), editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecaoMultipla(String, Class, Boolean)}
	 */
	protected <T extends Object> SelecaoMultipla<T> maisCampoSelecaoMultipla( String nome, Class<? extends Object> classe, Boolean editavel ) {
		return maisCampoSelecaoMultipla( nome, classe, nome, (List<T>) null, null, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecionavelTexto(String, Class, String, String, List, Boolean)}
	 */
	protected SelecionavelTexto maisCampoSelecionavelTexto( String nome, Class<? extends Object> classe, String atributo, String texto, List<String> opcoes, Boolean editavel ) {
		SelecionavelTexto dado = new SelecionavelTexto( nome, classe, atributo, texto, opcoes, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public SelecionavelTexto maisSelecionavelTexto( String nome, Class<? extends Object> classe, String atributo, String texto, List<String> opcoes, Boolean editavel ) {
		SelecionavelTexto dado = new SelecionavelTexto( nome, classe, atributo, texto, opcoes, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisSelecionavelTexto(String, Class, String, String, String[], Boolean)}
	 */
	protected SelecionavelTexto maisCampoSelecionavelTexto( String nome, Class<? extends Object> classe, String atributo, String texto, String[] opcoes, Boolean editavel ) {
		return maisCampoSelecionavelTexto( nome, classe, atributo, texto, Arrays.asList( opcoes ), editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecionavelTexto(String, Class, String, List, Boolean)}
	 */
	protected SelecionavelTexto maisCampoSelecionavelTexto( String nome, Class<? extends Object> classe, String texto, List<String> opcoes, Boolean editavel ) {
		return maisCampoSelecionavelTexto( nome, classe, nome, texto, opcoes, editavel );
	}
	
	/**
	 * @deprecated {@link #maisSelecionavelTexto(String, Class, String, String[], Boolean)}
	 */
	protected SelecionavelTexto maisCampoSelecionavelTexto( String nome, Class<? extends Object> classe, String texto, String[] opcoes, Boolean editavel ) {
		return maisCampoSelecionavelTexto( nome, classe, nome, texto, Arrays.asList( opcoes ), editavel );
	}
	
	/**
	 * @deprecated {@link #maisBruto(String, Class, String, byte[], String, Boolean)}
	 */
	protected Bruto maisCampoBruto( String nome, Class<? extends Object> classe, String atributo, byte[] valor, String valorRotulo, Boolean editavel ) {
		Bruto dado = new Bruto( nome, classe, atributo, valor, valorRotulo, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Bruto maisBruto( String nome, Class<? extends Object> classe, String atributo, byte[] valor, String valorRotulo, Boolean editavel ) {
		Bruto dado = new Bruto( nome, classe, atributo, valor, valorRotulo, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisBruto(String, Class, byte[], String, Boolean)}
	 */
	protected Bruto maisCampoBruto( String nome, Class<? extends Object> classe, byte[] valor, String valorRotulo, Boolean editavel ) {
		return maisCampoBruto( nome, classe, nome, valor, valorRotulo, editavel );
	}
	
	/**
	 * @deprecated {@link #maisArquivo(String, Class, String, File, File, Boolean)}
	 */
	protected Arquivo maisCampoArquivo( String nome, Class<? extends Object> classe, String atributo, File arquivo, File localPreferido, Boolean editavel ) {
		Arquivo dado = new Arquivo( nome, classe, atributo, arquivo, localPreferido, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	@Override
	public Arquivo maisArquivo( String nome, Class<? extends Object> classe, String atributo, File arquivo, File localPreferido, Boolean editavel ) {
		Arquivo dado = new Arquivo( nome, classe, atributo, arquivo, localPreferido, editavel );
		maisCampo( getRotulo( classe, atributo ), dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisArquivo(String, Class, File, File, Boolean)}
	 */
	protected Arquivo maisCampoArquivo( String nome, Class<? extends Object> classe, File arquivo, File localPreferido, Boolean editavel ) {
		return maisCampoArquivo( nome, classe, nome, arquivo, localPreferido, editavel );
	}
	
	/**
	 * @deprecated {@link #maisBinario(String, Class, String, Boolean, Boolean)}
	 */
	protected Binario maisCampoBinario( String nome, Class<? extends Object> classe, String atributo, Boolean valor, Boolean editavel ) {
		Binario dado = new Binario( nome, classe, atributo, valor, editavel );
		maisCampo( null, dado );
		return dado;
	}
	
	@Override
	public Binario maisBinario( String nome, Class<? extends Object> classe, String atributo, Boolean valor, Boolean editavel ) {
		Binario dado = new Binario( nome, classe, atributo, valor, editavel );
		maisCampo( null, dado );
		return dado;
	}
	
	/**
	 * @deprecated {@link #maisBinario(String, Class, Boolean, Boolean)}
	 */
	protected Binario maisCampoBinario( String nome, Class<? extends Object> classe, Boolean valor, Boolean editavel ) {
		return maisCampoBinario( nome, classe, nome, valor, editavel );
	}
	
}
