var NGTICAE = NGTICAE || {};

//==========================================================================================================================

/* Tabela ================================================================================================
*  
*/
NGTICAE.Tabela = {
		atualizar: function(){
			console.log('Atualizando tabela...');
			console.log('window.location.search.indexOf("ocorrencias")', window.location.search.indexOf('ocorrencias'));
			
			if (window.location.search.indexOf('ocorrencias') == -1) {
				console.log('Atualizar página...');
				window.location.reload(true);
			}else{
				$.ajax({
					url: $('#url_nova_ocorrencia').data('url') + '/aluno/' + $('#codigo_aluno').val(),
					method: 'GET',
					success: this.atualizarTabela
				});
			}
			
		},
		atualizarTabela: function(ocorrencias){
			var containerOcorrencias = $('.js-container-ocorrencias');
			var htmlTabelaOcorrencias = $('#tabelaOcorrenciasPorAluno').html();
			var template = Handlebars.compile(htmlTabelaOcorrencias);
			
			var novatabela = template(ocorrencias);
			containerOcorrencias.html(novatabela);
			console.log('Tabela atualizada...');
			
			NGTICAE.MultiSelecao.iniciar();
			NGTICAE.MultiSelecao.toggleBtn();
			NGTICAE.Modal.iniciar();
		}
}
//==========================================================================================================================