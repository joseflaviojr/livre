
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

package com.joseflavio.tqc.aplicacao;

import com.joseflavio.CapturaDeExcecao;
import com.joseflavio.tqc.AcionamentoDeComando;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
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
		this( aplicacao, null, rotulo, "Sim", "N�o", pergunta );
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
