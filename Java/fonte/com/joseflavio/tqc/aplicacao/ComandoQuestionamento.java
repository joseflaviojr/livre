
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

package com.joseflavio.tqc.aplicacao;

import com.joseflavio.CapturaDeExcecao;
import com.joseflavio.tqc.AcionamentoDeComando;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class ComandoQuestionamento<T extends AplicacaoTQC> extends ComandoImplementado implements AcionamentoDeComando {

	private T aplicacao;
	
	private Comando cmdSim;
	private Comando cmdNao;
	
	private String pergunta;
	
	public ComandoQuestionamento( T aplicacao, String nome, String rotulo, String rotuloSim, String rotuloNao, String pergunta ) {
		super( nome, rotulo );
		this.aplicacao = aplicacao;
		this.cmdSim = new Comando( rotuloSim );
		this.cmdNao = new Comando( rotuloNao );
		this.pergunta = pergunta;
	}
	
	public ComandoQuestionamento( T aplicacao, String rotulo, String rotuloSim, String rotuloNao, String pergunta ) {
		this( aplicacao, null, rotulo, rotuloSim, rotuloNao, pergunta );
	}
	
	public ComandoQuestionamento( T aplicacao, String rotulo, String pergunta ) {
		this( aplicacao, null, rotulo, "Sim", "Não", pergunta );
	}

	public final void acao( Viagem viagem ) throws ValidacaoException, TomaraQueCaiaException {
		
		CapturaDeExcecao capturaDeExcecao = viagem.getAtual() instanceof CapturaDeExcecao ? (CapturaDeExcecao) viagem.getAtual() : null;
		
		viagem.visitar( new Questionamento<T>( aplicacao, aplicacao.getTitulo(), null, this, capturaDeExcecao ).maisMensagem( pergunta ).maisResposta( cmdSim ).maisResposta( cmdNao ) );
		
	}
	
	public final void acao( TomaraQueCaia tqc, Viagem viagem, Comando comando ) throws TomaraQueCaiaException {
		try{
			viagem.voltar();
			if( comando == cmdSim ) sim( aplicacao, viagem );
			else if( comando == cmdNao ) nao( aplicacao, viagem );
		}catch( TomaraQueCaiaException e ){
			throw e;
		}
	}
	
	public abstract void sim( T aplicacao, Viagem viagem ) throws TomaraQueCaiaException;
	
	public void nao( T aplicacao, Viagem viagem ) throws TomaraQueCaiaException {
	}
	
}
