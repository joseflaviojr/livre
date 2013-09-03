
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc;

/**
 * Mensagem que alerta o usu�rio sobre algum fato importante.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Alerta {
	
	public static final int INFORMACAO = 1;
	public static final int ATENCAO = 2;
	public static final int ERRO = 3;
	
	private int tipo;
	
	private String titulo;
	
	private String mensagem;

	public Alerta( int tipo, String titulo, String mensagem ) {
		this.tipo = tipo;
		this.titulo = titulo;
		this.mensagem = mensagem;
	}

	public static Alerta novaInformacao( String titulo, String mensagem ){
		return new Alerta( INFORMACAO, titulo, mensagem );
	}
	
	public static Alerta novaAtencao( String titulo, String mensagem ){
		return new Alerta( ATENCAO, titulo, mensagem );
	}
	
	public static Alerta novoErro( String titulo, String mensagem ){
		return new Alerta( ERRO, titulo, mensagem );
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo( String titulo ) {
		this.titulo = titulo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem( String mensagem ) {
		this.mensagem = mensagem;
	}

	public int getTipo() {
		return tipo;
	}
	
	public void setTipo( int tipo ) {
		this.tipo = tipo;
	}

}