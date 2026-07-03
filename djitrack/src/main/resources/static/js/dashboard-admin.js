/**
 * Dashboard Admin - Djitrack
 * Initialisation des graphiques Chart.js
 */

document.addEventListener('DOMContentLoaded', function () {

    // Graphique consommation mensuelle
    const ctxConso = document.getElementById('chartConsommation');
    if (ctxConso) {
        new Chart(ctxConso.getContext('2d'), {
            type: 'line',
            data: {
                labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin',
                         'Juil', 'Août', 'Sep', 'Oct', 'Nov', 'Déc'],
                datasets: [{
                    label: 'Consommation (m³)',
                    data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    borderColor: 'rgba(54, 162, 235, 1)',
                    backgroundColor: 'rgba(54, 162, 235, 0.15)',
                    fill: true,
                    tension: 0.4,
                    pointRadius: 4
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'top' },
                    title: { display: false }
                },
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    }

    // Graphique répartition clients
    const ctxClients = document.getElementById('chartClients');
    if (ctxClients) {
        new Chart(ctxClients.getContext('2d'), {
            type: 'doughnut',
            data: {
                labels: ['Particuliers', 'Entreprises', 'Administration'],
                datasets: [{
                    data: [0, 0, 0],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.8)',
                        'rgba(75, 192, 192, 0.8)',
                        'rgba(255, 159, 64, 0.8)'
                    ],
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' }
                }
            }
        });
    }
});
