
/*
 *  Copyright (C) 2009-2013 José Flávio de Souza Dias Júnior
 *  
 *  This file is part of José Flávio Livre - <http://www.joseflavio.com/livre/>.
 *  
 *  José Flávio Livre is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  José Flávio Livre is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with José Flávio Livre. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Direitos Autorais Reservados (C) 2009-2013 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
 */

package com.joseflavio.tqc;

/**
 * Estilo visual.<br>
 * Estilo é um conjunto de características visuais definidas externamente à {@link TomaraQueCaia} de acordo
 * com as peculiaridades da plataforma de execução.
 * @author José Flávio de Souza Dias Júnior
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
	 * @param estilo Conjunto de nomes, separados por espaços, que identificam o estilo visual. Ex.: "esquerda corFundo". 
	 */
	public Dado setEstilo( String estilo );

}