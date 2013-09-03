
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

<!-- ------------------------------------------------------------------------------------------------- -->
<%@ page contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.joseflavio.tqc.*" %>
<%@ page import="com.joseflavio.tqc.dado.*" %>
<%@ page import="com.joseflavio.tqc.servlet.*" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!-- ------------------------------------------------------------------------------------------------- -->
<%

//Parâmetros

String raiz = request.getContextPath();

String tqcClassName = request.getParameter( "tqc" );

TomaraQueCaia tqc = (TomaraQueCaia) session.getAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_SESSAO + tqcClassName );
String dest = (String) session.getAttribute( TomaraQueCaiaDesktopServlet.PREFIXO_TQC_URL + tqcClassName );

if( tqc == null || dest == null ) return;

Viagem viagem = tqc.getViagem( request.getParameter( "viagem" ) );
long passo = Long.parseLong( request.getParameter( "passo" ) );

if( viagem.getPasso() != passo ){
	response.sendRedirect( dest );
	return;
}

Informacao info = viagem.getAtual();
int indice = Integer.parseInt( request.getParameter( "dado" ) );

if( info == null || indice >= info.getTotalDados() ){
	response.sendRedirect( dest );
	return;
}

Dado dado = info.getDado( indice );

if( ( dado == null ) || ! ( dado instanceof Bruto || dado instanceof Arquivo ) ){
	response.sendRedirect( dest );
	return;
}

//Processamento do formulário

FileItem item = null;
boolean enviar = false;
boolean substituir = false;
int erro = -1;
String erroMsg = null;

try{

if( ServletFileUpload.isMultipartContent( request ) ){

	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setRepository( new java.io.File( application.getRealPath( "/tmp" ) ) );
	Iterator i = new ServletFileUpload( factory ).parseRequest( request ).iterator();

	while( i.hasNext() ){
		
		FileItem itemTmp = (FileItem) i.next();

		if( itemTmp.isFormField() ){
			String fieldName = itemTmp.getFieldName();
			if( fieldName.equals( "enviar" ) ) enviar = itemTmp.getString() != null;
			else if( fieldName.equals( "substituir" ) ) substituir = itemTmp.getString() != null;
		}else{
			item = itemTmp;
		}
		
	}
	
}

//Atribuição

if( enviar && erro == -1 ){

	if( dado instanceof Bruto ){
	
		Bruto bruto = (Bruto) dado;
		
		if( item.getSize() > 0 ){
			bruto.setValor( item.get() );
			bruto.setValorRotulo( com.joseflavio.util.ArquivoUtil.getNome( item.getName() ) );
		}else{
			bruto.setValor( null );
			bruto.setValorRotulo( null );
		}
		
	}else if( dado instanceof Arquivo ){
		
		Arquivo arquivo = (Arquivo) dado;
		
		if( item.getSize() > 0 ){
			
			String arquivoNome = com.joseflavio.util.ArquivoUtil.getNome( item.getName() );
			File local = arquivo.getLocalPreferido();
			if( ! local.exists() ) local.mkdirs();
			File novo = local != null ? new File( local, arquivoNome ) : null;
			
			if( novo == null ){
				erro = 0;
			}else if( novo.exists() ){
				if( substituir ){
					novo.delete();
				}else{
					erro = 1;
					erroMsg = arquivoNome;
				}
			}
			
			if( erro == -1 ){
				item.write( novo );
				arquivo.setArquivo( novo );
			}
			
		}else{
		
			arquivo.setArquivo( null );
			
		}
		
	}

	if( erro == -1 ){
		response.sendRedirect( dest );
		return;
	}

}

}catch( Throwable e ){
	erro = 0;
}

%>
<!-- ------------------------------------------------------------------------------------------------- -->
<!DOCTYPE html>
<html>

<head>

<title><%=tqc.getTitulo()%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" type="text/css" href="<%=raiz%>/css/tqc.css" />
<link rel="stylesheet" type="text/css" href="<%=raiz%>/ext/jquery/css/padrao/jquery-ui-1.10.3.custom.css" />

<script type="text/javascript" src="<%=raiz%>/ext/jquery/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/jquery/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/jquery/development-bundle/ui/i18n/jquery.ui.datepicker-pt-BR.js"></script>
<script type="text/javascript" src="<%=raiz%>/ext/css_browser_selector/css_browser_selector.js"></script>

<script>

$(function() {
	
	$("input[type=submit], input[type=button]")
		.button()
		.click(function( event ) {
			$("#formTQC").submit( event.handler );
	});
	
	$("#arquivo").focus();
	
});

$(document).keydown(function(e){
	var kc = e.keyCode;
	if( kc == 13 ){
		e.preventDefault();
		e.stopPropagation();
		$("#enviar").trigger( "click" );
		return false;
	}else if( kc == 27 ){
		e.preventDefault();
		e.stopPropagation();
		$("#cancelar").trigger( "click" );
		return false;
	}
});

</script>

</head>

<body>
<form id="formTQC" name="formTQC" method="POST" enctype="multipart/form-data">
<table width="100%" border="0">
<tr>
<td width="13%" valign="top"><img src="<%=raiz%>/img/upload.jpg"></td>
<td width="87%">
<span class="formSubtitulo">Selecione abaixo o arquivo desejado:</span><br><br>
<input type="file" name="arquivo" id="arquivo" /><br>
<% if( dado instanceof Arquivo ){ %>
<input type="checkbox" name="substituir" id="substituir" value="substituir" class="centroV" checked />
<span class="dadoTexto centroV">Substituir, caso já exista</span><br>
<% } %>
<br>
<input type="submit" name="enviar" id="enviar" value="Enviar" />
<input type="button" name="cancelar" id="cancelar" value="Cancelar" onClick="javascript:location='<%=dest%>'" />
<% if( erro == 0 ){ %><div class="formMensagemErro" align="left">N&atilde;o foi poss&iacute;vel enviar o arquivo.</div><% } %>
<% if( erro == 1 ){ %><div class="formMensagemErro" align="left">J&aacute; existe um arquivo com o mesmo nome no sistema: <%=erroMsg%></div><% } %></td>
</tr>
</table>
</form>
</body>
</html>
<!-- ------------------------------------------------------------------------------------------------- -->