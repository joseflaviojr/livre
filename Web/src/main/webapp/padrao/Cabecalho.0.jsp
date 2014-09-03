
<% /*

  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
  
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

  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
  
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

<!-- ------------------------------------------------------------------------------------------------- -->
<%@ include file="Zero.jsp" %>
<!-- ------------------------------------------------------------------------------------------------- -->
<%

//Banner

String banner = null;
if( informacao instanceof BaseInformacao ){
	banner = ((BaseInformacao)informacao).getBanner();
	//if( banner == null && tqc instanceof AplicacaoTQC ) banner = ((AplicacaoTQC)tqc).getBanner();
}

//Subtítulo

String subtitulo = null;
if( informacao instanceof BaseInformacao ){
	subtitulo = ((BaseInformacao)informacao).getSubtitulo();
	if( subtitulo != null && subtitulo.length() == 0 ) subtitulo = null;
}

//Rolagem

int rolagemHorizontal = 0;
int rolagemVertical = 0;
AnexoRolagem rolagem = informacao.getAnexo( AnexoRolagem.class );
if( rolagem != null ){
	rolagemHorizontal = rolagem.getHorizontal();
	rolagemVertical = rolagem.getVertical();
}

%>
<!-- ------------------------------------------------------------------------------------------------- -->
<!DOCTYPE html>
<html>

<head>

<title><tqc:titulo/></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" type="image/x-icon" href="<%=raiz%>/img/logotipo.ico">
<!-- ------------------------------------------------------------------------------------------------- -->
<script type="text/javascript" src="<%=raiz%>/ext/css_browser_selector/css_browser_selector.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/modernizr/modernizr.tqc.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/mask/jquery.mask.min.js"></script>
<script type="text/javascript" src="<%=raiz%>/<%=pele%>/script/Aplicacao.js"></script>
<!-- ------------------------------------------------------------------------------------------------- -->
<link rel="stylesheet" type="text/css" href="<%=raiz%>/css/tqc.css" />
<link rel="stylesheet" type="text/css" href="<%=raiz%>/ext/jquery/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=raiz%>/ext/jquery/jquery-ui.structure.min.css" />
<link rel="stylesheet" type="text/css" href="<%=raiz%>/ext/jquery/jquery-ui.theme.min.css" />
<%
EstiloFonte[] estiloFontes = tqc.listarEstiloFontes();
for( int i = 0; i < estiloFontes.length; i++ ){
	String estiloNome = estiloFontes[i].getNome();
	if( ! estiloNome.toLowerCase().endsWith( ".css" ) ) estiloNome += ".css";
%>
<link rel="stylesheet" type="text/css" href="<%=raiz%>/css/<%=estiloNome%>" />
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->
<script>
//----------------------

var raiz = "<%=raiz%>";

//----------------------

var negligenciarNavegador = "<%=session.getAttribute( "NEGLIGENCIAR.NAVEGADOR" )%>";

if( negligenciarNavegador != "sim" ){
	if( ! ( Modernizr.opacity && Modernizr.borderradius && Modernizr.boxshadow && Modernizr.textshadow && Modernizr.opacity ) ){
		window.location.replace( "<%=raiz%>/<%=pele%>/Compatibilidade.jsp?tqc=<%=tqcClassName%>" );
		return;
	}
}

//----------------------
</script>
<script>
<% boolean seltextoPrimeiro; %>
<% for( SelecionavelTexto st : informacao.getDados( SelecionavelTexto.class ) ){ %>
	$(function() {
		var opcoes = [
		<% seltextoPrimeiro = true; %>
		<% for( String op : st.getOpcoes() ){ %>
			<%= seltextoPrimeiro ? "" : ", " %>"<%=op%>"
			<% seltextoPrimeiro = false; %>
		<% } %>
		];
		$( "#<tqc:id nome='<%=st.getNome()%>'/>" ).autocomplete({
			source: opcoes,
			minLength: 0
		}).focus(function() {
			if( this.value == "" ) $(this).autocomplete( "search", "" );
		});
	});
<% } %>
</script>
<!-- ------------------------------------------------------------------------------------------------- -->