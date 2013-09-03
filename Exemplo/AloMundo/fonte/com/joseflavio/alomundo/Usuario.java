
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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.joseflavio.modelo.JFApresentacao;
import com.joseflavio.modelo.JFMascara;
import com.joseflavio.modelo.JFTexto;
import com.joseflavio.modelo.JFValidacaoNaoNulo;
import com.joseflavio.modelo.JFValidacaoNaoVazio;
import com.joseflavio.modelo.JFValidacaoTamanhoLimite;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;

/**
 * Usu�rio do sistema.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
@JFApresentacao(value="Usu�rio", plural="Usu�rios", masculino=true)
@Entity
public class Usuario {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;

	@JFApresentacao("Nome")
	@JFTexto(largura=50, max=100)
	@JFValidacaoNaoVazio(erro="Informe o nome.")
	@JFValidacaoTamanhoLimite(erro="O nome deve conter no m�ximo 100 caracteres.")
	@Column(length=100)
	private String nome;
	
	@JFApresentacao("Telefone")
	@JFMascara("(00) 0{3,5}-0000")
	@JFTexto(largura=20, max=50)
	@JFValidacaoTamanhoLimite(erro="O telefone deve conter no m�ximo 50 caracteres.")
	@Column(length=50)
	private String telefone;
	
	@JFApresentacao("E-Mail")
	@JFTexto(largura=20, max=50)
	@JFValidacaoTamanhoLimite(erro="O e-mail deve conter no m�ximo 50 caracteres.")
	@Column(length=50)
	private String email;

	@JFApresentacao("Usu�rio")
	@JFTexto(largura=20, max=20)
	@JFValidacaoNaoVazio(erro="Informe o nome de usu�rio.")
	@JFValidacaoTamanhoLimite(erro="O nome de usu�rio deve conter no m�ximo 20 caracteres.")
	@Column(length=20)
	private String usuario;

	@JFApresentacao("Senha")
	@JFTexto(largura=20, max=20)
	@JFValidacaoTamanhoLimite(erro="A senha deve conter no m�ximo 20 caracteres.")
	@Column(length=20)
	private String senha;
	
	@JFApresentacao("Tipo")
	@JFValidacaoNaoNulo(erro="Informe o tipo.")
	@Enumerated(EnumType.ORDINAL)
	private Tipo tipo = Tipo.COMUM;
	
	public Usuario() {
	}

	public Usuario( String nome, String telefone, String usuario, String senha, Tipo tipo ) {
		this.nome = nome;
		this.telefone = telefone;
		this.usuario = usuario;
		this.senha = senha;
		this.tipo = tipo;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == null || ! ( obj instanceof Usuario ) ) return false;
		Usuario o = (Usuario) obj;
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
	
	public String getNome() {
		return nome;
	}

	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone( String telefone ) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail( String email ) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario( String usuario ) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha( String senha ) {
		this.senha = senha;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo( Tipo tipo ) {
		this.tipo = tipo;
	}
	
	public static Usuario obter( AplicacaoTQC_JPA aplicacao, Long id ) {
		return aplicacao.obter( "select o from Usuario o where o.id = :p0", id );
	}
	
	public static Usuario obter( AplicacaoTQC_JPA aplicacao, String usuario ) {
		return aplicacao.obter( "select o from Usuario o where lower(o.usuario) = :p0", usuario.toLowerCase() );
	}

	public static List<Usuario> listar( AplicacaoTQC_JPA aplicacao ) {
		return aplicacao.listar( "select o from Usuario o order by o.nome asc" );
	}
	
	public static List<Usuario> listar( AplicacaoTQC_JPA aplicacao, Tipo tipo ) {
		return aplicacao.listar( "select o from Usuario o where o.tipo = :p0 order by o.nome asc", tipo );
	}
	
	public static enum Tipo {
		
		COMUM ( "Comum" ),
		ADMINISTRADOR ( "Administrador" );
		
		private String titulo;

		private Tipo( String titulo ) {
			this.titulo = titulo;
		}
		
		@Override
		public String toString() {
			return titulo;
		}
		
		public String getTitulo() {
			return titulo;
		}
		
	}
	
}
