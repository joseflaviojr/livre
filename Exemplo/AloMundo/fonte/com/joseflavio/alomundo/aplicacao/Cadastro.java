
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
 * {@link Formulario} de cadastro (adição/edição) de objeto.
 * @author José Flávio de Souza Dias Júnior
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
