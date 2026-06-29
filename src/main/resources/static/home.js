let currentAccounts = [];
let sortDirections = {
    usernamesort: 'asc',
    pathsort: 'asc'
};

document.addEventListener('DOMContentLoaded', () => {
    const savedPage = parseInt(localStorage.getItem('currentPage')) || 0;
    const savedSize = parseInt(localStorage.getItem('pageSize')) || 10;

    getAccountFromBackend(savedPage, savedSize);
    initSearch();
    initSortButtons();
    initLogout();
});

const initLogout = () => {
    const button = document.getElementById('logout-button');
    if (button){
        button.addEventListener('click', () => {
            logout();
        })
    }
}

const logout = async () => {
    try {
        const response = await fetch(`/logout`);

        if (!response.ok) {
            console.error("Failed to logout");
            return;
        }

        window.location.href = "/login";
    } catch (error) {
        console.error("Network error while trying to logout:", error);
    }
}

const initSortButtons = () => {
    const buttons = document.querySelectorAll('span.sort-icon');
    buttons.forEach(button => {
        sortButtonsEventListener(button)
    })
}

const sortButtonsEventListener = (button) => {
    button.addEventListener('click', () => sortByField(button.id))
}

const searchAccount = (searchText) => {
    const savedSize = parseInt(localStorage.getItem('pageSize')) || 10;
    localStorage.setItem('currentPage', 0);

    console.log("Suche nach:", searchText);

    fetch(`/getAccounts?page=0&size=${savedSize}&search=${encodeURIComponent(searchText)}`)
        .then(res => {
            if (!res.ok || res.status === 204) throw new Error("Kein Inhalt oder nicht autorisiert");
            return res.json();
        })
        .then(data => {
            if (data && data.content) {
                renderTable(data.content);
                currentAccounts = data.content;
                let totalPagesCount = 0;
                let currentPageIndex = 0;
                if (data.page) {
                    totalPagesCount = data.page.totalPages;
                    currentPageIndex = data.page.number;
                }
                renderPagination(totalPagesCount, currentPageIndex, savedSize);
            }
        })
        .catch(err => {
            console.error("Fehler bei der Suche:", err);
            const tbody = document.querySelector('tbody');
            if (tbody) clearTable(tbody);
        });
};


//HIGH FUNCTION ORDER
const initSearch = () => {
    const searchBox = document.getElementById('search-input');
    if (searchBox) {
        const debouncedSearch = debounce((event) => {
            searchAccount(event.target.value.trim());
        }, 300);

        searchBox.addEventListener('keyup', debouncedSearch);
    }
};

const debounce = (callbackFunction, delay = 300) => {
    let timeoutId;

    return (...args) => {
        clearTimeout(timeoutId);

        timeoutId = setTimeout(() => {
            callbackFunction(...args);
        }, delay);
    };
};

const createComparator = (field, direction) => {
    const sortOrder = direction === 'asc' ? 1 : -1;

    return (a, b) => {
        const valueA = field === 'pathsort' ? (a.path || '') : (a.email || a.username || '');
        const valueB = field === 'pathsort' ? (b.path || '') : (b.email || b.username || '');

        return valueA.localeCompare(valueB) * sortOrder;
    };
};

const sortByField = (field) => {
    if (!currentAccounts || currentAccounts.length === 0) return;

    const direction = sortDirections[field] === 'asc' ? 'desc' : 'asc';
    sortDirections[field] = direction;

    // HOF 2
    currentAccounts.sort(createComparator(field, direction));

    renderTable(currentAccounts);
};

const getAccountFromBackend = (page, size) => {
    localStorage.setItem('currentPage', page);
    localStorage.setItem('pageSize', size);

    fetch(`/getAccounts?page=${page}&size=${size}`)
        .then(res => {
            if (!res.ok || res.status === 204) throw new Error("Kein Inhalt oder nicht autorisiert");
            return res.json();
        })
        .then(data => {
            if (data && data.content) {
                currentAccounts = data.content;
                renderTable(data.content);
                let totalPagesCount = 0;
                let currentPageIndex = 0;
                if (data.page) {
                    totalPagesCount = data.page.totalPages;
                    currentPageIndex = data.page.number;
                }
                renderPagination(totalPagesCount, currentPageIndex, size);
            }
        })
        .catch(err => console.error("Fehler beim Abrufen der Daten:", err));
}

const clearTable = (tbody) => {
    tbody.innerHTML = '';
}

const renderTable = (accounts) => {
    const tbody = document.querySelector('tbody');
    clearTable(tbody);

    accounts.forEach(account => {
        const tr = document.createElement('tr');
        tr.id = 'row-' + account.accountUuid;

        const pwSpanId = `pw-${account.accountUuid}`;
        const encodedPw = account.passwordEncoded || '';

        tr.innerHTML = `
            <td>${account.path || ''}</td>
            <td>${account.email || account.username || ''}</td>
            <td>
                <span id="${pwSpanId}">********</span>
                <span class="sort-icon" style="margin-left: 10px; font-size: 1.2em;" onclick="togglePassword('${encodedPw}', '${pwSpanId}')">👁️</span>
            </td>
            <td><a class="action-link" onclick="editAccount('${account.accountUuid}')">edit</a></td>
            <td><a class="action-link" onclick="deleteAccount('${account.accountUuid}')">delete</a></td>
        `;
        tbody.appendChild(tr);
    });
}

const togglePassword = async (encryptedPw, spanId) => {
    const pwElement = document.getElementById(spanId);
    if (!pwElement || !encryptedPw) return;

    if (pwElement.innerText === '********') {
        try {
            const response = await fetch(`/decryptPw?pw=${encodeURIComponent(encryptedPw)}`);

            if (response.status === 401) {
                alert("Session expired or unauthorized. Please log in again.");
                return;
            }

            if (!response.ok) {
                console.error("Failed to decrypt password");
                return;
            }

            const decryptedPw = await response.text();
            pwElement.innerText = decryptedPw;

        } catch (error) {
            console.error("Network error while decrypting password:", error);
        }
    } else {
        pwElement.innerText = '********';
    }
};

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
    fetch(`/deleteAccount?id=${uuid}`)
        .then(res => {
            if (res.status !== 200){
                return;
            }

            hideRow(uuid)
        })
}

const hideRow = (uuid) => {
    const row = document.getElementById(`row-${uuid}`);
    if (row){
        row.style.display = 'none';
    }
}