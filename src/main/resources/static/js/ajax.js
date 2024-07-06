

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

        this.buttonHandler = null;
        this.showHandler = null;
        this.hideHandler = null;

        // this.init();
    }

    attachEventListeners(buttonHandler, showHandler, hideHandler) {
        this.buttonHandler = buttonHandler;
        this.showHandler = showHandler;
        this.hideHandler = hideHandler;
        modalManager.modalElement.addEventListener('shown.bs.modal', showHandler);
        modalManager.modalElement.addEventListener('hidden.bs.modal', hideHandler);
    }

    handleSave(event, targetElementId, successCallback) {
        event.preventDefault();
        const href = this.formElement.href;
        const url = this.baseUrl + '/' + href;
        const formData = new FormData(this.formElement);
        const params = new URLSearchParams(formData);
        createRequest(url, 'POST', params, targetElementId, () => {
            this.modalInstance.hide();
        });
    }

    cleanUp() {
        // reset the form
        this.formElement.reset();
        document.getElementById('medicationId').value = '';
        document.getElementById('createdBy').value = '';
        document.getElementById('lastUpdateBy').value = '';

        // Remove event listeners
        if (this.buttonHandler) {
            this.saveButtonElement.removeEventListener('click', this.buttonHandler);
        }
        if (this.showHandler) {
            this.modalElement.removeEventListener('shown.bs.modal', this.showHandler);
        }
        if (this.hideHandler) {
            this.modalElement.removeEventListener('hidden.bs.modal', this.hideHandler);
        }

        // Reset body scroll state
        // document.body.classList.remove('modal-open');
        // document.body.style.paddingRight = '';


        // Nullify instance to free up memory
        modalManager = null;
    }
}

// Asynchronous request (ajax)
// successCallback:
// is added to execute a function after the response is successfully returned
// targetElementId:
// 1- If the method == POST then it specifies the element that will be affected
// in response to the data change (ex: table will be reloaded after add/update/delete)
// 2- If the method == GET then it is null since it is not a partial update
// and the whole fragment will be replaced
function createRequest (url, method, params, targetElementId, successCallback) {
    const httpRequest = new XMLHttpRequest(url);
    httpRequest.addEventListener('readystatechange', (ajax_url) => {
        if (httpRequest.readyState === 4) {
            if (httpRequest.status === 200) {
                let target;
                let newContent;
                // This is based on the assumption that
                // only POST ajax requests will be for
                // partial target update (like updating the table)
                // otherwise (for GET requests), we will update the whole fragment
                // by updating the main-content
                if (method === 'POST') {
                    const doc = new DOMParser().parseFromString(httpRequest.responseText, 'text/html');
                    newContent = doc.getElementById(targetElementId);
                    target = document.getElementById(targetElementId);
                    target.innerHTML = newContent.innerHTML;
                } else {
                    newContent = httpRequest.responseText;
                    target = document.getElementById('main-content');
                    target.innerHTML = newContent;
                    history.pushState(null, '', url);
                }
                if (successCallback) successCallback();
            } else {
                console.log('Ajax Request did not succeed, url: ' + url);
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

// Function to dynamically load and execute page-specific scripts
// only if they are not already executed


function executePageSpecificScript(fragmentId) {
    if ( fragmentId) {
        const script = document.createElement('script');
        script.src = `/pharmacy-ms/js/${fragmentId}.js`;
        script.onload = () => {
            // Clean up by removing the script tag after it has been executed
            script.remove();
        }
        document.body.appendChild(script);
        console.log(document);
    }
}


// Main Layout Event Listeners
document.addEventListener('DOMContentLoaded', function () {
    // Handling backward and forward buttons in browser
    window.addEventListener('popstate', function(event) {
        const url = window.location.href;
        const targetElementId = window.location.pathname.replace('/pharmacy-ms/', '') + '-frag';
        createRequest(url, 'GET', null, null, function () {
            executePageSpecificScript(targetElementId); // here the targetElementId will be the new fragment id
        });
    });

    // Select all links in the navigation bar in the header fragment
    const navLinks = document.querySelectorAll('#navbarNav a');
    // Add an event listener to each link
    if (navLinks) {
            navLinks.forEach(link => {
                link.addEventListener('click', function(event) {
                    // Prevent the default action
                    event.preventDefault();
                    // Get the href attribute of the clicked link
                    const href = this.getAttribute('href');
                    const url = baseUrl + '/' + href;
                    const targetFragmentId = href + '-frag';
                    console.log(url);
                    createRequest(url, 'GET', null, null, function () {
                        executePageSpecificScript(targetFragmentId); // here the targetElementId will be the new fragment id
                    });
                });
            });
        }

    // initial load script
    const initialFragmentId = document.getElementById('main-content').getAttribute('data-id');
    executePageSpecificScript(initialFragmentId);
})







