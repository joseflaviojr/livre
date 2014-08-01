
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

package com.joseflavio.tqc;

import java.lang.reflect.Field;

import com.joseflavio.modelo.JFValidacaoNaoNulo;
import com.joseflavio.modelo.JFValidacaoPrimitiva;
import com.joseflavio.validacao.MultiplaValidacao;
import com.joseflavio.validacao.NaoNuloValidacao;
import com.joseflavio.validacao.Validacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class ValidavelDado extends SimplesDado implements ValidacaoDeConteudo {

	private Validacao validacao;
	
	private Object conteudoInvalido;
	
	private String mensagemValidacaoPrimitiva;
	
	public ValidavelDado() {
	}

	public Validacao getValidacao() {
		return validacao;
	}
	
	public Dado setValidacao( Validacao validacao ) {
		this.validacao = validacao;
		return this;
	}
	
	public Object getConteudoInvalido() {
		return conteudoInvalido;
	}
	
	public Dado setConteudoInvalido( Object conteudo ) {
		this.conteudoInvalido = conteudo;
		return this;
	}
	
	public String getMensagemValidacaoPrimitiva() {
		return mensagemValidacaoPrimitiva;
	}
	
	public Dado setMensagemValidacaoPrimitiva( String mensagem ) {
		this.mensagemValidacaoPrimitiva = mensagem;
		return this;
	}
	
	public Validacao getValidacao( Class<? extends Validacao> classe ) {
		
		Validacao v = this.validacao;
		
		if( v == null ) return null;
		
		if( v instanceof MultiplaValidacao ){
			
			MultiplaValidacao mv = (MultiplaValidacao) v;
			
			int total = mv.getTotal();
			for( int i = 0; i < total; i++ ){
				v = mv.getValidacao( i );
				if( classe.isAssignableFrom( v.getClass() ) ) return v; 
			}
			
		}else{
			
			if( classe.isAssignableFrom( v.getClass() ) ) return v;
			
		}
		
		return null;
		
	}
	
	public boolean isVazio() {
		return getConteudo() == null && getConteudoInvalido() == null;
	}
	
	/**
	 * @see JFValidacaoPrimitiva
	 * @see JFValidacaoNaoNulo
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		String nome = this instanceof Identificacao ? ((Identificacao)this).getNome() : atributo.getName();
		
		JFValidacaoPrimitiva      jfValidacaoPrimitiva       = atributo.getAnnotation( JFValidacaoPrimitiva.class );
		JFValidacaoNaoNulo        jfValidacaoNaoNulo         = atributo.getAnnotation( JFValidacaoNaoNulo.class );

		if( jfValidacaoPrimitiva != null ) setMensagemValidacaoPrimitiva( jfValidacaoPrimitiva.erro() );
		if( jfValidacaoNaoNulo != null ) mais( new NaoNuloValidacao( nome, ValidacaoException.ERRO, jfValidacaoNaoNulo.erro() ) );
		
		return this;
		
	}

}
