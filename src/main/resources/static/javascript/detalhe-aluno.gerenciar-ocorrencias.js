var NGTICAE = NGTICAE || {};

NGTICAE.GerenciarOcorrencias = function(){
	
	function GerenciarOcorrencias(){
		this.novaOcorrenciaBtn = $('.js-nova-ocorrencia');
		this.containerOcorrencias = $('.js-container-ocorrencias');
		this.containerFormularioOcorrencias = $('.js-container-formulario-ocorrencias');
		this.cancelarOcorrenciaBtn = $('.js-cancelar-ocorrencias');
		this.editarOcorrenciaBtn = $('.js-editar-ocorrencia');
		this.excluirOcorrenciaBtn = $('.js-excluir-ocorrencias');
	}
	
	GerenciarOcorrencias.prototype.iniciar = function (){
		this.novaOcorrenciaBtn.on('click', onNovaOcorrenciaBtnClicado.bind(this));
		this.cancelarOcorrenciaBtn.on('click', onCancelarOcorrenciaBtnClicado.bind(this));
	}
	
	function onNovaOcorrenciaBtnClicado(){
		console.log('Nova ocorrencia...');
		
		//desabilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.addClass('disabled');
		this.editarOcorrenciaBtn.addClass('disabled');
		this.excluirOcorrenciaBtn.addClass('disabled');
		
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
	}
	
	function onCancelarOcorrenciaBtnClicado(){
		console.log('Cancelar nova ocorrencia...');
		
		//habilitar os botoes novo, editar e excluir
		this.novaOcorrenciaBtn.removeClass('disabled');
		this.editarOcorrenciaBtn.removeClass('disabled');
		this.excluirOcorrenciaBtn.removeClass('disabled');
		
		//tornar invisivel o botão cancelar
		this.cancelarOcorrenciaBtn.addClass('hide');
		
		//mostrar container de ocorrencias
		this.containerOcorrencias.show('slow');
		
		//esconder container do formulario de ocorrencias
		this.containerFormularioOcorrencias.hide('slow');
	}
	
	return GerenciarOcorrencias;
	
}();

$(function(){
	var gerenciarOcorrencias = new NGTICAE.GerenciarOcorrencias();
	gerenciarOcorrencias.iniciar();
});