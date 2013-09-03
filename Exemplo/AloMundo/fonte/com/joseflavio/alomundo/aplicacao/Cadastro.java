
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

package com.joseflavio.alomundo.aplicacao;

import com.joseflavio.modelo.JFApresentacao;
import com.joseflavio.tqc.OpcaoDeMenu;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.anotacao.TQCComando;
import com.joseflavio.tqc.aplicacao.Formulario;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.util.ModeloUtil;
import com.joseflavio.validacao.ValidacaoException;

/**
 * {@link Formulario} de cadastro (adi��o/edi��o) de objeto.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public abstract class Cadastro<O> extends Formulario<AloMundo> {
	
	protected O objeto;
	
	protected boolean novo;
	
	protected Cadastro( AloMundo aplicacao, O objeto, boolean novo, String titulo, String subtitulo, boolean construir ) throws TomaraQueCaiaException {
		
		super( aplicacao, getTitulo( objeto, novo, titulo ), getTitulo( objeto, novo, subtitulo ) );
		
		this.objeto = objeto;
		this.novo = novo;
		
		if( construir ) construir();
		
	}
	
	protected Cadastro( AloMundo aplicacao, O objeto, boolean novo, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		this( aplicacao, objeto, novo, titulo, subtitulo, true );
	}
	
	protected Cadastro( AloMundo aplicacao, O objeto, boolean novo, String titulo ) throws TomaraQueCaiaException {
		this( aplicacao, objeto, novo, titulo, titulo, true );
	}
	
	protected Cadastro( AloMundo aplicacao, O objeto, boolean novo ) throws TomaraQueCaiaException {
		this( aplicacao, objeto, novo, "", "", true );
	}
	
	private static String getTitulo( Object objeto, boolean novo, String formato ) throws TomaraQueCaiaException {
		
		if( formato == null ) return null;
		
		JFApresentacao jfa = ModeloUtil.getJFApresentacao( objeto.getClass() );
		if( jfa == null ) throw new IllegalArgumentException( JFApresentacao.class.getSimpleName() );
		
		String singular = jfa.value();
		String plural = jfa.plural();
		
		if( formato != null && formato.length() > 0 ) return formato.replaceAll( "%%s", singular ).replaceAll( "%%p", plural );
		
		String prefixo = novo ? ( jfa.masculino() ? "Novo " : "Nova " ) : "";
		return prefixo + singular;
		
	}
	
	@Override
	protected void menu() throws TomaraQueCaiaException {
		for( OpcaoDeMenu om : aplicacao.getMenuPadrao() ) maisOpcaoDeMenu( om );
	}
	
	protected abstract void atribuicao() throws ValidacaoException, TomaraQueCaiaException;
	
	protected void atribuicao2() throws ValidacaoException, TomaraQueCaiaException {
	}
	
	@TQCComando( rotulo="Salvar", nome="salvar", ordem=1, funcao=Funcao.CONFIRMAR )
	public void salvar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		validarTudo();
		
		atribuicao();
		
		if( novo ) objeto = aplicacao.persistir( objeto );
		aplicacao.persistirPendencias();
		
		atribuicao2();
		
		aplicacao.persistirPendencias();

		sair( viagem );
		
	}
	
	@TQCComando( rotulo="Cancelar", nome="cancelar", funcao=Funcao.CANCELAR )
	public void cancelar( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		sair( viagem );
		
	}
	
}
