
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

package com.joseflavio.tqc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.joseflavio.modelo.JFDica;
import com.joseflavio.modelo.JFMascara;
import com.joseflavio.modelo.JFTamanho;
import com.joseflavio.modelo.JFTamanhos;
import com.joseflavio.util.Lista;

/**
 * {@link Dado} que possui {@link Estilo}.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see AnexoTamanho
 * @see AnexoPosicao
 * @see AnexoMensagem
 * @see AnexoDica
 * @see AnexoMascara
 */
public abstract class SimplesDado extends Dado implements Estilo {
	
	private String estilo;
	
	private Anexo anexo;
	
	public SimplesDado() {
	}
	
	public SimplesDado( String estilo ) {
		this();
		this.estilo = estilo;
	}

	public String getEstilo() {
		return estilo;
	}
	
	public Dado setEstilo( String estilo ) {
		this.estilo = estilo;
		return this;
	}
	
	public Dado maisEstilo( String estilo ) {
		this.estilo = this.estilo == null ? estilo : this.estilo + " " + estilo;
		return this;
	}
	
	/**
	 * @return {@link Anexo} ou {@link AnexoGrupo}.
	 */
	public Anexo getAnexo() {
		return anexo;
	}
	
	/**
	 * Especifica um {@link Anexo} para o {@link Dado}, podendo ser um {@link AnexoGrupo}.
	 */
	public void setAnexo( Anexo anexo ) {
		this.anexo = anexo;
	}
	
	/**
	 * Adiciona um {@link Anexo}, adequando-se a {@link AnexoGrupo} se necess�rio.
	 * @see AnexoGrupo#mais(Anexo)
	 */
	public SimplesDado mais( Anexo anexo ) {
		if( this.anexo == null ){
			this.anexo = anexo;
		}else if( this.anexo instanceof AnexoGrupo ){
			((AnexoGrupo)this.anexo).mais( anexo );
		}else{
			this.anexo = new AnexoGrupo( this.anexo ).mais( anexo );
		}
		return this;
	}
	
	/**
	 * Verifica se o {@link Anexo} corrente � do tipo desejado ou, no caso de {@link AnexoGrupo}, retorna {@link AnexoGrupo#getAnexo(Class)}.
	 * @see AnexoGrupo#getAnexo(Class)
	 * @see Class#isAssignableFrom(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Anexo> T getAnexo( Class<? extends T> tipo ) {
		
		if( anexo == null ) return null;
		
		if( anexo instanceof AnexoGrupo ){
			return (T) ((AnexoGrupo)anexo).getAnexo( tipo );
		}
		
		return tipo.isAssignableFrom( anexo.getClass() ) ? (T) anexo : null;
		
	}
	
	/**
	 * Retorna todos os {@link Anexo}s do tipo desejado contidos neste {@link SimplesDado}.
	 * @return lista vazia se n�o encontrar qualquer {@link Anexo} compat�vel.
	 * @see Class#isAssignableFrom(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Anexo> List<T> getAnexos( Class<? extends T> tipo ) {
		
		if( anexo == null ) return new ArrayList<T>();
		
		if( anexo instanceof AnexoGrupo ){
			return ((AnexoGrupo)anexo).getAnexos( tipo );
		}
		
		return tipo.isAssignableFrom( anexo.getClass() ) ? new Lista<T>( (T) anexo ) : new ArrayList<T>();
		
	}
	
	/**
	 * Executa {@link #getAnexo()}. Caso o {@link Anexo} desejado n�o exista, ele ser� {@link Class#newInstance() instanciado} e adicionado atrav�s
	 * de {@link #mais(Anexo)} (se o par�metro "adicionar" for positivo).
	 */
	public <T extends Anexo> T getAnexo( Class<? extends T> tipo, boolean adicionar ) throws InstantiationException, IllegalAccessException {
		T anexo = getAnexo( tipo );
		if( anexo == null && adicionar ){
			anexo = tipo.newInstance();
			mais( anexo );
		}
		return anexo;
	}
	
	/**
	 * Busca pelo primeiro {@link AnexoTamanho} que tenha uma defini��o de {@link AnexoTamanho#getLargura()}.
	 */
	public AnexoTamanho getTamanhoComLargura() {
		for( AnexoTamanho tam : getAnexos( AnexoTamanho.class ) ){
			if( tam.getLargura() != -1 ) return tam;
		}
		return null;
	}
	
	/**
	 * Busca pelo primeiro {@link AnexoTamanho} que tenha uma defini��o de {@link AnexoTamanho#getAltura()}.
	 */
	public AnexoTamanho getTamanhoComAltura() {
		for( AnexoTamanho tam : getAnexos( AnexoTamanho.class ) ){
			if( tam.getAltura() != -1 ) return tam;
		}
		return null;
	}
	
	/**
	 * Busca pelo primeiro {@link AnexoTamanho} que tenha uma defini��o de {@link AnexoTamanho#getLarguraMaxima()}.
	 */
	public AnexoTamanho getTamanhoComLarguraMaxima() {
		for( AnexoTamanho tam : getAnexos( AnexoTamanho.class ) ){
			if( tam.getLarguraMaxima() != -1 ) return tam;
		}
		return null;
	}
	
	/**
	 * Busca pelo primeiro {@link AnexoTamanho} que tenha uma defini��o de {@link AnexoTamanho#getAlturaMaxima()}.
	 */
	public AnexoTamanho getTamanhoComAlturaMaxima() {
		for( AnexoTamanho tam : getAnexos( AnexoTamanho.class ) ){
			if( tam.getAlturaMaxima() != -1 ) return tam;
		}
		return null;
	}
	
	/**
	 * Busca pelo primeiro {@link AnexoTamanho} que tenha uma defini��o de {@link AnexoTamanho#getLarguraMinima()}.
	 */
	public AnexoTamanho getTamanhoComLarguraMinima() {
		for( AnexoTamanho tam : getAnexos( AnexoTamanho.class ) ){
			if( tam.getLarguraMinima() != -1 ) return tam;
		}
		return null;
	}
	
	/**
	 * Busca pelo primeiro {@link AnexoTamanho} que tenha uma defini��o de {@link AnexoTamanho#getAlturaMinima()}.
	 */
	public AnexoTamanho getTamanhoComAlturaMinima() {
		for( AnexoTamanho tam : getAnexos( AnexoTamanho.class ) ){
			if( tam.getAlturaMinima() != -1 ) return tam;
		}
		return null;
	}
	
	/**
	 * @see AnexoTamanho#getLargura()
	 * @see #getTamanhoComLargura()
	 */
	public int getLargura() {
		AnexoTamanho tam = getTamanhoComLargura();
		return tam != null ? tam.getLargura() : -1;
	}
	
	/**
	 * @see AnexoTamanho#setLargura(int)
	 * @see #getTamanhoComLargura()
	 */
	public SimplesDado setLargura( int largura ) {
		AnexoTamanho tam = getTamanhoComLargura();
		if( tam == null ) tam = getAnexo( AnexoTamanho.class );
		if( tam == null ) return mais( new AnexoTamanho( largura ) );
		tam.setLargura( largura );
		return this;
	}
	
	/**
	 * @see AnexoTamanho#getAltura()
	 * @see #getTamanhoComAltura()
	 */
	public int getAltura() {
		AnexoTamanho tam = getTamanhoComAltura();
		return tam != null ? tam.getAltura() : -1;
	}
	
	/**
	 * @see AnexoTamanho#setAltura(int)
	 * {@link #getTamanhoComAltura()}
	 */
	public SimplesDado setAltura( int altura ) {
		AnexoTamanho tam = getTamanhoComAltura();
		if( tam == null ) tam = getAnexo( AnexoTamanho.class );
		if( tam == null ) return mais( new AnexoTamanho( -1, altura ) );
		tam.setAltura( altura );
		return this;
	}
	
	/**
	 * @see #setLargura(int)
	 * @see #setAltura(int)
	 */
	public SimplesDado setTamanho( int largura, int altura ) {
		setLargura( largura );
		setAltura( altura );
		return this;
	}
	
	/**
	 * @see JFTamanho
	 * @see JFTamanhos
	 * @see JFDica
	 */
	@Override
	protected Dado configurarPor( Field atributo ) {
		
		super.configurarPor( atributo );
		
		JFDica    jfDica    = atributo.getAnnotation( JFDica.class );
		JFMascara jfMascara = atributo.getAnnotation( JFMascara.class );
		JFTamanho jfTamanho = atributo.getAnnotation( JFTamanho.class );
		JFTamanhos jfTamanhos = atributo.getAnnotation( JFTamanhos.class );

		if( jfDica != null ) mais( new AnexoDica( jfDica.value() ) );
		if( jfMascara != null ) mais( new AnexoMascara( jfMascara.value() ) );
		if( jfTamanho != null ) mais( jfTamanho );
		if( jfTamanhos != null ) for( JFTamanho jft : jfTamanhos.value() ) mais( jft );
		
		return this;
		
	}
	
	private void mais( JFTamanho jfTamanho ) {
		mais( new AnexoTamanho( jfTamanho.largura(), jfTamanho.altura(), jfTamanho.larguraMax(), jfTamanho.alturaMax(), jfTamanho.larguraMin(), jfTamanho.alturaMin(), jfTamanho.unidade() ) );
	}
	
}
