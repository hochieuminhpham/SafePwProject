document.addEventListener('DOMContentLoaded', async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const accountId = urlParams.get('id');
    const isEditMode = Boolean(accountId);

    document.getElementById('form-title').innerText = isEditMode ? 'Edit Account' : 'Add New Account';
    document.getElementById('btn-submit').innerText = isEditMode ? 'Save Changes' : 'Add to Safe';

    if (isEditMode) {
        await populateFormForEdit(accountId);
    }

    const form = document.getElementById('account-form');
    form.addEventListener('submit', createSubmitHandler(isEditMode, accountId));
});

const createSubmitHandler = (isEdit, accountId) => {


    const apiEndpoint = isEdit ? `/editAccount?id=${accountId}` : `/addAccount`;
    const httpMethod = isEdit ? 'PUT' : 'POST';

    // HOF 3
    return async (event) => {
        event.preventDefault();

        const payload = {
            path: document.getElementById('path').value.trim(),
            username: document.getElementById('username').value.trim(),
            password: document.getElementById('password').value
        };

        try {
            const response = await fetch(apiEndpoint, {
                method: httpMethod,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                window.location.href = '/';
            }

        } catch (error) {
            console.error('Error saving account:', error);
            alert('A network error occurred.');
        }
    };
};

const populateFormForEdit = async (accountUuid) => {
    try {
        const response = await fetch(`/getAccount?id=${accountUuid}`);
        if (!response.ok) throw new Error("Could not fetch account data");

        const data = await response.json();

        document.getElementById('path').value = data.path || '';
        document.getElementById('username').value = data.username || data.email || '';

        // erklären wieso kein pw

    } catch (error) {
        console.error("Error populating form:", error);
    }
};