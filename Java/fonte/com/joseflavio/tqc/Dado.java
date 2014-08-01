
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
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class Dado {

	private boolean visivel = true;
	
	public Dado() {
	}
	
	/**
	 * Comparação por {@link Identificacao#getNome() nome}.
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
	 * Retorna o conteúdo {@link #validar() validado}.
	 */
	public final Object getConteudoValidado() throws ValidacaoException {
		validar();
		return getConteudo();
	}
	
	/**
	 * Valida o conteúdo do {@link Dado} de acordo com a sua {@link Validacao}.<br>
	 * A não implementação de {@link ValidacaoDeConteudo} não impede a invocação deste método.
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
	 * Constrói a {@link ValidacaoDeConteudo#getValidacao()} através de {@link MultiplaValidacao}.
	 * @throws RuntimeException caso este {@link Dado} não implemente {@link ValidacaoDeConteudo}.
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
	 * Implementação específica de {@link #configurarPor(Class, String)}.
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
	 * Configura propriedades do {@link Dado} de acordo com as {@link Annotation anotações} de um determinado {@link Field atributo} de {@link Class classe}.
	 * @throws IllegalArgumentException caso o atributo não seja encontrado.
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
