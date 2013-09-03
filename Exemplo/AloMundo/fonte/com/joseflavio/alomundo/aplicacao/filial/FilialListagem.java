
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

package com.joseflavio.alomundo.aplicacao.filial;

import java.util.List;

import com.joseflavio.alomundo.Filial;
import com.joseflavio.alomundo.Funcionario;
import com.joseflavio.alomundo.aplicacao.AloMundo;
import com.joseflavio.alomundo.aplicacao.Listagem;
import com.joseflavio.alomundo.aplicacao.funcionario.FuncionarioListagem;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.aplicacao.ComandoVisitar;
import com.joseflavio.util.Lista;
import com.joseflavio.util.ModeloUtil;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class FilialListagem extends Listagem<Filial> {

	public FilialListagem( AloMundo aplicacao ) throws TomaraQueCaiaException {
		super( aplicacao, FilialCadastro.class, ModeloUtil.getApresentacao( Filial.class, true ) );
	}
	
	@Override
	protected void colunas() throws TomaraQueCaiaException {
		maisColunaTexto( Filial.class, "sigla" );
		maisColunaTexto( Filial.class, "nome" );
		maisColuna( new ColunaAcoes() );
	}

	@Override
	protected void objetos( List<Filial> lista ) throws TomaraQueCaiaException {
		lista.addAll( Filial.listar( aplicacao ) );
	}
	
	@Override
	protected Lista<Dado> acoesPara( Filial elemento ) {
		return super.acoesPara( elemento ).mais( new ComandoVisitar( ModeloUtil.getApresentacao( Funcionario.class, true ), FuncionarioListagem.class, aplicacao, elemento ) );
	}
	
}
