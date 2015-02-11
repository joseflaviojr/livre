
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

package com.joseflavio.tqc.teste;

import java.io.File;
import java.util.Date;

import com.joseflavio.tqc.Alerta;
import com.joseflavio.tqc.Menu;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.anotacao.TQCComando;
import com.joseflavio.tqc.aplicacao.BaseInformacao;
import com.joseflavio.tqc.aplicacao.ComandoVoltar;
import com.joseflavio.tqc.aplicacao.Formulario;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.tqc.dado.Comando.Funcao;
import com.joseflavio.tqc.dado.Imagem;
import com.joseflavio.tqc.dado.Texto;
import com.joseflavio.validacao.ValidacaoException;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class TesteTQCFormulario extends Formulario<TesteTQC> {

	private boolean editavel = true;
	private boolean personalizado = false;
	
	public TesteTQCFormulario( TesteTQC teste ) throws TomaraQueCaiaException {
		
		super( teste, teste.getTitulo(), null, "Teste de Formul�rio" );
		
		setPersonalizada( personalizado );
		
		aplicacao.getViagemAtiva().mostrar( Alerta.novaAtencao( "Aten��o", "Favor preencher todos os campos." ) );
		
		construir();
		
	}
	
	@Override
	protected void antesDeMostrar( Viagem viagem ) throws TomaraQueCaiaException {
		
		getComando( "editavel" ).setRotulo( editavel ? "N�o Edit�vel" : "Edit�vel" );
		getComando( "personalizado" ).setRotulo( personalizado ? "N�o Personalizado" : "Personalizado" );
		
	}
	
	@Override
	protected void menu() throws TomaraQueCaiaException {
		
		maisOpcaoDeMenu( getComando( "personalizado" ) );
		maisOpcaoDeMenu( getComando( "alerta" ) );
		maisOpcaoDeMenu( getComando( "espera" ) );
		
		Menu a = new Menu( "Aaaaaaaaaaa" );
		Menu b = new Menu( "Bbbbbbbbbbb" );
		Menu c = new Menu( "Ccccccccccc" );
		
		a.mais( b );
		b.mais( c );
		c.mais( getComando( "editavel" ) );
		
		maisOpcaoDeMenu( a );
		
	}
	
	@Override
	protected void campos() throws TomaraQueCaiaException {
		
		maisTexto( "texto", TesteTQCEntidade.class, "Tomara Que Caia", true );
		maisInteiro( "inteiro", TesteTQCEntidade.class, 21L, true );
		maisReal( "real", TesteTQCEntidade.class, 21D, true );
		maisData( "data", TesteTQCEntidade.class, new Date(), true );
		maisSelecao( "selecao", TesteTQCEntidade.class, new String[]{ null, "Op��o 1", "Op��o 2", "Op��o 3", "Op��o 4" }, true );
		maisSelecaoMultipla( "selecaoMultipla", TesteTQCEntidade.class, new String[]{ "Op��o 1", "Op��o 2", "Op��o 3", "Op��o 4" }, null, true );
		maisSelecionavelTexto( "selecionavelTexto", TesteTQCEntidade.class, "Op��o 1", new String[]{ "Op��o 1", "Op��o 2", "Op��o 3", "Op��o 4" }, true );
		maisBruto( "bruto", TesteTQCEntidade.class, null, null, true );
		maisArquivo( "arquivo", TesteTQCEntidade.class, null, new File( aplicacao.getRaiz(), "tmp" ), true );
		maisSenha( "senha", TesteTQCEntidade.class, "1234", true );
		maisBinario( "binario", TesteTQCEntidade.class, false, true );
		maisCampo( "Imagem:", new Imagem( "imagem", "img/icone/128/globo.001.png" ).setTamanho( 64, 64 ) );
		
	}
	
	@TQCComando( rotulo="OK", nome="ok", ordem=1 )
	public void ok( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		validarTudo();
		
		setMensagemInformacao( "Dados validados com sucesso." );
		
	}
	
	@TQCComando( rotulo="", nome="editavel", ordem=2 )
	public void editavel( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		editavel = ! editavel;
		
		setEditavel( "texto", editavel );
		setEditavel( "inteiro", editavel );
		setEditavel( "real", editavel );
		setEditavel( "data", editavel );
		setEditavel( "selecao", editavel );
		setEditavel( "selecaoMultipla", editavel );
		setEditavel( "selecionavelTexto", editavel );
		setEditavel( "bruto", editavel );
		setEditavel( "arquivo", editavel );
		setEditavel( "senha", editavel );
		setEditavel( "binario", editavel );
		
	}
	
	@TQCComando( rotulo="", nome="personalizado", ordem=3 )
	public void personalizado( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
	
		personalizado = ! personalizado;
		
		setPersonalizada( personalizado );
		
	}
	
	@TQCComando( rotulo="Sair", nome="sair", imagem="img/iconep/remover.png", funcao=Funcao.CANCELAR )
	public void sair( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		aplicacao.encerrarViagens();
		
	}
	
	@TQCComando( rotulo="Teste de Alerta", nome="alerta", adicionar=false )
	public void alerta( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		viagem.mostrar( Alerta.novaInformacao( aplicacao.getTitulo(), "Teste de Alerta!" ) );
		
	}
	
	@TQCComando( rotulo="Teste de Espera", nome="espera", adicionar=false )
	public void espera( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
		
		try{
			for( int i = 0; i < 10; i++ ) Thread.sleep( 2000 );
		}catch( InterruptedException e ){
		}
		
	}
	
	@Override
	public BaseInformacao<TesteTQC> criarAjuda( TesteTQC aplicacao ) throws TomaraQueCaiaException {
		BaseInformacao<TesteTQC> ajuda = new BaseInformacao<TesteTQC>( aplicacao, getTitulo(), "Ajuda" ) {
			{
				mais( new Texto( "Ajuda sobre a tela '" + TesteTQCFormulario.this.getTitulo() + "'." ) );
				maisEspacoTextual();
				mais( new ComandoVoltar( "Voltar" ) );
			}
			@Override
			protected void acao( Viagem viagem, Comando comando ) throws ValidacaoException, TomaraQueCaiaException {
			}
		};
		return ajuda;
	}
	
	@Override
	public boolean possuiAjuda() {
		return true;
	}
	
}
