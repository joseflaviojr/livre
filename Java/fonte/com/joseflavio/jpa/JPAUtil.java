
/*
 *  Copyright (C) 2009-2015 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2015 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 * José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 * sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a versão 3 da Licença, como
 * (a seu critério) qualquer versão posterior.
 * 
 * José Flávio Livre é distribuído na expectativa de que seja útil,
 * porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 * COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 * Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 * junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class JPAUtil {

	private static Query prepararQuery( EntityManager em, String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		
		Query jpaQuery = em.createQuery( query );

		if( parametros != null ){
			int i = 0;
			for( Object p : parametros ){
				jpaQuery.setParameter( "p" + (i++), p );
			}
		}
		
		if( resultadoInicial > -1 ) jpaQuery.setFirstResult( resultadoInicial );
		if( maximoResultados > -1 ) jpaQuery.setMaxResults( maximoResultados );
		
		return jpaQuery;
		
	}
	
	/**
	 * Assistente de construção e execução de {@link Query}.
	 * @param resultadoInicial {@link Query#setFirstResult(int)}. -1 == desconsiderar.
	 * @param maximoResultados {@link Query#setMaxResults(int)}. -1 == desconsiderar.
	 * @param parametros Será executado {@link Query#setParameter(String, Object)} para cada elemento. Nomes dos parâmetros: p0, p1, p2, pN, ...
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> listarLimitada( EntityManager em, String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		return prepararQuery( em, query, resultadoInicial, maximoResultados, parametros ).getResultList();
	}
	
	/**
	 * @deprecated Ambiguidade com {@link #listar(EntityManager, String, Object...)}.
	 */
	public static <T extends Object> List<T> listar( EntityManager em, String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		return listarLimitada( em, query, resultadoInicial, maximoResultados, parametros );
	}
	
	/**
	 * @deprecated Ambiguidade com {@link #listar(AplicacaoTQC_JPA, String, Object...)}.
	 */
	public static <T extends Object> List<T> listar( AplicacaoTQC_JPA aplicacao, String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		return listarLimitada( aplicacao.getEntityManager(), query, resultadoInicial, maximoResultados, parametros );
	}
	
	public static <T extends Object> List<T> listarLimitada( AplicacaoTQC_JPA aplicacao, String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		return listarLimitada( aplicacao.getEntityManager(), query, resultadoInicial, maximoResultados, parametros );
	}
	
	public static <T extends Object> List<T> listarLimitada( AplicacaoTQC_JPA aplicacao, String query, int resultadoInicial, int maximoResultados ) {
		return listarLimitada( aplicacao.getEntityManager(), query, resultadoInicial, maximoResultados, (Object[]) null );
	}
	
	public static <T extends Object> List<T> listar( EntityManager em, String query, Object... parametros ) {
		return listarLimitada( em, query, -1, -1, parametros );
	}
	
	public static <T extends Object> List<T> listar( AplicacaoTQC_JPA aplicacao, String query, Object... parametros ) {
		return listarLimitada( aplicacao.getEntityManager(), query, -1, -1, parametros );
	}
	
	public static <T extends Object> T obter( EntityManager em, String query, Object... parametros ) {
		List<T> lista = listarLimitada( em, query, -1, 1, parametros );
		return lista.size() > 0 ? lista.get( 0 ): null;
	}
	
	public static <T extends Object> T obter( AplicacaoTQC_JPA aplicacao, String query, Object... parametros ) {
		List<T> lista = listarLimitada( aplicacao.getEntityManager(), query, -1, 1, parametros );
		return lista.size() > 0 ? lista.get( 0 ): null;
	}
	
	/**
	 * @param query "select count"
	 */
	public static long obterQuantidade( EntityManager em, String query, Object... parametros ) {
		return ( (Number) prepararQuery( em, query, -1, -1, parametros ).getSingleResult() ).longValue();
	}
	
	public static long obterQuantidade( AplicacaoTQC_JPA aplicacao, String query, Object... parametros ) {
		return obterQuantidade( aplicacao.getEntityManager(), query, parametros );
	}
	
	/**
	 * @param query "select count", "select sum", etc.
	 */
	public static Number obterNumero( EntityManager em, String query, Object... parametros ) {
		return (Number) prepararQuery( em, query, -1, -1, parametros ).getSingleResult();
	}
	
	public static Number obterNumero( AplicacaoTQC_JPA aplicacao, String query, Object... parametros ) {
		return obterNumero( aplicacao.getEntityManager(), query, parametros );
	}
	
	/**
	 * @see EntityManager#detach(Object)
	 * @deprecated Redirecionamento desnecessário. Utilizar EntityManager#detach(Object).
	 */
	public static <T extends Object> T destacar( EntityManager em, T obj ) {
		em.detach( obj );
		return obj;
	}
	
	/**
	 * @see EntityManager#detach(Object)
	 * @deprecated Redirecionamento desnecessário. Utilizar EntityManager#detach(Object).
	 */
	public static <T extends Object> T destacar( AplicacaoTQC_JPA aplicacao, T obj ) {
		return destacar( aplicacao.getEntityManager(), obj );
	}
	
	/**
	 * @see EntityManager#detach(Object)
	 */
	public static <T extends Object> List<T> destacar( EntityManager em, List<T> lista ) {
		for( T obj : lista ) em.detach( obj );
		return lista;
	}
	
	/**
	 * @deprecated Utilizar {@link #destacar(EntityManager, List)}
	 */
	public static <T extends Object> List<T> destacar( AplicacaoTQC_JPA aplicacao, List<T> lista ) {
		return destacar( aplicacao.getEntityManager(), lista );
	}
	
	/**
	 * @see EntityManager#refresh(Object)
	 */
	public static <T extends Object> List<T> atualizar( EntityManager em, List<T> lista ) {
		for( T obj : lista ) em.refresh( obj );
		return lista;
	}
	
	//TODO Método que teste se um objeto é entidade de uma aplicação.
	
}
