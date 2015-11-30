
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

package com.joseflavio.tqc.console;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.joseflavio.cultura.Cultura;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class NativoConsole extends GenericoConsole {
	
	private static boolean normal = false;
	
	/**
	 * @throws IOException Falha na carga da biblioteca nativa.
	 */
	public NativoConsole( Cultura cultura ) throws IOException {

		super( cultura );
		
		if( ! normal ){

			try{
				
				boolean windows = System.getProperty( "os.name" ).toLowerCase().contains( "windows" );
				boolean x64 = System.getProperty( "os.arch" ).toLowerCase().contains( "64" );
				
				String nome = NativoConsole.class.getName().replaceAll( "\\.", "_" ) + ( x64 ? "_64" : "_32" ) + ( windows ? ".dll" : ".so" );
				File arquivo = new File( System.getProperty( "user.home" ) + File.separator + nome );
				
				if( ! arquivo.exists() ){
					arquivo.createNewFile();
					InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream( nome );
					OutputStream out = new FileOutputStream( arquivo );
					int ch;
					while( ( ch = in.read() ) != -1 ) out.write( ch );
					in.close();
					out.flush();
					out.close();
				}
				
				System.load( arquivo.getAbsolutePath() );
				
				normal = true;
			
			}catch( IOException e ){
				throw e;
			}catch( Exception e ){
				throw new IOException( e.getMessage() );
			}
				
		}
		
	}
	
	public native int getTotalColunas();
	
	public native int getTotalLinhas();
	
	public native void setCorTexto( int cor );
	
	public native int getCorTexto();
	
	public native void setCorTextoFundo( int cor );
	
	public native int getCorTextoFundo();
	
	public native void limpar();
	
	public native char esperar( boolean mostrar ) throws IOException;
	
	public native void setTelaCheia( boolean cheia );
	
}
