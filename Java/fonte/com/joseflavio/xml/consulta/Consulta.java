
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

package com.joseflavio.xml.consulta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
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
