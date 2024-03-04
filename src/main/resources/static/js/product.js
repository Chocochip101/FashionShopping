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
