
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

package com.joseflavio.tqc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Grupo de {@link Anexo}s.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * @return <code>null</code>, caso n�o seja encontrado anexo do tipo desejado.
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
	 * @return lista vazia se n�o encontrar qualquer {@link Anexo} compat�vel.
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
	 * Executa {@link #getAnexo(Class)}. Caso o {@link Anexo} desejado n�o exista, ele ser� {@link Class#newInstance() instanciado} e adicionado atrav�s
	 * de {@link #mais(Anexo)} (se o par�metro "adicionar" for positivo).
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
