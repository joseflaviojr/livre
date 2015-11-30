
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 * Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 * sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 * (a seu crit�rio) qualquer vers�o posterior.
 * 
 * Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 * por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 * COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 * Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 * junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

/**
 * Utilit�rios para arquivos CSV.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class CSVUtil {
	
	/**
	 * Reconhece as colunas da pr�xima linha.
	 * @param arquivo Arquivo CSV.
	 * @param colunas Array que receber� o conte�do de cada coluna.
	 * @param separador Separador de colunas.
	 * @param buffer Buffer a ser utilizado durante o processamento da linha.
	 * @return o n�mero de colunas lidas.
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
	 * Retorna o valor da pr�xima coluna.
	 * @param arquivo Arquivo CSV.
	 * @param separador Separador de colunas.
	 * @param retirarAspas Retira as aspas que possivelmente estejam envolvendo o valor a ser retornado.
	 * @param buffer Buffer que receber� o conte�do da coluna. Observa��o: o buffer ser� limpado antes do procedimento.
	 * @return <code>null</code>, caso o fim de arquivo tenha sido alcan�ado.
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
