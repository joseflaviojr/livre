
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

<%@ include file="../Cabecalho.jsp" %>

<table width="70%" border="0" cellpadding="2" cellspacing="2">

<tr>
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Texto:</span></td>
<td width="30%" class="formColunaValor">
<% if( informacao.isEditavel( "texto" ) ){ %>
<input id="<tqc:id nome="texto"/>" name="<tqc:id nome="texto"/>" value="<tqc:conteudo nome="texto"/>" onfocus="foco(this)" class="dadoTextoEditavel" style="border-width:medium;background:#9FC;" size="30" />
<% }else{ %>
<span class="dadoTexto"><tqc:conteudo nome="texto"/></span>
<% } %>
</td>

<td width="20%" class="formColunaRotulo"><span class="formRotulo">Inteiro:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="inteiro"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>
</tr>

<tr>
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Real:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="real"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>

<td width="20%" class="formColunaRotulo"><span class="formRotulo">Data:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="data"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>
</tr>

<tr>
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Seleção:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="selecao"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>

<td width="20%" class="formColunaRotulo"><span class="formRotulo">Selecionável Texto:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="selecionavelTexto"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>
</tr>

<tr>
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Bruto:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="bruto"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>

<td width="20%" class="formColunaRotulo"><span class="formRotulo">Arquivo:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="arquivo"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>
</tr>

<tr>
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Senha:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="senha"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>

<td width="20%" class="formColunaRotulo">&nbsp;</td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="binario"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>
</tr>

<tr>
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Imagem:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="imagem"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>

<td width="20%" class="formColunaRotulo">&nbsp;</td>
<td width="30%" class="formColunaValor">&nbsp;</td>
</tr>

<tr>
<td colspan="4" class="esquerda">&nbsp;</td>
</tr>

<tr>
<td width="20%">&nbsp;</td>
<td colspan="3" class="esquerda">
<tqc:comandos>
<%@ include file="../Dado.jsp" %>
</tqc:comandos>
</td>
</tr>

</table>

<%@ include file="../Rodape.jsp" %>