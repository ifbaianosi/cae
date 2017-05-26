var NGTICAE = NGTICAE || {};

NGTICAE.NovaOcorrencia = {
		click: function(){
			$('.js-nova-ocorrencia').on('click', NGTICAE.Formulario.mostrar);
		}
}

NGTICAE.CancelarOcorrencia = {
		click: function(){
			$('.js-cancelar-ocorrencias').on('click', NGTICAE.Formulario.esconder);
		}
}


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
				codigo: $('#codigo').val(),
				dataRegistro: $('#dataRegistro').val(),
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
		
		$.ajax({
			url: $('.js-nova-ocorrencia').data('url') + '/' + $('#codigo_aluno').val(),
			method: 'GET',
			success: atualizarTabela.bind(this)
		});
		
		NGTICAE.Notificacao.mostrar('sucesso', 'Ocorrencia salva!', 'success');
		
		NGTICAE.Formulario.limpar();
		NGTICAE.Formulario.esconder();
		//ATUALIZAR A QUANTIDADE DE OCORRENCIAS NA TAB GERAL
		NGTICAE.AtualizarQuantidadeOcorrencias.iniciar();
		
	}
	
	function atualizarTabela(ocorrencias){
		var novatabela = this.template(ocorrencias);
		this.containerOcorrencias.html(novatabela);
		
		NGTICAE.MultiSelecao.iniciar();
		NGTICAE.MultiSelecao.toggleBtn();
	}
	
	function validarFormulario(){
		console.log('Validando formulario...');
	}
	
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
	}
	
	function onErroSalvandoOcorrencia(error){
		console.log('Erro ao salvar ocorrencia...', error);
		NGTICAE.Notificacao.mostrar('Formulario incompleto', 'preencha todos os campos!', 'danger');
	}
	
	function onFinalizarRequisicao(){
		console.log('Requisição finalizada...');
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
		NGTICAE.Notificacao.mostrar("Sucesso", "excluido", "success");
		
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
		
		NGTICAE.MultiSelecao.iniciar();
		NGTICAE.MultiSelecao.toggleBtn();
	}
	
	function finalizarRequisicao(){
		this.excluirOcorrenciaBtn.attr('disabled', 'disabled');
		this.editarOcorrenciaBtn.attr('disabled', 'disabled')
	}
	
	function onErroAoExcluir(erro){
		console.log('Erro ao tentar excluir!');
		NGTICAE.Notificacao.mostrar("Ops!", erro, "danger");
	}
	
	return ExcluirOcorrencia;
	
}();

NGTICAE.EditarOcorrencia = function(){
	
	function EditarOcorrencia(){
		this.excluirOcorrenciaBtn = $('.js-excluir-ocorrencias');
		this.editarOcorrenciaBtn = $('.js-editar-ocorrencia');
		this.checkbox = $('.js-checkbox');
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.htmlTabelaOcorrencias = $('#tabelaOcorrenciasPorAluno').html();
		this.template = Handlebars.compile(this.htmlTabelaOcorrencias);
	}
	
	EditarOcorrencia.prototype.iniciar = function (){
		this.editarOcorrenciaBtn.on('click', onEditarOcorrenciaBtnClicado.bind(this));
	}
	
	function onEditarOcorrenciaBtnClicado(){
		console.log('Chamando o editar...');
		var checkboxMarcados = new Array($('.js-checkbox').filter(':checked'));
		console.log('checkboxMarcados', checkboxMarcados);
		if(checkboxMarcados.length == 1){
			var codigoocorrencia = checkboxMarcados[0].data('codigo')
			console.log('capturou o codigo da ocorrencia: '+codigoocorrencia);
			
			//recuperar o model ocorrencia via AJAX
			$.ajax({
				url: $('.js-nova-ocorrencia').data('url') + '/ver/' + codigoocorrencia,
				method: 'GET',
				success: onObjetoRetornado.bind(this),
				error: function(){ console.log('Ops! Algo de muito estranho aconteceu e não foi possivel recuperar o recurso solicitado.') }
			});
		}
	}
	
	function onObjetoRetornado(ocorrencia){
		console.log('foi no servidor: ', ocorrencia);
		NGTICAE.Formulario.mostrar();
		
		//Adiciona o codigo da ocorrencia no campo hidden para o spring fazer a verificação se será criado um novo registro ou atualizar um ja existente
		$('#codigo').val(ocorrencia.codigo);
		$('#dataRegistro').val(ocorrencia.dataRegistro);
		
		//preenchar o formulario com os dados da ocorrencia para edição
		$('.js-dataOcorrencia').val(ocorrencia.dataOcorrido);
		$('.js-local').val(ocorrencia.local);
		$('.js-descricao').val(ocorrencia.descricao);
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
		NGTICAE.Notificacao.mostrar("Sucesso", "excluido", "success");
		
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
		NGTICAE.Notificacao.mostrar("Ops!", erro, "danger");
	}
				
	return EditarOcorrencia;
	
}();

/* Notificações ================================================================================================
*  
*/
NGTICAE.Notificacao = {
		mostrar: function mostrarNotificacao(title, message, type){
			$.notify({
				title: '<strong>' + title + '</strong>',
				message: message
			},{
				type: type,
				placement: {
					from: "top",
					align: "right"
				},
				offset: {
					x: 50,
					y: 76
				}
			});
		}
}
//==========================================================================================================================

/* Formulario ================================================================================================
*  
*/
NGTICAE.Formulario = {
		limpar: function(){
			$('#codigo').val('');
			$('#dataRegistro').val('');
			$('.js-dataOcorrencia').val('');
			$('.js-descricao').val('');
			$('.js-local').val('');
		},
		
		mostrar: function(){
			
			console.log('Mostrar formulario...');
			
			//desabilitar os botoes novo, editar e excluir
			$('.js-nova-ocorrencia').attr('disabled', 'disabled');
			$('.js-editar-ocorrencia').attr('disabled', 'disabled');
			$('.js-excluir-ocorrencias').attr('disabled', 'disabled');
			
			//NGTICAE.MultiSelecao.toggleBtn();
			
			//tornar visivel o botão cancelar
			$('.js-cancelar-ocorrencias').removeClass('hide');
			
			//esconder container de ocorrencias
			$('.js-container-ocorrencias').hide('slow');
			
			//monstrar container do formulario de ocorrencias
			$('.js-container-formulario-ocorrencias').show('slow');
		},
		
		esconder: function(){
			console.log('Enconder formulario...');
			
			NGTICAE.Formulario.limpar();
			
			//
			NGTICAE.MultiSelecao.toggleBtn();
			
			//habilitar os botoes novo, editar e excluir
			$('.js-nova-ocorrencia').removeAttr("disabled");
			
			//tornar invisivel o botão cancelar
			$('.js-cancelar-ocorrencias').addClass('hide');
			
			//mostrar container de ocorrencias
			$('.js-container-ocorrencias').show('slow');
			
			//esconder container do formulario de ocorrencias
			$('.js-container-formulario-ocorrencias').hide('slow');
		}
}
//==========================================================================================================================


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
	
	NGTICAE.NovaOcorrencia.click();
	
	NGTICAE.CancelarOcorrencia.click();
	
	var salvarOcorrencia = new NGTICAE.SalvarOcorrencia();
	salvarOcorrencia.iniciar();
	
	var excluirOcorrencia = new NGTICAE.ExcluirOcorrencia();
	excluirOcorrencia.iniciar();
	
	var editarOcorrencia = new NGTICAE.EditarOcorrencia();
	editarOcorrencia.iniciar();

	$('.datetimepicker').bootstrapMaterialDatePicker({
        format: 'DD/MM/YYYY HH:mm',
        lang: 'pt',
        clearButton: true,
        weekStart: 0,
        cancelText: 'CANCELAR',
        clearText: 'LIMPAR' 
    });
});