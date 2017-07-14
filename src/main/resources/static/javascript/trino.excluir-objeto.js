NGTICAE = NGTICAE || {};

NGTICAE.DialogoExcluir = (function() {
	
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn')
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		/*if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Excluído com sucesso!', 'success');
		}*/
	}
	
	function onExcluirClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		console.log('Tem certeza? Excluir "' + objeto + '"? Você não poderá recuperar depois.');
		
		alertify.confirm('Tem certeza? Excluir "' + objeto + '"? Você não poderá recuperar depois.', onExcluirConfirmado.bind(this, url), function() {
		    // user clicked "cancel"
			console.log('Não, Cancelar exclusão!');
		});
		
		/*swal({
			title: 'Tem certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, exclua agora!',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, url));*/
	}
	
	function onExcluirConfirmado(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso() {
		console.log('Atualizar página...');
		window.location.reload(true);
		
		/*var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;*/
	}
	
	function onErroExcluir(e) {
		console.log('erro', e.responseText);
		NGTICAE.Notificacao.mostrar("Ops", e.responseText, "danger");
		/*swal('Oops!', e.responseText, 'error');*/
	}
	
	return DialogoExcluir;
	
}());

$(function() {
	var dialogo = new NGTICAE.DialogoExcluir();
	dialogo.iniciar();
});