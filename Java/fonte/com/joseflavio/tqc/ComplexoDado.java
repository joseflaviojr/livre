
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

package com.joseflavio.tqc;

import com.joseflavio.tqc.aplicacao.ComandoImplementado;
import com.joseflavio.tqc.dado.Comando;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class ComplexoDado extends ValidavelDado implements Identificacao, Edicao {

	private Informacao informacao;
	
	private String nome;
	
	private boolean editavel = false;
	
	private Comando comando;
	
	public ComplexoDado( String nome, boolean editavel ) {
		this.nome = nome;
		this.editavel = editavel;
	}
	
	public ComplexoDado( String nome ) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public Dado setNome( String nome ) {
		this.nome = nome;
		return this;
	}
	
	public boolean isEditavel() {
		return editavel;
	}
	
	public Dado setEditavel( boolean editavel ) {
		this.editavel = editavel;
		return this;
	}
	
	public Comando getComando() {
		return comando;
	}
	
	/**
	 * {@link Comando} auxiliar. Ele poderá ser acionado em caso de confirmação (tecla ENTER, etc.).<br>
	 * Não se exige a presença do {@link Comando} na mesma {@link Informacao} deste {@link ComplexoDado}, contudo,
	 * no caso de isolamento do {@link Comando}, ele precisará ser do tipo {@link ComandoImplementado}.
	 */
	public void setComando( Comando comando ) {
		this.comando = comando;
	}
	
	/**
	 * Especifica a {@link Informacao} na qual o {@link ComplexoDado} está inserido.<br>
	 * Método utilizado pela própria {@link Informacao}.
	 */
	public void setInformacao( Informacao informacao ) {
		this.informacao = informacao;
	}
	
	/**
	 * @see #setInformacao(Informacao)
	 */
	public Informacao getInformacao() {
		return informacao;
	}
	
}
