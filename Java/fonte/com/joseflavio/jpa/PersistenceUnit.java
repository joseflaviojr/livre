
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
 * Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 * sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 * (a seu crit�rio) qualquer vers�o posterior.
 * 
 * Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 * por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 * COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 * Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 * junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.spi.PersistenceProvider;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.tqc.aplicacao.BancoDeDadosException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class PersistenceUnit {
	
	private String name;
	
	Class<? extends PersistenceProvider> provider;
	
	List<Class<? extends Object>> classes = new ArrayList<Class<? extends Object>>();
	
	Map<String, String> properties = new HashMap<String, String>();

	PersistenceUnit( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Class<? extends PersistenceProvider> getProvider() {
		return provider;
	}
	
	public List<Class<? extends Object>> getClasses() {
		return classes;
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}
	
	public String getPropriedade( String nome ) {
		return properties != null ? properties.get( nome ) : null;
	}
	
	private String nomeAtributoId( Class<? extends Object> c ) {
		for( Field f : AssistenteDeAtributo.getCampos( c ) ){
			if( f.getAnnotation( Id.class ) != null ) return f.getName();
		}
		return null;
	}
	
	/**
	 * @see Id
	 */
	public boolean temReferencia( EntityManager em, Object alvo ) throws BancoDeDadosException {

		try{
			
			Class<? extends Object> alvoClasse = alvo.getClass();
			String alvoNomeId = nomeAtributoId( alvoClasse );
			Object alvoValorId = AssistenteDeAtributo.getMetodoGet( alvoClasse, alvoNomeId ).invoke( alvo );
			
			int quant;
			
			for( Class<? extends Object> c : classes ){
				
				if( Modifier.isAbstract( c.getModifiers() ) || Modifier.isInterface( c.getModifiers() ) ) continue;
				
				for( Field f : AssistenteDeAtributo.getCampos( c ) ){
					
					if( f.getType().isAssignableFrom( alvoClasse ) ){
						
						quant = ( (Number) em.createQuery( "select count(c) from " + c.getSimpleName() + " c where c." + f.getName() + "." + alvoNomeId + " = :id" ).setParameter( "id", alvoValorId ).getSingleResult() ).intValue();
						
						if( quant > 0 ) return true;
						
					}
					
				}
				
			}
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
		return false;
		
	}
	
	/**
	 * @see Id
	 */
	public int contarReferencias( EntityManager em, Object alvo ) throws BancoDeDadosException { //TODO Ordernar classes por hierarquia e excluir subclasses?

		int total = 0;
		
		try{
			
			Class<? extends Object> alvoClasse = alvo.getClass();
			String alvoNomeId = nomeAtributoId( alvoClasse );
			Object alvoValorId = AssistenteDeAtributo.getMetodoGet( alvoClasse, alvoNomeId ).invoke( alvo );
			
			for( Class<? extends Object> c : classes ){
				
				if( Modifier.isAbstract( c.getModifiers() ) || Modifier.isInterface( c.getModifiers() ) ) continue;
				
				for( Field f : c.getDeclaredFields() ){
					
					if( f.getType().isAssignableFrom( alvoClasse ) ){
						
						total += ( (Number) em.createQuery( "select count(c) from " + c.getSimpleName() + " c where c." + f.getName() + "." + alvoNomeId + " = :id" ).setParameter( "id", alvoValorId ).getSingleResult() ).intValue();
						
					}
					
				}
				
			}
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
		return total;
		
	}
	
	/**
	 * Calcula a quantidade de objetos persistidos de uma determinada classe.
	 */
	public int contarObjetos( EntityManager em, Class<? extends Object> classe ) throws BancoDeDadosException {
		
		try{
		
			return ( (Number) em.createQuery( "select count(c) from " + classe.getSimpleName() + " c" ).getSingleResult() ).intValue();
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
	}
	
}
