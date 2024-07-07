
// Medications Fragment Event Listeners

function attachMedEventListeners() {

    const addMed = document.getElementById('add-medication');
    const medNameSearchInput = document.querySelector("input[name='medication-name']");
    const medCategorySelection = document.querySelector('#medication-search select[name="category"]');

    attachMedTableListeners();

    if (addMed) {
        addMed.addEventListener('click', function(event) {
            event.preventDefault();
            modalManager = new ModalManager('medicationModal', 'save-medication', 'medicationForm', 'medicationId', baseUrl);

            const buttonHandler = function (event) {
                modalManager.handleSave(event, 'medication-table', () => modalManager.cleanUp());
            }
            const showHandler = function () {
                modalManager.saveButtonElement.addEventListener('click', buttonHandler);
            }
            const hideHandler = function () {
                modalManager.cleanUp();
            }
            modalManager.attachEventListeners(buttonHandler, showHandler, hideHandler)
            initMedicationForm();
            modalManager.modalInstance.show();
        });
    }

    if (medNameSearchInput) {
        medNameSearchInput.addEventListener('input', medCriteriaChangeHandler);
    }

    if (medCategorySelection) {
        medCategorySelection.addEventListener('change', medCriteriaChangeHandler);
    }

}

function attachMedTableListeners () {
    // will use event delegation for handling actions within the table rows
    const medicationTable = document.getElementById('medication-table');
    if (medicationTable) {
        medicationTable.addEventListener('click', function(event) {

            if (event.target && event.target.classList.contains('med-link')) {
                modalManager = new ModalManager('medicationModal', 'save-medication', 'medicationForm', 'medicationId', baseUrl);
                event.preventDefault();

                const buttonHandler = function (event) {
                    modalManager.handleSave(event, 'medication-table', () => modalManager.cleanUp());
                }
                const showHandler = function () {
                    modalManager.saveButtonElement.addEventListener('click', buttonHandler);
                }
                const hideHandler = function () {
                    modalManager.cleanUp();
                }
                modalManager.attachEventListeners(buttonHandler, showHandler, hideHandler)

                populateMedicationForm(event.target);
                modalManager.modalInstance.show();
            }

            if (event.target && event.target.classList.contains('delete-medication')) {
                event.preventDefault();
                const id = event.target.getAttribute('data-id');
                const url = baseUrl + '/medications/' + id;
                // if the event listeners are lost after reloading the table,
                // pass the attachTableListeners function as the successCallback parameter
                // to reattach the listeners.
                // However, this will not happen because we are using event delegation
                createRequest(url, 'POST', null, 'medication-table', null);
            }
        });
    }

}

// Helper functions
function populateMedicationForm(link) {
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
}

function setSelectedValue(selectElement, value) {
    for (let i = 0; i < selectElement.options.length; i++) {
        if (selectElement.options[i].value === value) {
            selectElement.options[i].selected = true;
            break;
        }
    }
}

function initMedicationForm() {
    // Reset form fields for adding
    document.getElementById('medicationModalLabel').innerText = 'Add Medication';
    document.getElementById('medicationForm').href = 'medications/add';
    document.getElementById('medicationForm').reset();
    document.getElementById('medicationId').value = '';
    document.getElementById('createdBy').value = '';
    document.getElementById('lastUpdateBy').value = '';
    // prepare date fields
    let date = new Date();
    // Format the date as yyyy-MM-dd
    let formattedDate = date.toISOString().split('T')[0];

    // Set the value of the hidden input to the formatted date
    document.getElementById('creationDate').value = formattedDate;
    document.getElementById('lastUpdateDate').value = formattedDate;
}

function medCriteriaChangeHandler() {
    const name = document.querySelector("input[name='medication-name']").value.toLowerCase();
    const categoryCode = document.querySelector('#medication-search select[name="category"]').value.toLowerCase();
    searchMedications(name, categoryCode);
}

function searchMedications(name, categoryCode) {
    const table = document.getElementById('medication-table');
    const rows = table.getElementsByTagName('tr');

    // define loop variables
    let currentMedicationLink;
    let currentName;
    let currentCategory;
    // start from 1 to skip the header row
    for (let i = 1; i < rows.length; i++) {
        currentMedicationLink = rows[i].getElementsByTagName('td')[0].getElementsByTagName('a')[0];
        currentName = currentMedicationLink.getAttribute('data-name');
        currentCategory = currentMedicationLink.getAttribute('data-category');
        if (currentName.toLowerCase().startsWith(name)
            && currentCategory.toLowerCase().startsWith(categoryCode)) {
            rows[i].style.display = '';
        } else {
            rows[i].style.display = 'none';
        }
    }
}


attachMedEventListeners();
