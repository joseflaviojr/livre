
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

<%@ page contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.joseflavio.tqc.*" %>
<%@ page import="com.joseflavio.tqc.aplicacao.*" %>
<%@ page import="com.joseflavio.tqc.servlet.*" %>
<%@ page import="java.util.*" %>
<%

session.setAttribute( "NEGLIGENCIAR.NAVEGADOR", "nao" );

Properties propriedades = TomaraQueCaia.carregarPropriedades( null );
String prop_titulo = propriedades.getProperty( "tqc.titulo" );
String prop_reiniciar = propriedades.getProperty( "tqc.reiniciarAoEncerrar" );

String raiz = request.getContextPath();

String tqc = request.getParameter( "tqc" );
if( tqc == null ) tqc = "";

Bilhete bilhete = (Bilhete) session.getAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_BILHETE + tqc );

String url = null;
String titulo = null;
String banner = null;

if( bilhete != null ){
	url = bilhete.getURL();
    titulo = bilhete.getTitulo();
    banner = bilhete.getBanner();
}

if( url == null ) url = raiz + "/index.jsp";
if( titulo == null ) titulo = prop_titulo != null ? prop_titulo : tqc;
if( banner == null || banner.length() == 0 ) banner = "img/aplicacaofinalizada.jpg";

if( prop_reiniciar != null && prop_reiniciar.equals( "sim" ) ){
	response.sendRedirect( url );
	return;
}

%>
<!DOCTYPE html>
<html>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" type="image/x-icon" href="<%=raiz%>/img/logotipo.ico">

<link rel="stylesheet" type="text/css" href="<%=raiz%>/css/tqc.css" />
<link rel="stylesheet" type="text/css" href="<%=raiz%>/ext/jquery/css/padrao/jquery-ui-1.10.3.custom.css" />

<style>
.finalizacaoFundo {
	background: #FFF;
}
.finalizacaoConteudo {
	text-align: center;
}
</style>

<script type="text/javascript" src="<%=raiz%>/ext/jquery/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/jquery/js/jquery-ui-1.10.3.custom.js"></script>

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
<body class="finalizacaoFundo">
<div class="finalizacaoConteudo">
<a href="<%=url%>"><img id="banner" src="<%=raiz%>/<%=banner%>" border="0"></a>
</div>
</body>
<script>
$(document).ready(function(e) {
	var imagem = document.getElementById( "banner" );
	var largura = imagem.width;
	var altura = imagem.height;
	if( largura > 0 && altura > 0 ){
		imagem.style.position = "absolute";
		imagem.style.top = "50%";
		imagem.style.left = "50%";
		imagem.style.marginTop = ( - altura / 2 ) + "px";
		imagem.style.marginLeft = ( - largura / 2 ) + "px";
	}
});
</script>
</html>