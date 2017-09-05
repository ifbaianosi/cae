var NGTICAE = NGTICAE || {};

/* AUTOCOMPLETE ================================================================================================
*  
*/
NGTICAE.AutocompleteSimples = {
		iniciar: function(input){
			console.log('input: ', input);
			console.log('url: ', input.data('url'));
			
			/*var options = {
					url: input.data('url')
				};*/
			
			var options = {
					url: function(paremetro) {
						return input.data('url') + paremetro;
					}.bind(this),
					/*url: input.data('url'),*/
					minCharNumber: 3,
					requestDelay: 300,
					ajaxSettings: {
						contentType: 'application/json'
					},
					list: {
						maxNumberOfElements: 8,
						match: {
							enabled: true
						}
					}
				};

		 	input.easyAutocomplete(options);
		 	$('.easy-autocomplete').css( "width", "100%" );
			
		}
}

NGTICAE.AutocompleteAluno = {
		iniciar: function(input){
			console.log('input: ', input);
			console.log('url: ', input.data('url'));
			
			/*var options = {
					url: input.data('url')
				};*/
			
			var options = {
					url: function(paremetro) {
						return input.data('url') + paremetro;
					},
					/*url: input.data('url'),*/
					getValue: "nome",
					minCharNumber: 3,
					requestDelay: 300,
					ajaxSettings: {
						contentType: 'application/json'
					},
					list: {
						maxNumberOfElements: 8,
						match: {
							enabled: true
						},
						onChooseEvent: function(){
							var alunoSelecionado = input.getSelectedItemData();
							console.log('aluno selecionado: ', alunoSelecionado);
							$('#aluno').val(alunoSelecionado.codigo);
						}
					}
				};

		 	input.easyAutocomplete(options);
		 	$('.easy-autocomplete').css( "width", "100%" );
			
		},
		onItemSelecionado: function(input){
			var alunoSelecionado = input.getSelectedItemData();
			console.log('aluno selecionado: ', alunoSelecionado);
		}
}

$(function(){
	NGTICAE.AutocompleteSimples.iniciar($('#autocomplete-input'));
	NGTICAE.AutocompleteAluno.iniciar($('#autocomplete-input-aluno'));
});
//==========================================================================================================================
