
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

<%@ page contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.joseflavio.tqc.*" %>
<%@ page import="com.joseflavio.tqc.aplicacao.*" %>
<%@ page import="com.joseflavio.tqc.servlet.*" %>
<%@ page import="java.util.*" %>
<%

String raiz = request.getContextPath();

String tqc = request.getParameter( "tqc" );
if( tqc == null ) tqc = "";

Bilhete bilhete = (Bilhete) session.getAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_BILHETE + tqc );

String url = null;
String titulo = null;

if( bilhete != null ){
	url = bilhete.getURL();
    titulo = bilhete.getTitulo();
}

if( url == null ) url = raiz + "/index.jsp";
if( titulo == null ) titulo = tqc;

%>
<!DOCTYPE html>
<html>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="refresh" content="3; URL=<%=url%>" />
<link rel="shortcut icon" type="image/x-icon" href="<%=raiz%>/img/logotipo.ico">

<link rel="stylesheet" type="text/css" href="<%=raiz%>/css/tqc.css" />
<link rel="stylesheet" type="text/css" href="<%=raiz%>/ext/jquery/css/padrao/jquery-ui-1.10.3.custom.css" />

<script type="text/javascript" src="<%=raiz%>/ext/jquery/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/jquery/js/jquery-ui-1.10.3.custom.js"></script>

<style>
.esperaFundo {
	background: #FFF;
}
.esperaConteudo {
	text-align: center;
}
</style>

<script>
$(document).keydown(function(e){
	var kc = e.keyCode;
	if( ( kc >= 65 && kc <= 90 ) || ( kc == 13 || kc == 32 || kc == 27 ) ){
		e.preventDefault();
		e.stopPropagation();
		window.location.replace( "<%=url%>" );
		return false;
	}
});
</script>

</head>
<body class="esperaFundo">
<div class="esperaConteudo">
<a href="<%=url%>"><img src="<%=raiz%>/img/carregando.gif" border="0"></a>
</div>
</body>
</html>