

'use strict';
let baseUrl = window.location.origin + '/pharmacy-ms';

let modalManager;

class ModalManager {
    constructor(modalId, saveButtonId, formId, entityId, baseUrl) {
        this.modalElement = document.getElementById(modalId);
        this.saveButtonElement = document.getElementById(saveButtonId);
        this.formElement = document.getElementById(formId);
        this.entityId = entityId;
        this.baseUrl = baseUrl;
        this.modalInstance = new bootstrap.Modal(this.modalElement);

        this.buttonHandler = null;
        this.showHandler = null;
        this.hideHandler = null;
    }

    attachEventListeners(buttonHandler, showHandler, hideHandler) {
        this.buttonHandler = buttonHandler;
        this.showHandler = showHandler;
        this.hideHandler = hideHandler;
        modalManager.modalElement.addEventListener('shown.bs.modal', showHandler);
        modalManager.modalElement.addEventListener('hidden.bs.modal', hideHandler);
    }

    handleSave(successCallback) {
        const href = this.formElement.href;
        const url = this.baseUrl + '/' + href;
        const formData = new FormData(this.formElement);
        const params = new URLSearchParams(formData);
        createRequest(url, 'POST', "text/html", params, (responseText) => {
            successCallback(responseText);
            this.modalInstance.hide();
        });
    }

    cleanUp() {
        // reset the form
        this.formElement.reset();
        document.getElementById(this.entityId).value = '';
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
function createRequest (url, method, accepts, params, successCallback) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.addEventListener('readystatechange', (ajax_url) => {
        if (httpRequest.readyState === 4) {
            handleResponse(url, httpRequest, successCallback);
        }
    });
    // For GET requests, append the params to the URL
    if (method === 'GET' && params) {
        url += '?' + params.toString();
    }
    httpRequest.open(method, url, true);
    httpRequest.setRequestHeader("Accept", accepts);
    httpRequest.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    // For POST requests, add the required headers
    if (method === 'POST') {
        // Get CSRF token from meta tag
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        httpRequest.setRequestHeader(csrfHeader, csrfToken);
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    }
    httpRequest.send(params);
}

// Function for handling the ajax response
function handleResponse(url, httpRequest, successCallback) {
    if (httpRequest.status === 200) {
        if (successCallback) {
            successCallback(httpRequest.responseText);
        }
    } else {
        console.log('Ajax Request did not succeed, url: ' + url);
    }
}

// Function for partial update UI elements
function partialUpdateElement(httpResponseText, targetElementId) {
    const doc = new DOMParser().parseFromString(httpResponseText, 'text/html');
    const newElement = doc.getElementById(targetElementId);
    const targetElement = document.getElementById(targetElementId);
    targetElement.innerHTML = newElement.innerHTML;
}

// Function for changing the whole fragment
function replaceFragment(newFragment, url) {
    const fragment = document.getElementById('main-content');
    fragment.innerHTML = newFragment;
    history.pushState(null, '', url);
}

// Function to dynamically load and execute page-specific scripts
function executeFragmentScript(fragmentId) {
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
        const newFragmentId = window.location.pathname.replace('/pharmacy-ms/', '') + '-frag';
        createRequest(url, 'GET', "text/html", null, (responseText) => {
            replaceFragment(responseText, url); // this is for updating the fragment in the layout
            executeFragmentScript(newFragmentId); // this is for re-executing the javascript for that fragment
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
                    const newFragmentId = href + '-frag';
                    console.log(url);
                    createRequest(url, 'GET', "text/html", null, (responseText) => {
                        replaceFragment(responseText, url); // this is for updating the fragment in the layout
                        executeFragmentScript(newFragmentId); // this is for re-executing the javascript for that fragment
                    });
                });
            });
        }

    // initial load script
    const initialFragmentId = document.getElementById('main-content').getAttribute('data-id');
    executeFragmentScript(initialFragmentId);
})







