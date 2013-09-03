
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

package com.joseflavio.tqc;

import com.joseflavio.tqc.dado.Comando;
import com.joseflavio.util.Lista;

/**
 * Menu de {@link OpcaoDeMenu a��es}.<br>
 * As {@link OpcaoDeMenu a��es} do tipo {@link Comando} ser�o capturados pelo m�todo {@link Informacao#acao(TomaraQueCaia, Viagem, Comando)}.
 * @author Jos� Fl�vio de Souza Dias J�nior
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
	 * Busca por uma {@link OpcaoDeMenu} com espec�fica {@link OpcaoDeMenu#getIdentificacao()}.
	 * @return <code>null</code> se n�o encontrar.
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
