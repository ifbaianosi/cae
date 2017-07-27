var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.TrocarSenha = (function(){
	
	function TrocarSenha (){//construtor
		this.alterarSenhaBtn = $('.js-alterar-senha');
		this.preloader = $('.preloader');
		this.url = this.alterarSenhaBtn.data('url');
		this.senhaAtual = $('.js-senha-atual');
		this.novaSenha = $('.js-nova-senha');
		this.repitaSenha = $('.js-repita-senha');
	}
	
	TrocarSenha.prototype.iniciar = function(){
		this.alterarSenhaBtn.on('click', onTrocarSenha.bind(this));
		$('#modalTrocarSenhaUsuario').on('hidden.bs.modal', fecharJanela);
	}
	
	function onTrocarSenha(evento){
		evento.preventDefault();
		$('.js-mensagem').addClass('hide');
		console.log('Iniciando alteração da senha...');
		console.log('url: ', this.url);
		console.log('senha atual: ', this.senhaAtual.val());
		console.log('nova senha: ', this.novaSenha.val());
		console.log('repita senha: ', this.repitaSenha.val());
		
		/*if(this.senhaAtual.val().length > 0){*/
			console.log('vai no servidor aqui...');
			$.ajax({
				url: this.url + '/trocarSenha',
				method: 'PUT',
				data: {
					senhaAtual: this.senhaAtual.val(),
					senha: this.novaSenha.val(),
					confirmacaoSenha: this.repitaSenha.val()
				},
				beforeSend: iniciarRequisicaoModal.bind(this),
				success: sucesso.bind(this),
				error: erroRetorno.bind(this),
				complete: finalizaRequisicao.bind(this)
			});
		/*}else{
			console.log('Preencha todos os campos do formulário.');
			$('.js-texto-mensagem').text('É preciso informar a senha atual.')
			$('.js-mensagem').removeClass('hide');
		}*/
		
	}
	
	function sucesso(retorno){
		console.log('pesquisa concluida...', retorno);
		fecharJanela();
		NGTICAE.Notificacao.mostrar('Sucesso', 'Senha alterada', 'info', $('body'));
	}
	
	function limparFormulario(){
		console.log('limpar formulário...')
		$('.js-senha-atual').val(''),
		$('.js-nova-senha').val('');
		$('.js-repita-senha').val('')
	}
	
	function erroRetorno(erro){
		console.log('erro', erro);
		
		var errors = "";
		erro.responseJSON.forEach(function(valor, chave){
		    errors += "<span>"+valor+"</span><br>";
		});
		
		$('.js-texto-mensagem').html(errors)
		$('.js-mensagem').removeClass('hide');
	}
	
	function iniciarRequisicaoModal(){
		console.log('iniciar requisição...');
		this.preloader.removeClass('hide');
	}
	
	function finalizaRequisicao(){
		console.log('finalizar requisição...');
		this.preloader.addClass('hide');
	}
	
	function fecharJanela(){
		console.log('Fechar janela...');
		limparFormulario();
		$('.js-mensagem').addClass('hide');
		$('#modalTrocarSenhaUsuario').modal('hide');
	}
	
	return TrocarSenha;
	
}());

$(function(){
	var trocarSenha = new NGTICAE.TrocarSenha();
	trocarSenha.iniciar();
});