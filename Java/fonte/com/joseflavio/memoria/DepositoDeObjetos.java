
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

package com.joseflavio.memoria;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Sistema de armazenamento avançado de {@link Objeto}s. Armazena uma cópia {@link Serializable serializada} de cada {@link Objeto} indicado.<br>
 * Por questões de segurança da informação, o {@link DepositoDeObjetos} não mantém relacionamento com os {@link Objeto}s externos, mesmo os obtidos através dela.<br>
 * Portanto, alterações realizadas em {@link Objeto}s serão persistidas somente através do método {@link #armazenar(Objeto, boolean)} ou {@link #sincronizar(ObjetoSincronizavel, boolean)}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface DepositoDeObjetos {
	
	//TODO Transação
	//TODO Controle de inserção (chaves primárias)
	
	/**
	 * Armazena uma cópia do {@link Objeto} no {@link DepositoDeObjetos} de tal forma que ele possa ser recuperado posteriormente.<br>
	 * O {@link DepositoDeObjetos} armazena apenas uma única cópia de cada {@link Objeto}.<br>
	 * Caso o {@link Objeto} não possua {@link Objeto#getIdentidade() identidade}, será gerada uma para ele automaticamente.<br>
	 * Caso o {@link Objeto} já tenha sido anteriormente armazenado, sua cópia será substituída.
	 * @param cascata Determina que os {@link Objeto}s referenciados pelo {@link Objeto} em questão deverão também ser armazenados.
	 */
	public void armazenar( Objeto objeto, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #armazenar(Objeto, boolean)} em cascata.
	 */
	public void armazenar( Objeto objeto ) throws DepositoException;
	
	/**
	 * Sincroniza o conteúdo do {@link ObjetoSincronizavel} com a sua cópia no {@link DepositoDeObjetos} levando em consideração a sua {@link ObjetoSincronizavel#getUltimaModificacao() data de modificação}.<br>
	 * Prevalece no {@link DepositoDeObjetos} o conteúdo mais novo.<br>
	 * Caso o {@link ObjetoSincronizavel} não exista no {@link DepositoDeObjetos}, ele será {@link #armazenar(Objeto, boolean) armazenado}.<br>
	 * Caso o conteúdo do {@link ObjetoSincronizavel} seja mais antigo que o da cópia, ele será {@link #atualizar(Objeto) atualizado}.<br>
	 * O processamento no caso de datas iguais poderá ser ignorado.
	 * @param cascata Determina que os {@link ObjetoSincronizavel}s referenciados pelo {@link Objeto} em questão deverão também ser sincronizados.
	 */
	public void sincronizar( ObjetoSincronizavel objeto, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #sincronizar(ObjetoSincronizavel, boolean)} em cascata.
	 */
	public void sincronizar( ObjetoSincronizavel objeto ) throws DepositoException;
	
	/**
	 * Atualiza o conteúdo do {@link Objeto} de acordo com a sua cópia.<br>
	 * Aos atributos do {@link Objeto} serão atribuídos os valores contidos na cópia armazenada.<br>
	 * Caso o {@link Objeto} não tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}, nada será feito.
	 */
	public void atualizar( Objeto objeto ) throws DepositoException;
	
	/**
	 * Remove a cópia do {@link Objeto} contida no {@link DepositoDeObjetos}.<br>
	 * Caso o {@link Objeto} não tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}, nada será feito.<br>
	 * Caso opte por não {@link #existeDependencia(Class, long)}, a remoção do {@link Objeto} poderá causar inconsistência no {@link DepositoDeObjetos}.
	 * @param verificarDependencia Executa {@link #existeDependencia(Class, long)}, disparando {@link DepositoException#RAZAO_DEPENDENCIA} caso outros {@link Objeto}s façam referência ao(s) {@link Objeto}(s) a ser(em) removido(s).
	 * @param cascata Determina que os {@link Objeto}s referenciados pelo {@link Objeto} em questão deverão também ser removidos.
	 */
	public void remover( Objeto objeto, boolean verificarDependencia, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #remover(Objeto, boolean, boolean)} em cascata e com verificação de dependência.
	 */
	public void remover( Objeto objeto ) throws DepositoException;
	
	/**
	 * Remove a cópia do {@link Objeto} especificado do {@link DepositoDeObjetos}.<br>
	 * Caso o {@link Objeto} não tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}, nada será feito.<br>
	 * Caso opte por não {@link #existeDependencia(Class, long)}, a remoção do {@link Objeto} poderá causar inconsistência no {@link DepositoDeObjetos}.
	 * @param classe Classe mais específica do {@link Objeto} desejado.
	 * @param identidade {@link Objeto#getIdentidade()}
	 * @param verificarDependencia Executa {@link #existeDependencia(Class, long)}, disparando {@link DepositoException#RAZAO_DEPENDENCIA} caso outros {@link Objeto}s façam referência ao(s) {@link Objeto}(s) a ser(em) removido(s).
	 * @param cascata Determina que os {@link Objeto}s referenciados pelo {@link Objeto} em questão deverão também ser removidos.
	 */
	public void remover( Class<? extends Objeto> classe, long identidade, boolean verificarDependencia, boolean cascata ) throws DepositoException;
	
	/**
	 * {@link #remover(Class, long, boolean, boolean)} em cascata e com verificação de dependência.
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
	 * @return lista vazia, caso não haja referências.
	 */
	public List<Objeto> listarDependencia( Class<? extends Objeto> classe, long identidade ) throws DepositoException;
	
	/**
	 * Obtém do {@link DepositoDeObjetos} o {@link Objeto} especificado.
	 * @param classe Classe mais específica do {@link Objeto} desejado.
	 * @param identidade {@link Objeto#getIdentidade()}
	 * @return <code>null</code>, caso o {@link Objeto} não tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}.
	 * @throws DepositoException caso haja inconsistência.
	 */
	public <T extends Objeto> T obter( Class<T> classe, long identidade ) throws DepositoException;
	
	/**
	 * Obtém do {@link DepositoDeObjetos} o {@link Objeto} especificado levando em consideração um cache de {@link Objeto}s.<br>
	 * O cache mantém um conjunto de {@link Objeto}s em memória.<br>
	 * Quaisquer {@link Objeto}s necessários serão primeiramente buscados no cache, incluse o {@link Objeto} desejado principal.<br>
	 * Quaisquer {@link Objeto}s não contidos no cache serão obtidos neste {@link DepositoDeObjetos} e imediatamente inseridos no cache.
	 * @param classe Classe mais específica do {@link Objeto} desejado.
	 * @param identidade {@link Objeto#getIdentidade()}
	 * @return <code>null</code>, caso o {@link Objeto} não esteja no cache e não tenha sido anteriormente {@link #armazenar(Objeto, boolean) armazenado}.
	 * @throws DepositoException caso haja inconsistência.
	 */
	public <T extends Objeto> T obter( Class<T> classe, long identidade, Map<Referencia, Objeto> cache ) throws DepositoException;
	
	/**
	 * Executa {@link #obter(Class, long)} sem verificar a consistência referencial.<br>
	 * Todo {@link Objeto} referenciado inexistente terá apenas a {@link Objeto#setIdentidade(long) identidade}.
	 */
	public <T extends Objeto> T obterSemVerificacao( Class<T> classe, long identidade ) throws DepositoException;
	
	/**
	 * Retorna o primeiro {@link Objeto} da lista obtida com {@link #listar(Class, Selecionador, Ordenador)}.
	 * @return <code>null</code>, caso nenhum {@link Objeto} seja selecionado.
	 * @throws DepositoException caso haja inconsistência.
	 */
	public <T extends Objeto> T obter( Class<T> classe, Selecionador<T> selecionador, Ordenador<T> ordenador ) throws DepositoException;
	
	/**
	 * Lista {@link Objeto}s de uma classe de acordo com um {@link Selecionador} e um {@link Ordenador}.<br>
	 * São incluídos todos os {@link Objeto}s da classe especificada e de suas subclasses.
	 * @param classe Classe de {@link Objeto}s desejada.
	 * @param selecionador {@link Selecionador}. Pode ser null.
	 * @param ordenador {@link Ordenador}. Pode ser null.
	 * @return uma lista vazia, caso nenhum {@link Objeto} seja selecionado.
	 * @throws DepositoException caso haja inconsistência.
	 */
	public <T extends Objeto> List<T> listar( Class<T> classe, Selecionador<T> selecionador, Ordenador<T> ordenador ) throws DepositoException;
	
	/**
	 * {@link #listar(Class, Selecionador, Ordenador)} sem {@link Selecionador} e {@link Ordenador}.
	 */
	public <T extends Objeto> List<T> listar( Class<T> classe ) throws DepositoException;
	
	/**
	 * {@link Enumeracao Enumera} {@link Objeto}s de uma classe de acordo com um {@link Selecionador} e um {@link Ordenador}.<br>
	 * São incluídos todos os {@link Objeto}s da classe especificada e de suas subclasses.
	 * @param classe Classe de {@link Objeto}s desejada.
	 * @param selecionador {@link Selecionador}. Pode ser null.
	 * @param ordenador {@link Ordenador}. Pode ser null.
	 * @throws DepositoException caso haja inconsistência.
	 */
	public <T extends Objeto> Enumeracao<T> enumerar( Class<T> classe, Selecionador<T> selecionador, Ordenador<T> ordenador ) throws DepositoException;
	
	/**
	 * {@link #enumerar(Class, Selecionador, Ordenador)} sem {@link Selecionador} e {@link Ordenador}.
	 */
	public <T extends Objeto> Enumeracao<T> enumerar( Class<T> classe ) throws DepositoException;
	
	/**
	 * Contabiliza {@link Objeto}s de uma classe de acordo com um {@link Selecionador}.<br>
	 * São incluídos todos os {@link Objeto}s da classe especificada e de suas subclasses.
	 * @param classe Classe de {@link Objeto}s desejada.
	 * @param selecionador {@link Selecionador}. Pode ser null.
	 * @throws DepositoException caso haja inconsistência.
	 */
	public <T extends Objeto> long contar( Class<T> classe, Selecionador<T> selecionador ) throws DepositoException;
	
	/**
	 * {@link #contar(Class, Selecionador)} sem {@link Selecionador}.
	 */
	public <T extends Objeto> long contar( Class<T> classe ) throws DepositoException;
	
}
