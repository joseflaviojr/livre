
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
 * @author José Flávio de Souza Dias Júnior
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
