
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

package com.joseflavio.xml.consulta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.joseflavio.xml.Tipo;

/**
 * http://www.joseflavio.com/xml/Consulta.xsd
 * @author José Flávio de Souza Dias Júnior
 * @version 2014
 */
public class Consultas {
	
	private String descricao;
	
	private String documentacao;
	
	private Map<String,Parametro> parametro = new HashMap<String,Parametro>();
	
	private Map<String,Consulta> consulta = new HashMap<String,Consulta>();
	
	private Map<String,Traducao> traducao = new HashMap<String,Traducao>();
	
	private Set<Consulta> desconsiderar = new HashSet<Consulta>();
	
	public Consultas() {
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao( String descricao ) {
		this.descricao = descricao;
	}

	public String getDocumentacao() {
		return documentacao;
	}

	public void setDocumentacao( String documentacao ) {
		this.documentacao = documentacao;
	}
	
	public Map<String,Parametro> getParametro() {
		return parametro;
	}
	
	public void setParametro( Map<String,Parametro> parametro ) {
		this.parametro = parametro;
	}

	public Map<String,Consulta> getConsulta() {
		return consulta;
	}
	
	public void setConsulta( Map<String,Consulta> consulta ) {
		this.consulta = consulta;
	}
	
	public Map<String,Traducao> getTraducao() {
		return traducao;
	}
	
	public void setTraducao( Map<String,Traducao> traducao ) {
		this.traducao = traducao;
	}
	
	public Set<Consulta> getDesconsiderar() {
		return desconsiderar;
	}
	
	public void setDesconsiderar( Set<Consulta> desconsiderar ) {
		this.desconsiderar = desconsiderar;
	}
	
	/**
	 * Converte um XML para {@link Consultas}, validando-o antes conforme http://www.joseflavio.com/xml/Consulta.xsd.
	 * @param xml Conteúdo do arquivo XML.
	 * @param schema Versão off-line de http://www.joseflavio.com/xml/Consulta.xsd
	 */
	public static Consultas carregar( InputStream xml, Schema schema ) throws IOException {
		
		try{

			try{
				URL url = new URL( "http://www.joseflavio.com/xml/Consulta.xsd" );
				URLConnection conexao = url.openConnection();
				conexao.connect();
				schema = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI ).newSchema( new StreamSource( conexao.getInputStream() ) );
			}catch( Exception e ){
				if( schema == null ) throw new IOException( e );
			}
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware( true );
			dbf.setSchema( schema );
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			db.setErrorHandler( new ErrorHandler() {
				public void warning   ( SAXParseException e ) throws SAXException { throw e; }
				public void error     ( SAXParseException e ) throws SAXException { throw e; }
				public void fatalError( SAXParseException e ) throws SAXException { throw e; }
			} );
			
			return carregar( db.parse( xml ) );
		
		}catch( IOException e ){
			throw e;
		}catch( Exception e ){
			throw new IOException( e );
		}
		
	}
	
	/**
	 * @see #carregar(InputStream, Schema)
	 */
	public static Consultas carregar( File xml, Schema schema ) throws IOException {
		return carregar( new FileInputStream( xml ), schema );
	}

	/**
	 * @see #carregar(InputStream, Schema)
	 */
	public static Consultas carregar( URL xml, Schema schema ) throws IOException {
		return carregar( xml.openConnection().getInputStream(), schema );
	}
	
	private static Consultas carregar( Document xml ) throws IOException {
		
		Node n = xml.getFirstChild();
		NodeList nl = n.getChildNodes();
		NamedNodeMap a = n.getAttributes();

		Consultas consultas = new Consultas();
		consultas.setDescricao( a.getNamedItem( "descricao" ).getNodeValue() );
		
		for( int i = 0, total = nl.getLength(); i < total; i++ ){
			
			n = nl.item( i );
			String nome = n.getNodeName();
			
			if( nome.equals( "parametro" ) ) xml_parametro( n, consultas.getParametro() );
			else if( nome.equals( "consulta" ) ) xml_consultas_consulta( n, consultas );
			else if( nome.equals( "traducao" ) ) xml_consultas_traducao( n, consultas );
			else if( nome.equals( "desconsiderar" ) ) xml_consultas_desconsiderar( n, consultas );
			else if( nome.equals( "documentacao" ) ) consultas.setDocumentacao( n.getTextContent() );
			
		}
		
		return consultas;
		
	}
	
	private static void xml_parametro( Node n, Map<String,Parametro> destino ) throws IOException {
		
		NamedNodeMap a = n.getAttributes();
		
		String nome = a.getNamedItem( "nome" ).getNodeValue();
		String valor = a.getNamedItem( "valor" ).getNodeValue();
		
		String tipoStr = a.getNamedItem( "tipo" ) != null ? a.getNamedItem( "tipo" ).getNodeValue() : null;
		Tipo tipo = null;
		
		if( tipoStr == null || tipoStr.length() == 0 ) tipo = null;
		else if( tipoStr.equals( "Logico" ) ) tipo = Tipo.LOGICO;
		else if( tipoStr.equals( "Letra" ) ) tipo = Tipo.LETRA;
		else if( tipoStr.equals( "Texto" ) ) tipo = Tipo.TEXTO;
		else if( tipoStr.equals( "TextoMultilinha" ) ) tipo = Tipo.TEXTO_MULTILINHA;
		else if( tipoStr.equals( "Inteiro" ) ) tipo = Tipo.INTEIRO;
		else if( tipoStr.equals( "Real" ) ) tipo = Tipo.REAL;
		else if( tipoStr.equals( "Data" ) ) tipo = Tipo.DATA;
		else if( tipoStr.equals( "Hora" ) ) tipo = Tipo.HORA;
		else if( tipoStr.equals( "DataHora" ) ) tipo = Tipo.DATA_HORA;
		
		boolean alteravel = a.getNamedItem( "alteravel" ) != null ? a.getNamedItem( "alteravel" ).getNodeValue().equals( "S" ) : false;
		
		Parametro parametro = new Parametro( nome, valor );
		parametro.setTipo( tipo );
		parametro.setAlteravel( alteravel );
		destino.put( nome, parametro );
		
	}
	
	private static void xml_consultas_consulta( Node n, Consultas consultas ) throws IOException {
		
		NodeList nl = n.getChildNodes();
		NamedNodeMap a = n.getAttributes();
		
		Consulta consulta = new Consulta( a.getNamedItem( "nome" ).getNodeValue() );
		consulta.setDescricao( a.getNamedItem( "descricao" ).getNodeValue() );
		
		for( int i = 0, total = nl.getLength(); i < total; i++ ){
			
			n = nl.item( i );
			String nome = n.getNodeName();
			
			if( nome.equals( "parametro" ) ) xml_parametro( n, consulta.getParametro() );
			else if( nome.equals( "questionamento" ) ) consulta.setQuestionamento( n.getTextContent().trim() );
			else if( nome.equals( "traducao" ) ) xml_consultas_consulta_traducao( n, consultas, consulta );
			else if( nome.equals( "documentacao" ) ) consulta.setDocumentacao( n.getTextContent() );
			
		}
		
		consultas.getConsulta().put( consulta.getNome(), consulta );
		
	}
	
	private static void xml_consultas_traducao( Node n, Consultas consultas ) throws IOException {
		
		NodeList nl = n.getChildNodes();
		NamedNodeMap a = n.getAttributes();
		
		String nome = a.getNamedItem( "nome" ).getNodeValue();
		Traducao traducao = consultas.getTraducao().get( nome );
		if( traducao == null ) consultas.getTraducao().put( nome, traducao = new Traducao( nome ) );
		
		for( int i = 0, total = nl.getLength(); i < total; i++ ){
			
			n = nl.item( i );
			nome = n.getNodeName();
			
			if( nome.equals( "valor" ) ) xml_consultas_traducao_valor( n, traducao );
			
		}
		
	}
	
	private static void xml_consultas_traducao_valor( Node n, Traducao traducao ) throws IOException {
		NamedNodeMap a = n.getAttributes();
		String original = a.getNamedItem( "original" ).getNodeValue();
		traducao.getValor().put( original, new Valor( original, a.getNamedItem( "traducao" ).getNodeValue() ) );
	}
	
	private static void xml_consultas_consulta_traducao( Node n, Consultas consultas, Consulta consulta ) throws IOException {
		
		NamedNodeMap a = n.getAttributes();
		
		String campo = a.getNamedItem( "campo" ).getNodeValue();
		String tipo = a.getNamedItem( "tipo" ).getNodeValue();
		
		Traducao traducao = consultas.getTraducao().get( tipo );
		if( traducao == null ) consultas.getTraducao().put( tipo, traducao = new Traducao( tipo ) );
		
		consulta.getTraducao().put( campo, new Traducao.Instancia( campo, traducao ) );
		
	}
	
	private static void xml_consultas_desconsiderar( Node n, Consultas consultas ) throws IOException {
		
		NodeList nl = n.getChildNodes();
		
		for( int i = 0, total = nl.getLength(); i < total; i++ ){
			
			n = nl.item( i );
			String nome = n.getNodeName();
			
			if( nome.equals( "consulta" ) ) consultas.getDesconsiderar().add( consultas.getConsulta().get( n.getTextContent().trim() ) );
			
		}
		
	}
	
}
