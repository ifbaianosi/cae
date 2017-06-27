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
	
	function onSalvarOcorrencia(event){
		event.preventDefault();
		console.log('Salvando ocorrencia...');
		validarFormulario();
		
		$.ajax({
			url: this.novaOcorrenciaBtn.data('url') + '/salvar',
			method: 'POST',
			data: {
				codigo: $('#codigo').val(),
				dataRegistro: $('#dataRegistro').val(),
				serie: $('#serie').val(),
				identificacao: $('#identificacao').val(),
				aluno:  $('#codigo_aluno').val(),
				dataOcorrido: $('.js-dataOcorrencia').val(),
				descricao: $('.js-descricao').val(),
				local: $('.js-local').val()
			},
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroSalvandoOcorrencia,
			success: onOcorrenciaSalva.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
	}
	
	function onOcorrenciaSalva(retorno){
		console.log('Sucesso... ocorrencia criada no servidor...', retorno);

		NGTICAE.Tabela.atualizar();
		
		NGTICAE.Notificacao.mostrar('sucesso', 'Ocorrencia salva!', 'info', $('body'));
		
		NGTICAE.Formulario.limpar();
		NGTICAE.Formulario.esconder();
		//ATUALIZAR A QUANTIDADE DE OCORRENCIAS NA TAB GERAL
		NGTICAE.AtualizarQuantidadeOcorrencias.iniciar();
		
	}
	
	function validarFormulario(){
		console.log('Validando formulario...');
	}
	
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
		this.salvarOcorrenciaBtn.text('SALVANDO, Aguarde...').attr("disabled", "true");
	}
	
	function onErroSalvandoOcorrencia(error){
		console.log('Erro ao salvar ocorrencia...', error);
		NGTICAE.Notificacao.mostrar('Formulario incompleto', 'preencha todos os campos!', 'danger', $('body'));
	}
	
	function onFinalizarRequisicao(){
		console.log('Requisição finalizada...');
		this.salvarOcorrenciaBtn.text('SALVAR').removeAttr("disabled");
	}
	
	return SalvarOcorrencia;
	
}();

NGTICAE.ExcluirOcorrencia = function(){
	
	function ExcluirOcorrencia(){
		this.excluirOcorrenciaBtn = $('.js-excluir-ocorrencias');
		this.editarOcorrenciaBtn = $('.js-editar-ocorrencia');
		this.checkbox = $('.js-checkbox');
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
		NGTICAE.Notificacao.mostrar("Sucesso", "excluido", "info");
		
		NGTICAE.Tabela.atualizar();
		
		//ATUALIZAR A QUANTIDADE DE OCORRENCIAS NA TAB GERAL
		NGTICAE.AtualizarQuantidadeOcorrencias.iniciar();
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
		$('#serie').val(ocorrencia.serie);
		$('#identificacao').val(ocorrencia.identificacao);
		
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
		NGTICAE.Notificacao.mostrar("Sucesso", "excluido", "info");
		
		NGTICAE.Tabela.atualizar();
		
		//ATUALIZAR A QUANTIDADE DE OCORRENCIAS NA TAB GERAL
		NGTICAE.AtualizarQuantidadeOcorrencias.iniciar();
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
		mostrar: function mostrarNotificacao(title, message, type, elementoPai){
			$.notify({
				title: '<strong>' + title + '</strong>',
				message: message,
				target: '_top'
			},{
				element: elementoPai,
				type: type,
				placement: {
					from: "top",
					align: "center"
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
			var resposta = $.ajax({
				url: $('.js-nova-ocorrencia').data('url-alunos') + '/por-matricula',
				method: 'GET',
				contentType: 'application/json',
				data: {
					matricula: $('.js-matricula-aluno').text(),
				}
			});
			
			resposta.done(function(aluno){
				$('.js-container-formulario-ocorrencias').show('slow');
				if(!aluno.cadastroCompleto){
					$('.js-mensagem-cadastro-incompleto').removeClass('hide');
					$('.js-link-completar-cadastro').attr('href', aluno.codigo);
					$('.js-restante-formulario').addClass('hide');
				}else{
					
				}
			});
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

/* Tabela ================================================================================================
*  
*/
NGTICAE.Tabela = {
		atualizar: function(){
			console.log('Atualizando tabela...');
			$.ajax({
				url: $('.js-nova-ocorrencia').data('url') + '/aluno/' + $('#codigo_aluno').val(),
				method: 'GET',
				success: this.atualizarTabela
			});
		},
		atualizarTabela: function(ocorrencias){
			var containerOcorrencias = $('.js-container-ocorrencias');
			var htmlTabelaOcorrencias = $('#tabelaOcorrenciasPorAluno').html();
			var template = Handlebars.compile(htmlTabelaOcorrencias);
			
			var novatabela = template(ocorrencias);
			containerOcorrencias.html(novatabela);
			console.log('Tabela atualizada...');
			
			NGTICAE.MultiSelecao.iniciar();
			NGTICAE.MultiSelecao.toggleBtn();
			NGTICAE.Modal.iniciar();
		}
}
//==========================================================================================================================

$(function(){
	
	NGTICAE.NovaOcorrencia.click();
	
	NGTICAE.CancelarOcorrencia.click();
	
	NGTICAE.Tabela.atualizar();
	
	var salvarOcorrencia = new NGTICAE.SalvarOcorrencia();
	salvarOcorrencia.iniciar();
	
	
	var excluirOcorrencia = new NGTICAE.ExcluirOcorrencia();
	excluirOcorrencia.iniciar();
	
	var editarOcorrencia = new NGTICAE.EditarOcorrencia();
	editarOcorrencia.iniciar();

});