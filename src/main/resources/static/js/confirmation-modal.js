function confirmDelete(deleteLink) {
    var modal = document.getElementById("confirmation-modal");
    modal.style.display = "block";

    var closeButton = document.getElementsByClassName("close")[0];
    closeButton.onclick = function() {
        modal.style.display = "none";
    }

    var cancelButton = document.getElementById("cancel-button");
    cancelButton.onclick = function() {
        modal.style.display = "none";
        return false; // Evita a exclusão
    }

    var confirmButton = document.getElementById("confirm-button");
    confirmButton.onclick = function() {
        if (deleteLink) {
            window.location.href = deleteLink.href;
        }
    }

    return false;
}
