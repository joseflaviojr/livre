
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

package com.joseflavio.xml.consulta.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joseflavio.xml.Tipo;
import com.joseflavio.xml.TipoFormatacao;
import com.joseflavio.xml.consulta.Consulta;
import com.joseflavio.xml.consulta.Consultas;
import com.joseflavio.xml.consulta.Parametro;
import com.joseflavio.xml.consulta.Traducao;
import com.joseflavio.xml.consulta.Valor;

/**
 * {@link Consultas} realizadas através de {@link Connection JDBC}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2014
 */
public class ConsultaSQL {
	
	/**
	 * Realiza as {@link Consultas} e retorna a tabela de resultado correspondente a cada uma delas.<br>
	 * <pre>
	 * Observações:
	 *    - a primeira linha da tabela de resultado conterá o nome das colunas.
	 *    - os valores serão formatados conforme padrão "http://www.joseflavio.com/xml/Tipo.xsd".
	 * </pre>
	 * @return {@link Map} associando {@link Consulta#getNome()} e tabela de resultado.
	 * @throws IllegalArgumentException caso {@link Consultas} não contenha um dos {@link Parametro}s: jdbc, url, usuario e senha.
	 * @throws ClassNotFoundException {@link Parametro} jdbc incorreto.
	 */
	public static Map<String,String[][]> realizar( Consultas consultas ) throws ClassNotFoundException, SQLException {
		
		//Parâmetros
		Map<String,Parametro> parametrosGlobais = consultas.getParametro();
		String jdbc    = parametrosGlobais.get( "jdbc" ).getValor();
		String url     = parametrosGlobais.get( "url" ).getValor();
		String usuario = parametrosGlobais.get( "usuario" ).getValor();
		String senha   = parametrosGlobais.get( "senha" ).getValor();
		
		if( jdbc == null || url == null || usuario == null || senha == null ) throw new IllegalArgumentException();
		
		//Conexão
		Class.forName( jdbc );
		Connection conexao = DriverManager.getConnection( url, usuario, senha );
		
		//Consultas
		Map<String,String[][]> resultado = new HashMap<String,String[][]>();
		List<String> valor = new ArrayList<String>( 4096 );
		TipoFormatacao tipoForm = new TipoFormatacao();
		
		for( Consulta consulta : consultas.getConsulta().values() ){
			
			if( consultas.getDesconsiderar().contains( consulta ) ) continue;
			
			Map<String,Parametro> parametrosLocais = consulta.getParametro();
			
			//SQL
			String sql = consulta.getQuestionamento().trim();
			if( ! sql.toUpperCase().startsWith( "SELECT" ) ) throw new IllegalArgumentException( "Aceita-se apenas instruções do tipo SELECT." );
			PreparedStatement ps = conexao.prepareStatement( sql.replaceAll( "::.*?::", "?" ) );
			
			int pi = 0, pf = 0, pindice = 1;
			while( ( pi = sql.indexOf( "::", pf ) ) > -1 ){
				pf = sql.indexOf( "::", pi + 3 );
				if( pf == -1 ) break;
				String sqlParamNome = sql.substring( pi + 2, pf );
				Parametro sqlParam = parametrosLocais.get( sqlParamNome );
				if( sqlParam == null ) sqlParam = parametrosGlobais.get( sqlParamNome );
				if( sqlParam != null ){
					String sqlParamValor = sqlParam.getValor();
					Tipo sqlParamTipo = sqlParam.getTipo();
					if( sqlParamTipo == null ) sqlParamTipo = tipoForm.reconhecerTipo( sqlParamValor );
					if( sqlParamTipo != null ){
						ps.setObject( pindice++, tipoForm.transformar( sqlParamValor, sqlParamTipo ), converter( sqlParamTipo ) );
					}else{
						ps.setObject( pindice++, tipoForm.transformar( sqlParamValor, sqlParamTipo ) );
					}
				}else{
					throw new IllegalArgumentException( "Parâmetro indeterminado: " + sqlParamNome );
				}
				pf += 2;
			}
			
			//Execução e Resultado
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmeta = rs.getMetaData();
			int totalColunas = rsmeta.getColumnCount();
			Traducao.Instancia traducao[] = new Traducao.Instancia[totalColunas];
			valor.clear();
			
			for( int coluna = 1; coluna <= totalColunas; coluna++ ){
				String nome = rsmeta.getColumnLabel( coluna );
				valor.add( nome );
				traducao[coluna-1] = consulta.getTraducao().get( nome );
			}
			
			while( rs.next() ){
				
				for( int coluna = 1; coluna <= totalColunas; coluna++ ){
					
					Object o = rs.getObject( coluna );
					String v = null;
					
					if( o != null ){
						switch( rsmeta.getColumnType( coluna ) ){
							case Types.DOUBLE :
							case Types.FLOAT :
							case Types.REAL :
								v = tipoForm.transcrever( rs.getDouble( coluna ), Tipo.REAL );
								break;
							case Types.DATE :
								v = tipoForm.transcrever( rs.getDate( coluna ), Tipo.DATA );
								break;
							case Types.TIME :
								v = tipoForm.transcrever( rs.getTime( coluna ), Tipo.HORA );
								break;
							case Types.TIMESTAMP :
								v = tipoForm.transcrever( rs.getTimestamp( coluna ), Tipo.DATA_HORA );
								break;
							default :
								v = rs.getString( coluna );
								break;
						}
					}else{
						v = "";
					}
					
					if( traducao[coluna-1] != null ){
						Valor t = traducao[coluna-1].getTipo().getValor().get( v );
						valor.add( t != null ? t.getTraducao() : v );
					}else{
						valor.add( v );
					}
				
				}
				
			}

			//Tabela de resultado
			int origem = 0;
			int totalLinhas = valor.size() / totalColunas;
			String[][] tabela = new String[ totalLinhas ][ totalColunas ];
			for( int i = 0; i < totalLinhas; i++ ){
				for( int j = 0; j < totalColunas; j++ ){
					tabela[i][j] = valor.get( origem++ );
				}
			}
			
			resultado.put( consulta.getNome(), tabela );
			
		}
		
		return resultado;
		
	}
	
	private static int converter( Tipo tipo ) {
		switch( tipo ){
			case LOGICO : return Types.BOOLEAN;
			case LETRA : return Types.CHAR;
			case INTEIRO : return Types.INTEGER;
			case REAL : return Types.REAL;
			case DATA : return Types.DATE;
			case HORA : return Types.TIME;
			case DATA_HORA : return Types.TIMESTAMP;
			default : return Types.VARCHAR;
		}
	}
	
}
