
<% /*

  Copyright (C) 2009-2013 José Flávio de Souza Dias Júnior
  
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

  Direitos Autorais Reservados (C) 2009-2013 José Flávio de Souza Dias Júnior
  
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

Texto texto = (Texto) request.getAttribute( "TQC_Dado_Corrente" );

//-----------------------

String conteudo = texto.getTexto();
if( conteudo == null ) conteudo = "";

String estiloTamanho = estiloTamanho( texto );

AnexoMascara mascara = texto.getAnexo( AnexoMascara.class );

//-----------------------

if( texto.isEditavel() ){
	
	TextoLimite textoLimite = (TextoLimite) texto.getValidacao( TextoLimite.class );
	int maxCaracs = textoLimite != null ? textoLimite.getMaxCaracteres() : 0;
	
	if( texto.isMultiplaLinha() ){
		conteudo = conteudo.replaceAll( "\"", "&quot;" );
	
%>
<textarea id="<tqc:id nome='<%=texto.getNome()%>'/>" name="<tqc:id nome='<%=texto.getNome()%>'/>" <%= texto.getLarguraTextual() > 0 ? " cols=\"" + texto.getLarguraTextual() + "\"" : "" %> <%= texto.getAlturaTextual() > 0 ? " rows=\"" + texto.getAlturaTextual() + "\"" : "" %> wrap="<%= texto.isQuebraDeLinhaAutomatica() ? "soft" : "off" %>" style="<%=estiloTamanho%>" class="<%= texto.getEstilo() != null ? texto.getEstilo() : "dadoTextoEditavel" %>" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)"><%=conteudo%></textarea>
<%
	}else{
%>
<input type="text" id="<tqc:id nome='<%=texto.getNome()%>'/>" name="<tqc:id nome='<%=texto.getNome()%>'/>" value="<%=conteudo%>" <%= texto.getLarguraTextual() > 0 ? " size=\"" + texto.getLarguraTextual() + "\"" : "" %> <%= maxCaracs > 0 ? " maxlength=\"" + maxCaracs + "\"" : "" %> style="<%=estiloTamanho%>" class="<%= texto.getEstilo() != null ? texto.getEstilo() : "dadoTextoEditavel" %>" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)" />
<% if( mascara != null ){ %>
<script>$(function() { $( "#<tqc:id nome='<%=texto.getNome()%>'/>" ).mask( "<%=converterMascara( mascara.getMascara() )%>" ); });</script>
<% } %>
<%
	}
%>
<%
}else{
	conteudo = TQCServletUtil.converterParaHTML( conteudo );
%>
<span class="<%= texto.getEstilo() != null ? texto.getEstilo() : "dadoTexto" %>"><%=conteudo%></span>
<%
}
%>