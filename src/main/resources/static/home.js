document.addEventListener('DOMContentLoaded', () => {
    getAccountFromBackend(0, 10);
});

const getAccountFromBackend = (page, size) => {
    fetch(`/getAccounts?page=${page}&size=${size}`)
        .then(res => {
            if (!res.ok || res.status === 204) throw new Error("Kein Inhalt oder nicht autorisiert");
            return res.json();
        })
        .then(data => {
            if (data && data.content) {
                renderTable(data.content);

                let totalPagesCount = 0;
                let currentPageIndex = 0;

                debugger;
                if (data.page) {
                    totalPagesCount = data.page.totalPages;
                    currentPageIndex = data.page.number;
                }

                renderPagination(totalPagesCount, currentPageIndex, size);
            }
        })
        .catch(err => console.error("Fehler beim Abrufen der Daten:", err));
}


const renderTable = (accounts) => {
    const tbody = document.querySelector('tbody');
    tbody.innerHTML = '';

    accounts.forEach(account => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${account.path || ''}</td>
            <td>${account.email || account.username || ''}</td>
            <td>${account.passwordHash || ''}</td>
            <td></td> 
            <td><a class="action-link" onclick="editAccount('${account.accountUuid}')">edit</a></td>
            <td><a class="action-link" onclick="deleteAccount('${account.accountUuid}')">delete</a></td>
        `;
        tbody.appendChild(tr);
    });
}

const renderPagination = (totalPages, currentPage, size) => {
    const paginationDiv = document.querySelector('.pagination');
    paginationDiv.innerHTML = '';

    if (!totalPages || totalPages <= 1) return;

    const prevSpan = document.createElement('span');
    prevSpan.innerHTML = '&laquo;';
    if (currentPage > 0) {
        prevSpan.style.color = '#0056b3';
        prevSpan.style.cursor = 'pointer';
        prevSpan.onclick = () => getAccountFromBackend(currentPage - 1, size);
    } else {
        prevSpan.style.color = '#ccc';
        prevSpan.style.cursor = 'not-allowed';
        prevSpan.onclick = null;
    }
    paginationDiv.appendChild(prevSpan);

    for (let i = 0; i < totalPages; i++) {
        const pageSpan = document.createElement('span');
        pageSpan.textContent = i + 1;

        if (i === currentPage) {
            pageSpan.style.backgroundColor = '#dcdcdc';
            pageSpan.style.color = '#000';
            pageSpan.style.cursor = 'default';
            pageSpan.onclick = null;
        } else {
            pageSpan.style.color = '#0056b3';
            pageSpan.style.cursor = 'pointer';
            pageSpan.onclick = () => getAccountFromBackend(i, size);
        }
        paginationDiv.appendChild(pageSpan);
    }

    const nextSpan = document.createElement('span');
    nextSpan.innerHTML = '&raquo;';
    if (currentPage < totalPages - 1) {
        nextSpan.style.cursor = 'pointer';
        nextSpan.onclick = () => getAccountFromBackend(currentPage + 1, size);
    } else {
        nextSpan.style.color = '#ccc';
        nextSpan.style.cursor = 'not-allowed';
        nextSpan.onclick = null;
    }
    paginationDiv.appendChild(nextSpan);
}

const editAccount = (uuid) => {
    console.log("Edit clicked for UUID:", uuid);
}

const deleteAccount = (uuid) => {
    console.log("Delete clicked for UUID:", uuid);
}
