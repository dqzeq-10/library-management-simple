// Form validation
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

// Set today as default for date fields when creating new records
document.addEventListener('DOMContentLoaded', function () {
    // For loan date input
    const loanDateInput = document.getElementById('loanDate');
    const membershipDateInput = document.getElementById('membershipDate');

    if (loanDateInput && !loanDateInput.value) {
        const today = new Date().toISOString().split('T')[0];
        loanDateInput.value = today;
    }

    if (membershipDateInput && !membershipDateInput.value) {
        const today = new Date().toISOString().split('T')[0];
        membershipDateInput.value = today;
    }

    // Set default due date to 14 days from today for new loans
    const dueDateInput = document.getElementById('dueDate');
    if (dueDateInput && !dueDateInput.value) {
        const dueDate = new Date();
        dueDate.setDate(dueDate.getDate() + 14); // 14 days from now
        dueDateInput.value = dueDate.toISOString().split('T')[0];
    }

    // Initialize any Bootstrap tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })
});

// Confirm delete actions
function confirmDelete(message) {
    return confirm(message || 'Bạn có chắc chắn muốn xóa mục này?');
}