var NGTICAE = NGTICAE || {};


NGTICAE.NotificacoesSistema = (function(){
	
	function NotificacoesSistema(){
		this.url = $('#url-notificacoes').data('url');
		
		this.htmlNotificacoes = $('#notificacoesTamplate').html();
		this.template = Handlebars.compile(this.htmlNotificacoes);
		this.containerNotificacoes = $('.js-container-notificacoes');
	}
	
	NotificacoesSistema.prototype.enable = function(){
		console.log('url de acesso as notificacoes: ', this.url);
		
		atualizarNotificacoes();
		window.setInterval(atualizarNotificacoes, 60 * 1000); //60 segundos
	}
	
	function atualizarNotificacoes(){
		console.log('url: ', $('#url-notificacoes').data('url'));
		var resposta = $.ajax({
			url: $('#url-notificacoes').data('url'),
			method: 'GET'
		});
		
		resposta.done(function(notificacoes){
			
			console.log(notificacoes);
			
			var template = Handlebars.compile($('#notificacoesTamplate').html());
			var notificacoesTemplate = template(notificacoes);
			
			$('.js-notificacao-qty').html(notificacoes.length);
			$('.js-container-notificacoes').html(notificacoesTemplate);
			
			$('.js-notificacao').on('click', function(event){
				/*var uri = $(event.currentTarget).data('uri');*/
				var url = $('#url-notificacoes').data('url')  +  "/" + $(event.currentTarget).data('codigo') ;
				console.log(url);
				window.location.href = url;
			});
		});
		
		console.log('verifica notificações no servidor...');
	}
	
	function verNotificacoes(notificacoes){
		
		console.log(notificacoes);
		
		var notificacoesTemplate = this.template(notificacoes);
		
		this.containerNotificacoes.html(notificacao);
	}
	
	return NotificacoesSistema;
}());

$(function() {

	var notificacoesSistema = new NGTICAE.NotificacoesSistema();
	notificacoesSistema.enable();
});


