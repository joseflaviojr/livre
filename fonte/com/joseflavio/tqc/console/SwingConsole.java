
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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.joseflavio.cultura.Cultura;

/**
 * {@link Console} simulado em {@link JFrame}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class SwingConsole extends Console { //TODO Revisar sincronização

	private Caractere[][] cadeia;
	
	private int linhas;
	
	private int colunas;
	
	private JFrame janela;
	
	private Fundo fundo;
	
	private Font fonte;
	
	private int fonteLargura;
	
	private int fonteAltura;
	
	private int fonteDesc;
	
	private Cor corTexto = Cor.BRANCA;
	
	private Cor corTextoFundo = Cor.PRETA;

	private Entrada entrada = new Entrada();
	
	private int cursorL = 0;
	
	private int cursorC = 0;
	
	/**
	 * @param titulo {@link JFrame#getTitle()}
	 * @param tamanhoFonte {@link Font#getSize()}
	 * @param sairAoFechar {@link System#exit(int)} em {@link WindowListener#windowClosing(java.awt.event.WindowEvent)}.
	 */
	public SwingConsole( Cultura cultura, String titulo, int tamanhoFonte, int linhas, int colunas, boolean sairAoFechar ) {
		
		super( cultura );
		
		this.cadeia = new Caractere[linhas][colunas];
		this.linhas = linhas;
		this.colunas = colunas;
		this.janela = new JFrame( titulo );
		this.fonte = new Font( Font.MONOSPACED, Font.BOLD, tamanhoFonte );
		
		FontMetrics metrica = janela.getFontMetrics( fonte );
		this.fonteLargura = metrica.charWidth( 'O' ) + 1;
		this.fonteAltura = metrica.getMaxAscent() + metrica.getMaxDescent();
		this.fonteDesc = metrica.getMaxDescent();

		this.fundo = new Fundo( colunas * fonteLargura, linhas * fonteAltura );
		
		for( int i = 0; i < linhas; i++ ) for( int j = 0; j < colunas; j++ ) cadeia[i][j] = new Caractere();
		
		janela.getContentPane().add( new JScrollPane( fundo ) );
		janela.addKeyListener( entrada );
		janela.setDefaultCloseOperation( sairAoFechar ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE );
		janela.pack();
		janela.setVisible( true );
		
	}
	
	public SwingConsole( Cultura cultura, String titulo, int tamanhoFonte, int linhas, int colunas ) {
		this( cultura, titulo, tamanhoFonte, linhas, colunas, true );
	}
	
	public SwingConsole( Cultura cultura, String titulo ) {
		this( cultura, titulo, 12, 30, 80, true );
	}
	
	public SwingConsole( String titulo, boolean sairAoFechar ) {
		this( Cultura.getPadrao(), titulo, 12, 30, 80, sairAoFechar );
	}
	
	public SwingConsole( String titulo ) {
		this( Cultura.getPadrao(), titulo, 12, 30, 80, true );
	}
	
	public JFrame getJanela() {
		return janela;
	}
	
	@Override
	public int getTotalColunas() {
		return colunas;
	}

	@Override
	public int getTotalLinhas() {
		return linhas;
	}

	@Override
	public synchronized void setCorTexto( int cor ) {
		this.corTexto = Cor.getCor( cor );
	}

	@Override
	public synchronized int getCorTexto() {
		return corTexto.getCor();
	}

	@Override
	public synchronized void setCorTextoFundo( int cor ) {
		this.corTextoFundo = Cor.getCor( cor );
	}

	@Override
	public synchronized int getCorTextoFundo() {
		return corTextoFundo.getCor();
	}

	@Override
	public synchronized void limpar() {
		for( int i = 0; i < linhas; i++ ){
			for( int j = 0; j < colunas; j++ ){
				cadeia[i][j].limpar();
			}
		}
		cursorL = cursorC = 0;
		fundo.repintar();
	}

	@Override
	public char esperar( boolean mostrar ) throws IOException {
		return entrada.esperar( mostrar );
	}

	@Override
	public synchronized void setTelaCheia( boolean cheia ) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow( cheia ? janela : null );
	}

	@Override
	public String receber() throws IOException {
		return entrada.receber();
	}

	@Override
	public synchronized void enviar( String texto ) {
		
		int total = texto.length();
		char ch;
		Caractere c;
		
		for( int i = 0; i < total; i++ ){
			
			ch = texto.charAt( i );
			
			if( ch == '\n' ){
				saltarUmaLinha();
				continue;
			}
			
			c = cadeia[cursorL][cursorC];
			c.letra = ch;
			c.corLetra = corTexto;
			c.corFundo = corTextoFundo;
			
			avancarCursor();
			
		}
		
		fundo.repintar();
		
	}

	@Override
	public void enviarln( String texto ) {
		enviar( texto + "\n" );
	}

	@Override
	public void enviarln() {
		enviar( "\n" );
	}

	@Override
	public void enviar( String formato, Object... args ) {
		Formatter formatter = new Formatter();
		enviar( formatter.format( formato, args ).toString() );
		formatter.close();
	}
	
	private void deslocarLinhasParaCima() {
		
		int i, j;
		
		for( i = 0; i < (linhas-1); i++ ){
			for( j = 0; j < colunas; j++ ){
				cadeia[i][j].copiar( cadeia[i+1][j] );
			}
		}
		
		i = linhas - 1;
		for( j = 0; j < colunas; j++ ){
			cadeia[i][j].limpar();
		}
		
	}
	
	private void avancarCursor() {
		cursorC++;
		if( cursorC == colunas ) saltarUmaLinha();
	}
	
	private void recuarCursor() {
		if( cursorC == 0 && cursorL == 0 ) return;
		cursorC--;
		if( cursorC == -1 ){
			cursorC = colunas - 1;
			cursorL--;
		}
	}
	
	private void saltarUmaLinha() {
		cursorL++;
		cursorC = 0;
		if( cursorL == linhas ){
			deslocarLinhasParaCima();
			cursorL--;
		}
	}
	
	/**
	 * Desloca 1 casa para frente todos os {@link Caractere}s a partir do cursor, inclusive.
	 */
	private void deslocarCursor() {
		
		int i, j;
		
		for( i = linhas - 1; i > cursorL; i-- ){
			for( j = colunas - 1; j > 0; j-- ){
				cadeia[i][j].copiar( cadeia[i][j-1] );
			}
			cadeia[i][0].copiar( cadeia[i-1][colunas-1] );
		}
		
		for( i = cursorL, j = colunas - 1; j > cursorC; j-- ){
			cadeia[i][j].copiar( cadeia[i][j-1] );
		}
		
		cadeia[cursorL][cursorC].letra = ' ';
		
	}
	
	/**
	 * Apaga o {@link Caractere} corrente, deslocando os posteriores para o seu lugar.
	 */
	private void apagarCursor() {

		for( int i = cursorL; i < linhas; i++ ){
			for( int j = i == cursorL ? cursorC : 0; j < ( colunas - 1 ); j++ ){
				cadeia[i][j].copiar( cadeia[i][j+1] );
			}
			if( i < ( linhas - 1 ) ) cadeia[i][colunas-1].copiar( cadeia[i+1][0] );
		}
		
		cadeia[linhas-1][colunas-1].letra = ' ';
		
	}
	
	private class Caractere {

		private char letra;
		
		private Cor corLetra;
		
		private Cor corFundo;

		public Caractere( char letra, Cor corLetra, Cor corFundo ) {
			this.letra    = letra;
			this.corLetra = corLetra;
			this.corFundo = corFundo;
		}
		
		public Caractere() {
			this( ' ', corTexto, corTextoFundo );
		}
		
		public void limpar() {
			letra    = ' ';
			corLetra = corTexto;
			corFundo = corTextoFundo;
		}
		
		public void copiar( Caractere c ) {
			letra    = c.letra;
			corLetra = c.corLetra;
			corFundo = c.corFundo;
		}
		
	}
	
	private class Fundo extends JPanel {
		
		private static final long serialVersionUID = 1L;

		private BufferedImage buffer;
		
		private Cursor cursor = Cursor.NAO_MOSTRAR;
		
		public Fundo( int largura, int altura ) {
			
			buffer = new BufferedImage( largura, altura, BufferedImage.TYPE_INT_RGB );
			
			Dimension dimensao = new Dimension( largura, altura );
			setPreferredSize( dimensao );
			setSize( dimensao );
			
		}
		
		private void pintar() {
			
			Graphics g = buffer.getGraphics();
			g.setFont( fonte );
			
			g.setColor( corTextoFundo.getColor() );
			g.fillRect( 0, 0, buffer.getWidth(), buffer.getHeight() );
			
			Caractere c;
			char[] letra = new char[1];
			
			for( int i = 0; i < linhas; i++ ){
				for( int j = 0; j < colunas; j++ ){
					
					c = cadeia[i][j];
					letra[0] = c.letra;
					
					g.setColor( c.corFundo.getColor() );
					g.fillRect( j * fonteLargura, i * fonteAltura, fonteLargura, fonteAltura );
					
					g.setColor( c.corLetra.getColor() );
					g.drawChars( letra, 0, 1, j * fonteLargura, ( (i+1) * fonteAltura ) - fonteDesc );
				
				}
			}
			
		}
		
		private void pintarApenasCursor() {
			
			Graphics g = buffer.getGraphics();
			g.setFont( fonte );
			
			Caractere c = cadeia[cursorL][cursorC];
			char[] letra = { c.letra };
			
			g.setColor( c.corFundo.getColor() );
			g.fillRect( cursorC * fonteLargura, cursorL * fonteAltura, fonteLargura, fonteAltura );
			
			g.setColor( c.corLetra.getColor() );
			g.drawChars( letra, 0, 1, cursorC * fonteLargura, ( (cursorL+1) * fonteAltura ) - fonteDesc );

			//Cursor
			
			int x = cursorC * fonteLargura;
			int y = cursorL * fonteAltura;
			g.setColor( corTexto.getColor() );

			switch( cursor ){
				case NORMAL :
					g.drawLine( x, y, x, y + fonteAltura - 1 );
					break;
				case SUBSTITUICAO :
					g.drawRect( x, y, fonteLargura - 1, fonteAltura - 1 );					
					break;
				default :
					break;
			}
			
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			g.setColor( corTextoFundo.getColor() );
			g.fillRect( 0, 0, getWidth(), getHeight() );
			g.drawImage( buffer, 0, 0, null );
		}
		
		private synchronized void repintar0( boolean apenasCursor ) {
			if( apenasCursor ) pintarApenasCursor();
			else pintar();
			repaint();
		}
		
		public void repintar() {
			repintar0( false );
		}
		
		public void repintarCursor() {
			repintar0( true );
		}

	}
	
	private class Entrada extends KeyAdapter {
		
		private StringBuilder buffer = new StringBuilder( 50 );
		
		private int bufferIndice;
		
		private boolean iniciada = false;
		
		private boolean finalizada;
		
		private boolean capturarApenas1 = false;
		
		private boolean insert;
		
		private Timer timerCursor;
		
		@Override
		public void keyPressed( KeyEvent e ) {
			
			if( ! iniciada ) return;
			
			switch( e.getKeyCode() ){
			
				case KeyEvent.VK_LEFT :
					if( bufferIndice > 0 ){
						bufferIndice--;
						recuarCursor();
						fundo.repintar();
					}
					break;
				
				case KeyEvent.VK_RIGHT :
					if( bufferIndice < buffer.length() ){
						bufferIndice++;
						avancarCursor();
						fundo.repintar();
					}
					break;
				
				case KeyEvent.VK_HOME :
					while( bufferIndice > 0 ){
						bufferIndice--;
						recuarCursor();
					}
					fundo.repintar();
					break;
				
				case KeyEvent.VK_END :
					while( bufferIndice < buffer.length() ){
						bufferIndice++;
						avancarCursor();
					}
					fundo.repintar();
					break;
					
				case KeyEvent.VK_INSERT :
					insert = ! insert;
					break;
					
			}
		
		}
		
		@Override
		public void keyTyped( KeyEvent e ) {
			
			if( capturarApenas1 ){
				buffer.append( e.getKeyChar() );
				capturarApenas1 = false;
				return;
			}
			
			if( ! iniciada ) return;
			
			char ch = e.getKeyChar();
			
			switch( ch ){
				
				case KeyEvent.VK_ENTER :
					
					pararDePiscarCursor();
					
					while( bufferIndice < buffer.length() ){
						bufferIndice++;
						avancarCursor();
					}
					saltarUmaLinha();
					
					fundo.repintar();
					
					finalizada = true;
					
					break;
				
				case KeyEvent.VK_BACK_SPACE :
					if( bufferIndice > 0 ){
						bufferIndice--;
						buffer.delete( bufferIndice, bufferIndice + 1 );
						recuarCursor();
						apagarCursor();
						fundo.repintar();
					}
					break;
					
				case KeyEvent.VK_DELETE :
					if( bufferIndice < buffer.length() ){
						buffer.delete( bufferIndice, bufferIndice + 1 );
						apagarCursor();
						fundo.repintar();
					}
					break;
					
				case KeyEvent.VK_ESCAPE :
					break;

				default :

					Caractere c = cadeia[cursorL][cursorC];
					
					if( insert ){
						buffer.insert( bufferIndice, ch );
						deslocarCursor();
					}else if( bufferIndice == buffer.length() ){
						buffer.append( ch );
					}else{
						buffer.setCharAt( bufferIndice, ch );
					}

					c.letra = ch;
					c.corLetra = corTexto;
					c.corFundo = corTextoFundo;
					
					bufferIndice++;
					avancarCursor();
					
					fundo.repintar();
					
					break;
					
			}
			
		}
		
		public synchronized String receber() throws IOException {

			buffer.delete( 0, buffer.length() );
			bufferIndice = 0;
			insert = true;
			finalizada = false;
			iniciada = true;
			
			piscarCursor();
			
			while( ! finalizada ){
				try{
					Thread.sleep( 50 );
				}catch( InterruptedException e ){
					throw new IOException( e );
				}
			}
			
			iniciada = false;
			
			return buffer.toString();
			
		}
		
		public synchronized char esperar( boolean mostrar ) throws IOException {

			buffer.delete( 0, buffer.length() );
			insert = true;
			capturarApenas1 = true;
			
			piscarCursor();
			
			while( capturarApenas1 ){
				try{
					Thread.sleep( 50 );
				}catch( InterruptedException e ){
					throw new IOException( e );
				}
			}
			
			pararDePiscarCursor();
			
			char ch = buffer.charAt( 0 );
			
			if( mostrar ){
				Caractere c = cadeia[cursorL][cursorC];
				c.letra = ch;
				c.corLetra = corTexto;
				c.corFundo = corTextoFundo;
				avancarCursor();
			}
			
			fundo.repintar();
			
			return ch;
			
		}
		
		private void piscarCursor() {
			if( timerCursor == null ){
				timerCursor = new Timer();
				timerCursor.schedule( new RelogioCursor( this ), 500, 500 );
			}
		}
		
		private void pararDePiscarCursor() {
			if( timerCursor != null ){
				timerCursor.cancel();
				timerCursor = null;
				fundo.cursor = Cursor.NAO_MOSTRAR;
			}
		}
		
	}
	
	private class RelogioCursor extends TimerTask {
		
		private Entrada entrada;
		
		public RelogioCursor( Entrada entrada ) {
			this.entrada = entrada;
		}

		@Override
		public void run() {
			fundo.cursor = fundo.cursor == Cursor.NAO_MOSTRAR ? ( entrada.insert ? Cursor.NORMAL : Cursor.SUBSTITUICAO ) : Cursor.NAO_MOSTRAR;
			fundo.repintarCursor();
		}
		
	}
	
	private enum Cursor { NAO_MOSTRAR, NORMAL, SUBSTITUICAO };

}
