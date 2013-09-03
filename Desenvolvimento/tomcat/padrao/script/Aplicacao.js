
/*

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

*/

/*

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

*/

//----------------------

var inibirConfirmacao = false;
var tempoFecharTela;

//----------------------
/*
$(function() {
	$( "input[type=submit], button" )
		.button()
		.click(function( event ) {
			$("#formTQC").submit( event.handler );
	});
});
*/
//----------------------

$(function() {
	$( document ).tooltip({
		show: {
			delay: 1500
		}
    });
});

//----------------------

function viajar( comando ) {
	document.formTQC.comandoViagem.value = comando;
	document.formTQC.submit();
}

//----------------------

function fecharTela( tela ) {
	tempoFecharTela = setTimeout( function() { viajar( 'fechar_' + tela ); }, 250 );
}

//----------------------

function encerrarAplicacao() {
	if( tempoFecharTela ) clearTimeout( tempoFecharTela );
	viajar( "encerrarAplicacao" );
}

//----------------------

$(document).keydown(function(e){
	if( inibirConfirmacao ) return true;
	var kc = e.keyCode;
	if( kc == 13 ){
		e.preventDefault();
		e.stopPropagation();
		viajar( "confirmar" + ( e.target.name != null ? "@" + e.target.name : "" ) );
		return false;
	}else if( kc == 27 ){
		e.preventDefault();
		e.stopPropagation();
		viajar( "cancelar" );
		return false;
	}else if( kc == 37 && e.target == document.body ){
		e.preventDefault();
		e.stopPropagation();
		viajar( "voltar" );
		return false;
	}else if( kc == 39 && e.target == document.body ){
		e.preventDefault();
		e.stopPropagation();
		viajar( "avancar" );
		return false;
	}
});

//----------------------

$(function() {
	$( "#mensagemAlerta" ).dialog({
		modal: true,
		closeOnEscape: false,
		closeText : "Fechar",
		buttons: {
			"OK": function() {
			 	$( this ).dialog( "close" );
			}
		},
		close: function( event, ui ) { inibirConfirmacao = false; }
	});
});

//----------------------

function mostrar( componente ){
	if( $( "#" + componente ).is(":hidden") ) $( "#" + componente ).fadeIn( 500 );
}

function ocultar( componente ){
	if( ! $( "#" + componente ).is(":hidden") ) $( "#" + componente ).fadeOut( 500 );
}

function mostrar( componente, duracao ){
	if( $( "#" + componente ).is(":hidden") ) $( "#" + componente ).fadeIn( duracao );
}

function ocultar( componente, duracao ){
	if( ! $( "#" + componente ).is(":hidden") ) $( "#" + componente ).fadeOut( duracao );
}

function mostrarOuOcultar( componente ){
	if( $( "#" + componente ).is(":hidden") ) $( "#" + componente ).fadeIn( 500 ); else $( "#" + componente ).fadeOut( 500 );
}

function tremer( componente ){
	if( ! $( "#" + componente ).is(":hidden") ) $( "#" + componente ).effect( "shake", null, 600, null )
}

function retirarPelaDireita( componente ){
	if( ! $( "#" + componente ).is(":hidden") ) $( "#" + componente ).hide( "drop", {direction: 'right'}, 1000, null )
}

function retirarPelaEsquerda( componente ){
	if( ! $( "#" + componente ).is(":hidden") ) $( "#" + componente ).hide( "drop", {direction: 'left'}, 1000, null )
}

//----------------------

function mostrarOuOcultarJanelaMensagem(){
	if( tempoJanelaMensagemTremer ) clearTimeout( tempoJanelaMensagemTremer );
	if( tempoJanelaMensagemRetirar ) clearTimeout( tempoJanelaMensagemRetirar );
	mostrarOuOcultar( 'janelaMensagem' );
}

//----------------------

function temClasse( elemento, classe ) {
	return ( ' ' + elemento.className + ' ' ).indexOf( ' ' + classe + ' ' ) > -1;
}

//----------------------

function opacidade( id, valor ) {
	document.getElementById( id ).style.opacity = valor;
}

//----------------------

function foco( elemento ){
	document.getElementById( "focoDado" ).value = elemento.id;
	if( elemento.nodeName == "TEXTAREA" ) inibirConfirmacao = true;
	else inibirConfirmacao = false;
}

function focoperdido( elemento ){
	//document.getElementById( "focoDado" ).value = "";
	inibirConfirmacao = false;
}

//----------------------

function menuMouseE( menu ) {
	opacidade( 'iconeMenu', 0.7 );
	mostrarMenu( menu );
}

function menuMouseS( menu ) {
	opacidade( 'iconeMenu', 1 );
}

function mostrarMenuSemEfeito( menu ) {
	opacidade( menu, 1 );
	document.getElementById( menu ).style.visibility = "visible";
}

function ocultarMenuSemEfeito( menu ) {
	opacidade( menu, 0 );
	document.getElementById( menu ).style.visibility = "hidden";
}

function mostrarMenu( menu ) {
	if( $( "#" + menu ).css( "visibility" ) == "hidden" ){
		ocultarMenus( menu );
		document.getElementById( menu ).style.visibility = "visible";
		for( var i = 0.1; i <= 1; i += 0.1 ) setTimeout( "opacidade( '" + menu + "', " + i + " )", i * 250 );
	}
}

function ocultarMenu( menu ) {
	if( $( "#" + menu ).css( "visibility" ) == "visible" ){
		for( var i = 0.9; i >= 0.1; i -= 0.1 ) setTimeout( "opacidade( '" + menu + "', " + i + " )", ( 1 - i ) * 250 );
		setTimeout( "ocultarMenuSemEfeito( '" + menu + "' )", 250 );
	}
}

function ocultarMenus( excecao ) {
	desativarAreaDeSaida();
	for( var i = 0; i < quantMenus; i++ ){
		var m = 'menu' + i;
		if( m != excecao && $( "#" + m ) ) ocultarMenu( m );
	}
}

function mostrarOuOcultarMenu( menu ) {
	if( $( "#" + menu ).css( "visibility" ) == "hidden" ) mostrarMenu( menu ); else ocultarMenu( menu );
}

function ativarAreaDeSaida() {
	document.getElementById( "menuAreaDeSaida" ).style.visibility = "visible";
}

function desativarAreaDeSaida() {
	document.getElementById( "menuAreaDeSaida" ).style.visibility = "hidden";
}

//----------------------

$(function() {
	$( ".dadoData" ).datepicker({
		regional: "pt-BR",
		showOn: "button",
		buttonImage: raiz + "/img/iconep/calendario.png",
		buttonImageOnly: true,
		buttonText: null,
		numberOfMonths: 2,
		showButtonPanel: true,
		changeMonth: true,
		changeYear: true
	});
	$( ".dadoData" ).mask( "99/99/9999" );
	$( ".dadoHora" ).mask( "99:99" );
	$( ".dadoDataHora" ).mask( "99/99/9999 99:99" );
});

//----------------------

String.prototype.larguraVisual = function()
{
    var lv = document.getElementById( "larguraVisual" );
    lv.innerHTML = this;
    return lv.offsetWidth;
}

//----------------------

function mostrarOuOcultarRodapeFixo() {
	var deslocamento = $(document).scrollTop();
	var visivel = $(document).height() - $(window).height();
	if( deslocamento >= visivel && deslocamento > 0 ){
		ocultar( 'cantoDireitoInferior' );
		ocultar( 'direitosAutorais' );
	}else{
		mostrar( 'cantoDireitoInferior' );
		mostrar( 'direitosAutorais' );
	}
}

//----------------------

function rolagem() {
	document.getElementById( 'rolagemHorizontal' ).value = $(document).scrollLeft();
	document.getElementById( 'rolagemVertical' ).value = $(document).scrollTop();
	mostrarOuOcultarRodapeFixo();
}

window.onscroll = rolagem;
window.onresize = rolagem;

//----------------------