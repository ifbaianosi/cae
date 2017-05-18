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
		this.contatoResponsavelInput = $('#responsavel_contato');
		this.whatsappResponsavelCheckBox = $('#responsavel_whatsapp');

		this.adicionarBtn = $('.js-adicionar-responsavel');
		this.urlResponsaveis = this.adicionarBtn.data('url');
		
		//BOTOES DA TABELA DE RESPONSAVEIS
		this.removerResponsavelBtn = null;
	}
	
	GerenciarResponsaveis.prototype.iniciar = function(){
		this.adicionarBtn.on('click', onAdicionarResponsavel.bind(this));
		this.removerTodosBtn.on('click', onRemoverTodosResponsaveis.bind(this));
		this.modal.on('hide.bs.modal', onFecharModal);
	}
	
	/* ADICIONAR */
	function onAdicionarResponsavel(){
		console.log('adicionar responsavel - validar campos!');
		
		$.ajax({
			url: this.urlResponsaveis + '/adicionar', // /responsaveis/adicionar
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				parentesco: this.grauParentescoSelect.val(),
				nome: this.nomeResponsavelInput.val(),
				contato: this.contatoResponsavelInput.val(),
				whatsapp: this.whatsappResponsavelCheckBox.val() == 'on' ? 'true' : 'false',
				uuid: this.uuid
			}),
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroSalvandoResponsavel,
			success: onAtualizarTabela.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
		console.log('--> uuid: '+this.uuid);
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
	
	
	/* FUNCOES PARA ADICIONAR RESPONSAVEL*/
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
	}
	
	function onErroSalvandoResponsavel(erro){
		console.log('Erro ao adicionar o responsavel: ', erro);
	}
	
	function onAtualizarTabela(responsaveis) {
		console.log('lista de responsaveis atualizada...');
		console.log(responsaveis);
		console.log('testando o contexto "this"',this.uuid)
		if(responsaveis.length>0){
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
		}
		this.modal.modal('hide');
	}
	
	function onFinalizarRequisicao(){
		console.log('requisição finalizada...');
	}
	
	/* FUNCOES PARA REMOÇÃO DOS RESPONSAVEIS*/
	function onErroExcluindoResponsaveis(erro){ console.log('Erro ao remover o(s) responsavel(is): ', erro); }

	/* FUNÇÕES PARA REMOVER UM RESPONSAVEL */
	function adicionarEventoRemocao(){
		this.removerResponsavelBtn = $('.js-remover');
		this.removerResponsavelBtn.on('click', onRemoverResponsavel);
	}
	
	function onRemoverResponsavel(evento){
		console.log('remover responsavel...');
		var contato = $(evento.currentTarget).data('contato');
		
		console.log('contato:', contato);
		console.log($('.js-adicionar-responsavel').data('url') + '/remover/' + $('#uuid').val() +'/' + contato);
		
		$.ajax({
			url: $('.js-adicionar-responsavel').data('url') + '/remover/' + $('#uuid').val() +'/' + contato,
			method: 'DELETE',
			success: onAtualizarTabela.bind(this),
			error: onErroExcluindoResponsaveis
		});
	}
	
	function onFecharModal(){
		limparFormulario();
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