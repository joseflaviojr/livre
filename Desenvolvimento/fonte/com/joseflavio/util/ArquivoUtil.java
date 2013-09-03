
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

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ArquivoUtil {
	
	/**
	 * @return aaaaaa.bbb
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
	
	public static String getExtencao( String endereco ) {
		
		int i = endereco.length() - 1;
		char ch = endereco.charAt( i );
		if( ch == '\\' || ch == '/' || ch == '.' ) endereco = endereco.substring( 0, i );

		i = endereco.lastIndexOf( '.' );
		return i != -1 ? endereco.substring( i + 1 ) : "";
		
	}
	
	public static String getHTMLContentType( String endereco ) {
		
		String ext = getExtencao( endereco ).toLowerCase();
		
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

}
