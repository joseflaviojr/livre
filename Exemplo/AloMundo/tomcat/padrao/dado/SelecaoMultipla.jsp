
<% /*

  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
  
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

  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
  
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