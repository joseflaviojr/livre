
/*
 *  Copyright (C) 2009-2015 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2015 José Flávio de Souza Dias Júnior
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
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class TesteTQCFormulario extends Formulario<TesteTQC> {

	private boolean editavel = true;
	private boolean personalizado = false;
	
	public TesteTQCFormulario( TesteTQC teste ) throws TomaraQueCaiaException {
		
		super( teste, teste.getTitulo(), null, "Teste de Formulário" );
		
		setPersonalizada( personalizado );
		
		aplicacao.getViagemAtiva().mostrar( Alerta.novaAtencao( "Atenção", "Favor preencher todos os campos." ) );
		
		construir();
		
	}
	
	@Override
	protected void antesDeMostrar( Viagem viagem ) throws TomaraQueCaiaException {
		
		getComando( "editavel" ).setRotulo( editavel ? "Não Editável" : "Editável" );
		getComando( "personalizado" ).setRotulo( personalizado ? "Não Personalizado" : "Personalizado" );
		
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
		maisSelecao( "selecao", TesteTQCEntidade.class, new String[]{ null, "Opção 1", "Opção 2", "Opção 3", "Opção 4" }, true );
		maisSelecaoMultipla( "selecaoMultipla", TesteTQCEntidade.class, new String[]{ "Opção 1", "Opção 2", "Opção 3", "Opção 4" }, null, true );
		maisSelecionavelTexto( "selecionavelTexto", TesteTQCEntidade.class, "Opção 1", new String[]{ "Opção 1", "Opção 2", "Opção 3", "Opção 4" }, true );
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
