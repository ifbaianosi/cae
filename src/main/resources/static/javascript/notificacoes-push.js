var NGTICAE = NGTICAE || {};

/* Notificações ================================================================================================
*  
*/
NGTICAE.Notificacao = {
		mostrar: function mostrarNotificacao(title, message, type, elementoPai){
			$.notify({
				title: '<strong>' + title + '</strong>',
				message: message,
				target: '_top'
			},{
				element: elementoPai,
				type: type,
				placement: {
					from: "top",
					align: "center"
				},
				offset: {
					x: 50,
					y: 76
				}
			});
		}
}
//==========================================================================================================================