
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

Inteiro inteiro = (Inteiro) request.getAttribute( "TQC_Dado_Corrente" );

//-----------------------

String conteudo = null;

if( inteiro.getConteudoInvalido() != null ){
	
	conteudo = inteiro.getConteudoInvalido().toString();
	
}else{
	try{
		
		NumeroTransformacao transformacao = tqc.getCultura().novoNumeroTransformacao();
		transformacao.setMaximoDigitosNaFracao( 0 );
		transformacao.setMinimoDigitosNaFracao( 0 );
		transformacao.setUsarMilha( false );
		
		Long numero = inteiro.getNumero();
		conteudo = numero != null ? transformacao.transcrever( numero.longValue() ) : "";
		
	}catch( TransformacaoException e ){
	}
}
		
if( conteudo == null ) conteudo = "";

String estiloTamanho = estiloTamanho( inteiro );

AnexoMascara mascara = inteiro.getAnexo( AnexoMascara.class );

//-----------------------

if( inteiro.isEditavel() ){
	
	TextoLimite textoLimite = (TextoLimite) inteiro.getValidacao( TextoLimite.class );
	int maxCaracs = textoLimite != null ? textoLimite.getMaxCaracteres() : 0;
	
%>
<input type="text" id="<tqc:id nome='<%=inteiro.getNome()%>'/>" name="<tqc:id nome='<%=inteiro.getNome()%>'/>" value="<%=conteudo%>" <%= inteiro.getLarguraTextual() > 0 ? " size=\"" + inteiro.getLarguraTextual() + "\"" : "" %> <%= maxCaracs > 0 ? " maxlength=\"" + maxCaracs + "\"" : "" %> style="<%=estiloTamanho%>" class="<%= inteiro.getEstilo() != null ? inteiro.getEstilo() : "dadoTextoEditavel" %>" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)" />
<% if( mascara != null ){ %>
<script>$(function() { $( "#<tqc:id nome='<%=inteiro.getNome()%>'/>" ).mask( "<%=converterMascara( mascara.getMascara() )%>" ); });</script>
<% } %>
<%
}else{
%>
<span class="<%= inteiro.getEstilo() != null ? inteiro.getEstilo() : "dadoTexto" %>"><%=TextoUtil.limitarComprimento( conteudo, inteiro.getLarguraTextual(), true )%></span>
<%
}
%>