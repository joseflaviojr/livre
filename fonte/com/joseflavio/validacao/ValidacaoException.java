
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

package com.joseflavio.validacao;


/**
 * Exceção disparada quando uma {@link Validacao} é contrariada.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class ValidacaoException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	public static final int INFORMACAO = 1;
	public static final int ATENCAO = 2;
	public static final int ERRO = 3;
	
	private String nome;
	
	private int tipo;
	
	private Object objetoInvalido;
	

	/**
	 * @param nome Nome que identifica o objeto.
	 * @param tipo Tipo desta exceção.
	 * @param objetoInvalido Objeto na forma invalidada pela {@link Validacao}. 
	 * @param mensagem Mensagem que comunica o erro identificado.
	 */
	public ValidacaoException( String nome, int tipo, Object objetoInvalido, String mensagem ) {
		super( mensagem );
		this.nome = nome;
		this.tipo = tipo;
		this.objetoInvalido = objetoInvalido;
	}

	public static ValidacaoException novaInformacao( String nome, Object objetoInvalido, String mensagem ){
		return new ValidacaoException( nome, INFORMACAO, objetoInvalido, mensagem );
	}
	
	public static ValidacaoException novaAtencao( String nome, Object objetoInvalido, String mensagem ){
		return new ValidacaoException( nome, ATENCAO, objetoInvalido, mensagem );
	}
	
	public static ValidacaoException novoErro( String nome, Object objetoInvalido, String mensagem ){
		return new ValidacaoException( nome, ERRO, objetoInvalido, mensagem );
	}
	
	public static ValidacaoException novaInformacao( String mensagem ){
		return new ValidacaoException( null, INFORMACAO, null, mensagem );
	}
	
	public static ValidacaoException novaAtencao( String mensagem ){
		return new ValidacaoException( null, ATENCAO, null, mensagem );
	}
	
	public static ValidacaoException novoErro( String mensagem ){
		return new ValidacaoException( null, ERRO, null, mensagem );
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public Object getObjetoInvalido() {
		return objetoInvalido;
	}
	
	public String getMensagem() {
		return getMessage();
	}
	
}
