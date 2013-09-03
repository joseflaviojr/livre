
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
 * Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 * sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 * Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 * (a seu crit�rio) qualquer vers�o posterior.
 * 
 * Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 * por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 * COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 * Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 * junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.jpa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.spi.PersistenceProvider;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public class PersistenceXML implements ContentHandler {

	private Map<String, PersistenceUnit> units = new HashMap<String, PersistenceUnit>();
	
	private StringBuilder buffer;
	
	private PersistenceUnit ultimaUnit;
	
	public PersistenceXML() throws IOException {
		
		try{
	
			XMLReader xml = XMLReaderFactory.createXMLReader();
			xml.setContentHandler( this );
			xml.parse( new InputSource( PersistenceXML.class.getClassLoader().getResourceAsStream( "META-INF/persistence.xml" ) ) );
			
		}catch( SAXException e ){
			throw new IOException( e );
		}
		
	}
	
	public PersistenceUnit getPersistenceUnit( String name ){
		
		return units.get( name );
		
	}
	
	public void startDocument() throws SAXException {
		
		buffer = new StringBuilder( 256 );
		
	}

	public void startElement( String uri, String localName, String qName, Attributes atts ) throws SAXException {
		
		buffer.delete( 0, buffer.length() );
		
		if( localName.equals( "persistence-unit" ) ){
			
			ultimaUnit = new PersistenceUnit( atts.getValue( "name" ) );
			units.put( ultimaUnit.getName(), ultimaUnit );
			
		}else if( localName.equals( "property" ) ){
			
			ultimaUnit.properties.put( atts.getValue( "name" ), atts.getValue( "value" ) );
		
		}
		
	}

	public void characters( char[] ch, int start, int length ) throws SAXException {
		
		buffer.append( ch, start, length );
		
	}
	
	@SuppressWarnings("unchecked")
	public void endElement( String uri, String localName, String qName ) throws SAXException {
		
		try{
			
			if( localName.equals( "class" ) ){
				
				ultimaUnit.classes.add( Class.forName( buffer.toString() ) );
				
			}else if( localName.equals( "provider" ) ){
				
				ultimaUnit.provider = (Class<PersistenceProvider>) Class.forName( buffer.toString() );
				
			}
			
		}catch( ClassNotFoundException e ){
			throw new SAXException( e );
		}
		
	}
	
	public void endDocument() throws SAXException {
		
		ultimaUnit = null;
		buffer = null;
		
	}
	
	public void ignorableWhitespace( char[] ch, int start, int length ) throws SAXException {
	}

	public void processingInstruction( String target, String data ) throws SAXException {
	}

	public void setDocumentLocator( Locator locator ) {
	}

	public void skippedEntity( String name ) throws SAXException {
	}

	public void startPrefixMapping( String prefix, String uri ) throws SAXException {
	}
	
	public void endPrefixMapping( String prefix ) throws SAXException {
	}

}
