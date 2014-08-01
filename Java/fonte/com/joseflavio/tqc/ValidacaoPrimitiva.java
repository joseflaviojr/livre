
/*
 *  Copyright (C) 2009-2014 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2014 José Flávio de Souza Dias Júnior
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

import com.joseflavio.tqc.dado.Inteiro;
import com.joseflavio.validacao.Validacao;
import com.joseflavio.validacao.ValidacaoException;

/**
 * Recurso que orienta a {@link Validacao} de um {@link Dado} de tal modo que ela considere, sob o aspecto primitivo, a intenção de conteúdo.<br>
 * A {@link ValidacaoPrimitiva} se refere a um conteúdo ainda não definido para o {@link Dado}, o que a difere das demais {@link Validacao}'s,
 * as quais atuam sobre o atual conteúdo do {@link Dado}.<br>
 * Exemplo: A {@link Validacao} será falsa caso se tente definir como conteúdo o valor "3,14" para um {@link Inteiro}.<br>
 * Observação: o conteúdo <code>null</code> é perfeitamente aceitável.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface ValidacaoPrimitiva {
	
	/**
	 * Determina o conteúdo que ficou impossibilitado de ser assumido como conteúdo de fato pelo {@link Dado}, por ter infringido as
	 * primitivas do {@link Dado}.
	 * @param conteudo <code>null</code> == conteúdo válido e assumido pelo {@link Dado}.
	 */
	public Dado setConteudoInvalido( Object conteudo );

	/**
	 * @return <code>null</code> caso a {@link ValidacaoPrimitiva} tenha tido sucesso, isto é, o {@link Dado} assumiu o conteúdo pretendido.
	 */
	public Object getConteudoInvalido();
	
	public Dado setMensagemValidacaoPrimitiva( String mensagem );
	
	/**
	 * Retorna a mensagem a ser utilizada pela {@link ValidacaoException}.
	 * @return <code>null</code> caso a mensagem não tenha sido definida.
	 */
	public String getMensagemValidacaoPrimitiva();
	
}
