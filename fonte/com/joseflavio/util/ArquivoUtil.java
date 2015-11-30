
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

package com.joseflavio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.regex.Pattern;

/**
 * Utilitários para {@link File arquivos}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2015
 */
public class ArquivoUtil {
	
	/**
	 * @param endereco "/caminho/nome.extensao"
	 */
	public static String getNome( String endereco ) {
		
		int i = endereco.length() - 1;
		char ch = endereco.charAt( i );
		if( ch == '\\' || ch == '/' ) endereco = endereco.substring( 0, i );

		i = endereco.lastIndexOf( '\\' );
		if( i == -1 ) i = endereco.lastIndexOf( '/' );
		if( i != -1 ) endereco = endereco.substring( i + 1 );
		
		return endereco;
		
	}
	
	/**
	 * @param endereco "/caminho/nome.extensao"
	 * @deprecated Erro ortográfico.
	 * @see #getExtensao(String)
	 */
	public static String getExtencao( String endereco ) {
		return getExtensao( endereco );
	}
	
	/**
	 * @param endereco "/caminho/nome.extensao"
	 */
	public static String getExtensao( String endereco ) {
		
		int i = endereco.length() - 1;
		char ch = endereco.charAt( i );
		if( ch == '\\' || ch == '/' || ch == '.' ) endereco = endereco.substring( 0, i );

		i = endereco.lastIndexOf( '.' );
		return i != -1 ? endereco.substring( i + 1 ) : "";
		
	}
	
	/**
	 * @param endereco {@link File}
	 */
	public static String getHTMLContentType( String endereco ) {
		
		String ext = getExtensao( endereco ).toLowerCase();
		
		if( ext.equals( "doc" ) || ext.equals( "docx" ) ) return "application/msword";
		if( ext.equals( "pdf" ) ) return "application/pdf";
		if( ext.equals( "ai" ) || ext.equals( "ps" ) || ext.equals( "eps" ) ) return "application/postscript";
		if( ext.equals( "rtf" ) ) return "application/rtf";
		if( ext.equals( "xls" ) || ext.equals( "xlsx" ) ) return "application/vnd.ms-excel";
		if( ext.equals( "pps" ) || ext.equals( "ppsx" ) || ext.equals( "ppt" ) || ext.equals( "pptx" ) ) return "application/vnd.ms-powerpoint";
		if( ext.equals( "swf" ) ) return "application/x-shockwave-flash";
		if( ext.equals( "tar" ) ) return "application/x-tar";
		if( ext.equals( "zip" ) ) return "application/zip";
		if( ext.equals( "mid" ) ) return "audio/mid";
		if( ext.equals( "mp3" ) ) return "audio/mpeg";
		if( ext.equals( "aif" ) || ext.equals( "aifc" ) || ext.equals( "aiff" ) ) return "audio/x-aiff";
		if( ext.equals( "m3u" ) ) return "audio/x-mpegurl";
		if( ext.equals( "wav" ) ) return "audio/x-wav";
		if( ext.equals( "bmp" ) ) return "image/bmp";
		if( ext.equals( "gif" ) ) return "image/gif";
		if( ext.equals( "jpg" ) || ext.equals( "jpeg" ) || ext.equals( "jpe" ) ) return "image/jpeg";
		if( ext.equals( "svg" ) ) return "image/svg+xml";
		if( ext.equals( "tif" ) || ext.equals( "tiff" ) ) return "image/tiff";
		if( ext.equals( "ico" ) ) return "image/x-icon";
		if( ext.equals( "mht" ) || ext.equals( "mhtml" ) || ext.equals( "nws" ) ) return "message/rfc822";
		if( ext.equals( "css" ) ) return "text/css";
		if( ext.equals( "html" ) || ext.equals( "htm" ) || ext.equals( "stm" ) ) return "text/html";
		if( ext.equals( "txt" ) ) return "text/plain";
		if( ext.equals( "mp2" ) || ext.equals( "mpa" ) || ext.equals( "mpe" ) || ext.equals( "mpeg" ) || ext.equals( "mpg" ) || ext.equals( "mpv2" ) ) return "video/mpeg";
		if( ext.equals( "mov" ) || ext.equals( "qt" ) ) return "video/quicktime";
		if( ext.equals( "asf" ) || ext.equals( "asr" ) || ext.equals( "asx" ) ) return "video/x-ms-asf";
		if( ext.equals( "avi" ) ) return "video/x-msvideo";
		if( ext.equals( "movie" ) ) return "video/x-sgi-movie";
		if( ext.equals( "wma" ) ) return "audio/x-ms-wma";
		if( ext.equals( "wmv" ) ) return "video/x-ms-wmv";
		
		return "application/octet-stream";
		
	}
	
	/**
	 * Transfere o conteúdo de um {@link InputStream} para um {@link OutputStream}.<br>
	 * Os streams não serão fechados.
	 */
	public static void transferir( InputStream origem, OutputStream destino ) throws IOException {
		int b;
		while( ( b = origem.read() ) != -1 ){
			destino.write( b );
		}
	}
	
	/**
	 * Realiza {@link Acao} em {@link File arquivos} de um diretório.
	 * @param diretorio Ponto de partida da navegação.
	 * @param recursivo {@link #navegar(File, boolean, Filtro, Acao) Navegar} pelos subdiretórios?
	 * @param filtro {@link Filtro} que seleciona os arquivos/diretórios que serão submetidos à {@link Acao}. Opcional.
	 * @throws IOException se a {@link Acao} falhar.
	 */
	public static void navegar( File diretorio, boolean recursivo, Filtro<File> filtro, Acao<File> acao ) throws IOException {
		
		if( diretorio == null || ! diretorio.isDirectory() ) throw new IllegalArgumentException( "diretorio" );
		if( acao == null ) throw new IllegalArgumentException( "acao" );
		
		try{
			for( File x : diretorio.listFiles() ){
				if( filtro != null && ! filtro.aceitar( x ) ) continue;
				acao.acao( x );
				if( recursivo && x.isDirectory() ){
					navegar( x, recursivo, filtro, acao );
				}
			}
		}catch( IOException e ){
			throw e;
		}catch( Exception e ){
			throw new IOException( e );
		}
		
	}

	/**
	 * Verifica se os diretórios possuem a mesma estrutura e os mesmos arquivos (conteúdos).
	 * @param filtro {@link Filtro} que seleciona os arquivos/diretórios que serão submetidos à comparação. Opcional.
	 * @param acao {@link Acao} para cada par de arquivos diferentes. Arquivos inexistentes serão passados como <code>null</code>.
	 */
	public static void compararDiretorios( final File dir1, final File dir2, boolean recursivo, final Filtro<File> filtro, final Acao<Par<File,File>> acao ) throws IOException {
		
		if( dir1 == null || ! dir1.isDirectory() ) throw new IllegalArgumentException( "dir1" );
		if( dir2 == null || ! dir2.isDirectory() ) throw new IllegalArgumentException( "dir2" );
		if( acao == null ) throw new IllegalArgumentException( "acao" );
		
		if( dir1.equals( dir2 ) ) return;
		
		final String p1 = dir1.getAbsolutePath();
		final String p2 = dir2.getAbsolutePath();
		
		Acao<File> verificador1 = new Acao<File>() {
			public void acao( File arq1 ) throws Exception {
				File arq2 = new File( p2 + arq1.getAbsolutePath().substring( p1.length() ) );
				if( ! arq2.exists() ){
					acao.acao( new Par<File,File>( arq1, null ) );
				}else if( arq1.isFile() && arq2.isFile() && ! arquivosIdenticos( arq1, arq2 ) ){
					acao.acao( new Par<File,File>( arq1, arq2 ) );
				}
			}
		};
		
		Acao<File> verificador2 = new Acao<File>() {
			public void acao( File arq2 ) throws Exception {
				File arq1 = new File( p1 + arq2.getAbsolutePath().substring( p2.length() ) );
				if( ! arq1.exists() ){
					acao.acao( new Par<File,File>( null, arq2 ) );
				}
			}
		};
		
		navegar( dir1, recursivo, filtro, verificador1 );
		navegar( dir2, recursivo, filtro, verificador2 );
		
	}
	
	/**
	 * Verifica se os dois arquivos são idênticos, byte a byte.
	 */
	public static boolean arquivosIdenticos( File arq1, File arq2 ) throws IOException {
		
		if( arq1 == null || ! arq1.isFile() ) throw new IllegalArgumentException( "arq1" );
		if( arq2 == null || ! arq2.isFile() ) throw new IllegalArgumentException( "arq2" );
		
		if( arq1.equals( arq2 ) ) return true;
		
		FileInputStream fis1 = null;
		FileInputStream fis2 = null;
		
		try{
			
			fis1 = new FileInputStream( arq1 );
			fis2 = new FileInputStream( arq2 );
		
			int b1, b2;
			while( true ){
				
				b1 = fis1.read();
				b2 = fis2.read();
				
				if( b1 == b2 ){
					if( b1 == -1 ) break;
				}else{
					return false;
				}
				
			}
			
			return true;
			
		}finally{
			if( fis1 != null ) fis1.close();
			if( fis2 != null ) fis2.close();
		}
		
	}
	
	/**
	 * @see #arquivosQueContem(String, boolean, File, boolean, Filtro, Acao, boolean, StringBuilder)
	 */
	public static void arquivosQueContem( final String conteudo, final boolean expressaoRegular, final File diretorio, final boolean recursivo, final Filtro<File> filtro, final Acao<File> acao, final StringBuilder buffer ) throws IOException {
		arquivosQueContem( conteudo, expressaoRegular, diretorio, recursivo, filtro, acao, false, buffer );
	}
	
	/**
	 * @see #arquivosQueContem(String, boolean, File, boolean, Filtro, Acao, boolean, StringBuilder)
	 */
	public static void arquivosQueNaoContem( final String conteudo, final boolean expressaoRegular, final File diretorio, final boolean recursivo, final Filtro<File> filtro, final Acao<File> acao, final StringBuilder buffer ) throws IOException {
		arquivosQueContem( conteudo, expressaoRegular, diretorio, recursivo, filtro, acao, true, buffer );
	}
	
	/**
	 * Busca por {@link File arquivos} que contenham um específico conteúdo.
	 * @param conteudo Conteúdo desejado, podendo ser um {@link Pattern}.
	 * @param expressaoRegular O conteúdo consiste em {@link Pattern}?
	 * @param diretorio Ponto de partida da busca.
	 * @param recursivo {@link #navegar(File, boolean, Filtro, Acao) Navegar} pelos subdiretórios?
	 * @param filtro {@link Filtro} que seleciona os arquivos/diretórios que serão verificados. Opcional.
	 * @param acao {@link Acao} que será realizada para cada arquivo que contenha o conteúdo desejado.
	 * @param negacao Inverter a lógica da busca? Isto é, buscar pelos {@link File arquivos} que NÃO contenham o conteúdo especificado?
	 * @param buffer {@link StringBuilder} que receberá temporariamente o conteúdo de cada arquivo. Opcional.
	 */
	public static void arquivosQueContem( final String conteudo, final boolean expressaoRegular, final File diretorio, final boolean recursivo, final Filtro<File> filtro, final Acao<File> acao, final boolean negacao, final StringBuilder buffer ) throws IOException {

		if( conteudo == null || conteudo.length() == 0 ) throw new IllegalArgumentException( "conteudo" );
		if( diretorio == null || ! diretorio.isDirectory() ) throw new IllegalArgumentException( "diretorio" );
		if( acao == null ) throw new IllegalArgumentException( "acao" );
		
		final Pattern padrao = expressaoRegular ? Pattern.compile( conteudo ) : null;
		
		Acao<File> verificador = new Acao<File>() {
			public void acao( File o ) throws Exception {
				if( ! o.isFile() ) return;
				if( padrao == null ){
					if( arquivoContem( conteudo, o, negacao, buffer ) ) acao.acao( o );
				}else{
					if( arquivoContem( padrao, o, negacao, buffer ) ) acao.acao( o );
				}
			}
		};
		
		navegar( diretorio, recursivo, filtro, verificador );
		
	}
	
	/**
	 * @see #arquivoContem(String, File, boolean, StringBuilder)
	 */
	public static boolean arquivoContem( String conteudo, File arquivo, StringBuilder buffer ) throws IOException {
		return arquivoContem( conteudo, arquivo, false, buffer );
	}
	
	/**
	 * @see #arquivoContem(String, File, boolean, StringBuilder)
	 */
	public static boolean arquivoNaoContem( String conteudo, File arquivo, StringBuilder buffer ) throws IOException {
		return arquivoContem( conteudo, arquivo, true, buffer );
	}
	
	/**
	 * Verifica se o {@link File arquivo} contém um específico conteúdo.
	 * @param conteudo Conteúdo desejado.
	 * @param negacao Inverter a lógica da verificação? Isto é, verificar se o {@link File arquivo} NÃO contém o conteúdo especificado?
	 * @param buffer {@link StringBuilder} que receberá temporariamente o conteúdo do arquivo. Opcional.
	 */
	public static boolean arquivoContem( String conteudo, File arquivo, boolean negacao, StringBuilder buffer ) throws IOException {
		
		if( conteudo == null || conteudo.length() == 0 ) throw new IllegalArgumentException( "conteudo" );
		if( arquivo == null || ! arquivo.isFile() ) throw new IllegalArgumentException( "arquivo" );

		if( buffer == null ) buffer = new StringBuilder( 1024 * 50 );
		
		return ( carregarConteudo( arquivo, buffer ).indexOf( conteudo ) >= 0 ) ^ negacao;
		
	}
	
	/**
	 * @see #arquivoContem(Pattern, File, boolean, StringBuilder)
	 */
	public static boolean arquivoContem( Pattern padrao, File arquivo, StringBuilder buffer ) throws IOException {
		return arquivoContem( padrao, arquivo, false, buffer );
	}
	
	/**
	 * @see #arquivoContem(Pattern, File, boolean, StringBuilder)
	 */
	public static boolean arquivoNaoContem( Pattern padrao, File arquivo, StringBuilder buffer ) throws IOException {
		return arquivoContem( padrao, arquivo, true, buffer );
	}
	
	/**
	 * Verifica se o conteúdo do {@link File arquivo} possui o {@link Pattern padrão} de uma expressão regular.
	 * @param padrao {@link Pattern Padrão} de expressão regular.
	 * @param negacao Inverter a lógica da verificação? Isto é, verificar se o {@link File arquivo} NÃO contém o conteúdo especificado?
	 * @param buffer {@link StringBuilder} que receberá temporariamente o conteúdo do arquivo. Opcional.
	 */
	public static boolean arquivoContem( Pattern padrao, File arquivo, boolean negacao, StringBuilder buffer ) throws IOException {
		
		if( padrao == null ) throw new IllegalArgumentException( "padrao" );
		if( arquivo == null || ! arquivo.isFile() ) throw new IllegalArgumentException( "arquivo" );
		
		if( buffer == null ) buffer = new StringBuilder( 1024 * 50 );
		
		return padrao.matcher( carregarConteudo( arquivo, buffer ) ).matches() ^ negacao;
		
	}
	
	/**
	 * Carrega todo o conteúdo de um {@link File arquivo} num {@link StringBuilder}.<br>
	 * O {@link StringBuilder} será limpo, primeiramente.
	 * @return o {@link StringBuilder} especificado.
	 */
	public static StringBuilder carregarConteudo( File arquivo, StringBuilder destino ) throws IOException {
		
		if( arquivo == null || ! arquivo.isFile() ) throw new IllegalArgumentException( "arquivo" );
		if( destino == null ) throw new IllegalArgumentException( "destino" );
		
		destino.delete( 0, destino.length() );
		
		Reader br = null;
		try{
			br = new FileReader( arquivo );
			int c;
			while( ( c = br.read() ) != -1 ) destino.append( (char) c );
		}finally{
			if( br != null ){
				br.close();
				br = null;
			}
		}
		
		return destino;
		
	}
	
	//TODO Detectar codificação de arquivo.
	
}
