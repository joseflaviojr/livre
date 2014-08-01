
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 * José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 * sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a versão 3 da Licença, como
 * (a seu critério) qualquer versão posterior.
 * 
 * José Flávio Livre é distribuído na expectativa de que seja útil,
 * porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 * COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 * Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 * junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc.teste;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.joseflavio.modelo.JFApresentacao;
import com.joseflavio.modelo.JFData;
import com.joseflavio.modelo.JFData.DataTipo;
import com.joseflavio.modelo.JFInteiro;
import com.joseflavio.modelo.JFReal;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoNaoNulo;
import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.modelo.JFValidacaoPrimitiva;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.modelo.JFValidacaoValorLimite;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;

/**
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
@JFApresentacao("Entidade")
@Entity
public class TesteTQCEntidade {
	
	@JFApresentacao("Id")
	@JFInteiro
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@JFApresentacao("Texto")
	@JFTexto(largura=30, max=30)
	@JFValidacaoPrimitiva(erro="Informe corretamente o texto.")
	@JFValidacaoNaoVazio(erro="Informe o texto.")
	@JFValidacaoTamanhoLimite(erro="O texto deve conter no máximo 30 caracteres.")
	private String texto = null;
	
	@JFApresentacao("Inteiro")
	@JFInteiro(min=10, max=50)
	@JFTexto(largura=15)
	@JFValidacaoPrimitiva(erro="Informe corretamente o inteiro.")
	@JFValidacaoNaoNulo(erro="Informe o inteiro.")
	@JFValidacaoValorLimite(erro="O inteiro deve ser um valor entre 10 e 50 (inclusive).")
	private long inteiro = 0;
	
	@JFApresentacao("Real")
	@JFReal(min=10.5, max=50.63, maxFracao=4)
	@JFTexto(largura=15)
	@JFValidacaoPrimitiva(erro="Informe corretamente o real.")
	@JFValidacaoNaoNulo(erro="Informe o real.")
	@JFValidacaoValorLimite(erro="O real deve ser um valor entre 10,5 e 50,63 (inclusive).")
	private double real = 0;
	
	@JFApresentacao("Data")
	@JFData(tipo=DataTipo.DATA)
	@JFTexto(largura=15)
	@JFValidacaoPrimitiva(erro="Informe corretamente a data.")
	@JFValidacaoNaoNulo(erro="Informe a data.")
	private Date data = null;
	
	@JFApresentacao("Seleção")
	@JFValidacaoPrimitiva(erro="Informe corretamente a seleção.")
	@JFValidacaoNaoNulo(erro="Informe a seleção.")
	private String selecao = null;
	
	@JFApresentacao("Selecionável Texto")
	@JFTexto(largura=30, max=30)
	@JFValidacaoPrimitiva(erro="Informe corretamente o selecionável texto.")
	@JFValidacaoNaoVazio(erro="Informe o selecionável texto.")
	@JFValidacaoTamanhoLimite(erro="O selecionável texto deve conter no máximo 30 caracteres.")
	private String selecionavelTexto = null;
	
	@JFApresentacao("Seleção Múltipla")
	@JFValidacaoPrimitiva(erro="Escolha corretamente na seleção múltipla.")
	@JFValidacaoNaoVazio(erro="Escolha na seleção múltipla.")
	private String[] selecaoMultipla = null;
	
	@JFApresentacao("Bruto")
	@JFValidacaoPrimitiva(erro="Informe corretamente o bruto.")
	@JFValidacaoNaoNulo(erro="Informe o bruto.")
	private byte[] bruto = null;
	
	@JFApresentacao("Arquivo")
	@JFValidacaoPrimitiva(erro="Informe corretamente o arquivo.")
	@JFValidacaoNaoNulo(erro="Informe o arquivo.")
	private File arquivo = null;
	
	@JFApresentacao("Senha")
	@JFTexto(largura=15, max=15)
	@JFValidacaoPrimitiva(erro="Informe corretamente a senha.")
	@JFValidacaoNaoVazio(erro="Informe a senha.")
	@JFValidacaoTamanhoLimite(erro="A senha deve conter no máximo 15 caracteres.")
	private String senha = null;
	
	@JFApresentacao("Binário")
	private boolean binario = false;
	
	public TesteTQCEntidade() {
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || ! ( obj instanceof TesteTQCEntidade ) ) return false;
		TesteTQCEntidade o = (TesteTQCEntidade) obj;
		return id == o.id || ( id != null && o.id != null && id.equals( o.id ) );
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return texto;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto( String texto ) {
		this.texto = texto;
	}
	
	public long getInteiro() {
		return inteiro;
	}

	public void setInteiro( long inteiro ) {
		this.inteiro = inteiro;
	}

	public double getReal() {
		return real;
	}
	
	public void setReal( double real ) {
		this.real = real;
	}

	public Date getData() {
		return data;
	}
	
	public void setData( Date data ) {
		this.data = data;
	}
	
	public String getSelecao() {
		return selecao;
	}
	
	public void setSelecao( String selecao ) {
		this.selecao = selecao;
	}

	public String getSelecionavelTexto() {
		return selecionavelTexto;
	}

	public void setSelecionavelTexto( String selecionavelTexto ) {
		this.selecionavelTexto = selecionavelTexto;
	}

	public String[] getSelecaoMultipla() {
		return selecaoMultipla;
	}
	
	public void setSelecaoMultipla( String[] selecaoMultipla ) {
		this.selecaoMultipla = selecaoMultipla;
	}
	
	public byte[] getBruto() {
		return bruto;
	}
	
	public void setBruto( byte[] bruto ) {
		this.bruto = bruto;
	}
	
	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo( File arquivo ) {
		this.arquivo = arquivo;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha( String senha ) {
		this.senha = senha;
	}
	
	public boolean isBinario() {
		return binario;
	}
	
	public void setBinario( boolean binario ) {
		this.binario = binario;
	}

	public static TesteTQCEntidade obter( AplicacaoTQC_JPA aplicacao, Long id ) {
		return aplicacao.obter( "select o from TesteTQCEntidade o where o.id = :p0", id );
	}

	public static List<TesteTQCEntidade> listar( AplicacaoTQC_JPA aplicacao ) {
		return aplicacao.listar( "select o from TesteTQCEntidade o order by o.texto asc" );
	}
	
}
