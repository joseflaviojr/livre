
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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * Assistente de constru��o e execu��o de {@link Query}.
	 * @param resultadoInicial {@link Query#setFirstResult(int)}. -1 == desconsiderar.
	 * @param maximoResultados {@link Query#setMaxResults(int)}. -1 == desconsiderar.
	 * @param parametros Ser� executado {@link Query#setParameter(String, Object)} para cada elemento. Nomes dos par�metros: p0, p1, p2, pN, ...
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
	 * @deprecated Redirecionamento desnecess�rio. Utilizar EntityManager#detach(Object).
	 */
	public static <T extends Object> T destacar( EntityManager em, T obj ) {
		em.detach( obj );
		return obj;
	}
	
	/**
	 * @see EntityManager#detach(Object)
	 * @deprecated Redirecionamento desnecess�rio. Utilizar EntityManager#detach(Object).
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
	
	//TODO M�todo que teste se um objeto � entidade de uma aplica��o.
	
}
