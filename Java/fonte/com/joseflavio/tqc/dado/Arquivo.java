
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

import java.io.File;

import com.joseflavio.tqc.Aparencia;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Dado} que referencia um {@link File}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Arquivo extends ComplexoDado {

	private File localPreferido;
	
	private File arquivo;
	
	private Aparencia<Arquivo> aparencia;
	
	/**
	 * @param arquivo Conteúdo. Pode ser <code>null</code>.
	 * @param localPreferido Destino de um possível novo arquivo. Pode ser <code>null</code>.
	 */
	public Arquivo( String nome, File arquivo, File localPreferido, boolean editavel ) {
		super( nome, editavel );
		this.arquivo = arquivo;
		this.localPreferido = localPreferido;
	}
	
	/**
	 * {@link Arquivo#Arquivo(String, File, File, boolean) Arquivo}( null, arquivo, null, false );
	 */
	public Arquivo( File arquivo ) {
		this( null, arquivo, null, false );
	}
	
	public Arquivo( String nome, Class<? extends Object> classe, String atributo, File arquivo, File localPreferido, Boolean editavel ) {
		
		super( nome );
		
		configurarPor( classe, atributo );
		
		this.arquivo = arquivo;
		this.localPreferido = localPreferido;
		
		if( editavel != null ) setEditavel( editavel );
		
	}
	
	public Arquivo( String nome, Class<? extends Object> classe, File arquivo, File localPreferido, Boolean editavel ) {
		this( nome, classe, nome, arquivo, localPreferido, editavel );
	}
	
	public Object getConteudo() {
		return arquivo;
	}
	
	public File getArquivoValidado() throws ValidacaoException {
		validar();
		return arquivo;
	}
	
	public File getArquivo() {
		return arquivo;
	}
	
	public Arquivo setArquivo( File arquivo ) {
		this.arquivo = arquivo;
		return this;
	}
	
	public File getLocalPreferido() {
		return localPreferido;
	}
	
	public Arquivo setLocalPreferido( File localPreferido ) {
		this.localPreferido = localPreferido;
		return this;
	}
	
	public Aparencia<Arquivo> getAparencia() {
		return aparencia;
	}
	
	public Arquivo setAparencia( Aparencia<Arquivo> aparencia ) {
		this.aparencia = aparencia;
		return this;
	}
	
	public Arquivo maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Arquivo maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Arquivo maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
}
