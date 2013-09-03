
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

package com.joseflavio.memoria;


/**
 * Capacidade do objeto de entrar em modo de somente leitura.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 * @see AlteracaoExclusiva
 */
public interface SomenteLeitura {
	
	/**
	 * Este objeto está em modo de somente leitura?
	 * @see #setSomenteLeitura(boolean)
	 */
	public boolean isSomenteLeitura();
	
	/**
	 * A {@link Thread} corrente está em processo de somente leitura? Isto é, esta {@link Thread} não negativou {@link #setSomenteLeitura(boolean)}?
	 * @see #setSomenteLeitura(boolean)
	 */
	public boolean estouComSomenteLeitura();
	
	/**
	 * Determina que este objeto deverá entrar o mais breve possível em modo de somente leitura, isto é, alterações e remoções de dados internos estarão bloqueadas.<br>
	 * Processos que tentarem realizar alterações e remoções ficarão em estado de espera pela liberação.<br>
	 * Toda chamada positiva a este método deverá ser negativada pela {@link Thread} original.<br>
	 * Uma subsequente chamada positiva a outra chamada positiva será retornada imediatamente. Contudo, não exclui o dever da negação.
	 * @see #isSomenteLeitura()
	 * @see #estouComSomenteLeitura()
	 */
	public void setSomenteLeitura( boolean somenteLeitura );
	
}
