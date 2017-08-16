var NGTICAE = NGTICAE || {}; //contrução do namespace

NGTICAE.EditarResponsavel = (function(){
	
	function EditarResponsavel(){//construtor
		this.modal = $('.js-modal-cadastro-responsavel');
		this.editarBtn = $('.js-editar-responsavel');
		this.url = this.editarBtn.data('url');
		
		//CAMPOS DO FORMULARIO
		this.formulario = $('#formulario_responsavel');
		this.grauParentescoSelect = $('#responsavel_parentesco');
		this.nomeResponsavelInput = $('#responsavel_nome');
		this.contatoResponsavelInput = $('#responsavel_contato');
		this.contato2Input = $('#responsavel_contato2');
		this.whatsappResponsavelCheckBox = $('#responsavel_whatsapp');
		this.whatsapp2CheckBox = $('#responsavel_whatsapp2');
		this.email = $('#responsavel_email');
	}
	
	EditarResponsavel.prototype.iniciar = function(){
		this.modal.on('hide.bs.modal', onFecharModal);
		this.modal.on('show.bs.modal', onBuscarDadosDoResponsavel.bind(this));
		this.editarBtn.on('click', onAbrirModal.bind(this));
	}
	
	/* ABRIR MODAL COM OS DADOS */
	function onAbrirModal(){
		this.modal.modal('show');
	}
	
	function onBuscarDadosDoResponsavel(){
		console.log('abrindo modal...');
		console.log('url para buscar responsavel', this.url);
		$.ajax({
			url: this.url,
			method: 'GET',
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroSalvandoResponsavel,
			success: onSucesso.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
	}
	
	function sucesso(responsavel){
		
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
				contato_whatsapp: this.whatsappResponsavelCheckBox.val() == 'on' ? 'true' : 'false',
				contato2: this.contato2Input.val(),
				contato_whatsapp2: this.whatsapp2CheckBox.val() == 'on' ? 'true' : 'false',
				email: this.email.val(),
				uuid: this.uuid
			}),
			beforeSend: onIniciarRequisicao.bind(this),
			error: onErroSalvandoResponsavel,
			success: onAtualizarTabela.bind(this),
			complete: onFinalizarRequisicao.bind(this)
		});
		console.log('--> uuid: '+this.uuid);
	}
	
	
	
	/* FUNCOES PARA ADICIONAR RESPONSAVEL*/
	function onIniciarRequisicao(){
		console.log('Iniciando requisição...');
	}
	
	function onErroSalvandoResponsavel(erro){
		console.log('Erro ao adicionar o responsavel: ', erro);
		NGTICAE.Notificacao.mostrar("Ops!", "Preencha os campos obrigatórios", "danger", $('.js-modal-cadastro-responsavel'));
	}
	
	function onAtualizarTabela(responsaveis) {
		console.log('lista de responsaveis atualizada...');
		console.log(responsaveis);
		console.log('testando o contexto "this"',this.uuid);
		
		var novatabela = this.template(responsaveis);
		this.containerResponsaveis.html(novatabela);
		
		this.removerResponsavelBtn = $('.js-remover');
		this.removerResponsavelBtn.on('click', onRemoverResponsavel.bind(this));
		
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
		
		this.modal.modal('hide');
		this.removerTodosBtn.removeAttr("disabled");
	}
	
	function onFinalizarRequisicao(){
		console.log('requisição finalizada...');
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
	
	return EditarResponsavel;
	
}());

$(function(){
	var editarResponsavel = new NGTICAE.EditarResponsavel();
	editarResponsavel.iniciar();
});