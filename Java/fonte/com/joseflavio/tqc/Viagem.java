
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

package com.joseflavio.tqc;

import java.util.ArrayList;
import java.util.List;

import com.joseflavio.util.ListaUtil;


/**
 * Meio pelo qual se explora {@link Informacao informações}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public final class Viagem {

	private TomaraQueCaia tqc;
	
	private String nome;
	
	private List<Informacao> lista = new ArrayList<Informacao>();
	
	private Alerta alerta;
	
	private long passo = 0;
	
	Viagem( TomaraQueCaia tqc, String nome ) {
		this.tqc = tqc;
		this.nome = nome;
	}
	
	public Viagem visitar( Informacao informacao ) {
		if( informacao == null ) throw new IllegalArgumentException();
		lista.add( informacao );
		passo++;
		return this;
	}
	
	/**
	 * Volta para a {@link Informacao} anteriormente {@link #visitar(Informacao) visitada}.<br>
	 * Caso {@link #getAtual()} seja a única {@link Informacao} presente, a {@link Viagem} será {@link #encerrar() encerrada}.
	 */
	public Viagem voltar() {
		ListaUtil.menosUltimo( lista );
		if( lista.size() == 0 ) encerrar();
		passo++;
		return this;
	}

	/**
	 * Retorna a {@link Informacao} que está atualmente sendo {@link #visitar(Informacao) visitada}.
	 * @return <code>null</code> caso a {@link Viagem} não esteja visitando qualquer {@link Informacao}.
	 */
	public Informacao getAtual() {
		return ListaUtil.getUltimo( lista );
	}
	
	/**
	 * Retorna a {@link Informacao} anteriormente {@link #visitar(Informacao) visitada} e em condições de {@link #voltar()}.
	 */
	public Informacao getAnterior() {
		if( lista == null || lista.size() <= 1 ) return null;
		return lista.get( lista.size() - 2 );
	}
	
	/**
	 * {@link Viagem} sem {@link Informacao}?
	 * @return {@link #getAtual()} == <code>null</code>
	 */
	public boolean isVazia() {
		return getAtual() == null;
	}
	
	/**
	 * Remove todas as {@link Informacao informações} {@link #visitar(Informacao) visitadas}, porém não {@link #encerrar() encerra} a {@link Viagem}.
	 */
	public Viagem limpar() {
		lista.clear();
		alerta = null;
		passo++;
		return this;
	}
	
	/**
	 * Mostra o {@link Alerta} no primeiro momento oportuno, juntamente com a {@link Informacao} {@link #getAtual()}.<br>
	 * OBS.: os {@link Alerta}'s não se acumulam e são removidos logo após que são mostrados.
	 */
	public Viagem mostrar( Alerta alerta ){
		this.alerta = alerta;
		return this;
	}
	
	public Alerta alerta(){
		return alerta;
	}
	
	public Alerta removerAlerta(){
		Alerta a = alerta;
		alerta = null;
		return a;
	}
	
	public Viagem ativar(){
		tqc.ativar( this );
		return this;
	}
	
	/**
	 * Encerramento: limpa a {@link Viagem} e a retira da {@link TomaraQueCaia}.
	 */
	public void encerrar() {
		
		lista.clear();
		tqc.remover( this );

		tqc = null;
		lista = null;
		alerta = null;
		
	}
	
	public boolean isEncerrada() {
		return tqc == null;
	}
	
	/**
	 * {@link TomaraQueCaia} que usufrui desta {@link Viagem}.
	 */
	public TomaraQueCaia getTomaraQueCaia() {
		return tqc;
	}
	
	public String getNome() {
		return nome;
	}
	
	/**
	 * Número de passos dados na {@link Viagem}: {@link #visitar(Informacao)} + {@link #voltar()} + {@link #limpar()}.
	 */
	public long getPasso() {
		return passo;
	}
	
	/**
	 * @see TomaraQueCaia#novaViagem()
	 */
	public Viagem novaViagem() {
		return tqc.novaViagem();
	}
	
}
