
<% /*

  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
  
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

  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
  
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
<td width="20%" class="formColunaRotulo"><span class="formRotulo">Sele��o:</span></td>
<td width="30%" class="formColunaValor"><tqc:dadoCorrente nome="selecao"><%@ include file="../Dado.jsp" %></tqc:dadoCorrente></td>

<td width="20%" class="formColunaRotulo"><span class="formRotulo">Selecion�vel Texto:</span></td>
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