
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
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class FuncionarioCadastro extends CadastroDeDependente<Funcionario, Filial> {
	
	/**
	 * Adição.
	 */
	public FuncionarioCadastro( AloMundo aplicacao, Filial filial ) throws TomaraQueCaiaException {
		super( aplicacao, new Funcionario(), true, filial );
	}
	
	/**
	 * Edição.
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
