

'use strict';
let baseUrl = window.location.origin + '/pharmacy-ms';

const createRequest = function (url) {
    const httpRequest = new XMLHttpRequest(url);
    httpRequest.addEventListener('readystatechange', (ajax_url) => {
        if (httpRequest.readyState === 4
            && httpRequest.status === 200) {
            const mainContent = document.getElementById('main-content');
            if (mainContent) {
                mainContent.innerHTML = httpRequest.response;
                history.pushState(null, '', url);
            } else {
                console.error('Element with id "main-content" not found.');
            }
        }
    });
    httpRequest.open('GET', url);
    httpRequest.setRequestHeader("Accept", "text/html");
    httpRequest.send();
}


// Load Initial Content Based on URL
document.addEventListener('DOMContentLoaded', function() {
    const url = window.location.href;
    createRequest(url);
});


// Handling backward and forward buttons in browser
window.addEventListener('popstate', function(event) {
    const url = window.location.href;
    createRequest(url);
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
        const url = baseUrl + href;
        console.log(url);
        createRequest(url);
    });
});


// Add event listener for new sale button
const newSaleBtn = document.getElementById('new-sale-btn');
newSaleBtn.addEventListener('click', function(event) {
    event.preventDefault();
    const href = this.getAttribute('href');
    const url = baseUrl + href;
    console.log(url);
    createRequest(url);
});

// Add event listeners for sale ID links
const saleLinks = document.querySelectorAll('.sale-link');
saleLinks.forEach(link => {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        const href = this.getAttribute('href');
        const url = baseUrl + href;
        console.log(url);
        createRequest(url);
    });
});

