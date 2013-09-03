
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
 * @author Jos� Fl�vio de Souza Dias J�nior
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
				.mais( new ComandoApagar( "Apagar Direto", aplicacao, elemento, "N�o foi poss�vel apagar o cadastro do funcion�rio porque h� refer�ncias a ele no sistema.", true ) )
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
