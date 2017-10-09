var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.PesquisaAluno = (function(){
	
	function PesquisaAluno (){//construtor
		this.pesquisarBtn = $('.js-pesquisar-aluno');
		this.preloader = $('.preloader');
		this.url = this.pesquisarBtn.data('url');
		this.nomeOuMatriculaInputModal = $('.js-nomeOuMatricula-aluno-modal');
		
		this.matriculaInput = $('.js-matricula');
		this.validarMatriculaBtn = $('.js-validar-numero-matricula');
		
		this.htmlTabelaAlunos = $('#tabelaAdicionarAlunoOcorrencia').html();
		this.template = Handlebars.compile(this.htmlTabelaAlunos);
		this.containerTabelaAlunos = $('.js-container-tabela-alunos');
		
		this.checkboxSelecionarTodos = $('.js-selecionar-todos');
		
		this.remover = $('.js-remover');
	}
	
	PesquisaAluno.prototype.iniciar = function(){
		this.pesquisarBtn.on('click', onPesquisarClicado.bind(this));
		this.validarMatriculaBtn.on('click', onValidarMatriculaBtnClicado.bind(this));
		this.validarMatriculaBtn.on('keypress', onValidarMatriculaBtnClicado.bind(this));
		this.remover.on('click', onRemoverAluno.bind(this));
		$('#adicionarAlunoNaOcorrenciaModal').on('hidden.bs.modal', fecharJanela);
		listarAlunosDaOcorrencia();

		/*$('#autocomplete-input').on('keyup', NGTICAE.Autocomplete.iniciar($('#autocomplete-input')));*/
	}
	
	function onPesquisarClicado(evento){
		evento.preventDefault();
		$('.js-mensagem-caracteres').addClass('hide');
		console.log('Iniciando pesquisa');
		console.log('url: ', this.url);
		
		console.log('pesquisando...');
		console.log('nome: ',$('#nome').val().trim());
		console.log('matricula_: ',$('#matricula_').val().trim());
		console.log('curso: ',$('#curso').val().trim());
		console.log('serie: ',$('#serieTurma').val().trim());
		console.log('alojamento: ',$('#alojamento').val().trim());
		console.log('apartamento: ',$('#apartamento').val().trim());
		
		$.ajax({
			url: this.url + '/filtrar',
			method: 'GET',
			data: {
				nome: $('#nome').val().trim(),
				matricula: $('#matricula_').val().trim(),
				curso: $('#curso').val().trim(),
				serieTurma: $('#serieTurma').val().trim(),
				alojamento: $('#alojamento').val().trim(),
				apartamento: $('#apartamento').val().trim(),
			},
			beforeSend: iniciarRequisicaoModal.bind(this),
			success: pesquisaConcluida.bind(this),
			error: erroRetornoPesquisa.bind(this),
			complete: finalizaRequisicao.bind(this)
		});
		
	}
	
	/*function onPesquisarClicado(evento){
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
		
	}*/
	
	function pesquisaConcluida(alunos){
		console.log('pesquisa concluida...', alunos);
		
		var novatabela = this.template(alunos);
		this.containerTabelaAlunos.html(novatabela);

		this.containerTabelaAlunos.removeClass('hide');
		
		$('.js-selecionar-todos').on('click', selecionarTodos);
		
		$('.js-selecionar').on('click', onSelecionar.bind(this));
	}
	
	function onSelecionar(){
		/*console.log('Selecionar aluno clicado...', $(event.currentTarget).data('codigo'));
		console.log('Selecionar aluno clicado com matricula...', $(event.currentTarget).data('matricula'));*/
		
		/*var resposta = $.ajax({
			url: this.url + '/por-matricula',
			method: 'GET',
			contentType: 'application/json',
			data: {
				matricula: $(event.currentTarget).data('matricula'),
			}
		});*/
		
		var checkboxSelecionados = $('.js-checkbox').filter(':checked');
		var codigos = $.map(checkboxSelecionados, function(c) {
			return $(c).data('codigo');
		});
		
		console.log('codigos', codigos);
		console.log('codigo', codigos[0]);
		/*console.log('url', this.url + '/adicionar');*/
		
		$('#aluno').val(codigos[0]);
		
		$.ajax({
			url: this.url + '/adicionar',
			method: 'POST',
			data: {
				codigos: codigos,
				uuid: $('#uuid').val()
			},
			beforeSend: function(){console.log('inicindo requisição')},
			success: sucesso,
			error: function(erro){console.log('erro',erro)}
		});
		
		/*resposta.done(sucesso.bind(this));*/
		
		fecharJanela();
	}
	
	function selecionarTodos(){
		var status = $('.js-selecionar-todos').prop('checked');
		console.log('selecionar todos: ',status);
		if (status == true) {
			console.log('entrou no if');
			$('.js-checkbox').attr('checked', 'checked'); 
		}else{
			$('.js-checkbox').removeAttr('checked');
		}
		//$('.js-selecionar').prop(status ? 'checked' : 'unchecked');
	}
	
	function onValidarMatriculaBtnClicado(event){
		
		event.preventDefault();
		
		if(event.wich == 13 || event.keyCode == 13){
			event.preventDefault();
			console.log('teste passou por aqui!!!');
		}
		
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
			success: adicionarAluno.bind(this),
			error: erroRetornoPesquisa.bind(this),
			complete: finalizaRequisicao.bind(this)
		});
	}
	
	function adicionarAluno(aluno){
		var codigos = [aluno.codigo];
		console.log('retorno pesquisa aluno pela matricula: ', aluno);
		console.log('codigo aluno: ', codigos);
		$.ajax({
			url: $('.js-pesquisar-aluno').data('url') + '/adicionar',
			method: 'POST',
			data: {
				codigos: codigos,
				uuid: $('#uuid').val()
			},
			beforeSend: function(){console.log('iniciando requisição', this.url + '/adicionar')},
			success: sucesso,
			error: function(erro){console.log('erro',erro)}
		});
	}
	
	function sucesso(alunos){
		console.log('alunos', alunos);
		
		var htmlCardAluno = $('#cardAlunoTemplate').html();
		var template = Handlebars.compile(htmlCardAluno);
		
		var novoCard = template(alunos);
		$('#container_card_aluno').html(novoCard);
		
		$('.js-restante-formulario').removeClass('hide');
		$('.js-card-aluno').removeClass('hide');

		//campo matricula
		$('.js-matricula').val('');
		$('#name-error').remove();
		$('#divInput').removeClass('error');

		$('.js-remover-aluno').on('click', onRemoverAlunoDaLista.bind(this));
		$('.js-remover-todos').on('click', onRemoverTodosAlunosDaLista.bind(this));
		
		//CODIFICAÇÂO NOVA PARA VARIOS ALUNOS SELECIONADOS
		/*var resposta = $.ajax({
			url: $('.js-pesquisar-aluno').data('url') + '/listar', //
			method: 'GET',
			data: {
				uuid: $('#uuid').val()
			}
		});
		
		resposta.done(function(alunos){console.log('alunos', alunos)});*/
		
		/*if(aluno.codigo != null){
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
		}*/
	}
	
	function listarAlunosDaOcorrencia(){
		var resposta = $.ajax({
			url: $('.js-pesquisar-aluno').data('url') + '/listar', //
			method: 'GET',
			data: {
				uuid: $('#uuid').val()
			}
		});
		
		resposta.done(sucesso);
	}
	
	function onRemoverAlunoDaLista(event){
		console.log('entrou no metodo onRemoverAlunoDaLista');
		var codigoAluno = $(event.currentTarget).data('codigo');
		console.log('remover aluno: ', codigoAluno);
		var uuid = $('#uuid').val();
		
		var resposta = $.ajax({
			url: $('.js-pesquisar-aluno').data('url') + '/remover/'+ uuid +'/'+ codigoAluno, //http://localhost:8080/trino/alunos/remover/uuid/codigoaluno
			method: 'DELETE'
		});
		
		resposta.done(sucesso);
	}
	
	function onRemoverTodosAlunosDaLista(){
		var uuid = $('#uuid').val();
		
		var resposta = $.ajax({
			url: $('.js-pesquisar-aluno').data('url') + '/remover-todos/'+ uuid, //http://localhost:8080/trino/alunos/remover/uuid
			method: 'DELETE'
		});
		
		resposta.done(sucesso);
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
		$('#adicionarAlunoNaOcorrenciaModal').modal('hide');
	}
	
	return PesquisaAluno;
	
}());

$(function(){
	$('#alterarAluno').addClass('hide');
	var pesquisaAluno = new NGTICAE.PesquisaAluno();
	pesquisaAluno.iniciar();
});