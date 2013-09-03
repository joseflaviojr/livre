
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

package com.joseflavio.tqc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Grupo de {@link Anexo}s.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class AnexoGrupo extends Anexo {

	private List<Anexo> grupo = new LinkedList<Anexo>();
	
	public AnexoGrupo() {
	}
	
	public AnexoGrupo( Anexo anexoInicial ) {
		grupo.add( anexoInicial );
	}
	
	public AnexoGrupo mais( Anexo anexo ) {
		grupo.add( anexo );
		return this;
	}
	
	public AnexoGrupo menos( Anexo anexo ) {
		grupo.remove( anexo );
		return this;
	}
	
	/**
	 * Remove todos os {@link Anexo anexos} do {@link Class tipo} especificado.
	 * @see Class#isAssignableFrom(Class)
	 */
	public <T extends Anexo> AnexoGrupo menos( Class<? extends T> tipo ) {
		
		for( int i = 0; i < grupo.size(); ){
			Class<? extends Anexo> anexoClasse = grupo.get( i ).getClass();
			if( tipo.isAssignableFrom( anexoClasse ) ){
				grupo.remove( i );
			}else{
				i++;
			}
		}
		
		return this;
		
	}
	
	public List<Anexo> getGrupo() {
		return grupo;
	}
	
	/**
	 * Retorna o primeiro {@link Anexo} do tipo especificado.
	 * @param tipo Tipo de {@link Anexo} desejado.
	 * @return <code>null</code>, caso não seja encontrado anexo do tipo desejado.
	 * @see Class#isAssignableFrom(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Anexo> T getAnexo( Class<? extends T> tipo ) {
		
		for( Anexo anexo : grupo ){
			if( tipo.isAssignableFrom( anexo.getClass() ) ) return (T) anexo;
		}
		
		return null;
		
	}
	
	/**
	 * Retorna todos os {@link Anexo}s do tipo desejado contidos neste {@link AnexoGrupo}.
	 * @return lista vazia se não encontrar qualquer {@link Anexo} compatível.
	 * @see Class#isAssignableFrom(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Anexo> List<T> getAnexos( Class<? extends T> tipo ) {
		
		List<T> lista = new ArrayList<T>();
		
		for( Anexo anexo : grupo ){
			if( tipo.isAssignableFrom( anexo.getClass() ) ) lista.add( (T) anexo );
		}
		
		return lista;
		
	}
	
	/**
	 * Executa {@link #getAnexo(Class)}. Caso o {@link Anexo} desejado não exista, ele será {@link Class#newInstance() instanciado} e adicionado através
	 * de {@link #mais(Anexo)} (se o parâmetro "adicionar" for positivo).
	 */
	public <T extends Anexo> T getAnexo( Class<? extends T> tipo, boolean adicionar ) throws InstantiationException, IllegalAccessException {
		T anexo = getAnexo( tipo );
		if( anexo == null && adicionar ){
			anexo = tipo.newInstance();
			mais( anexo );
		}
		return anexo;
	}
	
}
