
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

package com.joseflavio.alomundo.aplicacao;

import java.io.File;
import java.util.Map;

import com.joseflavio.alomundo.Usuario;
import com.joseflavio.alomundo.Usuario.Tipo;
import com.joseflavio.alomundo.aplicacao.autenticacao.AutenticacaoFormulario;
import com.joseflavio.alomundo.aplicacao.filial.FilialListagem;
import com.joseflavio.alomundo.aplicacao.filtro.AdministradorFiltro;
import com.joseflavio.alomundo.aplicacao.filtro.ComOuSemUsuarioFiltro;
import com.joseflavio.alomundo.aplicacao.filtro.QualquerUsuarioFiltro;
import com.joseflavio.alomundo.aplicacao.filtro.RetroativoFiltro;
import com.joseflavio.alomundo.aplicacao.usuario.UsuarioListagem;
import com.joseflavio.tqc.Informacao;
import com.joseflavio.tqc.OpcaoDeMenu;
import com.joseflavio.tqc.TomaraQueCaia;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.Viagem;
import com.joseflavio.tqc.Viajante;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC_JPA;
import com.joseflavio.tqc.aplicacao.ComandoVisitar;
import com.joseflavio.tqc.aplicacao.Questionamento;
import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.util.Calendario;
import com.joseflavio.util.Lista;
import com.joseflavio.util.ModeloUtil;

/**
 * Aplicação Alô Mundo!
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class AloMundo extends AplicacaoTQC_JPA {

	private Usuario usuario;
	
	public AloMundo() {
		
		super( "alomundo" );
	
		mais( AutenticacaoFormulario.class, ComOuSemUsuarioFiltro.instancia );
		mais( Questionamento.class, RetroativoFiltro.instancia );
		
		mais( "com.joseflavio.alomundo.aplicacao", QualquerUsuarioFiltro.instancia );

		mais( "com.joseflavio.alomundo.aplicacao.usuario", AdministradorFiltro.instancia );
		mais( "com.joseflavio.alomundo.aplicacao.filial", AdministradorFiltro.instancia );
		mais( "com.joseflavio.alomundo.aplicacao.funcionario", AdministradorFiltro.instancia );
		
		int anoInicial = 2009;
		int anoAtual = new Calendario( getCultura() ).getAno();
		setDireitosAutorais( "joseflavio.com &copy; " + ( anoInicial < anoAtual ? anoInicial + "-" + anoAtual : anoAtual ) + "\nTodos os direitos reservados." );
		
	}
	
	public String getBanner() {
		return "img/banner.png";
	}
	
	public void permissaoNegada( Viagem viagem, Informacao informacao ) throws TomaraQueCaiaException {
		viagem.visitar( new AutenticacaoFormulario( this, new Viajante.Voltar(), new Viajante.VoltarSair() ) );
	}
	
	public boolean negarAcessoSemFiltro() {
		return true;
	}
	
	public void inicio( Map<String, String> parametros ) throws TomaraQueCaiaException {
		
		if( Usuario.listar( this ).size() == 0 ){
			
			Usuario padrao = new Usuario();
			padrao.setNome( "Administrador" );
			padrao.setTelefone( "(94) 8146-0404" );
			padrao.setUsuario( "admin" );
			padrao.setSenha( "1234" );
			padrao.setTipo( Tipo.ADMINISTRADOR );
			
			padrao = persistir( padrao );
			persistirPendencias();
			
		}
		
		Viajante destino = new Viajante() {
			public void viajar( TomaraQueCaia tqc, Viagem viagem ) throws TomaraQueCaiaException {
				viagem.limpar().visitar( new FilialListagem( AloMundo.this ) );
			}
		};
		
		novaViagem().visitar( new AutenticacaoFormulario( this, destino, new Viajante.Encerrar() ) );
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario( Usuario usuario ) {
		this.usuario = usuario;
	}
	
	public boolean isAdministrador() {
		return usuario != null ? usuario.getTipo() == Tipo.ADMINISTRADOR : false;
	}
	
	public File getLocalDocumentos() {
		return new File( this.getRaiz(), "doc" );
	}
	
	/**
	 * @see ComandoVisitar
	 */
	public Lista<OpcaoDeMenu> getMenuPadrao() {
		
		Comando usuarios = new ComandoVisitar( "usuarios", ModeloUtil.getApresentacao( Usuario.class, true ), true, UsuarioListagem.class, this );
		
		return new Lista<OpcaoDeMenu>( usuarios );
		
	}
	
}
