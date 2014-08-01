
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.JFAcesso;
import com.joseflavio.modelo.JFDica;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.validacao.MultiplaValidacao;
import com.joseflavio.validacao.Validacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Elemento de uma {@link Informacao}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class Dado {

	private boolean visivel = true;
	
	public Dado() {
	}
	
	/**
	 * Compara��o por {@link Identificacao#getNome() nome}.
	 */
	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( ! ( obj instanceof Dado ) ) return false;
		if( this instanceof Identificacao && obj instanceof Identificacao ){
			String n1 = ((Identificacao)this).getNome();
			String n2 = ((Identificacao)obj).getNome();
			if( n1 == n2 || ( n1 != null && n2 != null && n1.equals( n2 ) ) ) return true;
		}
		return super.equals( obj );
	}
	
	public abstract Object getConteudo();

	/**
	 * Retorna o conte�do {@link #validar() validado}.
	 */
	public final Object getConteudoValidado() throws ValidacaoException {
		validar();
		return getConteudo();
	}
	
	/**
	 * Valida o conte�do do {@link Dado} de acordo com a sua {@link Validacao}.<br>
	 * A n�o implementa��o de {@link ValidacaoDeConteudo} n�o impede a invoca��o deste m�todo.
	 * @throws ValidacaoException proveniente da {@link Validacao}.
	 * @see ValidacaoPrimitiva
	 * @see ValidacaoDeConteudo
	 * @see #getConteudo()
	 */
	public final void validar() throws ValidacaoException {
		
		if( this instanceof ValidacaoPrimitiva ){
			
			ValidacaoPrimitiva vp = (ValidacaoPrimitiva) this;
			
			if( vp.getConteudoInvalido() != null ){
				
				String nome = this instanceof Identificacao ? ((Identificacao)this).getNome() : null;
				
				throw ValidacaoException.novoErro( nome, vp.getConteudoInvalido(), vp.getMensagemValidacaoPrimitiva() );
				
			}
			
		}
		
		if( this instanceof ValidacaoDeConteudo ){
			
			Validacao validacao = ((ValidacaoDeConteudo)this).getValidacao();
			
			if( validacao != null ) validacao.validar( getConteudo() );
			
		}
		
	}
	
	/**
	 * Constr�i a {@link ValidacaoDeConteudo#getValidacao()} atrav�s de {@link MultiplaValidacao}.
	 * @throws RuntimeException caso este {@link Dado} n�o implemente {@link ValidacaoDeConteudo}.
	 */
	public Dado mais( Validacao v ){
		
		if( ! ( this instanceof ValidacaoDeConteudo ) ) throw new RuntimeException();
			
		ValidacaoDeConteudo vc = (ValidacaoDeConteudo) this;
		Validacao validacao = vc.getValidacao();
		
		if( validacao == null ) validacao = v;
		else if( validacao instanceof MultiplaValidacao ) ((MultiplaValidacao)validacao).mais( v );
		else validacao = new MultiplaValidacao( validacao ).mais( v );
		
		vc.setValidacao( validacao );
		
		return this;
		
	}
	
	public boolean isVisivel() {
		return visivel;
	}
	
	public Dado setVisivel( boolean visivel ) {
		this.visivel = visivel;
		return this;
	}
	
	/**
	 * Implementa��o espec�fica de {@link #configurarPor(Class, String)}.
	 * @see JFAcesso
	 */
	protected Dado configurarPor( Field atributo ) {
		
		JFAcesso jfAcesso = atributo.getAnnotation( JFAcesso.class );

		if( jfAcesso != null ){
			setVisivel( jfAcesso.visivel() );
			if( this instanceof Edicao ) ((Edicao)this).setEditavel( jfAcesso.editavel() );
		}
		
		return this;
		
	}
	
	/**
	 * Configura propriedades do {@link Dado} de acordo com as {@link Annotation anota��es} de um determinado {@link Field atributo} de {@link Class classe}.
	 * @throws IllegalArgumentException caso o atributo n�o seja encontrado.
	 * @see JFTexto
	 * @see JFValidacaoNaoVazio
	 * @see JFDica
	 */
	public final Dado configurarPor( Class<? extends Object> classe, String atributo ) {
		
		try{
			
			return configurarPor( AssistenteDeAtributo.getCampo( classe, atributo ) );
			
		}catch( Exception e ){
			throw new IllegalArgumentException( "Atributo " + classe.getName() + "." + atributo + " desconhecido.", e ); 
		}
		
	}
	
}
