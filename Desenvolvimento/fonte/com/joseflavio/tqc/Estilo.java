
/*
 *  Copyright (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
 *  
 *  This file is part of Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 *  
 *  Jos� Fl�vio Livre is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Jos� Fl�vio Livre is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Jos� Fl�vio Livre. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Direitos Autorais Reservados (C) 2009-2013 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc;

/**
 * Estilo visual.<br>
 * Estilo � um conjunto de caracter�sticas visuais definidas externamente � {@link TomaraQueCaia} de acordo
 * com as peculiaridades da plataforma de execu��o.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface Estilo {
	
	public static final String barraControle = "barraControle";
	public static final String fundoGeral = "fundoGeral";
	public static final String fundoAplicacao = "fundoAplicacao";
	public static final String botaoViagem = "botaoViagem";
	public static final String botaoViagemAtiva = "botaoViagemAtiva";
	public static final String avisoTQC = "avisoTQC";
	public static final String esperaFundo = "esperaFundo";
	public static final String esperaConteudo = "esperaConteudo";
	public static final String dadoComando = "dadoComando";
	public static final String dadoTexto = "dadoTexto";
	public static final String dadoTextoEditavel = "dadoTextoEditavel";
	public static final String dadoSelecao = "dadoSelecao";
	public static final String dadoSelecaoEditavel = "dadoSelecaoEditavel";
	public static final String dadoTabela = "dadoTabela";
	public static final String formSubtitulo = "formSubtitulo";
	public static final String formRotulo = "formRotulo";
	public static final String formColunaRotulo = "formColunaRotulo";
	public static final String formColunaValor = "formColunaValor";
	public static final String formMensagemErro = "formMensagemErro";
	public static final String formMensagemInformacao = "formMensagemInformacao";
	public static final String listaTitulo = "listaTitulo";
	public static final String listaColunaTitulo = "listaColunaTitulo";
	public static final String listaColunaValor = "listaColunaValor";
	public static final String listaLinhaImpar = "listaLinhaImpar";
	public static final String listaLinhaPar = "listaLinhaPar";
	public static final String relatorioTitulo = "relatorioTitulo";
	public static final String relatorioSessao = "relatorioSessao";
	public static final String relatorioNormal = "relatorioNormal";
	public static final String relatorioNegrito = "relatorioNegrito";
	public static final String relatorioColunaRotulo = "relatorioColunaRotulo";
	public static final String relatorioColunaValor = "relatorioColunaValor";
	public static final String questMensagem = "questMensagem";
	public static final String questComandoResposta = "questComandoResposta";
	public static final String italico = "italico";
	public static final String esquerda = "esquerda";
	public static final String direita = "direita";
	public static final String emcima = "emcima";
	public static final String embaixo = "embaixo";
	public static final String centroH = "centroH";
	public static final String centroV = "centroV";
	public static final String largura3Porc = "largura3Porc";
	public static final String largura5Porc = "largura5Porc";
	public static final String largura10Porc = "largura10Porc";
	public static final String largura15Porc = "largura15Porc";
	public static final String largura20Porc = "largura20Porc";
	public static final String largura25Porc = "largura25Porc";
	public static final String largura30Porc = "largura30Porc";
	public static final String largura50Porc = "largura50Porc";
	public static final String largura70Porc = "largura70Porc";
	public static final String largura100Porc = "largura100Porc";
	
	public String getEstilo();
	
	/**
	 * @param estilo Conjunto de nomes, separados por espa�os, que identificam o estilo visual. Ex.: "esquerda corFundo". 
	 */
	public Dado setEstilo( String estilo );

}