$(function(){
	$('.datetimepicker').bootstrapMaterialDatePicker({
        format: 'DD/MM/YYYY HH:mm',
        lang: 'pt',
        clearButton: true,
        weekStart: 0,
        cancelText: 'CANCELAR',
        clearText: 'LIMPAR' 
    });
	
	/*$('.datepicker').bootstrapMaterialDatePicker({
        format: 'DD/MM/YYYY',
        lang: 'pt-BR',
        clearButton: true,
        weekStart: 0,
        cancelText: 'CANCELAR',
        clearText: 'LIMPAR' 
    });*/
	
	$('.datepicker').bootstrapMaterialDatePicker({ weekStart : 0, time: false });
});

