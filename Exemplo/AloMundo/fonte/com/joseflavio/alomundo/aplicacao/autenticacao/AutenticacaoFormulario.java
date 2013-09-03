
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
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
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class AutenticacaoFormulario extends Formulario<AloMundo> {
	
	private Viajante destinoOk;
	
	private Viajante destinoCancelado;
	
	public AutenticacaoFormulario( AloMundo aplicacao, Viajante destinoOk, Viajante destinoCancelado ) throws TomaraQueCaiaException {
		
		super( aplicacao, aplicacao.getTitulo(), "Autentica��o" );
		
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
		
		if( usuario == null ) throw ValidacaoException.novoErro( "Usu�rio n�o encontrado." );
			
		if( ! usuario.getSenha().equals( usuarioSenha ) ) throw ValidacaoException.novoErro( "Credencial inv�lida." );
		
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
