fetch('/debit-purchases/getTransactionTypeValues')
    .then(response => response.json())
    .then(data => {
        const labels = data.map(item => item[0]);
        const values = data.map(item => item[1]);

        const ctx = document.getElementById('totalPerTransactionType').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Transaction Type Total´s',
                    data: values,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            callback: function(value, index, values) {
                                return 'R$' + value.toFixed(2);
                            }
                        }
                    }]
                }
            }
        });
    });
