
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

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Objeto persistente e intercambi�vel.<br>
 * Todo atributo serializ�vel deste {@link Objeto} deve possuir um m�todo de captura (get/is) e um m�todo de atribui��o (set), segundo o padr�o JavaBeans.<br>
 * Todo {@link Objeto} deve possuir um construtor sem argumentos para fins de {@link Externalizable serializa��o}.<br>
 * O m�todo {@link Object#equals(Object)} deve comparar apenas a {@link Class} e a {@link #getIdentidade()}.<br>
 * O processo de {@link Externalizable Serializa��o} possui comportamento condicional:<br>
 * 1) Se {@link ObjectOutput} for inst�ncia de {@link ObjectOutputStream}, o {@link Objeto} dever� utilizar o m�todo {@link ObjectOutputStream#defaultWriteObject()}.<br>
 * 2) Se {@link ObjectOutput} for inst�ncia de outra classe, o {@link Objeto} dever� seguir as seguintes regras de {@link Externalizable Serializa��o}:<br>
 * - Devem ser serializados todos os atributos n�o-est�ticos e n�o-transientes acess�veis atrav�s de get/set da classe e de todas as superclasses.<br>
 * - A {@link #getIdentidade() identidade} dever� ser o primeiro atributo serializado.<br>
 * - Os demais atributos dever�o ser serializados respeitando a ordem alfab�tica de seus nomes.<br>
 * - Atributos do tipo {@link Objeto} n�o devem ser integralmente serializados, sendo serializado deles apenas o {@link Class#getName()} (ou {@link CodigoDeClasses#getCodigo(Class)}) e a {@link Objeto#getIdentidade()}.<br>
 * - Atributos do tipo {@link Byte} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeByte(int)}.<br>
 * - Atributos do tipo {@link Short} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeShort(int)}.<br>
 * - Atributos do tipo {@link Integer} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeInt(int)}.<br>
 * - Atributos do tipo {@link Long} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeLong(long)}.<br>
 * - Atributos do tipo {@link Float} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeFloat(float)}.<br>
 * - Atributos do tipo {@link Double} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeDouble(double)}.<br>
 * - Atributos do tipo {@link Boolean} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeBoolean(boolean)}.<br>
 * - Atributos do tipo {@link Character} (primitivo ou objeto) devem ser serializados com {@link ObjectOutput#writeChar(int)}.<br>
 * - Atributos do tipo {@link String} devem ser serializados com {@link ObjectOutput#writeUTF(String)}.<br>
 * - Atributos do tipo {@link Date} devem ser serializados com {@link ObjectOutput#writeLong(long)} na forma de {@link Date#getTime()}.<br>
 * - Atributos do tipo {@link Class} devem ser serializados na forma de {@link Class#getName()} ou {@link CodigoDeClasses#getCodigo(Class)}.<br>
 * - Atributos do tipo {@link Enum} devem ser serializados com {@link ObjectOutput#writeShort(int)} na forma de {@link Enum#ordinal()}.<br>
 * - Atributos de outros tipos n�o relacionados acima n�o devem ser serializados.
 * @author Jos� Fl�vio de Souza Dias J�nior
 * @version 2013
 */
public interface Objeto extends Externalizable, Referencia {

	/**
	 * Identifica��o universal do objeto em sua classe.<br>
	 * A identifica��o apenas poder� ser repetida em outra classe de objetos.
	 * @return 0 (zero) caso o objeto ainda n�o possua identidade.
	 */
	public long getIdentidade();
	
	public void setIdentidade( long identidade );
	
}
