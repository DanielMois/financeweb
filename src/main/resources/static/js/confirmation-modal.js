// Função para mostrar a modal de confirmação
function confirmDelete() {
    var modal = document.getElementById("confirmation-modal");
    modal.style.display = "block";

    // Event listener para fechar a modal ao clicar no botão "X"
    var closeButton = document.getElementsByClassName("close")[0];
    closeButton.onclick = function() {
        modal.style.display = "none";
    }

    // Event listener para fechar a modal ao clicar no botão "Cancel"
    var cancelButton = document.getElementById("cancel-button");
    cancelButton.onclick = function() {
        modal.style.display = "none";
        return false; // Evita a exclusão
    }

    // Event listener para redirecionar ao clicar no botão "Yes"
    var confirmButton = document.getElementById("confirm-button");
    confirmButton.onclick = function() {
        var deleteLink = document.querySelector('.delete-link');
        if (deleteLink) {
            window.location.href = deleteLink.href; // Redireciona para a ação de exclusão
        }
    }

    return false; // Evita a exclusão enquanto a modal está aberta
}
