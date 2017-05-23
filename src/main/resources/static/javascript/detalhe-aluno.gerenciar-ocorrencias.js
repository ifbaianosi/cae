var NGTICAE = NGTICAE || {};

NGTICAE.GerenciarOcorrencias = function(){
	
	function GerenciarOcorrencias(){
		this.novaOcorrenciaBtn = $('.js-nova-ocorrencia');
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.containerFormularioOcorrencias = $('.js-container-formulario-ocorrencias');
		this.cancelarOcorrenciaBtn = $('.js-cancelar-ocorrencias');
		this.editarOcorrenciaBtn = $('.js-editar-ocorrencia');
		this.excluirOcorrenciaBtn = $('.js-excluir-ocorrencias');
		this.checkbox = $('.js-checkbox');
	}
	
	GerenciarOcorrencias.prototype.iniciar = function (){
		this.novaOcorrenciaBtn.on('click', onNovaOcorrenciaBtnClicado.bind(this));
		this.cancelarOcorrenciaBtn.on('click', onCancelarOcorrenciaBtnClicado.bind(this));
		this.checkbox.on('change', onVerificaCheckboxChecado.bind(this));
		this.editarOcorrenciaBtn.on('click', onEditarOcorrencia.bind(this));
		this.excluirOcorrenciaBtn.on('click', onExcluirOcorrencia.bind(this));
	}
	
	function onNovaOcorrenciaBtnClicado(){
		console.log('Nova ocorrencia...');
		
		//desabilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.attr('disabled', 'disabled');
		this.editarOcorrenciaBtn.attr('disabled', 'disabled');
		this.excluirOcorrenciaBtn.attr('disabled', 'disabled');
		
		//tornar visivel o botão cancelar
		this.cancelarOcorrenciaBtn.removeClass('hide');
		
		//esconder container de ocorrencias
		this.containerOcorrencias.hide('slow');
		
		//monstrar container do formulario de ocorrencias
		this.containerFormularioOcorrencias.show('slow', onBuscarFormulario.bind(this));
	}
	
	function onBuscarFormulario() { 
		console.log('codigo aluno: ', $('#codigo_aluno').val());
		var resposta = $.ajax({
			url: this.novaOcorrenciaBtn.data('url') + '/formulario/' + $('#codigo_aluno').val(),
			method: 'GET'
		});
		
		resposta.done(adicionaFormulario.bind(this));
	}
	
	function adicionaFormulario(formulario){
			this.containerFormularioOcorrencias.html(formulario);
			$('.js-formulario').on('submit', function(event){ event.preventDefault() });
			$('.js-submit').on('click', onSalvarOcorrenciaBtnClicado.bind(this));
	} 
	
	function onSalvarOcorrenciaBtnClicado(){
		console.log('salvar ocorrencia via ajax ...');
				
		$.ajax({
			url: this.novaOcorrenciaBtn.data('url') + '/salvar',
			method: 'POST',
			data: {
				aluno:  $('#codigo_aluno').val(),
				dataOcorrido: $('.js-dataOcorrencia').val(),
				descricao: $('.js-descricao').val(),
				local: $('.js-local').val()
			},
			/*beforeSend: onIniciarRequisicao.bind(this),*/
			error: onErroSalvandoOcorrencia,
			success: onAtualizarTabela.bind(this)/*,
			complete: onFinalizarRequisicao.bind(this)*/
		});
	}
	
	function onAtualizarTabela(retorno){
		console.log('foi no servidor...', retorno);
		this.containerFormularioOcorrencias.html(retorno);
		
		atualizarTabelaOcorrencias.call(this);
		atualizarQuantidadeOcorrencias();
		
		/*onCancelarOcorrenciaBtnClicado.call(this);*/
	}
	
	function onCancelarOcorrenciaBtnClicado(){
		console.log('Cancelar nova ocorrencia...');
		
		//habilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.removeAttr("disabled");
		this.editarOcorrenciaBtn.removeAttr("disabled");
		this.excluirOcorrenciaBtn.removeAttr("disabled");
		
		//tornar invisivel o botão cancelar
		this.cancelarOcorrenciaBtn.addClass('hide');
		
		//mostrar container de ocorrencias
		this.containerOcorrencias.show('slow');
		
		//esconder container do formulario de ocorrencias
		this.containerFormularioOcorrencias.html('');
		this.containerFormularioOcorrencias.hide('slow');
	}
	
	function atualizarTabelaOcorrencias(){
		console.log('atualizar tabela de ocorrencias na view...');
		this.containerOcorrencias.html('<span>atualizando ocorrencias...</span>');
		var resposta = $.ajax({
			url: this.novaOcorrenciaBtn.data('url') + '/aluno/' + $('#codigo_aluno').val(),
			method: 'GET',
			contentType: 'application/json'
		});
		
		resposta.done(function(tabelaOcorrencias){
			$('.js-container-ocorrencias').html(tabelaOcorrencias)
		});
	}
	
	function atualizarQuantidadeOcorrencias(){
		console.log('atualizar quantidade de ocorrencias...');
		var quantidade = $('.js-quantidade-ocorrencias').text();
		console.log('quantidade atual: ', quantidade);
		quantidade++;
		console.log('quantidade atualizada: ', quantidade);
		$('.js-quantidade-ocorrencias').text(quantidade);
	}
	
	function onErroSalvandoOcorrencia(erro){
		console.log('erro: ', erro)
	}
	
	//ACÕES DOS CHECKBOX
	function onVerificaCheckboxChecado(evento){
		console.log('Checkbox selecionado...');
		
		var checkboxSelecionados = this.checkbox.filter(':checked');
		console.log(checkboxSelecionados);
		
		if(checkboxSelecionados.length == 1){
			this.editarOcorrenciaBtn.removeAttr('disabled');
		}else{
			this.editarOcorrenciaBtn.attr('disabled', 'disabled');
		}
		
		if(checkboxSelecionados.length > 0){
			this.excluirOcorrenciaBtn.removeAttr('disabled');
		}else{
			this.excluirOcorrenciaBtn.attr('disabled', 'disabled');
		}
	}
	
	function onEditarOcorrencia(){
		console.log('Editar ocorrencia selecionada...')
	}
	
	function onExcluirOcorrencia(){
		console.log('Excluir ocorrencia(s) selecionada(s)...')
	}
	
	return GerenciarOcorrencias;
	
}();

$(function(){
	var gerenciarOcorrencias = new NGTICAE.GerenciarOcorrencias();
	gerenciarOcorrencias.iniciar();
});