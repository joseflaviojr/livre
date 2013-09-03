
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
 * Funcion�rio da empresa.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
@JFApresentacao(value="Funcion�rio", plural="Funcion�rios", masculino=true)
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
	@JFValidacaoTamanhoLimite(erro="O nome deve conter no m�ximo 50 caracteres.")
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
	
	@JFApresentacao("Sal�rio")
	@JFReal(min=0,maxFracao=2)
	@JFTexto(largura=20, max=20)
	@JFValidacaoPrimitiva(erro="Informe corretamente o sal�rio.")
	@JFValidacaoNaoNulo(erro="Informe o sal�rio.")
	@JFValidacaoTamanhoLimite(erro="O sal�rio deve conter no m�ximo 20 caracteres.")
	@JFValidacaoValorLimite(erro="Informe corretamente o sal�rio.")
	private double salario;
	
	@JFApresentacao("Faltas")
	@JFInteiro(min=0,max=365)
	@JFTexto(largura=10, max=10)
	@JFValidacaoPrimitiva(erro="Informe corretamente o n�mero de faltas.")
	@JFValidacaoNaoNulo(erro="Informe o n�mero de faltas.")
	@JFValidacaoTamanhoLimite(erro="O n�mero de faltas deve conter no m�ximo 10 caracteres.")
	@JFValidacaoValorLimite(erro="O n�mero de faltas deve ser um valor entre 0 e 365.")
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
