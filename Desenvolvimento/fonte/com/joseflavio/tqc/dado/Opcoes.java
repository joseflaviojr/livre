
/*
 *  Copyright (C) 2009-2013 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2013 José Flávio de Souza Dias Júnior
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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.joseflavio.tqc.ComplexoDado;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.anotacao.TQCOpcoes;
import com.joseflavio.util.ClassUtil;

/**
 * Opções: conjunto de objetos selecionáveis.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see TQCOpcoes
 */
public abstract class Opcoes<T> extends ComplexoDado {
	
	private GeradorDeOpcoes<T> geradorDeOpcoes;
	
	private Method tqcOpcoes;
	
	public Opcoes( String nome, boolean editavel ) {
		super( nome, editavel );
	}

	public Opcoes( String nome ) {
		super( nome );
	}

	public abstract Opcoes<T> setOpcoes( List<T> opcoes );
	
	public abstract Opcoes<T> setOpcoes( T cabeca, T... cauda );
	
	public abstract Opcoes<T> setOpcoes( T cabeca, List<T> cauda );
	
	/**
	 * Opções disponíveis.
	 * @return lista vazia caso não haja opções.
	 */
	public abstract List<T> getOpcoes();

	public abstract int getTotalOpcoes();
	
	public abstract T getOpcao( int indice );
	
	public void setGeradorDeOpcoes( GeradorDeOpcoes<T> geradorDeOpcoes ) {
		this.geradorDeOpcoes = geradorDeOpcoes;
	}
	
	/**
	 * @return <code>null</code> caso não tenha sido definido um {@link GeradorDeOpcoes}.
	 */
	public GeradorDeOpcoes<T> getGeradorDeOpcoes() {
		return geradorDeOpcoes;
	}
	
	/**
	 * Atualiza as {@link #setOpcoes(List) opções} de acordo com um dos recursos a seguir, respeitada a ordem de prioridade:<br>
	 * 1) {@link #getGeradorDeOpcoes()}<br>
	 * 2) Correspondente {@link Method método} {@link Annotation anotado} com {@link TQCOpcoes} presente na {@link #getInformacao()}.<br>
	 * Este método é chamado automaticamente no momento da {@link Informacao#mais(Dado) adição} das {@link Opcoes} na {@link Informacao}.
	 * @see #setGeradorDeOpcoes(GeradorDeOpcoes)
	 * @see TQCOpcoes
	 */
	@SuppressWarnings("unchecked")
	public final Opcoes<T> atualizarOpcoes() throws IllegalAccessException, InvocationTargetException {
		
		GeradorDeOpcoes<T> go = getGeradorDeOpcoes();
		if( go != null ) return setOpcoes( go.gerar() );
		
		if( tqcOpcoes == null ) return this;
		
		Object lista = tqcOpcoes.invoke( getInformacao() );
		if( lista != null && ! ( lista instanceof List<?> ) ) throw new IllegalAccessException( "TQCOpcoes incompatível." );
		
		return setOpcoes( (List<T>) lista );
		
	}
	
	@Override
	public void setInformacao( Informacao informacao ) {
		
		super.setInformacao( informacao );
		
		if( informacao == null ){
			tqcOpcoes = null;
			return;
		}
		
		String nome = getNome();
		if( nome == null ) return;
		
		for( Method m : ClassUtil.listarMetodos( informacao.getClass() ) ){
			
			TQCOpcoes anotacao = m.getAnnotation( TQCOpcoes.class );
			if( anotacao == null ) continue;
			
			String dado = anotacao.dado();
			if( dado != null && dado.equals( nome ) ){
				tqcOpcoes = m;
				break;
			}
			
		}
		
		try{
			atualizarOpcoes();
		}catch( Exception e ){
		}
		
	}
	
	/**
	 * Gerador dinâmico de lista de opções.
	 * @author José Flávio de Souza Dias Júnior
	 * @version 2013
	 */
	public static interface GeradorDeOpcoes<T> {
		
		List<T> gerar();
		
	}

}
