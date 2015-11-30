
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

<%@ include file="Zero.jsp" %>
<!-- ------------------------------------------------------------------------------------------------- -->
<div class="barraControle">
<!-- ------------------------------------------------------------------------------------------------- -->
<img id="iconeMenu" src="<%=raiz%>/img/icone/20/menu.001.png" border="0" width="20" height="20" onmouseover="menuMouseE('menu0')" onmouseout="menuMouseS('menu0')" onclick="mostrarOuOcultarMenu('menu0')" class="logotipo" />
<!-- ------------------------------------------------------------------------------------------------- -->
<div class="viagens">
<%

boolean primeiraViagem = true;
String nomeTelaAtual = "";
		
for( Viagem v : tqc.getViagens() ){
	
	if( v.getAtual() == null ) continue;
	boolean viagemAtiva = v == tqc.getViagemAtiva();

%>
<% if( ! primeiraViagem ){ %>&nbsp;&nbsp;|&nbsp;&nbsp;<% } %>
<a href="javascript: viajar( 'irpara_<%=v.getNome()%>' );" class="<%= viagemAtiva ? "viagemAtiva" : "viagem" %>" tabindex="-1"><%=TextoUtil.fixarComprimento( v.getAtual().getTitulo(), 20, true )%></a>
<%

	if( viagemAtiva ) nomeTelaAtual = v.getNome();
	primeiraViagem = false;

}

%>
</div>
<!-- ------------------------------------------------------------------------------------------------- -->
<div class="comandos">

<% if( informacao.getMensagemErro() != null ){ %>
<img id="iconeErro" src="<%=raiz%>/img/icone/20/vermelho.001.png" border="0" width="20" height="20" onmouseover="opacidade('iconeErro',0.7)" onmouseout="opacidade('iconeErro',1)" onclick="mostrarOuOcultarJanelaMensagem()" title="Mensagem de erro." />
<% } %>
<% if( informacao.getMensagemAtencao() != null ){ %>
<img id="iconeAtencao" src="<%=raiz%>/img/icone/20/amarelo.001.png" border="0" width="20" height="20" onmouseover="opacidade('iconeAtencao',0.7)" onmouseout="opacidade('iconeAtencao',1)" onclick="mostrarOuOcultarJanelaMensagem()" title="Advert&ecirc;ncia" />
<% } %>
<% if( informacao.getMensagemInformacao() != null ){ %>
<img id="iconeInformacao" src="<%=raiz%>/img/icone/20/azul.001.png" border="0" width="20" height="20" onmouseover="opacidade('iconeInformacao',0.7)" onmouseout="opacidade('iconeInformacao',1)" onclick="mostrarOuOcultarJanelaMensagem()" title="Informa&ccedil;&atilde;o" />
<% } %>

<img id="iconeAtualizar" src="<%=raiz%>/img/icone/20/atualizar.001.png" border="0" width="20" height="20" onmouseover="opacidade('iconeAtualizar',0.7)" onmouseout="opacidade('iconeAtualizar',1)" onclick="javascript: document.formTQC.submit();" title="Atualizar as informa&ccedil;&otilde;es da tela." />

<% if( informacao.possuiAjuda() ){ %>
<img id="iconeAjuda" src="<%=raiz%>/img/icone/20/ajuda.001.png" border="0" width="20" height="20" onmouseover="opacidade('iconeAjuda',0.7)" onmouseout="opacidade('iconeAjuda',1)" onclick="viajar('mostrarAjuda')" title="Ajuda" />
<% } %>

<img id="iconeFechar" src="<%=raiz%>/img/icone/20/fechar.001.png" border="0" width="20" height="20" onmouseover="opacidade('iconeFechar',0.7)" onmouseout="opacidade('iconeFechar',1)"  onclick="fecharTela( '<%=nomeTelaAtual%>' )" ondblclick="encerrarAplicacao()" title="Fechar a tela atual. Utilize duplo clique para encerrar toda a aplica&ccedil;&atilde;o." />

</div>
<!-- ------------------------------------------------------------------------------------------------- -->
</div>
<!-- ------------------------------------------------------------------------------------------------- -->
<div id="menuAreaDeSaida" class="menuAreaDeSaida" onmouseover="ocultarMenus(null)"></div>
<!-- ------------------------------------------------------------------------------------------------- -->
<%!

private static void mapearMenus( Menu m, List<Menu> menus, boolean adicionar ) {
	if( adicionar ) menus.add( m );
	for( OpcaoDeMenu om : m.getOpcoes() ){
		if( om instanceof Menu ) mapearMenus( (Menu) om, menus, true );
	}
}

%>
<!-- ------------------------------------------------------------------------------------------------- -->
<%

Menu menu = informacao.getMenu();
List<Menu> menus = new ArrayList<Menu>();

if( menu != null ) mapearMenus( menu, menus, false );

%>
<!-- ------------------------------------------------------------------------------------------------- -->
<div id="menu0" class="menu" onmouseover="ativarAreaDeSaida()">
	<div class="titulo"><%= menu != null ? menu.getRotulo() : "Menu" %></div>
    <table border="0">
<% for( OpcaoDeMenu om : ( menu != null ? menu.getOpcoes() : new Lista<OpcaoDeMenu>() ) ){ %>
	<% if( om instanceof Menu ){ %>
        <tr>
        	<td><a href="javascript: mostrarMenu( 'menu<%= menus.indexOf( (Menu) om ) + 2 %>' );" class="submenu"><%=om.getRotulo()%></a></td>
        	<td><img src="<%=raiz%>/img/icone/128/setamenu.001.png" width="20" height="20" style="vertical-align: bottom" /></td>
        </tr>
	<% }else if( om instanceof Comando ){ %>
        <tr>
            <td><a href="javascript: viajar( 'menu@<%=((Comando)om).getNome()%>' );" class="comando"><%=om.getRotulo()%></a></td>
            <td>&nbsp;</td>
        </tr>
	<% } %>
<% } %>
        <tr>
            <td><a href="javascript: mostrarMenu( 'menu1' );" class="submenu">Telas</a></td>
            <td><img src="<%=raiz%>/img/icone/128/setamenu.001.png" width="20" height="20" style="vertical-align: bottom" /></td>
        </tr>
        <tr>
            <td><a href="javascript: encerrarAplicacao();" class="comando">Encerrar</a></td>
            <td>&nbsp;</td>
        </tr>
    </table>
</div>
<!-- ------------------------------------------------------------------------------------------------- -->
<div id="menu1" class="menu" onmouseover="ativarAreaDeSaida()">
	<div class="titulo">Telas</div>
    <table border="0">
<% for( Viagem v : tqc.getViagens() ){ if( v.getAtual() == null ) continue; %>
        <tr>
            <td><a href="javascript: viajar( 'irpara_<%=v.getNome()%>' );" class="comando"><%=v.getAtual().getTitulo()%></a></td>
            <td>&nbsp;</td>
        </tr>
<% } %>
   </table>
</div>
<!-- ------------------------------------------------------------------------------------------------- -->
<% for( Menu m : menus ){ %>
<div id="menu<%= menus.indexOf( m ) + 2 %>" class="menu" onmouseover="ativarAreaDeSaida()">
	<div class="titulo"><%=m.getRotulo()%></div>
    <table border="0">
<% for( OpcaoDeMenu om : m.getOpcoes() ){ %>
	<% if( om instanceof Menu ){ %>
        <tr>
        	<td><a href="javascript: mostrarMenu( 'menu<%= menus.indexOf( (Menu) om ) + 2 %>' );" class="submenu"><%=om.getRotulo()%></a></td>
        	<td><img src="<%=raiz%>/img/icone/128/setamenu.001.png" width="20" height="20" style="vertical-align: bottom" /></td>
        </tr>
	<% }else if( om instanceof Comando ){ %>
        <tr>
            <td><a href="javascript: viajar( 'menu@<%=((Comando)om).getNome()%>' );" class="comando"><%=om.getRotulo()%></a></td>
            <td>&nbsp;</td>
        </tr>
	<% } %>
<% } %>
    </table>
</div>
<% } %>
<!-- ------------------------------------------------------------------------------------------------- -->
<script>
	var quantMenus = <%= menus.size() + 2 %>;
</script>
<!-- ------------------------------------------------------------------------------------------------- -->