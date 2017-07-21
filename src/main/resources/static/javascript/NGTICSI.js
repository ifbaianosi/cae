var NGTICAE = NGTICAE || {};

/**
 * JQuery Mask Plugin
 */
NGTICAE.MaskPhoneNumber = (function (){
	
	function MaskPhoneNumber(){
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function (){
		var phoneMaskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
			}
			
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(phoneMaskBehavior.apply({}, arguments), options);
		    }
		};
		
		this.inputPhoneNumber.mask(phoneMaskBehavior, options);
	}	
	
	return MaskPhoneNumber;
	
	
}());

/**
 * Plugin Bootstrap-datepicker
 */
NGTICAE.MaskDate = (function(){
	
	function MaskDate(){
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function(){
		/*this.inputDate.mask('00/00/0000');*/
		this.inputDate.datepicker({
			format: "dd/mm/yyyy",
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true,
			clearBtn: true,
			daysOfWeekHighlighted: "0,6",
			todayHighlight: true
		});
	}
	
	return MaskDate;
	
}());

NGTICAE.Security = (function(){
	
	function Security(){
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function(){
		$(document).ajaxSend(function (event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

$(function() {

	var maskPhoneNumber = new NGTICAE.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskDate = new NGTICAE.MaskDate();
	maskDate.enable();
	
	
	var security = new NGTICAE.Security();
	security.enable();
	
	// Enable Bootstrap tooltip
	 $('.js-tooltip').tooltip();
	 
	// Enable Bootstrap popover
	 $('[data-toggle="popover"]').popover();
	 
	 $('.js-info-box-click').click(function(){
		 //alert('teste');
	 });
	
});


