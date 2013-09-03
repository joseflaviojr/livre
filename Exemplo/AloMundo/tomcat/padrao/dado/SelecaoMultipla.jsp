
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

SelecaoMultipla selecaoMultipla = (SelecaoMultipla) request.getAttribute( "TQC_Dado_Corrente" );

//-----------------------

int totalOpcoes = selecaoMultipla.getTotalOpcoes();
boolean horizontal = selecaoMultipla.isDisposicaoHorizontal();

String estiloTamanho = estiloTamanho( selecaoMultipla );

//-----------------------

%>
<div style="<%=estiloTamanho%>">
<%
if( selecaoMultipla.isEditavel() ){
%>
<input type="hidden" id="<tqc:id nome='<%=selecaoMultipla.getNome()%>'/>" name="<tqc:id nome='<%=selecaoMultipla.getNome()%>'/>" value="TQC#SELECAOMULTIPLA" />
<input type="hidden" id="<tqc:id nome='<%=selecaoMultipla.getNome()%>'/>_total" name="<tqc:id nome='<%=selecaoMultipla.getNome()%>'/>_total" value="<%=totalOpcoes%>" />
<%
for( int i = 0; i < totalOpcoes; i++ ){
	boolean opcaoSelecionada = selecaoMultipla.isSelecionado( i );
%>
<input type="checkbox" id="<tqc:id nome='<%=selecaoMultipla.getNome()%>'/>_<%=i%>" name="<tqc:id nome='<%=selecaoMultipla.getNome()%>'/>_<%=i%>" value="v" <%= opcaoSelecionada ? "checked" : "" %> class="centroV" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)" />
<span class="<%= selecaoMultipla.getEstilo() != null ? selecaoMultipla.getEstilo() : "dadoSelecaoMultipla centroV" %>"><%=selecaoMultipla.getTexto( i )%></span>
<% if( horizontal ){ %>&nbsp;&nbsp;<% }else{ %><br /><% } %>
<% } %>
<%
}else{
%>
<span class="<%= selecaoMultipla.getEstilo() != null ? selecaoMultipla.getEstilo() : "dadoSelecaoMultipla" %>">
<%
boolean primeiro = true;
for( int i = 0; i < totalOpcoes; i++ ){
	if( ! selecaoMultipla.isSelecionado( i ) ) continue;
%>
<% if( ! primeiro ){ %>,&nbsp;<% } %>
<%=selecaoMultipla.getTexto( i )%>
<%
	primeiro = false;
}
%>
</span>
<%
}
%>
</div>