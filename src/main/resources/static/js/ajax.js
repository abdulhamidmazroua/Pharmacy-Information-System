

'use strict';
let baseUrl = window.location.origin + '/pharmacy-ms';

const createRequest = function (url) {
    const httpRequest = new XMLHttpRequest(url);
    httpRequest.addEventListener('readystatechange', (url) => {
        if (httpRequest.readyState === 4
            && httpRequest.status === 200) {
            const mainContent = document.getElementById('main-content');
            if (mainContent) {
                mainContent.innerHTML = httpRequest.response;
            } else {
                console.error('Element with id "main-content" not found.');
            }
        }
    });
    httpRequest.open('GET', url);
    httpRequest.setRequestHeader("Accept", "text/html");
    httpRequest.send();
}

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