package com.joseflavio.alomundo.aplicacao;

import com.joseflavio.tqc.OpcaoDeMenu;
import com.joseflavio.tqc.TomaraQueCaiaException;
import com.joseflavio.tqc.aplicacao.AplicacaoTQC;
import com.joseflavio.tqc.aplicacao.Formulario;

/**
 * {@link Formulario} base da {@link AplicacaoTQC}.
 * @author José Flávio de Souza Dias Júnior
 * @version 2013
 */
public abstract class FormularioBase extends Formulario<AloMundo> {
	
	protected FormularioBase( AloMundo aplicacao, String titulo, String subtitulo ) throws TomaraQueCaiaException {
		super( aplicacao, titulo, subtitulo );
	}
	
	protected FormularioBase( AloMundo aplicacao, String titulo ) throws TomaraQueCaiaException {
		this( aplicacao, titulo, titulo );
	}
	
	@Override
	protected void menu() throws TomaraQueCaiaException {
		for( OpcaoDeMenu om : aplicacao.getMenuPadrao() ) maisOpcaoDeMenu( om );
	}
	
}
