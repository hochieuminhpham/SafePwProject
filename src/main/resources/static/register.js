const submitForm = async (form) => {
    const formData = new FormData(form);

    try {
        const response = await fetch(form.action, {
            method: form.method,
            body: formData
        });

        if (response.status === 200) {
            window.location.href = "/login";
        } else {
            alert("Register fehlgeschlagen");
            window.location.href = "/login.js"
        }
    } catch (error) {
        console.error("Fehler beim Senden:", error);
    }
}

const initForm = () => {
    const form = document.getElementById("loginform");

    if (form) {
        form.addEventListener('submit', (event) => {
            event.preventDefault();
            submitForm(form);
        });
    }
}

document.addEventListener("DOMContentLoaded", () => {
    initForm();
});
