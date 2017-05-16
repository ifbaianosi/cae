var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.GerenciarResponsaveis = (function(){
	
	function GerenciarResponsaveis(){//construtor
		this.tabelaResponsaveis = $('.js-tabela-responsaveis');
		this.tabelaResponsaveisBody = $('.js-tabela-responsaveis-body');
		this.removerTodosBtn = $('.js-remover-todos');
		//CAMPOS DO FORMULARIO
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
			success: onResponsavelSalvo.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
		
	}
	
	/* REMOVER TODOS */
	function onRemoverTodosResponsaveis(){
		console.log('remover todos os responsaveis!');
		$.ajax({
			url: this.urlResponsaveis + '/remover-todos', // /responsaveis/remover-todos
			method: 'DELETE',
			data: {	uuid: this.uuid	},
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroExcluindoTodosResponsaveis,
			success: onResponsavelSalvo.bind(this),
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
	
	function onResponsavelSalvo(responsaveis) {
		console.log('lista de responsaveis atualizada com sucesso...');
		
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
		
		this.tabelaResponsaveisBody.html(tr.length > 0 ? tr : '<tr><td colspan="4">Adicione ao menos um responsavel</td></tr>');
		adicionarEventoRemocao();
	}
	
	function onFinalizarRequisicao(){
		console.log('requisição finalizada...');
	}
	
	/* FUNCOES PARA REMOÇÃO DOS RESPONSAVEIS*/
	function onErroExcluindoTodosResponsaveis(erro){ console.log('Erro ao remover todos os responsaveis: ', erro); }

	/* FUNÇÕES PARA REMOVER UM RESPONSAVEL */
	function adicionarEventoRemocao(){
		this.removerResponsavelBtn = $('.js-remover');
		this.removerResponsavelBtn.on('click', onRemoverResponsavel);
	}
	
	function onRemoverResponsavel(evento){
		console.log('remover responsavel...');
		var contato = $(evento.currentTarget).data('contato');
		
		$.ajax({
			url: $('.js-adicionar-responsavel').data('url') + "/remover/" + $('#uuid').val() +"/" + contato,
			method: 'DELETE',
			success: onResponsavelSalvo.bind(this)
		});
	}
	
	return GerenciarResponsaveis;
	
}());

$(function(){
	var gerenciarResponsaveis = new NGTICAE.GerenciarResponsaveis();
	gerenciarResponsaveis.iniciar();
});