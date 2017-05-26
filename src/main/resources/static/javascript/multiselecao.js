var NGTICAE = NGTICAE || {};

NGTICAE.MultiSelecao = {
		iniciar: function(){
			$('.js-checkbox').on('change', this.toggleBtn);
		},
		
		toggleBtn: function(){
			console.log('Checkbox selecionado...');
			
			var checkboxSelecionados = $('.js-checkbox').filter(':checked');
			console.log(checkboxSelecionados);
			
			if(checkboxSelecionados.length == 1){
				$('.js-editar-ocorrencia').removeAttr('disabled');
			}else{
				$('.js-editar-ocorrencia').attr('disabled', 'disabled');
			}
			
			if(checkboxSelecionados.length > 0){
				$('.js-excluir-ocorrencias').removeAttr('disabled');
			}else{
				$('.js-excluir-ocorrencias').attr('disabled', 'disabled');
			}
		}
}

/*NGTICAE.MultiSelecao = function(){
	
	function MultiSelecao(){
		this.editarOcorrenciaBtn = $('.js-editar-ocorrencia');
		this.excluirOcorrenciaBtn = $('.js-excluir-ocorrencias');
		this.checkbox = $('.js-checkbox');
	}
	
	MultiSelecao.prototype.iniciar = function (){
		this.checkbox.on('change', onVerificaCheckboxChecado.bind(this));
	}
	
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
	
	return MultiSelecao;
	
}();*/

$(function(){
	//instanciar objeto de outro arquivo [multiselecao.js]
	/*var multiSelecao = new NGTICAE.MultiSelecao();
	multiSelecao.iniciar();*/
	
	NGTICAE.MultiSelecao.iniciar();
});