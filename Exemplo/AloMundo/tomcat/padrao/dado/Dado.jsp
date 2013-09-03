
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