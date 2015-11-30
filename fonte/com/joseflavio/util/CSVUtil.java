
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

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

/**
 * Utilitários para arquivos CSV.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class CSVUtil {
	
	/**
	 * Reconhece as colunas da próxima linha.
	 * @param arquivo Arquivo CSV.
	 * @param colunas Array que receberá o conteúdo de cada coluna.
	 * @param separador Separador de colunas.
	 * @param buffer Buffer a ser utilizado durante o processamento da linha.
	 * @return o número de colunas lidas.
	 */
	public static int proximaLinha( Reader arquivo, String[] colunas, char separador, StringBuilder buffer ) throws IOException {
		
		int len = buffer.length();
		if( len > 0 ) buffer.delete( 0, len );
		
		Arrays.fill( colunas, null );
		
		char ch;
		int c = 0;
		final char FDA = (char) -1;
		
		while( ( ch = (char) arquivo.read() ) != FDA ){
			
			if( ch == separador ){
				colunas[c++] = buffer.toString();
				buffer.delete( 0, buffer.length() );
			}else if( ch == '\n' ){
				colunas[c++] = buffer.toString();
				return c;
			}else if( ch == '\r' ){
				continue;
			}else{
				buffer.append( ch );
			}
			
		}
		
		if( c > 0 || buffer.length() > 0 ) colunas[c++] = buffer.toString();
		return c;
		
	}
	
	/**
	 * Retorna o valor da próxima coluna.
	 * @param arquivo Arquivo CSV.
	 * @param separador Separador de colunas.
	 * @param retirarAspas Retira as aspas que possivelmente estejam envolvendo o valor a ser retornado.
	 * @param buffer Buffer que receberá o conteúdo da coluna. Observação: o buffer será limpado antes do procedimento.
	 * @return <code>null</code>, caso o fim de arquivo tenha sido alcançado.
	 */
	public static StringBuilder proximaColuna( Reader arquivo, char separador, boolean retirarAspas, StringBuilder buffer ) throws IOException {
		
		int len = buffer.length();
		if( len > 0 ) buffer.delete( 0, len );
		
		int ch;
		while( ( ( ch = arquivo.read() ) != -1 ) && ch != separador && ch != '\n' ){
			if( ch == '\r' ) continue;
			buffer.append( (char) ch );
		}
		
		if( retirarAspas ){
			len = buffer.length();
			if( len >= 2 ){
				char p = buffer.charAt( 0 );
				if( p == '\'' || p == '"' ){
					buffer.deleteCharAt( len - 1 );
					buffer.deleteCharAt( 0 );
				}
			}
		}
		
		return ch != -1 ? buffer : null;
		
	}

}
