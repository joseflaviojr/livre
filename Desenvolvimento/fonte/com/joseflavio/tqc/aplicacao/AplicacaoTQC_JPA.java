
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

package com.joseflavio.tqc.aplicacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.joseflavio.cultura.Cultura;
import com.joseflavio.jpa.JPAUtil;
import com.joseflavio.jpa.PersistenceUnit;
import com.joseflavio.jpa.PersistenceXML;
import com.joseflavio.tqc.EstiloFonte;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link AplicacaoTQC} com {@link EntityManager}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class AplicacaoTQC_JPA extends AplicacaoTQC { //TODO Revisar sincronização

	private String persistenceUnitName;

	private PersistenceUnit persistenceUnit;
	
	private EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;
	
	private boolean autoAtualizar = false;
	
	private boolean autoDestacar = false;
	
	/**
	 * @see PersistenceUnit
	 */
	public AplicacaoTQC_JPA( String persistenceUnitName ) {
		
		super();
		
		this.persistenceUnitName = persistenceUnitName;
		
	}
	
	/**
	 * @deprecated Definições externas. Ver {@link #getPropriedade(String)}.
	 */
	public AplicacaoTQC_JPA( String titulo, String persistenceUnitName, Cultura cultura, EstiloFonte estiloFonte ) {
		
		super( titulo, cultura, estiloFonte );
		
		this.persistenceUnitName = persistenceUnitName;
		
	}
	
	/**
	 * @deprecated Definições externas. Ver {@link #getPropriedade(String)}.
	 */
	public AplicacaoTQC_JPA( String titulo, String persistenceUnitName, Cultura cultura ) {
		
		this( titulo, persistenceUnitName, cultura, null );
		
	}
	
	public synchronized PersistenceUnit getPersistenceUnit() throws BancoDeDadosException {
		
		try{
		
			if( persistenceUnit == null ){
				persistenceUnit = new PersistenceXML().getPersistenceUnit( persistenceUnitName );
			}
			
			return persistenceUnit;
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
	}
	
	public synchronized EntityManagerFactory getEntityManagerFactory() throws BancoDeDadosException {
		
		try{
		
			if( entityManagerFactory == null ){
				entityManagerFactory = Persistence.createEntityManagerFactory( persistenceUnitName );
			}
			
			return entityManagerFactory;
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
	}
	
	public synchronized EntityManager getEntityManager() throws BancoDeDadosException {
		
		try{
			
			if( entityManager == null || ! entityManager.isOpen() ){
				entityManager = getEntityManagerFactory().createEntityManager();
			}
			
			EntityTransaction entityTransaction = entityManager.getTransaction();
			
			if( entityTransaction != null && ! entityTransaction.isActive() ){
				entityTransaction.begin();
			}
			
			return entityManager;
		
		}catch( BancoDeDadosException e ){
			throw e;
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
	}
	
	/**
	 * @see EntityTransaction#commit()
	 */
	public synchronized void persistirPendencias() throws BancoDeDadosException {
		
		try{

			EntityTransaction entityTransaction = entityManager.getTransaction();
			
			if( entityTransaction != null && entityTransaction.isActive() ){
				entityTransaction.commit();
			}
			
			if( entityTransaction != null && ! entityTransaction.isActive() ){
				entityTransaction.begin();
			}
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
	}
	
	/**
	 * Cancela todas as alterações realizadas desde o último {@link #persistirPendencias()}.<br>
	 * @see EntityTransaction#rollback()
	 */
	public synchronized void cancelarPendencias() throws BancoDeDadosException {
		
		try{

			EntityTransaction entityTransaction = entityManager.getTransaction();
			
			if( entityTransaction != null && entityTransaction.isActive() ){
				entityTransaction.rollback();
			}
			
			if( entityTransaction != null && ! entityTransaction.isActive() ){
				entityTransaction.begin();
			}
			
		}catch( Exception e ){
			throw new BancoDeDadosException( e );
		}
		
	}
	
	public synchronized void fecharEntityManagerFactory() {
		
		try{
			if( entityManagerFactory != null && entityManagerFactory.isOpen() ) entityManagerFactory.close();
		}catch( Exception e ){
		}finally{
			entityManagerFactory = null;
		}
		
	}
	
	public synchronized void fecharEntityManager() {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		try{
			if( entityTransaction != null && entityTransaction.isActive() ) entityTransaction.commit();
		}catch( Exception e ){
		}finally{
			entityTransaction = null;
		}
		
		try{
			if( entityManager != null && entityManager.isOpen() ) entityManager.close();
		}catch( Exception e ){
		}finally{
			entityManager = null;
		}
		
	}
	
	public synchronized void fecharBancoDeDados() {
		
		fecharEntityManager();
		fecharEntityManagerFactory();
		
	}
	
	public void fim() {
		
		fecharBancoDeDados();
		
	}
	
	/**
	 * Persiste um novo objeto.
	 * @see EntityManager#persist(Object)
	 * @see EntityManager#merge(Object)
	 */
	public <T extends Object> T persistir( T obj ) {
		
		EntityManager em = getEntityManager();
		
		if( autoDestacar ){
			obj = em.merge( obj );
			persistirPendencias();
			em.detach( obj );
			return obj;
		}
		
		em.persist( obj );
		return obj;
		
	}
	
	/**
	 * Atualiza o estado do objeto de acordo com o bando de dados.
	 * @see EntityManager#refresh(Object)
	 */
	public <T extends Object> T atualizar( T obj ) {
		
		EntityManager em = getEntityManager();
		
		if( autoDestacar ){
			obj = em.merge( obj );
			em.refresh( obj );
			em.detach( obj );
			return obj;
		}

		em.refresh( obj );
		return obj;
		
	}
	
	/**
	 * Remove um objeto do banco de dados caso outro objeto não dependa de sua existência.
	 * @param obj Objeto a ser removido do banco de dados.
	 * @param msgDependencia Mensagem para {@link ValidacaoException}.
	 * @throws ValidacaoException caso outro objeto dependa da existência do que seria removido.
	 */
	public void remover( Object obj, String msgDependencia, boolean persistirPendencias ) throws ValidacaoException, BancoDeDadosException {
		
		EntityManager em = getEntityManager();
		
		if( autoDestacar ) obj = em.merge( obj );
		
		if( getPersistenceUnit().temReferencia( em, obj ) ){
			throw ValidacaoException.novaAtencao( msgDependencia );
		}
		
		em.remove( obj );
		if( persistirPendencias ) persistirPendencias();
		
	}
	
	/**
	 * @see JPAUtil#listarLimitada(AplicacaoTQC_JPA, String, int, int, Object...)
	 */
	public <T extends Object> List<T> listarLimitada( String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		List<T> lista = JPAUtil.listarLimitada( this, query, resultadoInicial, maximoResultados, parametros );
		if( lista != null ){
			EntityManager em = this.getEntityManager();
			if( autoAtualizar ) lista = JPAUtil.atualizar( em, lista );
			if( autoDestacar ) lista = JPAUtil.destacar( em, lista );
		}
		return lista;
	}
	
	/**
	 * @deprecated Ambiguidade com {@link #listar(String, Object...)}.
	 */
	public <T extends Object> List<T> listar( String query, int resultadoInicial, int maximoResultados, Object... parametros ) {
		return listarLimitada( query, resultadoInicial, maximoResultados, parametros );
	}
	
	/**
	 * @see JPAUtil#listarLimitada(AplicacaoTQC_JPA, String, int, int)
	 */
	public <T extends Object> List<T> listarLimitada( String query, int resultadoInicial, int maximoResultados ) {
		return listarLimitada( query, resultadoInicial, maximoResultados, (Object[]) null );
	}
	
	/**
	 * @see JPAUtil#listar(AplicacaoTQC_JPA, String, Object...)
	 */
	public <T extends Object> List<T> listar( String query, Object... parametros ) {
		List<T> lista = JPAUtil.listar( this, query, parametros );
		if( lista != null ){
			EntityManager em = this.getEntityManager();
			if( autoAtualizar ) lista = JPAUtil.atualizar( em, lista );
			if( autoDestacar ) lista = JPAUtil.destacar( em, lista );
		}
		return lista;
	}
	
	/**
	 * @see JPAUtil#obter(AplicacaoTQC_JPA, String, Object...)
	 */
	public <T extends Object> T obter( String query, Object... parametros ) {
		T obj = JPAUtil.obter( this, query, parametros );
		if( obj != null ){
			EntityManager em = this.getEntityManager();
			if( autoAtualizar ) em.refresh( obj );
			if( autoDestacar ) em.detach( obj );
		}
		return obj;
	}
	
	/**
	 * @see JPAUtil#obterQuantidade(AplicacaoTQC_JPA, String, Object...)
	 */
	public long obterQuantidade( String query, Object... parametros ) {
		return JPAUtil.obterQuantidade( this, query, parametros );
	}
	
	/**
	 * @see JPAUtil#obterNumero(AplicacaoTQC_JPA, String, Object...)
	 */
	public Number obterNumero( String query, Object... parametros ) {
		return JPAUtil.obterNumero( this, query, parametros );
	}
	
	/**
	 * @see #atualizar(Object)
	 */
	public void setAutoAtualizar( boolean autoAtualizar ) {
		this.autoAtualizar = autoAtualizar;
	}
	
	public boolean isAutoAtualizar() {
		return autoAtualizar;
	}
	
	/**
	 * @see EntityManager#detach(Object)
	 */
	public void setAutoDestacar( boolean autoDestacar ) {
		this.autoDestacar = autoDestacar;
	}
	
	public boolean isAutoDestacar() {
		return autoDestacar;
	}
	
}
