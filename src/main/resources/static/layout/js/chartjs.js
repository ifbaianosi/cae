$(function () {
    new Chart(document.getElementById("line_chart").getContext("2d"), getChartJs('line'));
    new Chart(document.getElementById("bar_chart").getContext("2d"), getChartJs('bar'));
});

function getChartJs(type) {
    var config = null;

    if (type === 'line') {
        config = {
            type: 'line',
            data: {
                labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago"],
                datasets: [{
                    label: "OCORRÊNCIAS",
                    data: [0, 0, 0, 0, 0, 0, 6, 47],
                    borderColor: 'rgba(0, 188, 212, 0.75)',
                    backgroundColor: 'rgba(0, 188, 212, 0.3)',
                    pointBorderColor: 'rgba(0, 188, 212, 0)',
                    pointBackgroundColor: 'rgba(0, 188, 212, 0.9)',
                    pointBorderWidth: 1
                }]
            },
            options: {
                responsive: true,
                legend: false
            }
        }
    }
    else if (type === 'bar') {
        config = {
            type: 'bar',
            data: {
                labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago"],
                datasets: [{
                    label: "SEXO MASCULINO",
                    data: [0, 0, 0, 0, 0, 0, 4, 36],
                    backgroundColor: 'rgba(0, 188, 212, 0.8)'
                }, {
                        label: "SEXO FEMININO",
                        data: [0, 0, 0, 0, 0, 0, 2, 13],
                        backgroundColor: 'rgba(233, 30, 99, 0.8)'
                    }]
            },
            options: {
                responsive: true,
                legend: false
            }
        }
    }
    return config;
}