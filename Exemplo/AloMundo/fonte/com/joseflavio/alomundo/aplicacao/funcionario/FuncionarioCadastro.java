
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

package com.joseflavio.alomundo.aplicacao.funcionario;

import java.io.File;
import java.util.List;

import com.joseflavio.alomundo.AparenciaSexo;
import com.joseflavio.alomundo.Cargo;
import com.joseflavio.alomundo.Filial;
import com.joseflavio.alomundo.Funcionario;
import com.joseflavio.alomundo.aplicacao.AloMundo;
import com.joseflavio.alomundo.aplicacao.CadastroDeDependente;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.anotacao.TQCOpcoes;
import com.joseflavio.util.Lista;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class FuncionarioCadastro extends CadastroDeDependente<Funcionario, Filial> {
	
	/**
	 * Adi��o.
	 */
	public FuncionarioCadastro( AloMundo aplicacao, Filial filial ) throws TomaraQueCaiaException {
		super( aplicacao, new Funcionario(), true, filial );
	}
	
	/**
	 * Edi��o.
	 */
	public FuncionarioCadastro( AloMundo aplicacao, Funcionario funcionario ) throws TomaraQueCaiaException {
		super( aplicacao, funcionario, false, funcionario.getFilial() );
	}
	
	@Override
	protected void campos() throws TomaraQueCaiaException {

		maisSelecao( "filial", Funcionario.class, dependencia, true );
		maisTexto( "nome", Funcionario.class, objeto.getNome(), true );
		maisSelecao( "cargo", Funcionario.class, Cargo.values(), objeto.getCargo(), true );
		maisData( "nascimento", Funcionario.class, objeto.getNascimento(), true );
		maisSelecao( "sexo", Funcionario.class, new Boolean[]{ true, false }, objeto.isSexo(), true ).setAparencia( new AparenciaSexo() );
		maisSelecao( "gerente", Funcionario.class, objeto.getGerente(), true );
		maisReal( "salario", Funcionario.class, objeto.getSalario(), true );
		maisInteiro( "faltas", Funcionario.class, objeto.getFaltas(), true );
		maisArquivo( "contrato", Funcionario.class, novo ? null : new File( aplicacao.getLocalDocumentos(), objeto.getContrato() ), aplicacao.getLocalDocumentos(), true );

	}
	
	@Override
	protected void atribuicao() throws ValidacaoException, TomaraQueCaiaException {
		
		objeto.setFilial( this.<Filial>objeto( "filial" ) );
		objeto.setNome( texto( "nome" ) );
		objeto.setCargo( this.<Cargo>objeto( "cargo" ) );
		objeto.setNascimento( data( "nascimento" ) );
		objeto.setSexo( logico( "sexo" ) );
		objeto.setGerente( this.<Funcionario>objeto( "gerente" ) );
		objeto.setSalario( real( "salario" ) );
		objeto.setFaltas( inteiro32( "faltas" ) );
		objeto.setContrato( this.<File>objeto( "contrato" ).getName() );
		
	}
	
	@TQCOpcoes(dado="filial")
	public List<Filial> opcoesDeFilial() {
		return Filial.listar( aplicacao );
	}
	
	@TQCOpcoes(dado="gerente")
	public List<Funcionario> opcoesDeGerente() {
		return new Lista<Funcionario>( null, Funcionario.listar( aplicacao, Cargo.GERENTE ) );
	}
	
}
