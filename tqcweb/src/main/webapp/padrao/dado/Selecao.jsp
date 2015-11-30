
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

Selecao selecao = (Selecao) request.getAttribute( "TQC_Dado_Corrente" );

//-----------------------

int totalOpcoes = selecao.getTotalOpcoes();
int selecionado = selecao.getIndiceSelecionado();

String estiloTamanho = estiloTamanho( selecao );

//-----------------------

if( selecao.isEditavel() ){
%>
<select id="<tqc:id nome='<%=selecao.getNome()%>'/>" name="<tqc:id nome='<%=selecao.getNome()%>'/>" style="<%=estiloTamanho%>" class="<%= selecao.getEstilo() != null ? selecao.getEstilo() : "dadoSelecaoEditavel" %>" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)">
<% for( int i = 0; i < totalOpcoes; i++ ){ %>
<option value="<%=i%>" <%= i == selecionado ? "selected" : "" %>><%=selecao.getTexto( i )%></option>
<% } %>
</select>
<%
}else{
%>
<span class="<%= selecao.getEstilo() != null ? selecao.getEstilo() : "dadoSelecao" %>"><%=selecao.getTexto( selecionado )%></span>
<%
}
%>