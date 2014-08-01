
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

import java.io.File;

import com.joseflavio.tqc.Aparencia;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Dado} que referencia um {@link File}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Arquivo extends ComplexoDado {

	private File localPreferido;
	
	private File arquivo;
	
	private Aparencia<Arquivo> aparencia;
	
	/**
	 * @param arquivo Conte�do. Pode ser <code>null</code>.
	 * @param localPreferido Destino de um poss�vel novo arquivo. Pode ser <code>null</code>.
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
