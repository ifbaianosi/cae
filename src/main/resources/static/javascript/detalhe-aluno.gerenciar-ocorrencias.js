var NGTICAE = NGTICAE || {};

/*NGTICAE.ListarOcorrencias = function(){
	
	function ListarOcorrencias(){
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.tabs = $('a[data-toggle="tab"]');
	}
	
	ListarOcorrencias.prototype.iniciar = function (){
		this.tabs.on('shown.bs.tab', onAtualizarTabela.bind(this));
	}
	
	function onAtualizarTabela(event) {
		//pega a tab active
		var tab = $(event.target).attr('href').replace('#', '');
		console.log('activated tab:', tab);
		if(){
			
		}
	}
	
	return ListarOcorrencias;
	
}();*/

NGTICAE.NovaOcorrencia = function(){
	
	function NovaOcorrencia(){
		this.novaOcorrenciaBtn = $('.js-nova-ocorrencia');
		this.cancelarOcorrenciaBtn = $('.js-cancelar-ocorrencias');
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.containerFormularioOcorrencias = $('.js-container-formulario-ocorrencias');
	}
	
	NovaOcorrencia.prototype.iniciar = function (){
		this.novaOcorrenciaBtn.on('click', onNovaOcorrenciaBtnClicado.bind(this));
		this.cancelarOcorrenciaBtn.on('click', onCancelarOcorrenciaBtnClicado.bind(this));
	}
	
	function onNovaOcorrenciaBtnClicado(){
		console.log('Nova ocorrencia...');
		
		//desabilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.attr('disabled', 'disabled');
		
		//tornar visivel o botão cancelar
		this.cancelarOcorrenciaBtn.removeClass('hide');
		
		//esconder container de ocorrencias
		this.containerOcorrencias.hide('slow');
		
		//monstrar container do formulario de ocorrencias
		this.containerFormularioOcorrencias.show('slow');
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
	} 
	
	function onCancelarOcorrenciaBtnClicado(){
		console.log('Cancelar nova ocorrencia...');
		
		//habilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.removeAttr("disabled");
		
		//tornar invisivel o botão cancelar
		this.cancelarOcorrenciaBtn.addClass('hide');
		
		//mostrar container de ocorrencias
		this.containerOcorrencias.show('slow');
		
		//esconder container do formulario de ocorrencias
		this.containerFormularioOcorrencias.hide('slow');
	}
	
	return NovaOcorrencia;
	
}();

NGTICAE.SalvarOcorrencia = function(){
	
	function SalvarOcorrencia(){
		this.novaOcorrenciaBtn = $('.js-nova-ocorrencia');
		this.salvarOcorrenciaBtn = $('.js-submit');
		this.cancelarOcorrenciaBtn = $('.js-cancelar-ocorrencias');
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.containerFormularioOcorrencias = $('.js-container-formulario-ocorrencias');
		this.htmlTabelaOcorrencias = $('#tabelaOcorrenciasPorAluno').html();
		this.template = Handlebars.compile(this.htmlTabelaOcorrencias);
	}
	
	SalvarOcorrencia.prototype.iniciar = function (){
		this.salvarOcorrenciaBtn.on('click', onSalvarOcorrencia.bind(this));
	}
	
	function onSalvarOcorrencia(){
		console.log('Salvando ocorrencia...');
		validarFormulario();
		
		$.ajax({
			url: this.novaOcorrenciaBtn.data('url') + '/salvar',
			method: 'POST',
			data: {
				aluno:  $('#codigo_aluno').val(),
				dataOcorrido: $('.js-dataOcorrencia').val(),
				descricao: $('.js-descricao').val(),
				local: $('.js-local').val()
			},
			beforeSend: onIniciarRequisicao,
			error: onErroSalvandoOcorrencia,
			success: onOcorrenciaSalva.bind(this),
			complete: onFinalizarRequisicao
		});
	}
	
	function onOcorrenciaSalva(retorno){
		console.log('Sucesso... ocorrencia criada no servidor...', retorno);
		limparFormulario();
		mostrarNotificacao('sucesso', 'Ocorrencia salva!', 'success');
		
		//habilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.removeAttr("disabled");
		
		//tornar invisivel o botão cancelar
		this.cancelarOcorrenciaBtn.addClass('hide');
		
		//esconder container do formulario de ocorrencias
		this.containerFormularioOcorrencias.hide('slow');
		
		//mostrar container de ocorrencias
		this.containerOcorrencias.show('slow');
		
		$.ajax({
			url: $('.js-nova-ocorrencia').data('url') + '/' + $('#codigo_aluno').val(),
			method: 'GET',
			success: atualizarTabela.bind(this)
		});
		
		//ATUALIZAR A QUANTIDADE DE OCORRENCIAS NA TAB GERAL
		NGTICAE.AtualizarQuantidadeOcorrencias.iniciar();
		
	}
	
	function atualizarTabela(ocorrencias){
		var novatabela = this.template(ocorrencias);
		this.containerOcorrencias.html(novatabela);
		
		//instanciar objeto de outro arquivo [multiselecao.js]
		var multiSelecao = new NGTICAE.MultiSelecao();
		multiSelecao.iniciar();
	}
	
	function validarFormulario(){
		console.log('Validando formulario...');
	}
	
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
	}
	
	function onErroSalvandoOcorrencia(error){
		console.log('Erro ao salvar ocorrencia...', error);
		mostrarNotificacao('Formulario incompleto', 'preencha todos os campos!', 'danger');
	}
	
	function onFinalizarRequisicao(){
		console.log('Requisição finalizada...');
	}
	
	function mostrarNotificacao(title, message, type){
		$.notify({
			title: '<strong>' + title + '</strong>',
			message: message
		},{
			type: type,
			placement: {
				from: "top",
				align: "center"
			},
			offset: {
				x: 50,
				y: 74
			}
		});
	}
	
	function limparFormulario(){
		$('.js-dataOcorrencia').val('');
		$('.js-descricao').val('');
		$('.js-local').val('');
	}
			
	return SalvarOcorrencia;
	
}();

NGTICAE.ExcluirOcorrencia = function(){
	
	function ExcluirOcorrencia(){
		this.excluirOcorrenciaBtn = $('.js-excluir-ocorrencias');
		this.editarOcorrenciaBtn = $('.js-editar-ocorrencia');
		this.checkbox = $('.js-checkbox');
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.htmlTabelaOcorrencias = $('#tabelaOcorrenciasPorAluno').html();
		this.template = Handlebars.compile(this.htmlTabelaOcorrencias);
	}
	
	ExcluirOcorrencia.prototype.iniciar = function (){
		this.excluirOcorrenciaBtn.on('click', onExcluirOcorrenciaBtnClicado.bind(this));
	}
	
	function onExcluirOcorrenciaBtnClicado(){
		// confirm dialog
		alertify.confirm("Deseja realmente excluir os objetos selecionados", excluir.bind(this), function() {
		    // user clicked "cancel"
			console.log('Não, Cancelar exclusão!');
		});
	}
	
	function excluir() {
		console.log('Sim, Excluir!');
		
		var checkboxMarcados = $('.js-checkbox').filter(':checked');
		console.log('checkboxMarcados', checkboxMarcados);
		
		var codigs = $.map(checkboxMarcados, function(c){
			return $(c).data('codigo');
		})
		
		console.log('codigos', codigs);
		
		$.ajax({
			url: $('.js-excluir-ocorrencias').data('url') + '/' + codigs,
			method: 'DELETE',
			success: onExcluidoComSucesso.bind(this),
			error: onErroAoExcluir,
			complete: finalizarRequisicao.bind(this)
		});
	}
	
	function onExcluidoComSucesso(){
		console.log('Excluído com sucesso!');
		mostrarNotificacao("Sucesso", "excluido", "success");
		
		$.ajax({
			url: $('.js-nova-ocorrencia').data('url') + '/' + $('#codigo_aluno').val(),
			method: 'GET',
			success: atualizarTabela.bind(this)
		});
		
		//ATUALIZAR A QUANTIDADE DE OCORRENCIAS NA TAB GERAL
		NGTICAE.AtualizarQuantidadeOcorrencias.iniciar();
	}
	
	function atualizarTabela(ocorrencias){
		var novatabela = this.template(ocorrencias);
		this.containerOcorrencias.html(novatabela);
		
		//instanciar objeto de outro arquivo [multiselecao.js]
		var multiSelecao = new NGTICAE.MultiSelecao();
		multiSelecao.iniciar();
	}
	
	function finalizarRequisicao(){
		this.excluirOcorrenciaBtn.attr('disabled', 'disabled');
		this.editarOcorrenciaBtn.attr('disabled', 'disabled')
	}
	
	function onErroAoExcluir(erro){
		console.log('Erro ao tentar excluir!');
		mostrarNotificacao("Ops!", erro, "danger");
	}
	
	function mostrarNotificacao(title, message, type){
		$.notify({
			title: '<strong>' + title + '</strong>',
			message: message
		},{
			type: type,
			placement: {
				from: "top",
				align: "center"
			},
			offset: {
				x: 50,
				y: 74
			}
		});
	}
			
	return ExcluirOcorrencia;
	
}();

/* Atualizar quantidade ocorrencias ================================================================================================
*  
*/
NGTICAE.AtualizarQuantidadeOcorrencias = {
    iniciar: function () {
    	var inputQuantidade = $('.js-quantidade-ocorrencias');
    	
    	$.ajax({
    		url: $('.js-nova-ocorrencia').data('url') + '/quantidade/' + $('#codigo_aluno').val(),
    		method: 'GET',
    		success: function(quantidade){ inputQuantidade.text(quantidade) }
    	});
    }
}
//==========================================================================================================================

$(function(){
	var novaOcorrencia = new NGTICAE.NovaOcorrencia();
	novaOcorrencia.iniciar();
	
	var salvarOcorrencia = new NGTICAE.SalvarOcorrencia();
	salvarOcorrencia.iniciar();
	
	var excluirOcorrencia = new NGTICAE.ExcluirOcorrencia();
	excluirOcorrencia.iniciar();

	$('.datetimepicker').bootstrapMaterialDatePicker({
        format: 'DD/MM/YYYY HH:mm',
        lang: 'pt',
        clearButton: true,
        weekStart: 0,
        cancelText: 'CANCELAR',
        clearText: 'LIMPAR' 
    });
});