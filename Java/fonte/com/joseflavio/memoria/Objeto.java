
/*
 *  Copyright (C) 2009-2015 José Flávio de Souza Dias Júnior
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
 *  Direitos Autorais Reservados (C) 2009-2015 José Flávio de Souza Dias Júnior
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

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * Objeto persistente e intercambiável.<br>
 * Todo atributo serializável deste {@link Objeto} deve possuir um método de captura (get/is) e um método de atribuição (set), segundo o padrão JavaBeans.<br>
 * Todo {@link Objeto} deve possuir um construtor sem argumentos para fins de {@link Externalizable serialização}.<br>
 * O método {@link Object#equals(Object)} deve comparar apenas a {@link Class} e a {@link #getIdentidade()}.<br>
 * O processo de {@link Externalizable Serialização} possui comportamento condicional:<br>
 * 1) Se {@link ObjectOutput} for instância de {@link ObjectOutputStream}, o {@link Objeto} deverá utilizar o método {@link ObjectOutputStream#defaultWriteObject()}.<br>
 * 2) Se {@link ObjectOutput} for instância de outra classe, o {@link Objeto} deverá seguir as seguintes regras de {@link Externalizable Serialização}:<br>
 * - Devem ser serializados todos os atributos não-estáticos e não-transientes acessíveis através de get/set da classe e de todas as superclasses.<br>
 * - A {@link #getIdentidade() identidade} deverá ser o primeiro atributo serializado.<br>
 * - Os demais atributos deverão ser serializados respeitando a ordem alfabética de seus nomes.<br>
 * - Atributos do tipo {@link Objeto} não devem ser integralmente serializados, sendo serializado deles apenas o {@link Class#getName()} (ou {@link CodigoDeClasses#getCodigo(Class)}) e a {@link Objeto#getIdentidade()}.<br>
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
 * - Atributos de outros tipos não relacionados acima não devem ser serializados.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public interface Objeto extends Externalizable, Referencia {

	/**
	 * Identificação universal do objeto em sua classe.<br>
	 * A identificação apenas poderá ser repetida em outra classe de objetos.
	 * @return 0 (zero) caso o objeto ainda não possua identidade.
	 */
	public long getIdentidade();
	
	public void setIdentidade( long identidade );
	
}
