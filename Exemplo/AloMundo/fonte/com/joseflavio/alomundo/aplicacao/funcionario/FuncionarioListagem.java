
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

import com.joseflavio.alomundo.Filial;
import com.joseflavio.alomundo.Funcionario;
import com.joseflavio.alomundo.aplicacao.AloMundo;
import com.joseflavio.alomundo.aplicacao.ListagemDeDependentes;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.aplicacao.ComandoApagar;
import com.joseflavio.tqc.aplicacao.ComandoImplementado;
import com.joseflavio.tqc.dado.Arquivo;
import com.joseflavio.tqc.molde.ListaMolde.ListaColunaPadrao;
import com.joseflavio.util.Lista;
import com.joseflavio.util.ModeloUtil;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class FuncionarioListagem extends ListagemDeDependentes<Funcionario, Filial> {

	public FuncionarioListagem( AloMundo aplicacao, Filial filial ) throws TomaraQueCaiaException {
		super( aplicacao, FuncionarioCadastro.class, filial, ModeloUtil.getApresentacao( Funcionario.class, true ) + " - " + filial.getSigla(), ModeloUtil.getApresentacao( Funcionario.class, true ) + " - " + filial.getNome() );
	}
	
	@Override
	protected void colunas() throws TomaraQueCaiaException {
		maisColunaTexto( Funcionario.class, "nome" );
		maisColunaData( Funcionario.class, "nascimento" );
		maisColunaTexto( Funcionario.class, "cargo" );
		maisColunaInteiro( Funcionario.class, "faltas" );
		maisColuna( new ColunaContrato() );
		maisColuna( new ColunaAcoes() );
	}

	@Override
	protected void objetos( List<Funcionario> lista ) throws TomaraQueCaiaException {
		lista.addAll( Funcionario.listar( aplicacao, dependencia ) );
	}
	
	@Override
	protected Lista<Dado> acoesPara( Funcionario elemento ) {
		return
				super.acoesPara( elemento )
				.mais( new ComandoApagar( "Apagar Direto", aplicacao, elemento, "Não foi possível apagar o cadastro do funcionário porque há referências a ele no sistema.", true ) )
				.mais( new ComandoFaltou( elemento ) );
	}
	
	private class ColunaContrato extends ListaColunaPadrao<Funcionario> {
		
		public String getTitulo() {
			return "Contrato";
		}
		
		public Dado getDado( Funcionario elemento ) {
			return new Arquivo( new File( aplicacao.getLocalDocumentos(), elemento.getContrato() ) );
		}
		
	}

	private class ComandoFaltou extends ComandoImplementado {

		private Funcionario elemento;
		
		public ComandoFaltou( Funcionario elemento ) {
			super( "Faltou" );
			this.elemento = elemento;
		}
		
		public void acao( Viagem viagem ) throws ValidacaoException, TomaraQueCaiaException {
			elemento.setFaltas( elemento.getFaltas() + 1 );
			aplicacao.persistirPendencias();
		}
		
	}
	
}
