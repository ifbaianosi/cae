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
