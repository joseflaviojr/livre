
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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

package com.joseflavio.tqc.aplicacao;

import java.util.Date;

import com.joseflavio.modelo.AssistenteDeAtributo;
import com.joseflavio.modelo.JFData;
import com.joseflavio.tqc.Dado;
import com.joseflavio.tqc.Estilo;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Data;
import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.tqc.dado.Real;
import com.joseflavio.tqc.dado.Tabela;
import com.joseflavio.tqc.dado.TabelaColuna;
import com.joseflavio.tqc.dado.TabelaColunaFim;
import com.joseflavio.tqc.dado.TabelaFim;
import com.joseflavio.tqc.dado.TabelaLinha;
import com.joseflavio.tqc.dado.TabelaLinhaFim;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @deprecated Utilizar {@link Informacao#setPersonalizadaInterface(String)}.
 */
public abstract class Relatorio<TQC extends AplicacaoTQC> extends BaseInformacao<TQC> {
	
	private String tituloRelatorio;
	
	/**
	 * @see #construir()
	 */
	public Relatorio( TQC aplicacao, String tituloInformacao, String banner, String tituloRelatorio ) throws TomaraQueCaiaException {
		
		super( aplicacao, tituloInformacao, banner, null );
		
		this.tituloRelatorio = tituloRelatorio;
		
	}
	
	public Relatorio( TQC aplicacao, String tituloInformacao, String tituloRelatorio ) throws TomaraQueCaiaException {
		
		this( aplicacao, tituloInformacao, null, tituloRelatorio );
		
	}

	protected void construir() throws TomaraQueCaiaException {
		
		super.construir();
		
		/*----------------------*/
		
		if( tituloRelatorio != null ){
			maisQuebraDeLinha();
			maisLinhaCentral();
			mais( new Texto( tituloRelatorio ).setEstilo( Estilo.relatorioTitulo ) );
			maisLinhaFim();
		}
		
		/*----------------------*/
		
		maisMarcador( "tqc_relatorio_corpo_i" );
		corpo();
		maisMarcador( "tqc_relatorio_corpo_f" );
		
		/*----------------------*/
		
	}
	
	public String getTituloRelatorio() {
		return tituloRelatorio;
	}
	
	protected abstract void corpo() throws TomaraQueCaiaException;
	
	/**
	 * Nada faz.
	 */
	protected void acao( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
	}
	
	/**
	 * �rea de destaque com {@link Dado}s afins.
	 */
	protected void abrirSessao() {
		
		mais( new Tabela( Estilo.relatorioSessao ) );
		mais( new TabelaLinha() );
		mais( new TabelaColuna() );
		
		maisMarcador( "tqc_relatorio_sessao_i" );
		
	}
	
	protected void fecharSessao() {
		
		maisMarcador( "tqc_relatorio_sessao_f" );
		
		mais( new TabelaColunaFim() );
		mais( new TabelaLinhaFim() );
		mais( new TabelaFim() );
		
	}

	protected Texto normal( String texto ) {
		
		return (Texto) new Texto( texto ).setEstilo( Estilo.relatorioNormal );
		
	}
	
	protected Texto negrito( String texto ) {
		
		return (Texto) new Texto( texto ).setEstilo( Estilo.relatorioNegrito );
		
	}
	
	protected void maisNormal( String texto ) {
		
		maisTexto( texto ).setEstilo( Estilo.relatorioNormal );
		
	}
	
	protected void maisNegrito( String texto ) {
		
		maisTexto( texto ).setEstilo( Estilo.relatorioNegrito );
		
	}
	
	/**
	 * @see #maisAtributo(String, Dado)
	 */
	protected void abrirListaDeAtributos() {
		
		mais( new Tabela( "largura100Porc" ) );
		
		maisMarcador( "tqc_relatorio_lista_i" );
		
	}
	
	protected void fecharListaDeAtributos() {
		
		maisMarcador( "tqc_relatorio_lista_f" );
		
		mais( new TabelaFim() );
		
	}
	
	protected void maisAtributo( String rotulo, String valor ) {
		
		mais( new TabelaLinha() );
		mais( new TabelaColuna( Estilo.relatorioColunaRotulo ) );
		
		maisMarcador( "tqc_relatorio_lista_rotulo_i" );
		maisNormal( rotulo );
		maisMarcador( "tqc_relatorio_lista_rotulo_f" );
		
		mais( new TabelaColunaFim() );
		mais( new TabelaColuna( Estilo.relatorioColunaValor ) );
		
		maisMarcador( "tqc_relatorio_lista_valor_i" );
		maisNegrito( valor );
		maisMarcador( "tqc_relatorio_lista_valor_f" );
		
		mais( new TabelaColunaFim() );
		mais( new TabelaLinhaFim() );
		
	}
	
	protected void maisAtributo( String rotulo, Dado valor ) {
		
		mais( new TabelaLinha() );
		mais( new TabelaColuna( Estilo.relatorioColunaRotulo ) );
		
		maisMarcador( "tqc_relatorio_lista_rotulo_i" );
		maisNormal( rotulo );
		maisMarcador( "tqc_relatorio_lista_rotulo_f" );
		
		mais( new TabelaColunaFim() );
		mais( new TabelaColuna( Estilo.relatorioColunaValor ) );
		
		maisMarcador( "tqc_relatorio_lista_valor_i" );
		mais( valor );
		maisMarcador( "tqc_relatorio_lista_valor_f" );
		
		mais( new TabelaColunaFim() );
		mais( new TabelaLinhaFim() );
		
	}
	
	protected void maisAtributoTexto( Class<? extends Object> classe, String atributo, String valor ) {
		maisAtributo( Formulario.getRotulo( classe, atributo ), new Texto( null, classe, atributo, valor, false ).setEstilo( Estilo.relatorioNegrito ) );
	}
	
	protected void maisAtributoObjeto( Class<? extends Object> classe, String atributo, Object valor ) {
		maisAtributo( Formulario.getRotulo( classe, atributo ), valor != null ? valor.toString() : "" );
	}
	
	protected void maisAtributoData( Class<? extends Object> classe, String atributo, Date valor ) {
		switch( AssistenteDeAtributo.getAnotacao( classe, atributo, JFData.class, true ).tipo() ){
			case DATA :
				maisAtributo( Formulario.getRotulo( classe, atributo ), new Data( valor, Data.DataTipo.DATA ).setEstilo( Estilo.relatorioNegrito ) );
				break;
			case HORA :
				maisAtributo( Formulario.getRotulo( classe, atributo ), new Data( valor, Data.DataTipo.HORA ).setEstilo( Estilo.relatorioNegrito ) );
				break;
			default :
				maisAtributo( Formulario.getRotulo( classe, atributo ), new Data( valor, Data.DataTipo.DATA_HORA ).setEstilo( Estilo.relatorioNegrito ) );
				break;
		}
	}
	
	protected void maisAtributoInteiro( Class<? extends Object> classe, String atributo, Long valor ) {
		maisAtributo( Formulario.getRotulo( classe, atributo ), new Inteiro( null, classe, atributo, valor, false ).setEstilo( Estilo.relatorioNegrito ) );
	}
	
	protected void maisAtributoReal( Class<? extends Object> classe, String atributo, Double valor ) {
		maisAtributo( Formulario.getRotulo( classe, atributo ), new Real( null, classe, atributo, valor, false ).setEstilo( Estilo.relatorioNegrito ) );
	}
	
	protected void maisAtributoBinario( Class<? extends Object> classe, String atributo, Boolean valor, String valorVerdadeiro, String valorFalso ) {
		maisAtributo( Formulario.getRotulo( classe, atributo ), valor != null && valor ? valorVerdadeiro : valorFalso );
	}
	
}
