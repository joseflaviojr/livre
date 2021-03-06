
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
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

package com.joseflavio.memoria;


/**
 * Capacidade do objeto de entrar em modo de altera��o exclusiva.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 * @see SomenteLeitura
 */
public interface AlteracaoExclusiva {

	/**
	 * Alguma {@link Thread} est� em processo de altera��o exclusiva deste objeto?
	 * @see #setAlteracaoExclusiva(boolean)
	 */
	public boolean isAlteracaoExclusiva();
	
	/**
	 * A {@link Thread} corrente est� em processo de altera��o exclusiva deste objeto?
	 * @see #setAlteracaoExclusiva(boolean)
	 */
	public boolean estouComAlteracaoExclusiva();
	
	/**
	 * Determina que este objeto dever� entrar o mais breve poss�vel em modo de altera��o exclusiva, isto �, altera��es e remo��es de dados internos poder�o ser realizadas apenas pela {@link Thread} corrente.<br>
	 * Processos que tentarem realizar altera��es e remo��es ficar�o em estado de espera pela libera��o.<br>
	 * Toda chamada positiva a este m�todo dever� ser negativada pela {@link Thread} original.<br>
	 * Uma subsequente chamada positiva a outra chamada positiva entrar� em modo de espera pela nega��o da primeira chamada.
	 * @see #isAlteracaoExclusiva()
	 * @see #estouComAlteracaoExclusiva()
	 */
	public void setAlteracaoExclusiva( boolean alteracaoExclusiva );
	
}
