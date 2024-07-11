
// Sales Fragment Event Listeners
function attachSalesListeners() {
    const newSale = document.getElementById('new-sale-btn');
    const saleIdSearchInput = document.querySelector("input[name='sale-id']");
    const customerNameSearchInput = document.querySelector("input[name='customer-name']");
    const dateFromInput = document.querySelector("input[name='date-from']");
    const dateToInput = document.querySelector("input[name='date-to']");
    const greaterThanAmountInput = document.querySelector("input[name='greater-than']");
    const resetButton = document.getElementById('reset-button');
    attachSaleTableListeners();

    if (newSale) {
        newSale.addEventListener('click', function(event) {
            event.preventDefault();
            const href = event.target.getAttribute('href');
            const url = baseUrl + '/' + href;
            console.log(url);
            createRequest(url, 'GET', "text/html", null, (responseText) => {
                replaceFragment(responseText, url); // this is for updating the fragment in the layout
                executeFragmentScript('new-sale-frag'); // this is for re-executing the javascript for that fragment
            });
        });
    }

    if (saleIdSearchInput) {
        saleIdSearchInput.addEventListener('input', saleCriteriaChangeHandler);
    }

    if (customerNameSearchInput) {
        customerNameSearchInput.addEventListener('input', saleCriteriaChangeHandler);
    }
    if (dateFromInput) {
        dateFromInput.addEventListener('input', function () {
            dateToInput.min = dateFromInput.value;
            saleCriteriaChangeHandler();
        });
    }
    if (dateToInput) {
        dateToInput.addEventListener('input', saleCriteriaChangeHandler);
    }

    if (greaterThanAmountInput) {
        greaterThanAmountInput.addEventListener('input', saleCriteriaChangeHandler);
    }

    if (resetButton) {
        resetButton.addEventListener('click', function () {
            saleIdSearchInput.value = '';
            customerNameSearchInput.value = '';
            const date = new Date();
            dateFromInput.value = date.toISOString();
            dateToInput.value = date.toISOString();
            greaterThanAmountInput.value = '';
            saleCriteriaChangeHandler();
        });
    }
}


function attachSaleTableListeners() {
    const salesTable = document.getElementById('sales-table');
    if (salesTable) {
        salesTable.addEventListener('click', function (event) {
            if (event.target && event.target.classList.contains('sale-id-link')) {
                event.preventDefault();
                populateSalesForm(event.target);
                modalManager = new ModalManager('saleDetailsModal', null, 'salesForm', 'saleId', baseUrl);
                modalManager.modalInstance.show();
            }

            if (event.target && event.target.id === 'delete-sale') {
                event.preventDefault();
                const id = event.target.getAttribute('data-id');
                const url = baseUrl + '/sales/' + id;
                // if the event listeners are lost after reloading the table,
                // pass the attachTableListeners function as the successCallback parameter
                // to reattach the listeners.
                // However, this will not happen because we are using event delegation
                createRequest(url, 'POST', "text/html", null,(responseText) => {
                    partialUpdateElement(responseText, 'sales-table');
                });
            }
        });
    }
}


// helper functions
function populateSalesForm(link) {

    // hidden values
    document.getElementById('lastUpdateDate').value = link.getAttribute('data-lastUpdateDate') || '';
    document.getElementById('lastUpdateBy').value = link.getAttribute('data-lastUpdateBy') || '';

    // Populate modal fields
    document.getElementById('saleId').value = link.getAttribute('data-id');
    document.getElementById('customerName').value = link.getAttribute('data-customerName');
    document.getElementById('creationDate').value = link.getAttribute('data-creationDate');
    document.getElementById('totalAmount').value = link.getAttribute('data-totalAmount');
    document.getElementById('createdBy').value = link.getAttribute('data-createdBy');


    // Populate sale items table
    const salesItems = JSON.parse(link.getAttribute('data-salesItems')); // Parse JSON string
    const saleLinesTableBody = document.getElementById('saleItemsTableBody');
    saleLinesTableBody.innerHTML = ''; // Clear existing rows
    salesItems.forEach(item => {
        const row = document.createElement('tr');
        const total = item.unitPrice * item.quantity;
        row.innerHTML = `
                    <td><input type="text" class="form-control" value="${item.medication.medicationName}" readonly></td>
                    <td><input type="text" class="form-control" value="${item.quantity}" readonly></td>
                    <td><input type="text" class="form-control" value="${item.unitPrice}" readonly></td>
                    <td><input type="text" class="form-control" value="${total}" readonly></td>
                `;
        saleLinesTableBody.appendChild(row);
    });
}

function saleCriteriaChangeHandler() {
    const saleId = document.querySelector("input[name='sale-id']").value.toLowerCase();
    const customerName = document.querySelector("input[name='customer-name']").value.toLowerCase();
    const dateFrom = document.querySelector("input[name='date-from']").value;
    const dateTo = document.querySelector("input[name='date-to']").value;
    const greaterThanAmount = document.querySelector("input[name='greater-than']").value;
    searchSales(saleId, customerName, dateFrom, dateTo, greaterThanAmount);
}

function searchSales(saleId, customerName, dateFrom, dateTo, greaterThanAmount) {
    const table = document.getElementById('sales-table');
    const rows = table.getElementsByTagName('tr');

    // define loop variables
    let currentSaleId;
    let currentSaleLink;
    let currentCustomerName;
    let currentDate;
    let currentGreaterThanAmount;
    // start from 1 to skip header row
    for (let i = 1; i < rows.length; i++) {
        currentSaleLink = rows[i].getElementsByTagName('td')[0].getElementsByTagName('a')[0];
        currentSaleId = currentSaleLink.getAttribute('data-id');
        currentCustomerName = currentSaleLink.getAttribute('data-customerName');
        currentDate = currentSaleLink.getAttribute('data-creationDate');
        currentGreaterThanAmount = currentSaleLink.getAttribute('data-totalAmount');

        if ((saleId === "" ||  currentSaleId === saleId)
            && (currentCustomerName.toLowerCase().startsWith(customerName))
            && (dateFrom === "" || currentDate >= dateFrom)
            && (dateTo === "" || currentDate <= dateTo)
            && (greaterThanAmount === "" || currentGreaterThanAmount >= greaterThanAmount)){
            rows[i].style.display = '';
        } else {
            rows[i].style.display = 'none';
        }
    }
}
attachSalesListeners();

