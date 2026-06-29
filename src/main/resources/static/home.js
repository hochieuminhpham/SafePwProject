let currentAccounts = [];
const sortDirections = {
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
    initFilterInputs();
});

const initFilterInputs = () => {
    const dateForInput = document.getElementById('date-for');
    const dateUntilInput = document.getElementById('date-until');
    const tbody = document.querySelector("tbody");
    if (dateForInput && dateUntilInput){
        dateForInput.addEventListener('change', (e) =>  filter(e, filterByForDate))
        dateUntilInput.addEventListener('change', (e) => filter(e, filterByUntilDate))
    }
}
const filter = (event, callback) => {
    const value = event.target.value;
    if (value){
        callback(value);
    }
}

const filterByForDate = (dateString) => {
    if (!currentAccounts) return;

    const filterDate = new Date(dateString);

    const filteredAccounts = currentAccounts.filter(account => {
        const accountDate = new Date(account.updatedAt);

        return accountDate >= filterDate;
    });

    renderTable(filteredAccounts);
}

const filterByUntilDate = (dateString) => {
    if (!currentAccounts) return;

    const filterDate = new Date(dateString);

    const filteredAccounts = currentAccounts.filter(account => {
        const accountDate = new Date(account.updatedAt);

        return accountDate <= filterDate;
    });

    renderTable(filteredAccounts);
}

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
    button.addEventListener('click', () => handleSortClick(button.id))
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

//PURE FUNCTION
const getSortedAccounts = (accounts, field, direction) => {
    return [...accounts].sort(createComparator(field, direction));
};

const handleSortClick = (field) => {
    if (!currentAccounts || currentAccounts.length === 0) return;

    const direction = sortDirections[field] === 'asc' ? 'desc' : 'asc';
    sortDirections[field] = direction;

    const sortedData = getSortedAccounts(currentAccounts, field, direction);
    renderTable(sortedData);
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
            <td class="last-update-column">${account.updatedAt}</td>
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


// DEKLARATIV
const renderPagination = (totalPages, currentPage, size) => {
    const paginationDiv = document.querySelector('.pagination');

    if (!totalPages || totalPages <= 1) {
        paginationDiv.innerHTML = '';
        return;
    }

    const isFirstPage = currentPage === 0;
    const prevButton = `<span 
        style="color: ${isFirstPage ? '#ccc' : '#0056b3'}; cursor: ${isFirstPage ? 'not-allowed' : 'pointer'};"
        ${isFirstPage ? '' : `onclick="getAccountFromBackend(${currentPage - 1}, ${size})"`}
    >&laquo;</span>`;

    const pageButtons = Array.from({ length: totalPages }, (_, index) => {
        const isCurrentPage = index === currentPage;
        return `<span 
            style="background-color: ${isCurrentPage ? '#dcdcdc' : 'transparent'}; 
                   color: ${isCurrentPage ? '#000' : '#0056b3'}; 
                   cursor: ${isCurrentPage ? 'default' : 'pointer'};"
            ${isCurrentPage ? '' : `onclick="getAccountFromBackend(${index}, ${size})"`}
        >${index + 1}</span>`;
    }).join('');

    const isLastPage = currentPage >= totalPages - 1;
    const nextButton = `<span 
        style="color: ${isLastPage ? '#ccc' : '#0056b3'}; cursor: ${isLastPage ? 'not-allowed' : 'pointer'};"
        ${isLastPage ? '' : `onclick="getAccountFromBackend(${currentPage + 1}, ${size})"`}
    >&raquo;</span>`;

    paginationDiv.innerHTML = `${prevButton}${pageButtons}${nextButton}`;
}

const editAccount = (uuid) => {
    window.location.href = `/account?id=${uuid}`;
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
    const rowToRemove = document.getElementById(`row-${uuid}`);

    if (rowToRemove) {
        rowToRemove.remove();
    }

    if (currentAccounts) {
        currentAccounts = currentAccounts.filter(account => account.accountUuid !== uuid);
    }
}

