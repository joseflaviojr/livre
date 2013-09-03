
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

package com.joseflavio.tqc;

import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.util.Lista;

/**
 * Menu de {@link OpcaoDeMenu ações}.<br>
 * As {@link OpcaoDeMenu ações} do tipo {@link Comando} serão capturados pelo método {@link Informacao#acao(TomaraQueCaia, Viagem, Comando)}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public class Menu implements OpcaoDeMenu {
	
	private String rotulo;
	
	private String imagem;
	
	private Lista<OpcaoDeMenu> opcoes = new Lista<OpcaoDeMenu>();
	
	public Menu( String rotulo, String imagem ) {
		this.rotulo = rotulo;
		this.imagem = imagem;
	}
	
	public Menu( String rotulo ) {
		this( rotulo, null );
	}
	
	@Override
	public int hashCode() {
		return ( rotulo != null ? rotulo.hashCode() : 0 ) + ( imagem != null ? imagem.hashCode() : 0 );
	}
	
	public Lista<OpcaoDeMenu> getOpcoes() {
		return opcoes;
	}
	
	public Menu mais( OpcaoDeMenu opcao ) {
		opcoes.mais( opcao );
		return this;
	}
	
	public int getTotal() {
		return opcoes.size();
	}
	
	/**
	 * Busca por uma {@link OpcaoDeMenu} com específica {@link OpcaoDeMenu#getIdentificacao()}.
	 * @return <code>null</code> se não encontrar.
	 */
	public OpcaoDeMenu getOpcaoDeMenu( String identificacao ) {
		for( OpcaoDeMenu om : opcoes ){
			if( om instanceof Menu ){
				OpcaoDeMenu om2 = ((Menu)om).getOpcaoDeMenu( identificacao );
				if( om2 != null ) return om2;
			}else{
				String id = om.getIdentificacao();
				if( id != null && id.equals( identificacao ) ) return om;
			}
		}
		return null;
	}
	
	@Override
	public String getIdentificacao() {
		return null;
	}
	
	@Override
	public String getRotulo() {
		return rotulo;
	}
	
	public void setRotulo( String rotulo ) {
		this.rotulo = rotulo;
	}
	
	@Override
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem( String imagem ) {
		this.imagem = imagem;
	}
	
}
