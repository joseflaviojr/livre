
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.dado;

import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Identificacao;
import com.joseflavio.tqc.ValidavelDado;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Representação visual de um {@link Object}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Objeto extends ValidavelDado implements Identificacao {
	
	private String nome;
	
	private Object objeto;
	
	private String texto;
	
	private String imagem;
	
	/**
	 * @param objeto Conteúdo. Pode ser <code>null</code>.
	 * @param texto Texto representativo. <code>null</code> implicará visualmente em object.toString()
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
