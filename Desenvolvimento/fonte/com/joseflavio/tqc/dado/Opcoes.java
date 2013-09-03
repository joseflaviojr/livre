
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 * Op��es: conjunto de objetos selecion�veis.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * Op��es dispon�veis.
	 * @return lista vazia caso n�o haja op��es.
	 */
	public abstract List<T> getOpcoes();

	public abstract int getTotalOpcoes();
	
	public abstract T getOpcao( int indice );
	
	public void setGeradorDeOpcoes( GeradorDeOpcoes<T> geradorDeOpcoes ) {
		this.geradorDeOpcoes = geradorDeOpcoes;
	}
	
	/**
	 * @return <code>null</code> caso n�o tenha sido definido um {@link GeradorDeOpcoes}.
	 */
	public GeradorDeOpcoes<T> getGeradorDeOpcoes() {
		return geradorDeOpcoes;
	}
	
	/**
	 * Atualiza as {@link #setOpcoes(List) op��es} de acordo com um dos recursos a seguir, respeitada a ordem de prioridade:<br>
	 * 1) {@link #getGeradorDeOpcoes()}<br>
	 * 2) Correspondente {@link Method m�todo} {@link Annotation anotado} com {@link TQCOpcoes} presente na {@link #getInformacao()}.<br>
	 * Este m�todo � chamado automaticamente no momento da {@link Informacao#mais(Dado) adi��o} das {@link Opcoes} na {@link Informacao}.
	 * @see #setGeradorDeOpcoes(GeradorDeOpcoes)
	 * @see TQCOpcoes
	 */
	@SuppressWarnings("unchecked")
	public final Opcoes<T> atualizarOpcoes() throws IllegalAccessException, InvocationTargetException {
		
		GeradorDeOpcoes<T> go = getGeradorDeOpcoes();
		if( go != null ) return setOpcoes( go.gerar() );
		
		if( tqcOpcoes == null ) return this;
		
		Object lista = tqcOpcoes.invoke( getInformacao() );
		if( lista != null && ! ( lista instanceof List<?> ) ) throw new IllegalAccessException( "TQCOpcoes incompat�vel." );
		
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
	 * Gerador din�mico de lista de op��es.
	 * @author Jos� Fl�vio de Souza Dias J�nior
	 * @version 2013
	 */
	public static interface GeradorDeOpcoes<T> {
		
		List<T> gerar();
		
	}

}
