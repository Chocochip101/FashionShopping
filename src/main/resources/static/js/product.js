document.addEventListener('DOMContentLoaded', function () {
    const createProductForm = document.getElementById('createProductForm');

    if (createProductForm) {
        createProductForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const createProductBrandId = document.getElementById('createProductBrandId').value;
            const createProductPrice = document.getElementById('createProductPrice').value;
            const createProductCategory = document.getElementById('createProductCategory').value;
            const data = {
                price: createProductPrice,
                category: createProductCategory
            };

            fetch(`http://localhost:8080/brands/${createProductBrandId}/products`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(handleResponse)
                .catch(handleError);
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const changeProductPriceForm = document.getElementById('changeProductPriceForm');

    if (changeProductPriceForm) {
        changeProductPriceForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const changeProductPriceId = document.getElementById('changeProductPriceId').value;
            const changeProductPrice = document.getElementById('changeProductPrice').value;

            const data = {
                price: changeProductPrice
            };

            fetch(`http://localhost:8080/products/${changeProductPriceId}/price`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(handleResponse)
                .catch(handleError);
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const changeProductCategoryForm = document.getElementById('changeProductCategoryForm');

    if (changeProductCategoryForm) {
        changeProductCategoryForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const changeProductCategoryId = document.getElementById('changeProductCategoryId').value;
            const changeProductCategory = document.getElementById('changeProductCategory').value;

            const data = {
                category: changeProductCategory
            };

            fetch(`http://localhost:8080/products/${changeProductCategoryId}/category`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(handleResponse)
                .catch(handleError);
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const deleteProductForm = document.getElementById('deleteProductForm');

    if (deleteProductForm) {
        deleteProductForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const deleteProductId = document.getElementById('deleteProductId').value;

            fetch(`http://localhost:8080/products/${deleteProductId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(handleResponse)
                .catch(handleError);
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const categoryBrandPriceBtn = document.getElementById('categoryBrandPriceBtn');

    if (categoryBrandPriceBtn) {
        categoryBrandPriceBtn.addEventListener('click', function (event) {
            event.preventDefault();

            fetch('http://localhost:8080/categories/min-prices', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        response.json().then(handleError);
                    }
                    return response.json();
                })
                .then(
                    data => {
                        displayCategoryBrandPriceTable(data);
                    }
                )
                .catch(handleError);
        });
    }
});

function displayCategoryBrandPriceTable(jsonData) {
    const resultTable = document.getElementById('resultTable');

    const table = document.createElement('table');
    table.border = '1';

    const thead = document.createElement('thead');
    const headerRow = thead.insertRow();
    for (const key in jsonData["카테고리"][0]) {
        const th = document.createElement('th');
        th.textContent = key;
        headerRow.appendChild(th);
    }
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');
    for (const item of jsonData["카테고리"]) {
        const row = tbody.insertRow();
        for (const key in item) {
            const cell = row.insertCell();
            cell.textContent = item[key];
        }
    }
    table.appendChild(tbody);

    const totalRow = tbody.insertRow();
    const totalCell = totalRow.insertCell();
    totalCell.colSpan = Object.keys(jsonData["카테고리"][0]).length;
    totalCell.textContent = `총액: ${jsonData["총액"]}`;

    resultTable.innerHTML = '';
    resultTable.appendChild(table);
}

document.addEventListener('DOMContentLoaded', function () {
    const categoryMinBrandPriceBtn = document.getElementById('categoryMinBrandPriceBtn');

    if (categoryMinBrandPriceBtn) {
        categoryMinBrandPriceBtn.addEventListener('click', function (event) {
            event.preventDefault();

            fetch('http://localhost:8080/brands/min-price-category', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        response.json().then(handleError);
                    }
                    return response.json();
                })
                .then(
                    data => {
                        displayCategoryBrandMinPriceTable(data);
                    }
                )
                .catch(handleError);
        });
    }
});

function displayCategoryBrandMinPriceTable(jsonData) {
    const resultTable = document.getElementById('resultTable');

    const table = document.createElement('table');
    table.border = '1';

    const thead = document.createElement('thead');
    const headerRow = thead.insertRow();

    // Assuming "브랜드", "카테고리", "가격" are present in each category
    const categorySample = jsonData["최저가"]["카테고리"][0];
    for (const key in categorySample) {
        const th = document.createElement('th');
        th.textContent = key;
        headerRow.appendChild(th);
    }

    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');

    jsonData["최저가"]["카테고리"].forEach(category => {
        const row = tbody.insertRow();
        for (const key in category) {
            const cell = row.insertCell();
            cell.textContent = category[key];
        }
    });

    // Add a row for the "총액"
    const totalRow = tbody.insertRow();
    const totalCell = totalRow.insertCell();
    totalCell.colSpan = Object.keys(categorySample).length;
    totalCell.textContent = `총액: ${jsonData["최저가"]["총액"]}`;

    // Add a row for the "브랜드"
    const brandRow = tbody.insertRow();
    const brandCell = brandRow.insertCell();
    brandCell.colSpan = Object.keys(categorySample).length;
    brandCell.textContent = `브랜드: ${jsonData["최저가"]["브랜드"]}`;

    table.appendChild(tbody);

    resultTable.innerHTML = '';
    resultTable.appendChild(table);
}


function handleResponse(response) {
    if (response.ok) {
        alert("성공했습니다.");
    } else {
        return response.json().then(handleError);
    }
}

function handleError(errorData) {
    const errorMessage = errorData.message;
    const failureValue = errorData.failureValue;
    alert("실패 이유: " + errorMessage + '\n실패 값: ' + failureValue);
}
