
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