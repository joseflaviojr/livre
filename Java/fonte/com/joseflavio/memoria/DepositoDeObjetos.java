
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

package com.joseflavio.memoria;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Sistema de armazenamento avan�ado de {@link Objeto}s. Armazena uma c�pia {@link Serializable serializada} de cada {@link Objeto} indicado.<br>
 * Por quest�es de seguran�a da informa��o, o {@link DepositoDeObjetos} n�o mant�m relacionamento com os {@link Objeto}s externos, mesmo os obtidos atrav�s dela.<br>
 * Portanto, altera��es realizadas em {@link Objeto}s ser�o persistidas somente atrav�s do m�todo {@link #armazenar(Objeto, boolean)} ou {@link #sincronizar(ObjetoSincronizavel, boolean)}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface DepositoDeObjetos {
	
	//TODO Transa��o
	//TODO Controle de inser��o (chaves prim�rias)
	
	/**
	 * Armazena uma c�pia do {@link Objeto} no {@link DepositoDeObjetos} de tal forma que ele possa ser recuperado posteriormente.<br>
	 * O {@link DepositoDeObjetos} armazena apenas uma �nica c�pia de cada {@link Objeto}.<br>
	 * Caso o {@link Objeto} n�o possua {@link Objeto#getIdentidade() identidade}, ser� gerada uma para ele automaticamente.<br>
	 * Caso o {@link Objeto} j� tenha sido anteriormente armazenado, sua c�pia ser� substitu�da.
	 * @param cascata Determina que os {@link Objeto}s referenciados pelo {@link Objeto} em quest�o dever�o tamb�m ser armazenados.
	 */
	public void armazenar( Objeto objeto, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #armazenar(Objeto, boolean)} em cascata.
	 */
	public void armazenar( Objeto objeto ) throws DepositoException;
	
	/**
	 * Sincroniza o conte�do do {@link ObjetoSincronizavel} com a sua c�pia no {@link DepositoDeObjetos} levando em considera��o a sua {@link ObjetoSincronizavel#getUltimaModificacao() data de modifica��o}.<br>
	 * Prevalece no {@link DepositoDeObjetos} o conte�do mais novo.<br>
	 * Caso o {@link ObjetoSincronizavel} n�o exista no {@link DepositoDeObjetos}, ele ser� {@link #armazenar(Objeto, boolean) armazenado}.<br>
	 * Caso o conte�do do {@link ObjetoSincronizavel} seja mais antigo que o da c�pia, ele ser� {@link #atualizar(Objeto) atualizado}.<br>
	 * O processamento no caso de datas iguais poder� ser ignorado.
	 * @param cascata Determina que os {@link ObjetoSincronizavel}s referenciados pelo {@link Objeto} em quest�o dever�o tamb�m ser sincronizados.
	 */
	public void sincronizar( ObjetoSincronizavel objeto, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #sincronizar(ObjetoSincronizavel, boolean)} em cascata.
	 */
	public void sincronizar( ObjetoSincronizavel objeto ) throws DepositoException;
	
	/**
	 * Atualiza o conte�do do {@link Objeto} de acordo com a sua c�pia.<br>
	 * Aos atributos do {@link Objeto} ser�o atribu�dos os valores contidos na c�pia armazenada.<br>
	 * Caso o {@link Objeto} n�o tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}, nada ser� feito.
	 */
	public void atualizar( Objeto objeto ) throws DepositoException;
	
	/**
	 * Remove a c�pia do {@link Objeto} contida no {@link DepositoDeObjetos}.<br>
	 * Caso o {@link Objeto} n�o tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}, nada ser� feito.<br>
	 * Caso opte por n�o {@link #existeDependencia(Class, long)}, a remo��o do {@link Objeto} poder� causar inconsist�ncia no {@link DepositoDeObjetos}.
	 * @param verificarDependencia Executa {@link #existeDependencia(Class, long)}, disparando {@link DepositoException#RAZAO_DEPENDENCIA} caso outros {@link Objeto}s fa�am refer�ncia ao(s) {@link Objeto}(s) a ser(em) removido(s).
	 * @param cascata Determina que os {@link Objeto}s referenciados pelo {@link Objeto} em quest�o dever�o tamb�m ser removidos.
	 */
	public void remover( Objeto objeto, boolean verificarDependencia, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #remover(Objeto, boolean, boolean)} em cascata e com verifica��o de depend�ncia.
	 */
	public void remover( Objeto objeto ) throws DepositoException;
	
	/**
	 * Remove a c�pia do {@link Objeto} especificado do {@link DepositoDeObjetos}.<br>
	 * Caso o {@link Objeto} n�o tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}, nada ser� feito.<br>
	 * Caso opte por n�o {@link #existeDependencia(Class, long)}, a remo��o do {@link Objeto} poder� causar inconsist�ncia no {@link DepositoDeObjetos}.
	 * @param classe Classe mais espec�fica do {@link Objeto} desejado.
	 * @param identidade {@link Objeto#getIdentidade()}
	 * @param verificarDependencia Executa {@link #existeDependencia(Class, long)}, disparando {@link DepositoException#RAZAO_DEPENDENCIA} caso outros {@link Objeto}s fa�am refer�ncia ao(s) {@link Objeto}(s) a ser(em) removido(s).
	 * @param cascata Determina que os {@link Objeto}s referenciados pelo {@link Objeto} em quest�o dever�o tamb�m ser removidos.
	 */
	public void remover( Class<? extends Objeto> classe, long identidade, boolean verificarDependencia, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #remover(Class, long, boolean, boolean)} em cascata e com verifica��o de depend�ncia.
	 */
	public void remover( Class<? extends Objeto> classe, long identidade ) throws DepositoException;
	
	/**
	 * Verifica se o {@link Objeto} indicado existe neste {@link DepositoDeObjetos}.
	 */
	public boolean existe( Class<? extends Objeto> classe, long identidade ) throws DepositoException;
	
	/**
	 * Verifica se outros {@link Objeto}s no {@link DepositoDeObjetos} referenciam o {@link Objeto} indicado.
	 */
	public boolean existeDependencia( Class<? extends Objeto> classe, long identidade ) throws DepositoException;
	
	/**
	 * Contabiliza os {@link Objeto}s do {@link DepositoDeObjetos} que referenciam o {@link Objeto} indicado.
	 */
	public long contarDependencia( Class<? extends Objeto> classe, long identidade ) throws DepositoException;
	
	/**
	 * Lista os {@link Objeto}s do {@link DepositoDeObjetos} que referenciam o {@link Objeto} indicado.
	 * @return lista vazia, caso n�o haja refer�ncias.
	 */
	public List<Objeto> listarDependencia( Class<? extends Objeto> classe, long identidade ) throws DepositoException;
	
	/**
	 * Obt�m do {@link DepositoDeObjetos} o {@link Objeto} especificado.
	 * @param classe Classe mais espec�fica do {@link Objeto} desejado.
	 * @param identidade {@link Objeto#getIdentidade()}
	 * @return <code>null</code>, caso o {@link Objeto} n�o tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}.
	 * @throws DepositoException caso haja inconsist�ncia.
	 */
	public <T extends Objeto> T obter( Class<T> classe, long identidade ) throws DepositoException;
	
	/**
	 * Obt�m do {@link DepositoDeObjetos} o {@link Objeto} especificado levando em considera��o um cache de {@link Objeto}s.<br>
	 * O cache mant�m um conjunto de {@link Objeto}s em mem�ria.<br>
	 * Quaisquer {@link Objeto}s necess�rios ser�o primeiramente buscados no cache, incluse o {@link Objeto} desejado principal.<br>
	 * Quaisquer {@link Objeto}s n�o contidos no cache ser�o obtidos neste {@link DepositoDeObjetos} e imediatamente inseridos no cache.
	 * @param classe Classe mais espec�fica do {@link Objeto} desejado.
	 * @param identidade {@link Objeto#getIdentidade()}
	 * @return <code>null</code>, caso o {@link Objeto} n�o esteja no cache e n�o tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}.
	 * @throws DepositoException caso haja inconsist�ncia.
	 */
	public <T extends Objeto> T obter( Class<T> classe, long identidade, Map<Referencia, Objeto> cache ) throws DepositoException;
	
	/**
	 * Executa {@link #obter(Class, long)} sem verificar a consist�ncia referencial.<br>
	 * Todo {@link Objeto} referenciado inexistente ter� apenas a {@link Objeto#setIdentidade(long) identidade}.
	 */
	public <T extends Objeto> T obterSemVerificacao( Class<T> classe, long identidade ) throws DepositoException;
	
	/**
	 * Retorna o primeiro {@link Objeto} da lista obtida com {@link #listar(Class, Selecionador, Ordenador)}.
	 * @return <code>null</code>, caso nenhum {@link Objeto} seja selecionado.
	 * @throws DepositoException caso haja inconsist�ncia.
	 */
	public <T extends Objeto> T obter( Class<T> classe, Selecionador<T> selecionador, Ordenador<T> ordenador ) throws DepositoException;
	
	/**
	 * Lista {@link Objeto}s de uma classe de acordo com um {@link Selecionador} e um {@link Ordenador}.<br>
	 * S�o inclu�dos todos os {@link Objeto}s da classe especificada e de suas subclasses.
	 * @param classe Classe de {@link Objeto}s desejada.
	 * @param selecionador {@link Selecionador}. Pode ser null.
	 * @param ordenador {@link Ordenador}. Pode ser null.
	 * @return uma lista vazia, caso nenhum {@link Objeto} seja selecionado.
	 * @throws DepositoException caso haja inconsist�ncia.
	 */
	public <T extends Objeto> List<T> listar( Class<T> classe, Selecionador<T> selecionador, Ordenador<T> ordenador ) throws DepositoException;
	
	/**
	 * {@link #listar(Class, Selecionador, Ordenador)} sem {@link Selecionador} e {@link Ordenador}.
	 */
	public <T extends Objeto> List<T> listar( Class<T> classe ) throws DepositoException;
	
	/**
	 * {@link Enumeracao Enumera} {@link Objeto}s de uma classe de acordo com um {@link Selecionador} e um {@link Ordenador}.<br>
	 * S�o inclu�dos todos os {@link Objeto}s da classe especificada e de suas subclasses.
	 * @param classe Classe de {@link Objeto}s desejada.
	 * @param selecionador {@link Selecionador}. Pode ser null.
	 * @param ordenador {@link Ordenador}. Pode ser null.
	 * @throws DepositoException caso haja inconsist�ncia.
	 */
	public <T extends Objeto> Enumeracao<T> enumerar( Class<T> classe, Selecionador<T> selecionador, Ordenador<T> ordenador ) throws DepositoException;
	
	/**
	 * {@link #enumerar(Class, Selecionador, Ordenador)} sem {@link Selecionador} e {@link Ordenador}.
	 */
	public <T extends Objeto> Enumeracao<T> enumerar( Class<T> classe ) throws DepositoException;
	
	/**
	 * Contabiliza {@link Objeto}s de uma classe de acordo com um {@link Selecionador}.<br>
	 * S�o inclu�dos todos os {@link Objeto}s da classe especificada e de suas subclasses.
	 * @param classe Classe de {@link Objeto}s desejada.
	 * @param selecionador {@link Selecionador}. Pode ser null.
	 * @throws DepositoException caso haja inconsist�ncia.
	 */
	public <T extends Objeto> long contar( Class<T> classe, Selecionador<T> selecionador ) throws DepositoException;
	
	/**
	 * {@link #contar(Class, Selecionador)} sem {@link Selecionador}.
	 */
	public <T extends Objeto> long contar( Class<T> classe ) throws DepositoException;
	
}
