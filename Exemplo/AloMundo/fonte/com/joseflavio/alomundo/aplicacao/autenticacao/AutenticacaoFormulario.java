
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

package com.joseflavio.alomundo.aplicacao.autenticacao;

import com.joseflavio.alomundo.Usuario;
import com.joseflavio.alomundo.aplicacao.AloMundo;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.Viajante;
import com.joseflavio.tqc.anotacao.TQCComando;
import com.joseflavio.tqc.aplicacao.Formulario;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class AutenticacaoFormulario extends Formulario<AloMundo> {
	
	private Viajante destinoOk;
	
	private Viajante destinoCancelado;
	
	public AutenticacaoFormulario( AloMundo aplicacao, Viajante destinoOk, Viajante destinoCancelado ) throws TomaraQueCaiaException {
		
		super( aplicacao, aplicacao.getTitulo(), "Autenticação" );
		
		if( destinoOk == null || destinoCancelado == null ) throw new IllegalArgumentException();
		
		this.destinoOk = destinoOk;
		this.destinoCancelado = destinoCancelado;
		
		construir();
		
	}
	
	protected void campos() throws TomaraQueCaiaException {
		
		maisTexto( "usuario", Usuario.class, "admin", true );
		maisSenha( "senha", Usuario.class, "1234", true );
		
	}
	
	@TQCComando( rotulo="OK", nome="ok", ordem=1, funcao=Funcao.CONFIRMAR )
	public void ok( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		validarTudo();
		
		String usuarioNome = texto( "usuario" );
		String usuarioSenha = texto( "senha" );
		
		Usuario usuario = Usuario.obter( aplicacao, usuarioNome );
		
		if( usuario == null ) throw ValidacaoException.novoErro( "Usuário não encontrado." );
			
		if( ! usuario.getSenha().equals( usuarioSenha ) ) throw ValidacaoException.novoErro( "Credencial inválida." );
		
		aplicacao.setUsuario( usuario );
			
		destinoOk.viajar( aplicacao, viagem );
			
	}
	
	@TQCComando( rotulo="Cancelar", nome="cancelar", funcao=Funcao.CANCELAR )
	public void cancelar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		destinoCancelado.viajar( aplicacao, viagem );
		
	}

	public void sair( Viagem viagem ) {
	}
	
}
