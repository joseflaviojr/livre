
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

package com.joseflavio.xml.consulta;

import com.joseflavio.xml.Tipo;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2014
 * @see Consultas
 */
public class Parametro {
	
	private String nome;
	
	private String valor;
	
	private Tipo tipo;
	
	private boolean alteravel = false;

	public Parametro( String nome, String valor ) {
		this.nome = nome;
		this.valor = valor;
	}
	
	public Parametro( String nome, String valor, Tipo tipo, boolean alteravel ) {
		this.nome = nome;
		this.valor = valor;
		this.tipo = tipo;
		this.alteravel = alteravel;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor( String valor ) {
		this.valor = valor;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo( Tipo tipo ) {
		this.tipo = tipo;
	}
	
	public boolean isAlteravel() {
		return alteravel;
	}
	
	public void setAlteravel( boolean alteravel ) {
		this.alteravel = alteravel;
	}
	
}
