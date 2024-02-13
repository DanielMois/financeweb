document.addEventListener("DOMContentLoaded", function() {
    var message = document.getElementById('message');


    if (message.classList.contains('error-message') || message.classList.contains('success-message')) {
        setTimeout(function() {
            message.style.opacity = '0';
            setTimeout(function() {
                message.style.display = 'none';
            }, 3000);
        }, 3000);
    }
});
