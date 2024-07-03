

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

        createRequest(url, 'POST', params.toString(), this.cleanup, 'medication-table');
    }

    cleanUp() {
        // remove the UI
        this.modalInstance.hide();
        this.modalInstance.dispose();
        // Remove event listeners
        this.saveButtonElement.removeEventListener('click', (event) => this.handleSave(event));
        this.modalElement.removeEventListener('shown.bs.modal', this.showHandler);

        // Nullify instance to free up memory
        modalManager = null;
    }
}

// Asynchronous request (ajax)
const createRequest = function (url, method, params, successCallback, updateTargetId) {
    const httpRequest = new XMLHttpRequest(url);
    httpRequest.addEventListener('readystatechange', (ajax_url) => {
        if (httpRequest.readyState === 4
            && httpRequest.status === 200) {
            const target = document.getElementById(updateTargetId);
            if (target) {
                // Call the success callback
                if (successCallback) {
                    successCallback();
                }
                // Use DOMParser to parse the response and safely insert it into the DOM
                const parser = new DOMParser();
                const doc = parser.parseFromString(httpRequest.responseText, 'text/html');
                const newContent = 'main-content'.startsWith(updateTargetId) ? httpRequest.responseText : doc.getElementById(updateTargetId);

                target.innerHTML = newContent.innerHTML;
                if (method === 'GET') {
                    history.pushState(null, '', url);
                }
            } else {
                console.error('Element with target id not found.');
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
    createRequest(url, 'GET', null, null, 'main-content');
});


// Select all links in the navigation bar in the header fragment
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
        createRequest(url, 'GET', null, null, 'main-content');

    });
});

// Add Event listeners for any event inside the replaceable fragment
// here we used event delegation to delegate to the parent div that
// is holding the changing fragment
document.addEventListener('DOMContentLoaded', function () {

    // Use event delegation to listen for any click inside the dynamic changeable fragment
    document.getElementById('main-content').addEventListener('click', function(event) {

        // handling the click on any medication link
        // opening the Medication Form Modal
        // prepopulating data
        if (event.target && event.target.classList.contains('med-link')) {
            modalManager = new ModalManager('medicationModal', 'save-medication', 'medicationForm', baseUrl);
            event.preventDefault();
            populateMedicationForm(event.target, modalManager);
        }

        // add medication button action that shows An Empty Medication Form Modal
        if (event.target && event.target.id === 'add-medication') {
            modalManager = new ModalManager('medicationModal', 'save-medication', 'medicationForm', baseUrl);
            event.preventDefault();
            restMedicationModal(modalManager);
        }

        // delete medication button action in the medication table
        if (event.target && event.target.id === 'delete-button'){
            event.preventDefault();
            console.log(event.target.id);
            const href = event.target.getAttribute('href');
            const url = baseUrl + '/medications/' + event.target.getAttribute('data-id')
            createRequest(url, 'POST', null, null, 'medication-table')
        }


        // sales-fragment events


        // new sale button for navigating to the new sale fragment
        if (event.target && event.target.id === 'new-sale-btn') {
            event.preventDefault();
            const href = event.target.getAttribute('href');
            const url = baseUrl + '/' + href;
            console.log(url);
            createRequest(url, 'GET', null, null, 'main-content');
        }

        // if (event.target && event.target.event.target.classList.contains('sale-id-link')) {
        //     event.preventDefault();
        //     const href = event.target.getAttribute('href');
        //     const url = baseUrl + '/content' + href;
        //     console.log(url);
        //     createRequest(url);
        // }


    });

});

// Add Event listeners for real-time search in the dynamically changed fragment
const medNameSearchInput = document.querySelector("input[name='medication-name']");
const medCategorySelection = document.querySelector('#medication-search select[name="category"]');
if (medNameSearchInput) {
    medNameSearchInput.addEventListener('input', criteriaChangeHandler);
}
if (medCategorySelection) {
    medCategorySelection.addEventListener('change', criteriaChangeHandler);
}


// helper functions
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

function criteriaChangeHandler() {
    const name = document.querySelector("input[name='medication-name']").value.toLowerCase();
    const categoryCode = document.querySelector('#medication-search select[name="category"]').value.toLowerCase();
    searchMedications(name, categoryCode);
}

function searchMedications(name, categoryCode) {
    const table = document.getElementById('medication-table');
    const rows = table.getElementsByTagName('tr');
    // start from 1 to skip the header row
    for (let i = 1; i < rows.length; i++) {
        const currentMedicationLink = rows[i].getElementsByTagName('td')[0].getElementsByTagName('a')[0];
        const currentName = currentMedicationLink.getAttribute('data-name');
        const currentCategory = currentMedicationLink.getAttribute('data-category');
        if (!(currentName.toLowerCase().startsWith(name) && currentCategory.toLowerCase().startsWith(categoryCode))) {
            rows[i].style.display = 'none';
        } else {
            rows[i].style.display = '';
        }
    }
}