document.getElementById('billForm').addEventListener('submit', calculateBill);

function addFriend() {
    const table = document.getElementById('friendsTable').getElementsByTagName('tbody')[0];
    const row = table.insertRow();

    const cellName = row.insertCell(0);
    const cellExpenses = row.insertCell(1);
    const cellActions = row.insertCell(2);

    cellName.innerHTML = `<input type="text" name="friendName" placeholder="Name">`;

    cellExpenses.innerHTML = `
        <div class="expenses">
            <div>
                <input type="text" name="expense" placeholder="Expense" oninput="validateMoneyInput(this)">
                <button type="button" onclick="removeExpense(this)">Remove Expense</button>
            </div>
        </div>
        <button type="button" onclick="addExpense(this)">Add Expense</button>
    `;

    cellActions.innerHTML = `<button type="button" onclick="removeFriend(this)">Remove</button>`;
}

function removeFriend(button) {
    const row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

function addExpense(button) {
    const expensesDiv = button.previousElementSibling;
    const expenseDiv = document.createElement('div');
    expenseDiv.innerHTML = `
        <input type="text" name="expense" placeholder="Expense" oninput="validateMoneyInput(this)">
        <button type="button" onclick="removeExpense(this)">Remove Expense</button>
    `;
    expensesDiv.appendChild(expenseDiv);
}

function removeExpense(button) {
    button.parentNode.remove();
}

function addDiscount() {
    const container = document.getElementById('discountsContainer');
    const div = document.createElement('div');
    div.classList.add('discount');
    div.innerHTML = `
        <input type="text" name="discountName" placeholder="Discount Name">
        <input type="text" name="discountValue" placeholder="Discount Value" oninput="validateMoneyInput(this)">
        <select name="discountType">
            <option value="percentage">Percentage (%)</option>
            <option value="value">Value (Fixed)</option>
        </select>
        <button type="button" onclick="removeDiscount(this)">Remove</button>
    `;
    container.appendChild(div);
}

function removeDiscount(button) {
    button.parentNode.remove();
}

function addAddition() {
    const container = document.getElementById('additionsContainer');
    const div = document.createElement('div');
    div.classList.add('addition');
    div.innerHTML = `
        <input type="text" name="additionName" placeholder="Addition Name">
        <input type="text" name="additionValue" placeholder="Addition Value" oninput="validateMoneyInput(this)">
        <select name="additionType">
            <option value="percentage">Percentage (%)</option>
            <option value="value">Value (Fixed)</option>
        </select>
        <button type="button" onclick="removeAddition(this)">Remove</button>
    `;
    container.appendChild(div);
}

function removeAddition(button) {
    button.parentNode.remove();
}

function calculateBill(event) {
    event.preventDefault();

    const friends = Array.from(document.querySelectorAll('#friendsTable tbody tr')).map(row => {
        const friendName = row.querySelector('input[name="friendName"]').value;
        const incomingExpenses = Array.from(row.querySelectorAll('input[name="expense"]')).map(input => parseFloat(input.value.replace(',', '.')) || 0);
        return { friendName, incomingExpenses, totalExpend: 0, totalDiscount: 0, totalAddition: 0, totalPayment: 0 };
    });

    const discounts = Array.from(document.querySelectorAll('#discountsContainer .discount')).map(discount => {
        const name = discount.querySelector('input[name="discountName"]').value;
        const value = parseFloat(discount.querySelector('input[name="discountValue"]').value.replace(',', '.')) || 0;
        const isPercentage = discount.querySelector('select[name="discountType"]').value === 'percentage';
        return { name, value, isPercentage };
    });

    const additions = Array.from(document.querySelectorAll('#additionsContainer .addition')).map(addition => {
        const name = addition.querySelector('input[name="additionName"]').value;
        const value = parseFloat(addition.querySelector('input[name="additionValue"]').value.replace(',', '.')) || 0;
        const isPercentage = addition.querySelector('select[name="additionType"]').value === 'percentage';
        return { name, value, isPercentage };
    });

    const billRequestDTO = {
        bill: {
            friendBills: friends,
            discounts: discounts,
            additions: additions
        }
    };

    fetch('http://localhost:8080/api/bill', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(billRequestDTO)
    })
    .then(response => {
        if (!response.ok) {
            throw response;
        }
        return response.json();
    })
    .then(data => {
        displayBillResults(data);
    })
    .catch(error => {
        if (error.json) {
            error.json().then(jsonError => {
                displayError(jsonError);
            });
        } else {
            displayError({ message: 'Unexpected error occurred.' });
        }
    });
}

function displayError(error) {
    const errorContainer = document.getElementById('responseContainer');
    const errorDetailsSplitted = error.details.split(":");
    errorContainer.innerHTML = `<p>Error: ${error.message}</p>
        <p>Status: ${error.status}</p>
        <p>Details: ${errorDetailsSplitted[errorDetailsSplitted.length - 1]}</p>`;
}


function validateMoneyInput(input) {
    input.value = input.value.replace(/[^0-9,]|(\,(?=.*\,))/g, ''); // Remove caracteres não numéricos, exceto vírgula, e múltiplas vírgulas
    input.value = input.value.replace(/^(-)?(\d{1,})(,[0-9]{0,2})?.*/g, '$1$2$3'); // Limita a dois dígitos após a vírgula e permite apenas um sinal de negativo no início
    input.value = input.value.replace(/(?!^)-/g, '');
}

function displayBillResults(data) {
    const resultContainer = document.getElementById('responseContainer');
    resultContainer.innerHTML = '';

    data.forEach(friendBill => {
        const table = document.createElement('table');
        table.classList.add('friend-bill');

        const headerRow = table.insertRow();
        const friendNameHeader = document.createElement('th');
        friendNameHeader.textContent = 'Friend Name';
        headerRow.appendChild(friendNameHeader);
        const expensesHeader = document.createElement('th');
        expensesHeader.textContent = 'Expenses';
        headerRow.appendChild(expensesHeader);
        const totalExpendHeader = document.createElement('th');
        totalExpendHeader.textContent = 'Total Expend';
        headerRow.appendChild(totalExpendHeader);
        const totalDiscountHeader = document.createElement('th');
        totalDiscountHeader.textContent = 'Total Discount';
        headerRow.appendChild(totalDiscountHeader);
        const totalAdditionHeader = document.createElement('th');
        totalAdditionHeader.textContent = 'Total Addition';
        headerRow.appendChild(totalAdditionHeader);
        const totalPaymentHeader = document.createElement('th');
        totalPaymentHeader.textContent = 'Total Payment';
        headerRow.appendChild(totalPaymentHeader);

        const row = table.insertRow();
        const friendNameCell = row.insertCell();
        friendNameCell.textContent = friendBill.friendName;
        const expensesCell = row.insertCell();
        expensesCell.textContent = friendBill.incomingExpenses.map(expense => formatCurrency(expense)).join(', ');
        const totalExpendCell = row.insertCell();
        totalExpendCell.textContent = formatCurrency(friendBill.totalExpend);
        const totalDiscountCell = row.insertCell();
        totalDiscountCell.textContent = formatCurrency(friendBill.totalDiscount);
        const totalAdditionCell = row.insertCell();
        totalAdditionCell.textContent = formatCurrency(friendBill.totalAddition);
        const totalPaymentCell = row.insertCell();
        totalPaymentCell.textContent = formatCurrency(friendBill.totalPayment);

        resultContainer.appendChild(table);
    });
}

function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
}