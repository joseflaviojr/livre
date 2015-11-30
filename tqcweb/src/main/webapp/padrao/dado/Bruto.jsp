
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

Bruto bruto = (Bruto) request.getAttribute( "TQC_Dado_Corrente" );

//-----------------------

Aparencia<Bruto> aparencia = bruto.getAparencia();
String conteudo = aparencia != null ? aparencia.texto( bruto ) : bruto.getValor() != null ? bruto.getValorRotulo() : "";

String link = request.getContextPath() + "/tqcdownload?tqc=" + tqc.getClass().getName() + "&viagem=" + viagem.getNome() + "&passo=" + viagem.getPasso() + "&dado=" + informacao.getIndice( bruto );

String estiloTamanho = estiloTamanho( bruto );

//-----------------------

if( bruto.isEditavel() ){
	
	if( conteudo.length() == 0 ) conteudo = "            ";
	
%>
<input type="submit" id="<tqc:id nome='<%=bruto.getNome()%>'/>" name="<tqc:id nome='<%=bruto.getNome()%>'/>" value="<%=conteudo%>" style="<%=estiloTamanho%>" class="dadoArquivo" <%= dica != null ? "title=\"" + dica + "\"" : "" %> onfocus="foco(this)" onblur="focoperdido(this)" />
<%

	if( bruto.getConteudo() != null ){
		
%>
&nbsp;<a href="<%=link%>" target="_blank"><img src="<%=raiz%>/img/icone/24/clipepapel.001.png" border="0" style="width:18px;height:18px;vertical-align:middle;"></a>
<%

	}

}else if( bruto.getConteudo() != null ){
	
%>
<a href="<%=link%>" target="_blank"><%=conteudo%></a>
<%

}

%>