
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

package com.joseflavio.tqc.console;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.joseflavio.cultura.Cultura;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
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
