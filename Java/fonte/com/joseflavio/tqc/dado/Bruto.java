
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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

import com.joseflavio.tqc.Aparencia;
import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Dado} com conte�do bruto (sequ�ncia de bytes de formato desconhecido).
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class Bruto extends ComplexoDado {

	private byte[] valor;
	
	private String valorRotulo;
	
	private Aparencia<Bruto> aparencia;
	
	/**
	 * @param valor Conte�do. Pode ser <code>null</code>.
	 * @param valorRotulo R�tulo que identifica o conte�do {@link Bruto}. Normalmente corresponde ao nome do arquivo contido em {@link Bruto}.
	 */
	public Bruto( String nome, byte[] valor, String valorRotulo, boolean editavel ) {
		super( nome, editavel );
		this.valor = valor;
		this.valorRotulo = valorRotulo;
	}
	
	public Bruto( String nome, Class<? extends Object> classe, String atributo, byte[] valor, String valorRotulo, Boolean editavel ) {
		super( nome );
		configurarPor( classe, atributo );
		this.valor = valor;
		this.valorRotulo = valorRotulo;
		if( editavel != null ) setEditavel( editavel );
	}
	
	public Bruto( String nome, Class<? extends Object> classe, byte[] valor, String valorRotulo, Boolean editavel ) {
		this( nome, classe, nome, valor, valorRotulo, editavel );
	}
	
	public Object getConteudo() {
		return valor;
	}
	
	public byte[] getValorValidado() throws ValidacaoException {
		validar();
		return valor;
	}
	
	public byte[] getValor() {
		return valor;
	}
	
	public Bruto setValor( byte[] valor ) {
		this.valor = valor;
		return this;
	}
	
	public String getValorRotulo() {
		return valorRotulo;
	}
	
	public Bruto setValorRotulo( String valorRotulo ) {
		this.valorRotulo = valorRotulo;
		return this;
	}
	
	public Aparencia<Bruto> getAparencia() {
		return aparencia;
	}
	
	public Bruto setAparencia( Aparencia<Bruto> aparencia ) {
		this.aparencia = aparencia;
		return this;
	}

	public Bruto maisValidacaoPrimitiva( String mensagem ) {
		setMensagemValidacaoPrimitiva( mensagem );
		return this;
	}
	
	public Bruto maisNaoNulo( int tipo, String mensagem ) {
		mais( new NaoNuloValidacao( getNome(), tipo, mensagem ) );
		return this;
	}
	
	public Bruto maisNaoNulo( String mensagem ) {
		return maisNaoNulo( ValidacaoException.ERRO, mensagem );
	}
	
}
