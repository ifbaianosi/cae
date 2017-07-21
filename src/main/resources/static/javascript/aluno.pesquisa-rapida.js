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
		$('#modalPesquisaAluno').on('hidden.bs.modal', fecharJanela);
	}
	
	function onPesquisarClicado(evento){
		evento.preventDefault();
		$('.js-mensagem-caracteres').addClass('hide');
		console.log('Iniciando pesquisa');
		console.log('url: ', this.url);
		console.log('nomeOuMatricula: ', this.nomeOuMatriculaInputModal.val().trim());
		
		if(this.nomeOuMatriculaInputModal.val().trim().length > 2){
			console.log('pesquisando...');
			$.ajax({
				url: this.url + '/por',
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
		}else{
			console.log('Digite ao menos 3 caracteres para efetuar a pesquisa.');
			$('.js-mensagem-caracteres').removeClass('hide');
			this.containerTabelaAlunos.addClass('hide');
		}
		
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
		
		var resposta = $.ajax({
			url: this.url + '/por-matricula',
			method: 'GET',
			contentType: 'application/json',
			data: {
				matricula: $(event.currentTarget).data('matricula'),
			}
		});
		
		resposta.done(sucesso);
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
			

			$('#aluno').val(aluno.codigo);
			$('.js-nome').text(aluno.nome);
			$('.js-nome').val(aluno.matricula + ' - ' + aluno.nome);
			$('.js-matricula').text(aluno.matricula);
			$('.js-curso').text(aluno.curso.nome);
			$('.js-status').text(aluno.status);
			$('.js-regime').text(aluno.regime);
			
			
			//CONSULTA DADOS DO ALUNO - VIGILANTE ================
			$('.js-sexo-descricao').text(aluno.sexo != null ? aluno.sexo : '-');
			$('.js-alojamento').text(aluno.alojamento != null ? aluno.alojamento : '-');
			$('.js-apartamento').text(aluno.apartamento != null ? aluno.apartamento : '-');
			$('.js-serie-turma').text(aluno.serieTurma != null ? aluno.serieTurma : '-');
			$('.js-data-nascimento').text(aluno.dataNascimentoFormatada != null ? aluno.dataNascimentoFormatada : '-');
			$('.js-email').text(aluno.email != null ? aluno.email : '-');
			$('.js-contato').text(aluno.contato != null ? aluno.contato : '-');
			if(aluno.whatsapp != true){
				$('.js-whatsapp').removeAttr('checked');
			}else{
				$('.js-whatsapp').attr('checked', true);
			}
			if(aluno.saida != true){
				$('.js-saida').removeAttr('checked');
			}else{
				$('.js-saida').attr('checked', true);
			}
			
			var htmlPanelResponsavel = $('#panelResponsaveisTemplate').html();
			var template = Handlebars.compile(htmlPanelResponsavel);
			
			var novoPainel = template(aluno.responsaveisDoAluno);
			$('.js-container-tabela-responsaveis').html(novoPainel);
			
			//=====================================================
			
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
		$('.js-nomeOuMatricula-aluno-modal').val('');
		$('.js-mensagem-caracteres').addClass('hide');
		$('#modalPesquisaAluno').modal('hide');
	}
	
	return PesquisaAluno;
	
}());

$(function(){
	var pesquisaAluno = new NGTICAE.PesquisaAluno();
	pesquisaAluno.iniciar();
});