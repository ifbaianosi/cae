var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.PesquisaAluno = (function(){
	
	function PesquisaAluno (){//construtor
		this.pesquisarBtn = $('.js-pesquisar-aluno');
		this.preloader = $('.preloader');
		this.url = this.pesquisarBtn.data('url');
		this.nomeOuMatriculaInputModal = $('.js-nomeOuMatricula-aluno-modal');
		
		this.matriculaInput = $('.js-matricula');
		
		this.htmlTabelaAlunos = $('#tabelaPesquisaAluno').html();
		this.template = Handlebars.compile(this.htmlTabelaAlunos);
		this.containerTabelaAlunos = $('.js-container-tabela-alunos');
	}
	
	PesquisaAluno.prototype.iniciar = function(){
		this.pesquisarBtn.on('click', onPesquisarClicado.bind(this));
		this.matriculaInput.on('blur', onFocusPerdido.bind(this));
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
	}
	
	function onFocusPerdido(){
		console.log('Fazer a pesquisa pela matricula informada...')
	}
	
	function erroRetornoPesquisa(erro){
		console.log('erro', erro);
	}
	
	function iniciarRequisicaoModal(){
		console.log('iniciar requisição...');
		this.preloader.removeClass('hide');
	}
	
	function finalizaRequisicao(){
		console.log('finalizar requisição...');
		this.preloader.addClass('hide');
	}
	
	return PesquisaAluno;
	
}());

$(function(){
	var pesquisaAluno = new NGTICAE.PesquisaAluno();
	pesquisaAluno.iniciar();
});