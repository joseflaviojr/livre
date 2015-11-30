
<% /*

  Copyright (C) 2009-2015 José Flávio de Souza Dias Júnior
  
  This file is part of José Flávio Livre - <http://www.joseflavio.com/livre/>.
  
  José Flávio Livre is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  José Flávio Livre is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU Lesser General Public License for more details.
  
  You should have received a copy of the GNU Lesser General Public License
  along with José Flávio Livre. If not, see <http://www.gnu.org/licenses/>.

*/ %>

<% /*

  Direitos Autorais Reservados (C) 2009-2015 José Flávio de Souza Dias Júnior
  
  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
  
  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
  Free Software Foundation, tanto a versão 3 da Licença, como
  (a seu critério) qualquer versão posterior.
  
  José Flávio Livre é distribuído na expectativa de que seja útil,
  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
  Licença Pública Menos Geral do GNU para mais detalhes.
  
  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.

*/ %>

<%

//-----------------------

Data data = (Data) request.getAttribute( "TQC_Dado_Corrente" );

Data.DataTipo dataTipo = data.getTipo();
Data.DataTipo DATA = Data.DataTipo.DATA;
Data.DataTipo HORA = Data.DataTipo.HORA;

//-----------------------

String conteudo = null;
String estiloPadrao = dataTipo == DATA ? "dadoData" : dataTipo == HORA ? "dadoHora" : "dadoDataHora";

if( data.getConteudoInvalido() != null ){
	
	conteudo = data.getConteudoInvalido().toString();

}else if( data.getData() != null ){
	
	try{
		
		DataTransformacao transformacao = tqc.getCultura().novaDataTransformacaoNumerica();
		
		switch( dataTipo ){
			case DATA :
				conteudo = transformacao.transcreverData( data.getData() );
				break;
			case HORA :
				conteudo = transformacao.transcreverHora( data.getData() );
				break;
			default :
				conteudo = transformacao.transcreverDataHora( data.getData() );
				break;
		}
		
	}catch( TransformacaoException e ){
	}
	
}
		
if( conteudo == null ) conteudo = "";

String estiloTamanho = estiloTamanho( data );

//-----------------------

if( data.isEditavel() ){
	
	int larguraTextual = data.getTipo() == DATA ? 10 : data.getTipo() == HORA ? 5 : 16;
	if( data.getLarguraTextual() > larguraTextual ) larguraTextual = data.getLarguraTextual();
	
%>
<input type="text" id="<tqc:id nome='<%=data.getNome()%>'/>" name="<tqc:id nome='<%=data.getNome()%>'/>" value="<%=conteudo%>" size="<%=larguraTextual%>" maxlength="<%=larguraTextual%>" style="<%=estiloTamanho%>" class="<%= data.getEstilo() != null ? data.getEstilo() : estiloPadrao %>" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)" />
<%
}else{
%>
<span class="<%= data.getEstilo() != null ? data.getEstilo() : "dadoTexto" %>"><%=TextoUtil.limitarComprimento( conteudo, data.getLarguraTextual(), true )%></span>
<%
}
%>