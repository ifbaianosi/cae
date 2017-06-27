var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.PesquisaAluno = (function(){
	
	function PesquisaAluno (){//construtor
		this.pesquisarBtn = $('.js-pesquisar-aluno');
		this.preloader = $('.preloader');
		this.url = this.pesquisarBtn.data('url');
		this.nomeOuMatriculaInputModal = $('.js-nomeOuMatricula-aluno-modal');
		
		this.matriculaInput = $('.js-matricula');
		this.validarMatriculaBtn = $('.js-validar-numero-matricula');
		
		this.htmlTabelaAlunos = $('#tabelaPesquisaAluno').html();
		this.template = Handlebars.compile(this.htmlTabelaAlunos);
		this.containerTabelaAlunos = $('.js-container-tabela-alunos');
		
		this.remover = $('.js-remover');
	}
	
	PesquisaAluno.prototype.iniciar = function(){
		this.pesquisarBtn.on('click', onPesquisarClicado.bind(this));
		this.validarMatriculaBtn.on('click', onValidarMatriculaBtnClicado.bind(this));
		this.remover.on('click', onRemoverAluno.bind(this));
	}
	
	function onPesquisarClicado(evento){
		evento.preventDefault();
		console.log('Iniciando pesquisa');
		console.log('url: ', this.url);
		console.log('nomeOuMatricula: ', this.nomeOuMatriculaInputModal.val().trim());
		$.ajax({
			url: this.url,
			method: 'GET',
			contentType: 'application/json',
			data: {
				nomeOuMatricula: this.nomeOuMatriculaInputModal.val().trim(),
			},
			beforeSend: iniciarRequisicaoModal.bind(this),
			success: pesquisaConcluida.bind(this),
			error: erroRetornoPesquisa.bind(this),
			complete: finalizaRequisicao.bind(this)
		});
	}
	
	function pesquisaConcluida(alunos){
		console.log('pesquisa concluida...', alunos);
		
		var novatabela = this.template(alunos);
		this.containerTabelaAlunos.html(novatabela);

		this.containerTabelaAlunos.removeClass('hide');
		
		$('.js-selecionar').on('click', onSelecionar.bind(this));
	}
	
	function onSelecionar(event){
		console.log('Selecionar aluno clicado...', $(event.currentTarget).data('codigo'));
		console.log('Selecionar aluno clicado com matricula...', $(event.currentTarget).data('matricula'));
		/*var aluno = {
				codigo: $(event.currentTarget).data('codigo'),
				nome: $(event.currentTarget).data('nome-aluno'),
				matricula: $(event.currentTarget).data('matricula'),
				curso: {
					nome: $(event.currentTarget).data('curso')
				}
		}*/
		
		var resposta = $.ajax({
			url: this.url + '/por-matricula',
			method: 'GET',
			contentType: 'application/json',
			data: {
				matricula: $(event.currentTarget).data('matricula'),
			}
		});
		
		resposta.done(sucesso);
		
		/*console.log('aluno clicado...: ',aluno);*/
		/*sucesso(aluno);*/
		fecharJanela();
	}
	
	function onValidarMatriculaBtnClicado(event){
		event.preventDefault();
		
		console.log('Fazer a pesquisa pela matricula informada...');
		console.log('matricula: ', this.matriculaInput.val().trim());
		
		$.ajax({
			url: this.url + '/por-matricula',
			method: 'GET',
			contentType: 'application/json',
			data: {
				matricula: this.matriculaInput.val().trim(),
			},
			beforeSend: iniciarRequisicaoModal.bind(this),
			success: sucesso.bind(this),
			error: erroRetornoPesquisa.bind(this),
			complete: finalizaRequisicao.bind(this)
		});
		
	}
	
	function sucesso(aluno){
		console.log('aluno', aluno);
		if(aluno.codigo != null){
			console.log('sucesso...', aluno);
			console.log('aluno.nome: ', aluno.nome);
			
			$('.js-nome').text(aluno.nome);
			$('.js-nome').val(aluno.matricula + ' - ' + aluno.nome);
			$('.js-matricula').text(aluno.matricula);
			$('.js-curso').text(aluno.curso.nome);
			
			$('.js-card-aluno').removeClass('hide');
			$('.js-aluno').addClass('hide');
			
			$('#aluno').val(aluno.codigo);
			$('#name-error').hide();
			
			if(aluno.cadastroCompleto){
				$('.js-restante-formulario').removeClass('hide');
				$('.js-mensagem-cadastro-incompleto').addClass('hide');
			}else{
				$('.js-mensagem-cadastro-incompleto').removeClass('hide');
				$('.js-link-completar-cadastro').attr('href', $('.js-pesquisar-aluno').data('url') + '/' + aluno.codigo);
				$('.js-restante-formulario').addClass('hide');
			}
		}
	}
	
	function onRemoverAluno(){
		$('.js-nome').text('');
		$('.js-matricula').text('');
		$('.js-curso').text('');
		
		$('.js-card-aluno').addClass('hide');
		$('.js-restante-formulario').addClass('hide');
		$('.js-mensagem-cadastro-incompleto').addClass('hide');
		$('.js-aluno').removeClass('hide');
		
		$('#aluno').val('');
		this.matriculaInput.val('');
	}
	
	function erroRetornoPesquisa(erro){
		console.log('erro', erro.responseText);
		
		var labelErro = '<label id="name-error" class="error" for="aluno">' + erro.responseText +'.</label>';
		$('#name-error').remove();
		$('#divInput').after(labelErro);
		$('#divInput').addClass('error');
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
		$('.js-container-tabela-alunos').addClass('hide');
		$('.js-matricula').val('');
		$('#modalPesquisaAluno').modal('hide');
	}
	
	return PesquisaAluno;
	
}());

$(function(){
	var pesquisaAluno = new NGTICAE.PesquisaAluno();
	pesquisaAluno.iniciar();
});