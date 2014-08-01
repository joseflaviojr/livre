
<% /*

  Copyright (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
  
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

  Direitos Autorais Reservados (C) 2009-2014 Jos� Fl�vio de Souza Dias J�nior
  
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

<!-- ------------------------------------------------------------------------------------------------- -->
</head>

<body class="fundoGeral">
<form id="formTQC" name="formTQC" method="POST" action="<tqc:url/>">

<tqc:identificacao/>

<input type="hidden" id="comandoViagem" name="comandoViagem" value="" />

<input type="hidden" id="rolagemHorizontal" name="rolagemHorizontal" value="0" />
<input type="hidden" id="rolagemVertical" name="rolagemVertical" value="0" />

<input type="hidden" id="focoDado" name="focoDado" value="" />

<% if( solicitacaoLocal ){ %>
<jsp:include page="Controle.jsp" flush="false" />
<% }else{ %>
<jsp:include page="../../padrao/Controle.jsp" flush="false" />
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->
<%

Alerta alerta = viagem.removerAlerta();

if( alerta != null ){
	
	String alertaTitulo = alerta.getTitulo();
	
%>
	<script>inibirConfirmacao=true;</script>
	<% if( alerta.getTipo() == Alerta.ERRO ){ %>
        <div id="mensagemAlerta" title="<%= alertaTitulo != null ? alertaTitulo : "Erro" %>">
        <p>
        <img src="<%=raiz%>/img/icone/32/vermelho.002.png" border="0" width="32" height="32" style="vertical-align: middle" />
        &nbsp;
		<%=alerta.getMensagem()%>
        </p>
        </div>
    <% }else if( alerta.getTipo() == Alerta.INFORMACAO ){ %>
        <div id="mensagemAlerta" title="<%= alertaTitulo != null ? alertaTitulo : "Informa&ccedil;&atilde;o" %>">
        <p>
        <img src="<%=raiz%>/img/icone/32/azul.002.png" border="0" width="32" height="32" style="vertical-align: middle" />
        &nbsp;
        <%=alerta.getMensagem()%>
        </p>
        </div>
    <% }else{ %>
        <div id="mensagemAlerta" title="<%= alertaTitulo != null ? alertaTitulo : "Aten&ccedil;&atilde;o" %>">
        <p>
        <img src="<%=raiz%>/img/icone/32/amarelo.002.png" border="0" width="32" height="32" style="vertical-align: middle" />
        &nbsp;
        <%=alerta.getMensagem()%>
        </p>
        </div>
    <% } %>
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->
<% boolean temMensagem = false; %>
<div id="janelaMensagem" name="janelaMensagem" class="ui-widget-content ui-corner-all janelaMensagem" onclick="javascript: ocultar( 'janelaMensagem' );">
<h3 class="ui-widget-header ui-corner-all">Mensagens</h3>
<%
for( AnexoErro anexo : informacao.getAnexos( AnexoErro.class ) ){
	if( anexo.getMensagem() == null ) continue;
	temMensagem = true;
%>
<p>
<img src="<%=raiz%>/img/icone/20/vermelho.001.png" border="0" width="20" height="20" style="vertical-align: middle" />
<%=TQCServletUtil.converterParaHTML( anexo.getMensagem() )%>
</p>
<% } %>
<%
for( AnexoAtencao anexo : informacao.getAnexos( AnexoAtencao.class ) ){
	if( anexo.getMensagem() == null ) continue;
	temMensagem = true;
%>
<p>
<img src="<%=raiz%>/img/icone/20/amarelo.001.png" border="0" width="20" height="20" style="vertical-align: middle" />
<%=TQCServletUtil.converterParaHTML( anexo.getMensagem() )%>
</p>
<% } %>
<%
for( AnexoInformacao anexo : informacao.getAnexos( AnexoInformacao.class ) ){
	if( anexo.getMensagem() == null ) continue;
	temMensagem = true;
%>
<p>
<img src="<%=raiz%>/img/icone/20/azul.001.png" border="0" width="20" height="20" style="vertical-align: middle" />
<%=TQCServletUtil.converterParaHTML( anexo.getMensagem() )%>
</p>
<% } %>
</div>
<% if( temMensagem ){ %>
<script>
	mostrar( "janelaMensagem" );
	tempoJanelaMensagemTremer = setTimeout( function(){ tremer( "janelaMensagem" ); }, 5000 );
	tempoJanelaMensagemRetirar = setTimeout( function(){ retirarPelaDireita( "janelaMensagem" ); }, 15000 );
</script>
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->
<div class="informacao"><!-- Informa��o -->
<!-- ------------------------------------------------------------------------------------------------- -->
<% if( banner != null ){ %>
<div class="centroH"><img src="<%=raiz%>/<%=banner%>" border="0" /></div>
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->
<% if( subtitulo != null ){ %>
<div class="<%=((BaseInformacao)informacao).isSubtituloCentral() ? "centroH" : "esquerda" %>"><span class="formSubtitulo"><%=subtitulo%></span></div>
<br />
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->