(function() {
let totalAmount = 0;
let salesItemsList = [];
function attachNewSaleEventListeners() {
    const barcodeInput = document.getElementById('barcode-input');
    const saleItems = document.getElementById('sale-items');
    const totalAmountSpan = document.getElementById('total-amount');
    const completeSaleBtn = document.getElementById('complete-sale');

    barcodeInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
// TODO: here we should use the bar code (we are using medication name for simulation now)
            const itemCode = barcodeInput.value;

// TODO: we should also implement the logic for handling barcode scanner input device (if there is any)

            const url = baseUrl + '/medications/search';
            const params = new URLSearchParams();
            params.append('medicationName', itemCode);
            createRequest(url, 'GET', 'application/json', params, (responseText) => {
                updateSalesItemsTable(JSON.parse(responseText), barcodeInput, saleItems, totalAmountSpan);
            });
        }
    });

    saleItems.addEventListener('click', function (event) {
        if (event.target.classList.contains('btn-danger')) {
            const row = event.target.closest('tr');
            const oldTotalPrice = parseFloat(row.querySelector('.total-price').innerText);
            const medicationId = row.querySelector('.medication-id').innerText;

            totalAmount -= oldTotalPrice;
            totalAmountSpan.innerText = totalAmount.toFixed(2);

            salesItemsList = salesItemsList.filter(item => item.medicationId != medicationId);
            row.remove();
        }
    });

    completeSaleBtn.addEventListener('click', function (event) {
        const url = baseUrl + '/sales/new-sale/add';

        const params = new URLSearchParams();
        const customerName = document.getElementById('customer-name').value;
        params.append('customerName', customerName);
        params.append('totalAmount', totalAmount);

        salesItemsList.forEach((item, index) => {
            params.append(`salesItems[${index}].medication.id`, item.medicationId);
            params.append(`salesItems[${index}].quantity`, item.quantity);
            params.append(`salesItems[${index}].unitPrice`, item.unitPrice);
        });
        createRequest(url, 'POST', 'text/html', params, (responseText) => {
            alert('Sale completed successfully!');

// TODO: here we should implement the logic for printing the receipt here

// Clear the sales items table and reset the total amount
            saleItems.innerHTML = '';
            totalAmount = 0;
            totalAmountSpan.innerText = totalAmount.toFixed(2);
            salesItemsList = [];
        });
    });
}

function updateSalesItemsTable(medication, barcodeInput, saleItems, totalAmountSpan) {
    const unitPrice = medication.price;
    let quantity = 1;
    let totalPrice = unitPrice * quantity;

    const newRow = document.createElement('tr');
    newRow.innerHTML = `
<td class="medication-id" style="display: none">${medication.id}</td>
<td class="medication-name">${medication.medicationName}</td>
<td class="unit-price">${unitPrice}</td>
<td><input type="number" class="form-control quantity-input" value="${quantity}" min="1" style="width: 60px;"></td>
<td class="total-price">${totalPrice.toFixed(2)}</td>
<td><button class="btn btn-danger btn-sm">Remove</button></td>
`;

    saleItems.appendChild(newRow);

    totalAmount += totalPrice;
    totalAmountSpan.innerText = totalAmount.toFixed(2);

    salesItemsList.push({
        medicationId: medication.id,
        quantity: quantity,
        unitPrice: unitPrice
    });

    barcodeInput.value = '';

    newRow.querySelector('.quantity-input').addEventListener('input', function () {
        const newQuantity = parseInt(this.value);
        const oldTotalPrice = parseFloat(newRow.querySelector('.total-price').innerText);
        const newTotalPrice = newQuantity * unitPrice;

        newRow.querySelector('.total-price').innerText = newTotalPrice.toFixed(2);

        totalAmount = totalAmount - oldTotalPrice + newTotalPrice;
        totalAmountSpan.innerText = totalAmount.toFixed(2);

        const medicationId = newRow.querySelector('.medication-id').innerText;
        const item = salesItemsList.find(item => item.medicationId == medicationId);
        item.quantity = newQuantity;
    });
}

attachNewSaleEventListeners();
})();