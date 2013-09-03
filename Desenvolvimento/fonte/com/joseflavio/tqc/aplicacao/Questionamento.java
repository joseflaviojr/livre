
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

import com.joseflavio.tqc.AcionamentoDeComando;
import com.joseflavio.tqc.EspacoTextual;
import com.joseflavio.tqc.Estilo;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Linha;
import com.joseflavio.tqc.dado.LinhaFim;
import com.joseflavio.tqc.dado.Marcador;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Questionamento<T extends AplicacaoTQC> extends BaseInformacao<T> {
	
	private AcionamentoDeComando questionador;
	
	private Marcador marcadorMensagem = new Marcador();
	
	private Marcador marcadorResposta = new Marcador();
	
	private boolean primeiraResposta = true;
	
	/**
	 * @see #maisMensagem(String)
	 * @see #maisResposta(Comando) 
	 */
	public Questionamento( T aplicacao, String titulo, String banner, AcionamentoDeComando questionador ) throws TomaraQueCaiaException {
		
		super( aplicacao, titulo, banner, null );
		
		this.questionador = questionador;
		
		/*----------------------*/
		
		construir();
		
		/*----------------------*/
		
		maisQuebraDeLinha();
		
		maisLinhaCentral();
		mais( marcadorMensagem );
		maisLinhaFim();

		/*----------------------*/
		
		maisQuebraDeLinha();
		
		maisLinhaCentral();
		mais( marcadorResposta );
		maisLinhaFim();
		
		/*----------------------*/
		
	}
	
	public Questionamento( T aplicacao, String titulo, AcionamentoDeComando questionador ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, null, questionador );
	}
	
	public Questionamento<T> maisMensagem( String texto ) {
		
		int pos = getIndice( marcadorMensagem );
		
		mais( new Linha( Estilo.centroH ), pos++ );
		mais( new Texto( texto ).setEstilo( Estilo.questMensagem ), pos++ );
		mais( new LinhaFim(), pos++ );
		
		return this;
		
	}
	
	public Questionamento<T> maisResposta( Comando comando ) {
		
		if( comando.getEstilo() == null ) comando.setEstilo( Estilo.questComandoResposta );
		int pos = getIndice( marcadorResposta );
		
		if( ! primeiraResposta ) mais( new EspacoTextual( 1 ), pos++ );
		mais( comando, pos );
		primeiraResposta = false;
		
		return this;
		
	}
	
	protected void acao( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		if( questionador != null ){
			
			questionador.acao( aplicacao, viagem, comando );
			
		}
		
	}

}
