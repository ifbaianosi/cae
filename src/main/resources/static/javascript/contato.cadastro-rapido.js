var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.ContatoCadastroRapido = (function(){
	
	function ContatoCadastroRapido(){//construtor
		this.contatoInput = $('.js-contato-input');
		this.codigoContato = $('.js-codigo-contato');
		this.botaoSubmit = $('.js-submit');
		this.formulario = $('.js-formulario');
		this.whatsappCheckBox = $('.js-whatsapp');
	}
	
	ContatoCadastroRapido.prototype.iniciar = function(){
		this.contatoInput.on('focusout', onCadastrarContato.bind(this));
		this.contatoInput.on('focus', onFocusContato.bind(this));
		this.whatsappCheckBox.on('change', onWhatsappCheckBoxClicado.bind(this));
	}
	
	function onCadastrarContato(){
		this.codigoContato.val('');
		if(this.contatoInput.val().trim().length > 0){
			console.log('Cadastrar contato agora!');
			$.ajax({
				url: this.contatoInput.data('url'), // /contatos/novo
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({numero: this.contatoInput.val()}),
				beforeSend: onIniciarRequisicao.bind(this),
				error: onErroSalvandoContato,
				success: onContatoSalvo.bind(this),
				complete: onFinalizarRequisicao.bind(this)
			});
		}else{
		}
	}
	
	function onIniciarRequisicao(){
		this.botaoSubmit.attr('disabled', true);
		this.whatsappCheckBox.attr('disabled', true);
	}
	
	function onErroSalvandoContato(erro){
		console.log('Erro ao salvar o contato: ', erro);
	}
	
	function onContatoSalvo(contato) {
		this.codigoContato.val(contato.codigo);
		console.log('contato.whatsapp',contato.whatsapp);
		this.whatsappCheckBox.attr('checked', contato.whatsapp);
		console.log('salvo com sucesso!', contato);
	}
	
	function onFinalizarRequisicao(){
		this.botaoSubmit.attr('disabled', false);
		this.whatsappCheckBox.attr('disabled', false);
	}
	
	function onFocusContato(){
		console.log('digite o numero para contato.');
	}
	
	function onWhatsappCheckBoxClicado(){
		if(this.contatoInput.val().trim().length > 0){
			var checked = this.whatsappCheckBox.is(':checked');
			$.ajax({
				url: this.whatsappCheckBox.data('url'),
				method: 'PUT',
				data: {
					codigo: this.codigoContato.val(),
					whatsapp: checked
				},
				beforeSend: onIniciarRequisicao.bind(this),
				error: onErroAtualizarContato,
				success: onContatoAtualizado.bind(this),
				complete: onFinalizarRequisicao.bind(this)
			});
		}else{
		}
	}
	
	function onErroAtualizarContato(erro){ console.log('Erro ao atualizar o contato whatsapp: ', erro); }
	
	function onContatoAtualizado(){	console.log('contato atualizado com sucesso!');	}
	
	return ContatoCadastroRapido;
	
}());

$(function(){
	var contatoCadastroRapido = new NGTICAE.ContatoCadastroRapido();
	contatoCadastroRapido.iniciar();
});