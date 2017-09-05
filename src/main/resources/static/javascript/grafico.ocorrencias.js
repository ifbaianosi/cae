var NGTICAE = NGTICAE || {};

NGTICAE.GraficoOcorrenciasPorMes = (function() {
	
	function GraficoOcorrenciasPorMes() {
		this.ctx = $('#graficoOcorrenciasPorMes')[0].getContext('2d');
	}
	
	GraficoOcorrenciasPorMes.prototype.iniciar = function() {
		$.ajax({
			url: 'ocorrencias/totalPorMes',
			method: 'GET', 
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(vendaMes) {
		var meses = [];
		var valores = [];
		vendaMes.forEach(function(obj) {
			meses.unshift(obj.mes);
			valores.unshift(obj.total);
		});
		
		var graficoOcorrenciasPorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: meses,
		    	datasets: [{
		    		label: 'Ocorrências no mês',
		    		backgroundColor: "rgba(26,179,148,0.5)",
	                pointBorderColor: "rgba(26,179,148,1)",
	                pointBackgroundColor: "#fff",
	                data: valores
		    	}]
		    },
		});
	}
	
	return GraficoOcorrenciasPorMes;
	
}());

/*NGTICAE.GraficoOcorrenciaPorSexo = (function() {
	
	function GraficoOcorrenciaPorSexo() {
		this.ctx = $('#graficoOcorrenciasPorSexo')[0].getContext('2d');
	}
	
	GraficoOcorrenciaPorSexo.prototype.iniciar = function() {
		$.ajax({
			url: 'ocorrencias/porSexo',
			method: 'GET', 
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(vendaSexo) {
		var meses = [];
		var masculino = [];
		var feminino = [];
		
		vendaSexo.forEach(function(obj) {
			meses.unshift(obj.mes);
			masculino.unshift(obj.totalNacional);
			feminino.unshift(obj.totalInternacional)
		});
		
		var graficoOcorrenciasPorSexo = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels: meses,
		    	datasets: [{
		    		label: 'Nacional',
		    		backgroundColor: "rgba(220,220,220,0.5)",
	                data: masculino
		    	},
		    	{
		    		label: 'Internacional',
		    		backgroundColor: "rgba(26,179,148,0.5)",
	                data: feminino
		    	}]
		    },
		});
	}
	
	return GraficoOcorrenciaPorSexo;
	
}());*/


$(function() {
	var graficoOcorrenciaPorMes = new NGTICAE.GraficoOcorrenciasPorMes();
	graficoOcorrenciaPorMes.iniciar();
});