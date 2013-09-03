
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

package com.joseflavio.alomundo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
 * Funcionário da empresa.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
@JFApresentacao(value="Funcionário", plural="Funcionários", masculino=true)
@Entity
public class Funcionario {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@JFApresentacao("Filial")
	@JFValidacaoNaoNulo(erro="Informe a filial.")
	@ManyToOne
	private Filial filial;

	@JFApresentacao("Nome")
	@JFTexto(largura=50, max=50)
	@JFValidacaoNaoVazio(erro="Informe o nome.")
	@JFValidacaoTamanhoLimite(erro="O nome deve conter no máximo 50 caracteres.")
	@Column(length=50)
	private String nome;
	
	@JFApresentacao("Cargo")
	@JFValidacaoNaoNulo(erro="Informe o cargo.")
	@Enumerated(EnumType.ORDINAL)
	private Cargo cargo;
	
	@JFApresentacao("Nascimento")
	@JFData(tipo=DataTipo.DATA)
	@JFValidacaoPrimitiva(erro="Informe corretamente a data de nascimento.")
	@JFValidacaoNaoNulo(erro="Informe a data de nascimento.")
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	
	@JFApresentacao("Sexo")
	@JFValidacaoNaoNulo(erro="Informe o sexo.")
	private boolean sexo;
	
	@JFApresentacao("Gerente")
	@ManyToOne
	private Funcionario gerente;
	
	@JFApresentacao("Salário")
	@JFReal(min=0,maxFracao=2)
	@JFTexto(largura=20, max=20)
	@JFValidacaoPrimitiva(erro="Informe corretamente o salário.")
	@JFValidacaoNaoNulo(erro="Informe o salário.")
	@JFValidacaoTamanhoLimite(erro="O salário deve conter no máximo 20 caracteres.")
	@JFValidacaoValorLimite(erro="Informe corretamente o salário.")
	private double salario;
	
	@JFApresentacao("Faltas")
	@JFInteiro(min=0,max=365)
	@JFTexto(largura=10, max=10)
	@JFValidacaoPrimitiva(erro="Informe corretamente o número de faltas.")
	@JFValidacaoNaoNulo(erro="Informe o número de faltas.")
	@JFValidacaoTamanhoLimite(erro="O número de faltas deve conter no máximo 10 caracteres.")
	@JFValidacaoValorLimite(erro="O número de faltas deve ser um valor entre 0 e 365.")
	private int faltas;
	
	@JFApresentacao("Contrato")
	@JFTexto(max=100)
	@JFValidacaoNaoNulo(erro="Informe o contrato de trabalho.")
	@Column(length=100)
	private String contrato;
	
	public Funcionario() {
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || ! ( obj instanceof Funcionario ) ) return false;
		Funcionario o = (Funcionario) obj;
		return id == o.id || ( id != null && o.id != null && id.equals( o.id ) );
	}
	
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : super.hashCode();
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public Filial getFilial() {
		return filial;
	}
	
	public void setFilial( Filial filial ) {
		this.filial = filial;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo( Cargo cargo ) {
		this.cargo = cargo;
	}
	
	public Date getNascimento() {
		return nascimento;
	}
	
	public void setNascimento( Date nascimento ) {
		this.nascimento = nascimento;
	}
	
	public boolean isSexo() {
		return sexo;
	}
	
	public void setSexo( boolean sexo ) {
		this.sexo = sexo;
	}
	
	public Funcionario getGerente() {
		return gerente;
	}
	
	public void setGerente( Funcionario gerente ) {
		this.gerente = gerente;
	}
	
	public double getSalario() {
		return salario;
	}
	
	public void setSalario( double salario ) {
		this.salario = salario;
	}
	
	public int getFaltas() {
		return faltas;
	}
	
	public void setFaltas( int faltas ) {
		this.faltas = faltas;
	}
	
	public String getContrato() {
		return contrato;
	}
	
	public void setContrato( String contrato ) {
		this.contrato = contrato;
	}
	
	public static Funcionario obter( AplicacaoTQC_JPA aplicacao, Long id ) {
		return aplicacao.obter( "select o from Funcionario o where o.id = :p0", id );
	}

	public static List<Funcionario> listar( AplicacaoTQC_JPA aplicacao ) {
		return aplicacao.listar( "select o from Funcionario o order by o.nome asc" );
	}
	
	public static List<Funcionario> listar( AplicacaoTQC_JPA aplicacao, Filial filial ) {
		return aplicacao.listar( "select o from Funcionario o where o.filial.sigla = :p0 order by o.nome asc", filial.getSigla() );
	}
	
	public static List<Funcionario> listar( AplicacaoTQC_JPA aplicacao, Cargo cargo ) {
		return aplicacao.listar( "select o from Funcionario o where o.cargo = :p0 order by o.nome asc", cargo );
	}
	
}
