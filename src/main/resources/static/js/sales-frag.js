
// Sales Fragment Event Listeners
function attachSalesListeners() {
    const newSale = document.getElementById('new-sale-btn');
    if (newSale) {
        newSale.addEventListener('click', function(event) {
            event.preventDefault();
            const href = event.target.getAttribute('href');
            const url = baseUrl + '/' + href;
            console.log(url);
            // createRequest(url, 'GET', null, null, 'main-content');
        });
    }
}

attachSalesListeners();

