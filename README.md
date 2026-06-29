
# Konzept

<img width="875" height="843" alt="image" src="https://github.com/user-attachments/assets/da37f947-6714-48b5-be3e-7a5c5279bb3e" />

## UseCases
<img width="990" height="909" alt="image" src="https://github.com/user-attachments/assets/9dcd24c5-f895-458c-b101-9bad922a8d3a" />

### 1. Nutzer loggt sich ein
<img width="1079" height="619" alt="image" src="https://github.com/user-attachments/assets/86f8afe0-9e3f-46ed-8bd1-32c2fd6f83d7" />

# Reflexion & Gedanken 183
Mir hat das Projekt viel Spass, weil ich meine volle Kreativität verwenden kann. Ich hatte eigentlich sehr vieles vor, Zum Beispiel die Frontend und Backend kommunizieren sich mit ein Asymmetrische Verschlüsselung (die pub und private keys werden auf dem Datenbank gespeichert), damit die Daten geheim bleiben. So kann ein dritter Abfänger nicht die Daten bekommen. Ausserdem möchte ich die Login- / Registermaske erweitern. Bei der Loginmaske möchte ich eine "PW vergessen" implementieren -> mithilfe von einem Zugangscode geschickt zur Email, kann man seine eigene Identität prüfen und ein neues PW erstellen -> das Mailversand gibt es bei mir im Büro ein Java implementation.

Im Register möchte ich noch verbessern -> funktioniert nicht 100%

Um alles kurz zusammenfassen habe ich generell alles implementiert und bin eigentlich zufrieden mit dem Endstand.

# Reflexion 323

## Immutable Data

anstatt das zu verwenden.

```java
for(let i = 0, i < currentAccounts.size, i++){
  currentAccounts[i].innerHTML = '';
}
```

Verwende ich das. CurrentAccounts bleibt unverändert und erstelle ein neues Array basierend auf die Originaldaten und Filterkondition

```java
const filterByUntilDate = (dateString) => {
    if (!currentAccounts) return;

    const filterDate = new Date(dateString);

    const filteredAccounts = currentAccounts.filter(account => {
        const accountDate = new Date(account.updatedAt);

        return accountDate <= filterDate;
    });

    renderTable(filteredAccounts);
}
```

Die Implementierung schätze ich es sehr, denn eine for Schleife könnte man die Übersicht verlieren.

## Deklarativ programmierung -> das "Was" in Programmieren, nicht das "Wie" (imperativ)

Für das Paging anstatt für jedes Eintrag einen 'td' erstellen, erstelle ich einfach das ganze Tbody basierend auf die Daten der Accounts und füge am Schluss in das Dokument ein.

```java
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
```

Für mich ist die Lösung sehr praktisch, denn bei vielen Appends könnte es zur eine Flüchtigkeitsfehler vorkommen.
Ich beschreibe was angezeigt werden soll (einen String aus HTML-Elementen), anstatt dem Browser Schritt für Schritt zu diktieren, wie er die Elemente konstruieren soll. Dies ist deutlich effizienter und lesbarer.

## HOF

Im Betireb musste ich eine Suchfunktion implementieren für eine Kunde, jedoch gab es viele Daten in der produktiven System und es gab ein Run Time Error wenn man zu schnell tippt.
Deswegen habe ich eine HOF Timeout Funktion erstellt. Das verbessert die Leistung, wenn man mit einer grossen Datenmenge arbeitet.

```java
const debounce = (callbackFunction, delay = 300) => {
    let timeoutId;
    return (...args) => {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            callbackFunction(...args);
        }, delay);
    };
};

const debouncedSearch = debounce((event) => {
    searchAccount(event.target.value.trim());
}, 300);
```

Die Verzögerung ist komplett von der Suchlogik getrennt. Lambda-Ausdrücke () => {} machen den Code hier extrem kompakt und bewahren gleichzeitig den korrekten Effekt, was bei herkömmlichen Funktionen oft zu Problemen führt. -> Das ist ein Vorteil

## Pure Functions -> es gibt keine Seiteeffekte

Ich habe sehr gut geachtet beim sortieren, dass es keine Seiteeffekte. Bei B2B Websiten könnte es zur riesigen Problemen führen.

```java

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
```

Mit einer Spread Operator kann ich die Accounts unverändert sortieren -> Gleiche Eingabe liefert immer exakt die gleiche Ausgabe.


TLDR Ich finde meiner Meinung sehr sinnvoll, wenn man Clean Codes schreibt. Das funktionelle Programmieren hilft es dabei. Ich werde ich als Entwickler mehr über Funktionelles Programmieren vertiefen.




