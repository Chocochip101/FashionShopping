document.addEventListener('DOMContentLoaded', function () {
    const createBrandForm = document.getElementById('createBrandForm');

    if (createBrandForm) {
        createBrandForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const brandName = document.getElementById('brandName').value;
            const data = {
                name: brandName
            };

            fetch('http://localhost:8080/brands', {
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
    const changeBrandNameForm = document.getElementById('changeBrandNameForm');

    if (changeBrandNameForm) {
        changeBrandNameForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const newBrandName = document.getElementById('newBrandName').value;
            const changeBrandId = document.getElementById('changeBrandId').value;

            const data = {
                name: newBrandName
            };

            fetch(`http://localhost:8080/brands/${changeBrandId}`, {
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
    const deleteBrandForm = document.getElementById('deleteBrandForm');

    if (deleteBrandForm) {
        deleteBrandForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const deleteBrandId = document.getElementById('deleteBrandId').value;

            fetch(`http://localhost:8080/brands/${deleteBrandId}`, {
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
