
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

package com.joseflavio.tqc.dado;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Identificacao;
import com.joseflavio.tqc.ValidavelDado;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Representa��o visual de um {@link Object}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Objeto extends ValidavelDado implements Identificacao {
	
	private String nome;
	
	private Object objeto;
	
	private String texto;
	
	private String imagem;
	
	/**
	 * @param objeto Conte�do. Pode ser <code>null</code>.
	 * @param texto Texto representativo. <code>null</code> implicar� visualmente em object.toString()
	 * @param imagem Imagem representativa. Pode ser <code>null</code>.
	 */
	public Objeto( String nome, Object objeto, String texto, String imagem ) {
		this.nome = nome;
		this.objeto = objeto;
		this.texto = texto;
		this.imagem = imagem;
	}
	
	public Objeto( String nome, Object objeto ) {
		this( nome, objeto, null, null );
	}
	
	public Object getConteudo() {
		return objeto;
	}
	
	public Objeto setObjeto( Object objeto ) {
		this.objeto = objeto;
		return this;
	}
	
	public Object getObjetoValidado() throws ValidacaoException {
		validar();
		return objeto;
	}
	
	public Object getObjeto() {
		return objeto;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto( String texto ) {
		this.texto = texto;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem( String imagem ) {
		this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}
	
	public Dado setNome( String nome ) {
		this.nome = nome;
		return this;
	}
	
}
