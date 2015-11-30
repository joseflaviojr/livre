
<% /*

  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
  
  This file is part of Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
  
  Jos� Fl�vio Livre is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  Jos� Fl�vio Livre is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU Lesser General Public License for more details.
  
  You should have received a copy of the GNU Lesser General Public License
  along with Jos� Fl�vio Livre. If not, see <http://www.gnu.org/licenses/>.

*/ %>

<% /*

  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
  
  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
  
  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
  (a seu crit�rio) qualquer vers�o posterior.
  
  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
  Licen�a P�blica Menos Geral do GNU para mais detalhes.
  
  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.

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