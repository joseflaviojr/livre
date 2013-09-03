
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

<%@ include file="../Zero.jsp" %>
<%

Dado dado = (Dado) request.getAttribute( "TQC_Dado_Corrente" );
Dado ultimoDado = (Dado) request.getAttribute( "TQC_Dado_Ultimo" );

if( dado.isVisivel() ){
	
AnexoDica anexoDica = dado instanceof SimplesDado ? ((SimplesDado)dado).getAnexo( AnexoDica.class ) : null;
String dica = anexoDica != null ? anexoDica.getMensagem() : null;
if( dica != null && dica.length() == 0 ) dica = null;

%>
<% if( dado instanceof Texto ){ %>
<%@ include file="Texto.jsp" %>

<% }else if( dado instanceof SelecionavelTexto ){ %>
<%@ include file="SelecionavelTexto.jsp" %>

<% }else if( dado instanceof Comando ){ %>
<%@ include file="Comando.jsp" %>

<% }else if( dado instanceof Inteiro ){ %>
<%@ include file="Inteiro.jsp" %>

<% }else if( dado instanceof Real ){ %>
<%@ include file="Real.jsp" %>

<% }else if( dado instanceof Data ){ %>
<%@ include file="Data.jsp" %>

<% }else if( dado instanceof Selecao ){ %>
<%@ include file="Selecao.jsp" %>

<% }else if( dado instanceof SelecaoMultipla ){ %>
<%@ include file="SelecaoMultipla.jsp" %>

<% }else if( dado instanceof Senha ){ %>
<%@ include file="Senha.jsp" %>

<% }else if( dado instanceof Bruto ){ %>
<%@ include file="Bruto.jsp" %>

<% }else if( dado instanceof Arquivo ){ %>
<%@ include file="Arquivo.jsp" %>

<% }else if( dado instanceof Binario ){ %>
<%@ include file="Binario.jsp" %>

<% }else if( dado instanceof Imagem ){ %>
<%@ include file="Imagem.jsp" %>

<% }else if( dado instanceof Objeto ){ %>
<%@ include file="Objeto.jsp" %>

<% }else if( dado instanceof QuebraDeLinha ){ %>
<br />

<% }else if( dado instanceof Linha ){	Linha linha = (Linha) dado; %>
<% if( ultimoDado != null && ! ( ultimoDado instanceof QuebraDeLinha || ultimoDado instanceof LinhaFim ) ){ %><br /><% } %>
<div class="<%= linha.getEstilo() != null ? linha.getEstilo() : "" %>">

<% }else if( dado instanceof LinhaFim ){ %>
</div>

<% }else if( dado instanceof Tabela ){	Tabela tabela = (Tabela) dado; %>
<table cellspacing="2" cellpadding="2" class="<%= tabela.getEstilo() != null ? tabela.getEstilo() : "dadoTabela" %>">

<% }else if( dado instanceof TabelaLinha ){	TabelaLinha tabelaLinha = (TabelaLinha) dado; %>
<tr class="<%= tabelaLinha.getEstilo() != null ? tabelaLinha.getEstilo() : "" %>">

<% }else if( dado instanceof TabelaColuna ){	TabelaColuna tabelaColuna = (TabelaColuna) dado; %>
<td class="<%= tabelaColuna.getEstilo() != null ? tabelaColuna.getEstilo() : "" %>">

<% }else if( dado instanceof TabelaColunaFim ){ %>
</td>

<% }else if( dado instanceof TabelaLinhaFim ){ %>
</tr>

<% }else if( dado instanceof TabelaFim ){ %>
</table>

<% }else if( dado instanceof Marcador ){ %>

<% }else if( dado instanceof EspacoTextual ){
	int quantEspacoTextual = ((EspacoTextual)dado).getQuantidade();
	for( int i = 0; i < quantEspacoTextual; i++ ){ %>&nbsp;<% } %>
<% } %>

<% if( dado instanceof EspacoLateral && ((EspacoLateral)dado).isEspacoTextualPosterior() ){ %>&nbsp;<% } %>

<%

if( ! ( dado instanceof Marcador ) ) request.setAttribute( "TQC_Dado_Ultimo", ultimoDado = dado );

} //Visivel

%>
<%!

private static String estiloTamanho( SimplesDado dado ) {
	
	String estilo = "";
	AnexoTamanho at;
	
	if( ( at = dado.getTamanhoComLargura() ) != null && at.getLargura() > 0  ) estilo += "width:" + at.getLargura() + at.getUnidade().toString() + ";";
	if( ( at = dado.getTamanhoComAltura() ) != null && at.getAltura() > 0  ) estilo += "height:" + at.getAltura() + at.getUnidade().toString() + ";";
	if( ( at = dado.getTamanhoComLarguraMaxima() ) != null && at.getLarguraMaxima() > 0  ) estilo += "max-width:" + at.getLarguraMaxima() + at.getUnidade().toString() + ";";
	if( ( at = dado.getTamanhoComAlturaMaxima() ) != null && at.getAlturaMaxima() > 0  ) estilo += "max-height:" + at.getAlturaMaxima() + at.getUnidade().toString() + ";";
	if( ( at = dado.getTamanhoComLarguraMinima() ) != null && at.getLarguraMinima() > 0  ) estilo += "min-width:" + at.getLarguraMinima() + at.getUnidade().toString() + ";";
	if( ( at = dado.getTamanhoComAlturaMinima() ) != null && at.getAlturaMinima() > 0  ) estilo += "min-height:" + at.getAlturaMinima() + at.getUnidade().toString() + ";";
	
	return estilo;
	
}

private static String converterMascara( String padrao ) {
	return padrao.replaceAll( "A", "S" ).replaceAll( "X", "A" );
}

%>