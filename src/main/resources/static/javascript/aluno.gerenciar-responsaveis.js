var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.GerenciarResponsaveis = (function(){
	
	function GerenciarResponsaveis(){//construtor
		this.modal = $('.js-modal-cadastro-responsavel');
		this.tabelaResponsaveis = $('.js-tabela-responsaveis');
		this.tabelaResponsaveisBody = $('.js-tabela-responsaveis-body');
		this.removerTodosBtn = $('.js-remover-todos');
		//CAMPOS DO FORMULARIO
		this.formulario = $('#formulario_responsavel');
		this.uuid = $('#uuid').val();
		this.grauParentescoSelect = $('#responsavel_parentesco');
		this.nomeResponsavelInput = $('#responsavel_nome');
		this.contatoResponsavelInput = $('#responsavel_contato');
		this.contato2Input = $('#responsavel_contato2');
		this.whatsappResponsavelCheckBox = $('#responsavel_whatsapp');
		this.whatsapp2CheckBox = $('#responsavel_whatsapp2');
		this.email = $('#responsavel_email');

		this.adicionarBtn = $('.js-adicionar-responsavel');
		this.urlResponsaveis = this.adicionarBtn.data('url');
		
		//BOTOES DA TABELA DE RESPONSAVEIS
		this.removerResponsavelBtn = null;
		
		this.containerResponsaveis = $('.js-container-tabela-responsaveis');
		this.htmlTabelaResponsaveis = $('#tabelaResponsaveisTemplate').html();
		this.template = Handlebars.compile(this.htmlTabelaResponsaveis);
		
		//PESQUISA RESPONSAVEL
		this.containerPesquisa = $('.js-conteiner-pesquisa');
		this.inputPesquisa = $('.js-input-pesquisa');
		this.urlPesquisaResponsaveisPorNome = this.inputPesquisa.data('url');
	}
	
	
	GerenciarResponsaveis.prototype.iniciar = function(){
		//FUNÇÃO PESQUISA
		this.inputPesquisa.on('keyup paste', pesquisarResponsavel.bind(this));
		
		this.adicionarBtn.on('click', onAdicionarResponsavel.bind(this));
		this.removerTodosBtn.on('click', onRemoverTodosResponsaveis.bind(this));
		this.modal.on('hide.bs.modal', onFecharModal);
		$('.js-exclusao-btn').on('click', onRemoverResponsavel);
		$('.js-novo-responsavel').on('click', onFormNovoResponsavel);
		$('.js-voltar-pesquisa').on('click', onIniciarPesquisa);
		$('.js-cadastrar-novo-responsavel').on('click', onCadastrarNovoResponsavel.bind(this));
		$('.js-retornar-pesquisa').on('click', onHabilitaPesquisa);
		$('.js-editar-parentesco').on('click', onEditarParentesco);
	}
	
	function pesquisarResponsavel(){
		var parametro = this.inputPesquisa.val();
		var containerTabelaResponsaveis = $('.js-container-responsaveis');
		var containerMensagens = $('.js-mensagem');
		
		if(parametro){
			console.log('pesquisar');
			containerTabelaResponsaveis.removeClass('hide');
			containerMensagens.addClass('hide');
			
			var retorno = $.ajax({
				url: this.urlPesquisaResponsaveisPorNome + parametro,
				method: 'GET'
			});
			
			retorno.done(function(dados){
				var htmlTabela = $('#tabelaResponsaveisTemplate').html();
				var template = Handlebars.compile(htmlTabela);
				
				var table = template(dados);
				containerTabelaResponsaveis.html(table);
				
				$('.js-selecionar-responsavel').on('click', onSelecionarResponsavel);
				$('.js-novo-responsavel').on('click', onFormNovoResponsavel);
			});
		}else{
			console.log('não deve pesquisar');
			containerTabelaResponsaveis.addClass('hide');
			containerTabelaResponsaveis.html('');
			containerMensagens.removeClass('hide');
		}
	}
	
	function onSelecionarResponsavel(event){
		var codigo = $(event.currentTarget).data('codigo');
		var nome = $(event.currentTarget).data('nome');
		console.log('responsavel selecionado: ', nome);
		
		$('.js-conteiner-pesquisa').addClass('hide');
		$('.js-container-responsaveis').addClass('hide');
		
		var containerFormParentesco = $('.js-container-form-parentesco');
		
		$('.js-nome-responsavel').text(nome);
		$('.js-codigo-responsavel').val(codigo);
		
		var codigoAluno = $('.js-modal-responsavel').data('aluno');
		var nomeAluno = $('.js-modal-responsavel').data('nome-aluno');
		
		$('.js-nome-aluno').text(nomeAluno);
		$('.js-codigo-aluno').val(codigoAluno);
		
		containerFormParentesco.removeClass('hide');
	}
	
	function onIniciarPesquisa(){
		onHabilitaPesquisa();
		$('.js-mensagem').removeClass('hide');
	}
	
	function onHabilitaPesquisa(){
		$('.js-codigo-responsavel').val('');
		$('.js-codigo-aluno').val('');
		$('.js-conteiner-pesquisa').removeClass('hide');
		$('.js-container-responsaveis').removeClass('hide');
		$('.js-container-form-parentesco').addClass('hide');
		$('.js-container-novo-responsavel').addClass('hide');
		$('.js-mensagem').addClass('hide');
	}
	
	function onEditarParentesco(){
		var codigo = $(event.currentTarget).data('codigo');
		$('#responsavelAluno').val(codigo);
		
		var codigoResponsavel = $(event.currentTarget).data('codigo-responsavel');
		var nome = $(event.currentTarget).data('nome');
		$('.js-nome-responsavel').text(nome);
		$('.js-codigo-responsavel').val(codigoResponsavel);
		
		var parentesco = $(event.currentTarget).data('parentesco');
		console.log(parentesco);
		$('#responsavel_parentesco').val(parentesco);
		
		var codigoAluno = $('.js-modal-responsavel').data('aluno');
		var nomeAluno = $('.js-modal-responsavel').data('nome-aluno');
		$('.js-nome-aluno').text(nomeAluno);
		$('.js-codigo-aluno').val(codigoAluno);
		
		$('.js-conteiner-pesquisa').addClass('hide');
		$('.js-container-responsaveis').addClass('hide');
		$('.js-container-form-parentesco').removeClass('hide');
		$('.js-container-novo-responsavel').addClass('hide');
		$('.js-mensagem').addClass('hide');
	}
	
	function onFormNovoResponsavel(){
		$('.js-conteiner-pesquisa').addClass('hide');
		$('.js-container-responsaveis').addClass('hide');
		$('.js-container-form-parentesco').addClass('hide');
		$('.js-mensagem').addClass('hide');
		
		$('.js-container-novo-responsavel').removeClass('hide');
	}
	
	function onKeyUp(){
		// volta para selecionar o elemento pai
		var $elementoPai = $('.bs-searchbox');
		console.log("$elementoPai", $elementoPai);
		
		// procura dentro do elemento pai o elemento [name="plano"]
		var $elemento = $elementoPai.find('.form-control'[3]);
		console.log("$elemento", $elemento);
		
		console.log("digitou!", $elemento.val());
	}
	
	/* ADICIONAR */
	function onAdicionarResponsavel(){
		console.log('adicionar responsavel - validar campos!');
		console.log('aluno: ', $('.js-codigo-aluno').val());
		console.log('responsavel: ', $('.js-codigo-responsavel').val());
		console.log('parentesco: ', this.grauParentescoSelect.val());
		
		$.ajax({
			url: this.urlResponsaveis + '/adicionar',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				codigo: $('#responsavelAluno').val(),
				aluno: {codigo: $('.js-codigo-aluno').val()},
				responsavel: {codigo: $('.js-codigo-responsavel').val()},
				parentesco: this.grauParentescoSelect.val().length > 0 ? this.grauParentescoSelect.val() : null
			}),
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroSalvandoResponsavel,
			success: onAtualizarTabela.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
	}
	
	function onCadastrarNovoResponsavel(){
		console.log('cadastrando novo responsavel...');
		$.ajax({
			url: this.urlResponsaveis + '/novo', // /responsaveis : POST
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				nome: this.nomeResponsavelInput.val(),
				contato: this.contatoResponsavelInput.val(),
				contato2: this.contato2Input.val(),
				contato_whatsapp: this.whatsappResponsavelCheckBox.val() == 'on' ? 'true' : 'false',
				contato2_whatsapp: this.whatsapp2CheckBox.val() == 'on' ? 'true' : 'false',
				email: this.email.val()
			}),
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroSalvandoResponsavel,
			success: onSalvoComSucesso.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
	}
	
	/* REMOVER TODOS */
	function onRemoverTodosResponsaveis(){
		console.log('remover todos os responsaveis!');
		console.log(this.uuid);
		$.ajax({
			url: this.urlResponsaveis + '/remover-todos/' + this.uuid, // /responsaveis/remover-todos/uuid
			method: 'DELETE',
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroExcluindoResponsaveis,
			success: onAtualizarTabela.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
	}
	
	function onRemoverResponsavel(evento){
		console.log('remover responsavel...');
		var urlRemoverResponsavel = $(evento.currentTarget).data('url');
		
		console.log('remover responsavel:', urlRemoverResponsavel);
		
		/*$.ajax({
			url: $('.js-adicionar-responsavel').data('url') + '/remover/' + $('#uuid').val() +'/' + contato,
			method: 'DELETE',
			success: onAtualizarTabela.bind(this),
			error: onErroExcluindoResponsaveis
		});*/
	}
	
	
	/* FUNCOES PARA ADICIONAR RESPONSAVEL*/
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
	}
	
	function onErroSalvandoResponsavel(erro){
		console.log('Ops!! erro: ', erro);
		NGTICAE.Notificacao.mostrar("Ops!</br>", erro.responseText, "danger", $('.js-modal-cadastro-responsavel'));
	}
	
	function onAtualizarTabela() {
		console.log('lista de responsaveis atualizada...');
		window.location.reload();
		/*window.location.replace("http://pt.stackoverflow.com");*/
		/*console.log('testando o contexto "this"',this.uuid);
		
		var novatabela = this.template(responsaveis);
		this.containerResponsaveis.html(novatabela);
		
		this.removerResponsavelBtn = $('.js-remover');
		this.removerResponsavelBtn.on('click', onRemoverResponsavel.bind(this));*/
		
		/*if(responsaveis.length>0){
			var tr = "";
			for (i = 0; i < responsaveis.length; i++){
				var responsavel = responsaveis[i].responsavel;
				
				tr += "<tr><td>"+ responsavel.nome +"</td><td>"+ responsavel.contato +"</td><td><b>"+ responsavel.parentesco +"</b></td>"+
				"<td>"+
				"<a href='javascript:void(0);'>"+
				"<i class='material-icons'>mode_edit</i>"+
				"</a>"+
				"<a href='javascript:void(0);' class='js-remover' data-contato='" + responsavel.contato + "'>"+
				"<i class='material-icons'>delete</i>"+
				"</a>"+
				"</td>"+
				"</tr>";
				
				
				
				
			}
			
			//adiciona as linhas na tabela
			this.tabelaResponsaveisBody.html(tr);
			
			//ADICIONAR AÇÕES AOS BOTOES NAS LINHAS
			this.removerResponsavelBtn = $('.js-remover');
			this.removerResponsavelBtn.on('click', onRemoverResponsavel.bind(this));
		}else{
			this.tabelaResponsaveisBody.html('<tr><td colspan="4">Adicione ao menos um responsavel</td></tr>');
		}*/
		
		/*this.modal.modal('hide');
		this.removerTodosBtn.removeAttr("disabled");*/
	}
	
	function onSalvoComSucesso(responsavel){
		console.log('responsavel salvo com sucesso!...');
		console.log('responsavel salvo', responsavel);
		
		$('.js-codigo-responsavel').val(responsavel.codigo);
		$('.js-nome-responsavel').text(responsavel.nome);
		
		var codigoAluno = $('.js-modal-responsavel').data('aluno');
		var nomeAluno = $('.js-modal-responsavel').data('nome-aluno');
		
		$('.js-nome-aluno').text(nomeAluno);
		$('.js-codigo-aluno').val(codigoAluno);
		
		/*$('.js-conteiner-pesquisa').removeClass('hide');*/
		/*$('.js-container-responsaveis').removeClass('hide');*/
		$('.js-container-form-parentesco').removeClass('hide');
		$('.js-container-novo-responsavel').addClass('hide');
		$('.js-mensagem').addClass('hide');
	}
	
	function onFinalizarRequisicao(){
		console.log('requisição finalizada...');
	}
	
	/* FUNCOES PARA REMOÇÃO DOS RESPONSAVEIS*/
	function onErroExcluindoResponsaveis(erro){ 
		console.log('Erro ao remover o(s) responsavel(is): ', erro); 
		NGTICAE.Notificacao.mostrar("Ops!", "Erro ao remover o(s) responsavel(is)", "danger", $('.js-modal-cadastro-responsavel'));
	}

	/* FUNÇÕES PARA REMOVER UM RESPONSAVEL */
	function adicionarEventoRemocao(){
		this.removerResponsavelBtn = $('.js-remover');
		this.removerResponsavelBtn.on('click', onRemoverResponsavel);
	}
	
	function onFecharModal(){
		limparFormulario();
		$('.js-conteiner-pesquisa').removeClass('hide');
		$('.js-container-responsaveis').addClass('hide');
		$('.js-container-form-parentesco').addClass('hide');
		$('.js-container-novo-responsavel').addClass('hide');
		$('.js-mensagem').removeClass('hide');
	}
	
	function limparFormulario(){
		console.log('limpar formulario...');
		$('#formulario_responsavel').each(function(){
			  this.reset();
			});
		$('#responsavel_parentesco').val('');
	}
	
	return GerenciarResponsaveis;
	
}());

$(function(){
	var gerenciarResponsaveis = new NGTICAE.GerenciarResponsaveis();
	gerenciarResponsaveis.iniciar();
});