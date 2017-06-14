var NGTICAE = NGTICAE || {};

//==================================================================================================================
//		MODAL
//==================================================================================================================
NGTICAE.Modal = {
		ocorrencia,
		iniciar: function(){
			$('.js-encaminhar-ocorrencia').on('click', this.abrir);
			$('#modalEncaminhamento').on('shown.bs.modal', this.carregar);
			$('#modalEncaminhamento').on('hide.bs.modal', this.finalizar);
		},
		
		abrir: function(event){
			var codigoOcorrencia = $(event.currentTarget).data('codigo');
			console.log('codigoOcorrenciaClicada:', codigoOcorrencia);
			NGTICAE.Modal.ocorrencia = codigoOcorrencia;
			$('#modalEncaminhamento').modal('show');
		},
		
		carregar: function(){
			console.log('carregar ocorrencia...');
			
			$('#preloader').removeClass('hide');
			
			$('#ocorrencia').val(NGTICAE.Modal.ocorrencia);
			
			//recuperar o model ocorrencia via AJAX
			$.ajax({
				url: $('.js-nova-ocorrencia').data('url') + '/ver/' + NGTICAE.Modal.ocorrencia,
				method: 'GET',
				success: function(ocorrencia){
					console.log('sucesso!', ocorrencia);
					
					$('.js-data-ocorrencia').text(ocorrencia.dataOcorrido);
					$('.js-local-ocorrencia').text(ocorrencia.local);
					$('.js-descricao-ocorrencia').text(ocorrencia.descricao);
					$('.js-nome-aluno').text($('#nomeAluno').text());
					
					alert('test');
					
					$('.js-card-ocorrencia').removeClass('hide');
					
					$('#preloader').addClass('hide');
					
					NGTICAE.FormularioEncaminhamento.iniciar();
				},
				error: function(){ 
					NGTICAE.Notificacao.mostrar('Ops!', 'Algo de muito estranho aconteceu e não foi possivel recuperar o recurso solicitado.', 'danger', $('#modalEncaminhamento'));
				}
			});
		},
		
		finalizar: function(){
			console.log('finalizando modal...');
			$('#preloader').removeClass('hide');
			$('.js-card-ocorrencia').addClass('hide');
			NGTICAE.FormularioEncaminhamento.finalizar();
		}
}
//===================================================================================================================


/*		ENCAMINHAMENTO	==================================================================================================================
 * 
 */
NGTICAE.Encaminhamento = function(){
	
	function Encaminhamento(){
		this.salvarBtn = $('.js-salvar-encaminhamento');
	}
	
	Encaminhamento.prototype.iniciar = function (){
		this.salvarBtn.on('click', onSalvar.bind(this));
	}
	
	function onSalvar(event){
		event.preventDefault();
		console.log('Salvando encaminhamento...');
		validarFormulario();
		
		console.log('ocorrencia', $('#ocorrencia').val());
		console.log('descricao', $('.js-encaminhamento-descricao').val());
		console.log('tipoEncaminhamento', $('#tipoEncaminhamento').val());
		
		$.ajax({
			url: this.salvarBtn.data('url') + '/salvar',
			method: 'POST',
			data: {
				/*codigo: $('#codigo').val(),
				dataEncaminhamento: $('#dataEncaminhamento').val(),*/
				ocorrencia: $('#ocorrencia').val(),
				descricao: $('.js-encaminhamento-descricao').val(),
				tipoEncaminhamento: $('#tipoEncaminhamento').val()
			},
			beforeSend: onIniciarRequisicao,
			error: onErroSalvandoOcorrencia,
			success: sucesso.bind(this),
			complete: onFinalizarRequisicao
		});
	}
	
	function sucesso(retorno){
		console.log('Sucesso... ocorrencia criada no servidor...', retorno);
		
		$('#modalEncaminhamento').modal('hide');
		NGTICAE.Tabela.atualizar();
		NGTICAE.Notificacao.mostrar('sucesso', 'Encaminhamento salvo!', 'info', $('body'));
	}
	
	function validarFormulario(){
		console.log('Validando formulario...');
	}
	
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
	}
	
	function onErroSalvandoOcorrencia(error){
		console.log('Erro ao salvar encaminhamento...', error);
		NGTICAE.Notificacao.mostrar('Formulario incompleto', 'preencha todos os campos!', 'danger', $('#modalEncaminhamento'));
	}
	
	function onFinalizarRequisicao(){
		console.log('Requisição finalizada...');
	}
	
	return Encaminhamento;
	
}();

//==================================================================================================================
//	FORMULARIO ENCAMINHAMENTO
//==================================================================================================================
NGTICAE.FormularioEncaminhamento = {
	iniciar: function(){
		console.log('Iniciando formulario...');
		$('.js-card-formulario-encaminhamento').removeClass('hide');
	},
	
	validar: function(){
		console.log('validando formulario...');
	},
	
	limpar: function(){
		console.log('limpando formulario...');
	},
	
	finalizar: function(){
		console.log('Finalizando formulario...');
		$('.js-card-formulario-encaminhamento').addClass('hide');
	}
}
//===================================================================================================================

$(function(){
	
	
	var encaminhamento = new NGTICAE.Encaminhamento();
	encaminhamento.iniciar();
	
});