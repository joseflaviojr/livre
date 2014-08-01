
/*
 *  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
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

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Teste extends AplicacaoConsole {
	
	@Override
	protected Argumentos processarArgumentos( String[] args ) {
		return new SemArgumentos( args );
	}
	
	@Override
	protected void principal() {
		
		try{
			
			limpar( Cor.BRANCA );
			setCorTexto( Console.COR_PRETA );
			
			enviarln( "(1)" );
			Thread.sleep( 500 );
			
			enviarln( "(2)" );
			Thread.sleep( 500 );
			
			enviarln( "(3)" );
			Thread.sleep( 500 );
			
			setTelaCheia( true );
			
			limpar( Cor.CINZA_INTENSA );
			setCorTexto( Cor.PRETA );
			
			enviarln();
			enviarln( "   Colunas: " + getTotalColunas() );
			enviarln( "   Linhas : " + getTotalLinhas() + "\n\n" );
			
			setCorTexto( Cor.PRETA );
			enviarln( "PRETA" );
			
			setCorTexto( Cor.AZUL );
			enviarln( "AZUL" );
			
			setCorTexto( Cor.VERDE );
			enviarln( "VERDE" );
			
			setCorTexto( Cor.CIANO );
			enviarln( "CIANO" );
			
			setCorTexto( Cor.VERMELHA );
			enviarln( "VERMELHA" );
			
			setCorTexto( Cor.MAGENTA );
			enviarln( "MAGENTA" );
			
			setCorTexto( Cor.MARRON );
			enviarln( "MARRON" );
			
			setCorTexto( Cor.CINZA_INTENSA );
			enviarln( "CINZA INTENSA" );
			
			setCorTexto( Cor.CINZA_ESCURA );
			enviarln( "CINZA ESCURA" );
			
			setCorTexto( Cor.AZUL_INTENSA );
			enviarln( "AZUL INTENSA" );
			
			setCorTexto( Cor.VERDE_INTENSA );
			enviarln( "VERDE INTENSA" );
			
			setCorTexto( Cor.CIANO_INTENSA );
			enviarln( "CIANO INTENSA" );
			
			setCorTexto( Cor.VERMELHA_INTENSA );
			enviarln( "VERMELHA INTENSA" );
			
			setCorTexto( Cor.MAGENTA_INTENSA );
			enviarln( "MAGENTA INTENSA" );
			
			setCorTexto( Cor.AMARELA );
			enviarln( "AMARELA" );
			
			setCorTexto( Cor.BRANCA );
			enviarln( "BRANCA" );
			
			esperar( false );
			
		}catch( Exception e ){
			e.printStackTrace();
		}

	}
	
	@Override
	protected void fim() {

		setTelaCheia( false );
		limpar();
		
	}
	
	public static void main( String[] args ) {
		new Teste().inicio( args );
	}

}
