
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
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

package com.joseflavio.tqc.console;

import java.io.IOException;
import java.util.Date;
import java.util.Formatter;
import java.util.Stack;

import com.joseflavio.cultura.Cultura;
import com.joseflavio.cultura.DataTransformacao;
import com.joseflavio.cultura.NumeroTransformacao;
import com.joseflavio.cultura.TransformacaoException;

/**
 * Linha de comando.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class Console {
	
	public static final int COR_PRETA = 0;
	public static final int COR_AZUL = 1;
	public static final int COR_VERDE = 2;
	public static final int COR_CIANO = 3;
	public static final int COR_VERMELHA = 4;
	public static final int COR_MAGENTA = 5;
	public static final int COR_MARRON = 6;
	public static final int COR_CINZA_INTENSA = 7;
	public static final int COR_CINZA_ESCURA = 8;
	public static final int COR_AZUL_INTENSA = 9;
	public static final int COR_VERDE_INTENSA = 10;
	public static final int COR_CIANO_INTENSA = 11;
	public static final int COR_VERMELHA_INTENSA = 12;
	public static final int COR_MAGENTA_INTENSA = 13;
	public static final int COR_AMARELA = 14;
	public static final int COR_BRANCA = 15;
	
	private Cultura cultura;
	
	private NumeroTransformacao realTransformacao;
	private NumeroTransformacao inteiroTransformacao;
	private DataTransformacao dataTransformacao;
	
	private int corTextoVocativo = -1;
	
	private Stack<Cores> pilhaCores = new Stack<Cores>();
	
	/**
	 * @param cultura <code>null</code> == {@link Cultura#getPadrao()}
	 */
	public Console( Cultura cultura ) {
		this.cultura = cultura != null ? cultura : Cultura.getPadrao();
		this.realTransformacao = cultura.novaRealTransformacao();
		this.inteiroTransformacao = cultura.novaInteiroTransformacao();
		this.dataTransformacao = cultura.novaDataTransformacao();
	}
	
	public abstract int getTotalColunas();
	
	public abstract int getTotalLinhas();
	
	public abstract void setCorTexto( int cor );
	
	public final void setCorTexto( Cor cor ) {
		setCorTexto( cor.getCor() );
	}
	
	public abstract int getCorTexto();
	
	/**
	 * @see #getCorTexto()
	 * @see Cor#getCor(int)
	 */
	public final Cor getCorTexto2(){
		return Cor.getCor( getCorTexto() );
	}
	
	public abstract void setCorTextoFundo( int cor );
	
	public final void setCorTextoFundo( Cor cor ) {
		setCorTextoFundo( cor.getCor() );
	}
	
	public abstract int getCorTextoFundo();
	
	/**
	 * @see #getCorTextoFundo()
	 * @see Cor#getCor(int)
	 */
	public final Cor getCorTextoFundo2(){
		return Cor.getCor( getCorTextoFundo() );
	}
	
	public abstract void limpar();
	
	public void limpar( int corFundo ){
		setCorTextoFundo( corFundo );
		limpar();
	}
	
	public final void limpar( Cor corFundo ){
		limpar( corFundo.getCor() );
	}

	/**
	 * Espera pelo pressionamento de qualquer tecla.
	 * @param mostrar Mostrar a tecla pressionada?
	 * @return tecla pressionada.
	 */
	public abstract char esperar( boolean mostrar ) throws IOException;
	
	/**
	 * @param vocativo Texto que precede {@link #esperar(boolean)}.
	 */
	public final char esperar( String vocativo, boolean mostrar ) throws IOException {
		
		enviarVocativo( vocativo );
		return esperar( mostrar );
			
	}
	
	public abstract void setTelaCheia( boolean cheia );
	
	public abstract String receber() throws IOException;
	
	public abstract void enviar( String texto );
	
	public final void enviar( Cor corTexto, String texto ) {
		int cor = getCorTexto();
		setCorTexto( corTexto );
		enviar( texto );
		setCorTexto( cor );
	}
	
	public abstract void enviarln( String texto );
	
	public final void enviarln( Cor corTexto, String texto ) {
		int cor = getCorTexto();
		setCorTexto( corTexto );
		enviarln( texto );
		setCorTexto( cor );
	}
	
	public final void enviarCentralizado( Cor corTexto, String texto ) {
		int cor = getCorTexto();
		setCorTexto( corTexto );
		enviarCentralizado( texto );
		setCorTexto( cor );
	}
	
	/**
	 * {@link #enviar(String) Envia} o texto de tal forma que fique centralizado na linha atual.<br>
	 * A linha toda terá a {@link #getCorTextoFundo() cor de fundo} atual e deve estar vazia.
	 * @param texto Texto com comprimento <= {@link #getTotalColunas()}
	 */
	public final void enviarCentralizado( String texto ) {
		
		int totalColunas = getTotalColunas();
		int textoTamanho = texto.length();
		if( textoTamanho > totalColunas ) throw new IllegalArgumentException( "Tamanho excessivo: " + textoTamanho );
		StringBuilder sb = new StringBuilder( totalColunas );

		int totalEspacos = ( totalColunas - textoTamanho ) / 2;
		while( totalEspacos > 0 ){
			sb.append( ' ' );
			totalEspacos--;
		}
		
		sb.append( texto );
		
		totalEspacos = totalColunas - sb.length();
		while( totalEspacos > 0 ){
			sb.append( ' ' );
			totalEspacos--;
		}
		
		enviar( sb.toString() );
		
	}
	
	public abstract void enviarln();
	
	/**
	 * @see Formatter
	 */
	public abstract void enviar( String formato, Object ... args );
	
	public final void enviar( Cor corTexto, String formato, Object ... args ) {
		int cor = getCorTexto();
		setCorTexto( corTexto );
		enviar( formato, args );
		setCorTexto( cor );
	}
	
	public Cultura getCultura() {
		return cultura;
	}
	
	public void setRealTransformacao( NumeroTransformacao realTransformacao ) {
		this.realTransformacao = realTransformacao;
	}
	
	public void setInteiroTransformacao( NumeroTransformacao inteiroTransformacao ) {
		this.inteiroTransformacao = inteiroTransformacao;
	}
	
	public void setDataTransformacao( DataTransformacao dataTransformacao ) {
		this.dataTransformacao = dataTransformacao;
	}
	
	/**
	 * Primeiro tenta instanciar um {@link NativoConsole}, caso contrário retornará um {@link GenericoConsole}.
	 */
	public static Console novoConsole( Cultura cultura ) {
		
		Console c;
		
		try{
			
			c = new NativoConsole( cultura );
			
		}catch( Throwable e ){
			
			c = new GenericoConsole( cultura );
			
		}
		
		return c;
		
	}
	
	/**
	 * {@link Cultura#getPadrao()}
	 */
	public static Console novoConsole() {
		
		return novoConsole( Cultura.getPadrao() );
		
	}
	
	/**
	 * @param corTextoVocativo -1 == cor atual
	 */
	public void setCorTextoVocativo( int corTextoVocativo ) {
		this.corTextoVocativo = corTextoVocativo;
	}
	
	public final void setCorTextoVocativo( Cor cor ) {
		setCorTextoVocativo( cor.getCor() );
	}
	
	public int getCorTextoVocativo() {
		return corTextoVocativo;
	}
	
	private void enviarVocativo( String vocativo ) {
		
		if( corTextoVocativo != -1 ){
			
			int corTexto = getCorTexto();
			setCorTexto( corTextoVocativo );
			enviar( vocativo );
			setCorTexto( corTexto );
			
		}else{
			
			enviar( vocativo );
			
		}
		
	}
	
	/**
	 * @param vocativo Texto que precede {@link #receber()}.
	 */
	public String receber( String vocativo ) throws IOException {
		
		enviarVocativo( vocativo );
		return receber();
			
	}
	
	public Double receberReal( NumeroTransformacao realTransformacao ) throws IOException, TransformacaoException {
		
		String str = receber();
		
		if( str.length() == 0 ) return null;
		
		return realTransformacao.transformarReal( str );
		
	}
	
	public Double receberReal() throws IOException, TransformacaoException {
		return receberReal( realTransformacao );
	}
	
	/**
	 * @param vocativo Texto que precede {@link #receberReal()}.
	 * @param mensagemErro Mensagem a ser mostrada caso o valor seja inválido. null == "Valor incorreto."
	 * @return um {@link Double} ou <code>null</code>
	 */
	public Double receberReal( String vocativo, String mensagemErro ) throws IOException {
		
		while( true ){
			
			try{
				
				enviarVocativo( vocativo );
				return receberReal();
				
			}catch( TransformacaoException e ){
				
				int corTexto = getCorTexto();
				setCorTexto( COR_VERMELHA_INTENSA );
				enviarln( mensagemErro != null ? mensagemErro : "Valor incorreto." );
				setCorTexto( corTexto );
				
			}
			
		}
			
	}
	
	public Long receberInteiro( NumeroTransformacao inteiroTransformacao ) throws IOException, TransformacaoException {
		
		String str = receber();
		
		if( str.length() == 0 ) return null;
		
		return inteiroTransformacao.transformarInteiro( str );
		
	}
	
	public Long receberInteiro() throws IOException, TransformacaoException {
		return receberInteiro( inteiroTransformacao );
	}
	
	/**
	 * @param vocativo Texto que precede {@link #receberInteiro()}.
	 * @param mensagemErro Mensagem a ser mostrada caso o valor seja inválido. null == "Valor incorreto."
	 * @return um {@link Long} ou <code>null</code>
	 */
	public Long receberInteiro( String vocativo, String mensagemErro ) throws IOException {
		
		while( true ){
			
			try{
				
				enviarVocativo( vocativo );
				return receberInteiro();
				
			}catch( TransformacaoException e ){
				
				int corTexto = getCorTexto();
				setCorTexto( COR_VERMELHA_INTENSA );
				enviarln( mensagemErro != null ? mensagemErro : "Valor incorreto." );
				setCorTexto( corTexto );
				
			}
			
		}
			
	}
	
	public Date receberData( DataTransformacao dataTransformacao ) throws IOException, TransformacaoException {
		
		String str = receber();
		
		if( str.length() == 0 ) return null;
		
		return dataTransformacao.transformarData( str );
		
	}
	
	public Date receberData() throws IOException, TransformacaoException {
		return receberData( dataTransformacao );
	}
	
	/**
	 * @param vocativo Texto que precede {@link #receberData()}.
	 * @param mensagemErro Mensagem a ser mostrada caso o valor seja inválido. null == "Valor incorreto."
	 * @return um {@link Date} ou <code>null</code>
	 */
	public Date receberData( String vocativo, String mensagemErro ) throws IOException {
		
		while( true ){
			
			try{
				
				enviarVocativo( vocativo );
				return receberData();
				
			}catch( TransformacaoException e ){
				
				int corTexto = getCorTexto();
				setCorTexto( COR_VERMELHA_INTENSA );
				enviarln( mensagemErro != null ? mensagemErro : "Valor incorreto." );
				setCorTexto( corTexto );
				
			}
			
		}
			
	}
	
	public Date receberHora( DataTransformacao dataTransformacao ) throws IOException, TransformacaoException {
		
		String str = receber();
		
		if( str.length() == 0 ) return null;
		
		return dataTransformacao.transformarHora( str );
		
	}
	
	public Date receberHora() throws IOException, TransformacaoException {
		return receberHora( dataTransformacao );
	}
	
	/**
	 * @param vocativo Texto que precede {@link #receberHora()}.
	 * @param mensagemErro Mensagem a ser mostrada caso o valor seja inválido. null == "Valor incorreto."
	 * @return um {@link Date} ou <code>null</code>
	 */
	public Date receberHora( String vocativo, String mensagemErro ) throws IOException {
		
		while( true ){
			
			try{
				
				enviarVocativo( vocativo );
				return receberHora();
				
			}catch( TransformacaoException e ){
				
				int corTexto = getCorTexto();
				setCorTexto( COR_VERMELHA_INTENSA );
				enviarln( mensagemErro != null ? mensagemErro : "Valor incorreto." );
				setCorTexto( corTexto );
				
			}
			
		}
			
	}
	
	public Date receberDataHora( DataTransformacao dataTransformacao ) throws IOException, TransformacaoException {
		
		String str = receber();
		
		if( str.length() == 0 ) return null;
		
		return dataTransformacao.transformarDataHora( str );
		
	}
	
	public Date receberDataHora() throws IOException, TransformacaoException {
		return receberDataHora( dataTransformacao );
	}
	
	/**
	 * @param vocativo Texto que precede {@link #receberDataHora()}.
	 * @param mensagemErro Mensagem a ser mostrada caso o valor seja inválido. null == "Valor incorreto."
	 * @return um {@link Date} ou <code>null</code>
	 */
	public Date receberDataHora( String vocativo, String mensagemErro ) throws IOException {
		
		while( true ){
			
			try{
				
				enviarVocativo( vocativo );
				return receberDataHora();
				
			}catch( TransformacaoException e ){
				
				int corTexto = getCorTexto();
				setCorTexto( COR_VERMELHA_INTENSA );
				enviarln( mensagemErro != null ? mensagemErro : "Valor incorreto." );
				setCorTexto( corTexto );
				
			}
			
		}
			
	}
	
	/**
	 * Armazena as cores atuais numa pilha.<br>
	 * Cores: {@link #getCorTexto()}, {@link #getCorTextoFundo()} e {@link #getCorTextoVocativo()}.
	 */
	public void guardarCores() {
		pilhaCores.push( new Cores() );
	}
	
	/**
	 * 1) {@link #guardarCores()}<br>
	 * 2) {@link #setCorTexto(int)}<br>
	 * 3) {@link #setCorTextoFundo(int)}
	 */
	public void guardarCores( int novaCorTexto, int novaCorTextoFundo ) {
		guardarCores();
		setCorTexto( novaCorTexto );
		setCorTextoFundo( novaCorTextoFundo );
	}
	
	/**
	 * 1) {@link #guardarCores()}<br>
	 * 2) {@link #setCorTexto(int)}
	 */
	public void guardarCores( int novaCorTexto ) {
		guardarCores();
		setCorTexto( novaCorTexto );
	}
	
	public final void guardarCores( Cor novaCorTexto, Cor novaCorTextoFundo ) {
		guardarCores( novaCorTexto.getCor(), novaCorTextoFundo.getCor() );
	}
	
	public final void guardarCores( Cor novaCorTexto ) {
		guardarCores( novaCorTexto.getCor() );
	}
	
	/**
	 * Restaura as cores armazenas no topo da pilha de cores.
	 * @see #guardarCores()
	 */
	public void restaurarCores() {
		pilhaCores.pop().restaurar();
	}
	
	private class Cores {
		
		private int corTexto;
		private int corTextoFundo;
		private int corTextoVocativo;
		
		public Cores() {
			this.corTexto = getCorTexto();
			this.corTextoFundo = getCorTextoFundo();
			this.corTextoVocativo = getCorTextoVocativo();
		}
		
		public void restaurar() {
			setCorTexto( corTexto );
			setCorTextoFundo( corTextoFundo );
			setCorTextoVocativo( corTextoVocativo );
		}
		
	}
	
}
