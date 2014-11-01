
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2014
 * @see Consultas
 */
public class Consulta {
	
	private String nome;
	
	private String descricao;
	
	private String documentacao;
	
	private String questionamento;
	
	private Map<String,Parametro> parametro = new HashMap<String,Parametro>();
	
	private Map<String,Traducao.Instancia> traducao = new HashMap<String,Traducao.Instancia>();

	public Consulta( String nome ) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao( String descricao ) {
		this.descricao = descricao;
	}
	
	public String getDocumentacao() {
		return documentacao;
	}
	
	public void setDocumentacao( String documentacao ) {
		this.documentacao = documentacao;
	}
	
	public String getQuestionamento() {
		return questionamento;
	}
	
	public void setQuestionamento( String questionamento ) {
		this.questionamento = questionamento;
	}
	
	public Map<String,Parametro> getParametro() {
		return parametro;
	}
	
	public void setParametro( Map<String,Parametro> parametro ) {
		this.parametro = parametro;
	}
	
	public Map<String,Traducao.Instancia> getTraducao() {
		return traducao;
	}
	
	public void setTraducao( Map<String,Traducao.Instancia> traducao ) {
		this.traducao = traducao;
	}
	
}
