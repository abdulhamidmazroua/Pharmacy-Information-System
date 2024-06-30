

'use strict';
let baseUrl = window.location.origin + '/pharmacy-ms';

let modalManager;

class ModalManager {
    constructor(modalId, saveButtonId, formId, baseUrl) {
        this.modalElement = document.getElementById(modalId);
        this.saveButtonElement = document.getElementById(saveButtonId);
        this.formElement = document.getElementById(formId);
        this.baseUrl = baseUrl;
        this.modalInstance = new bootstrap.Modal(this.modalElement);

        this.init();
    }

    init() {
        this.modalElement.addEventListener('shown.bs.modal', () => {
            this.saveButtonElement.addEventListener('click', (event) => this.handleSave(event));
        });
    }

    handleSave(event) {
        event.preventDefault();
        const href = this.formElement.href;
        const url = this.baseUrl + '/' + href;
        const formData = new FormData(this.formElement);
        const params = new URLSearchParams(formData);

        createRequest(url, 'POST', params.toString(), () => {
            this.modalInstance.dispose();
            this.cleanUp();
        });
    }

    cleanUp() {
        // Remove event listeners
        this.saveButtonElement.removeEventListener('click', (event) => this.handleSave(event));
        this.modalElement.removeEventListener('shown.bs.modal', this.showHandler);

        // Nullify instance to free up memory
        modalManager = null;
    }
}

// Asynchronous request (ajax)
const createRequest = function (url, method, params, successCallback) {
    const httpRequest = new XMLHttpRequest(url);
    httpRequest.addEventListener('readystatechange', (ajax_url) => {
        if (httpRequest.readyState === 4
            && httpRequest.status === 200) {
            const mainContent = document.getElementById('main-content');
            if (mainContent) {
                // Call the success callback
                if (successCallback) {
                    successCallback();
                }
                mainContent.innerHTML = httpRequest.response;
                if (method === 'GET') {
                    history.pushState(null, '', url);
                }
            } else {
                console.error('Element with id "main-content" not found.');
            }
        }
    });
    httpRequest.open(method, url, true);
    httpRequest.setRequestHeader("Accept", "text/html");
    httpRequest.setRequestHeader('X-Requested-With', 'XMLHttpRequest')
    if (method === 'POST') {
        // Get CSRF token from meta tag
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        httpRequest.setRequestHeader(csrfHeader, csrfToken);
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    }
    httpRequest.send(params);
}

// Handling backward and forward buttons in browser
window.addEventListener('popstate', function(event) {
    const url = window.location.href;
    createRequest(url, 'GET', null, null);
});


// Select all links in the navigation bar
const navLinks = document.querySelectorAll('#navbarNav a');
// Add an event listener to each link
navLinks.forEach(link => {
    link.addEventListener('click', function(event) {
        // Prevent the default action
        event.preventDefault();
        // Get the href attribute of the clicked link
        const href = this.getAttribute('href');
        const url = baseUrl + '/' + href;
        console.log(url);
        createRequest(url, 'GET', null, null);

    });
});

// Add Event listeners for any event inside the replaceable fragment
// here we used event delegation to delegate to the parent div that
// is holding the changing fragment

document.addEventListener('DOMContentLoaded', function () {

    // Use event delegation to listen for clicks on dynamically added medication links
    document.getElementById('main-content').addEventListener('click', function(event) {

        if (event.target && event.target.classList.contains('med-link')) {
            modalManager = new ModalManager('medicationModal', 'save-medication', 'medicationForm', baseUrl);
            event.preventDefault();
            populateMedicationForm(event.target, modalManager);
        }

        if (event.target && event.target.id === 'add-medication') {
            modalManager = new ModalManager('medicationModal', 'save-medication', 'medicationForm', baseUrl);
            event.preventDefault();
            restMedicationModal(modalManager);
        }

        if (event.target && event.target.id === 'delete-button'){
            event.preventDefault();
            console.log(event.target.id);
            const href = event.target.getAttribute('href');
            const url = baseUrl + '/medications/' + event.target.getAttribute('data-id')
            createRequest(url, 'POST', null, null)
        }


        // sales-fragment events

        if (event.target && event.target.id === 'new-sale-btn') {
            event.preventDefault();
            const href = event.target.getAttribute('href');
            const url = baseUrl + '/' + href;
            console.log(url);
            createRequest(url, 'GET', null, null);
        }

        // if (event.target && event.target.event.target.classList.contains('sale-id-link')) {
        //     event.preventDefault();
        //     const href = event.target.getAttribute('href');
        //     const url = baseUrl + '/content' + href;
        //     console.log(url);
        //     createRequest(url);
        // }


    });

    function populateMedicationForm(link, modalManager) {
        // Populate form fields for editing
        document.getElementById('medicationModalLabel').innerText = 'Edit Medication';
        document.getElementById('medicationForm').href = 'medications/update';
        // hidden values
        document.getElementById('medicationId').value = link.getAttribute('data-id') || '';
        document.getElementById('creationDate').value = link.getAttribute('data-creationDate') || '';
        document.getElementById('createdBy').value = link.getAttribute('data-createdBy') || '';
        document.getElementById('lastUpdateDate').value = link.getAttribute('data-lastUpdateDate') || '';
        document.getElementById('lastUpdateBy').value = link.getAttribute('data-lastUpdateBy') || '';


        document.getElementById('medicationName').value = link.getAttribute('data-name') || '';
        document.getElementById('description').value = link.getAttribute('data-description') || '';
        document.getElementById('medicationDosage').value = link.getAttribute('data-dosageStrength') || '';
        document.getElementById('medicationExpiration').value = link.getAttribute('data-expDate') || '';
        document.getElementById('medicationQuantity').value = link.getAttribute('data-quantity') || '';
        document.getElementById('medicationPrice').value = link.getAttribute('data-price') || '';

        // handling selection elements (category, unitOfMeasure)
        setSelectedValue(document.getElementById('medicationCategory'), link.getAttribute("data-category"));
        setSelectedValue(document.getElementById('primaryUom'), link.getAttribute("data-primaryUom"));

        modalManager.modalInstance.show();
    }

    function setSelectedValue(selectElement, value) {
        for (let i = 0; i < selectElement.options.length; i++) {
            if (selectElement.options[i].value === value) {
                selectElement.options[i].selected = true;
                break;
            }
        }
    }

    function restMedicationModal(modalManager) {
        // Reset form fields for adding
        document.getElementById('medicationModalLabel').innerText = 'Add Medication';
        document.getElementById('medicationForm').href = 'medications/add';
        document.getElementById('medicationForm').reset();
        // prepare date fields
        let date = new Date();
        // Format the date as yyyy-MM-dd
        let formattedDate = date.toISOString().split('T')[0];

        // Set the value of the hidden input to the formatted date
        document.getElementById('creationDate').value = formattedDate;
        document.getElementById('lastUpdateDate').value = formattedDate;
        modalManager.modalInstance.show();
    }

});

