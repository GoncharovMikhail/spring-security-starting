function validateEmail(email) {
    const EMAIL_REG_EXP = /^(([^<>()\[\]\.,;:\s@"]+(\.[^<>()\[\]\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return EMAIL_REG_EXP.test(String(email).toLowerCase());
}

function validateUsername(username) {
    //todo проверить
    const USERNAME_REG_EXP = /^.{6,32}$/;
    return USERNAME_REG_EXP.test(username);
}

function validatePassword(password) {
    //todo проверить
    const USERNAME_REG_EXP = /^.{6,32}$/;
    return USERNAME_REG_EXP.test(password);
}

$('#submit').click(function () {
        const email = $("#email").val();
        const username = $("#username").val();
        const password = $("#password").val();

        registrationPostRequest(email, username, password)
    }
);

function registrationPostRequest(email, username, password) {
    if (validateEmail(email) && validateUsername(username) && validatePassword(password)) {
        $.post(
            {
                url: '/registration',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: JSON.stringify(
                    {
                        "email": email,
                        "username": username,
                        "password": password
                    }
                ),
                success: function () {
                    alert('Successfully signed up');
                },
                error: function () {
                    //todo нормальные ерроры
                    // с ифом если юзернейм уже есть - провильно обработать
                    alert('An error occurred');
                }
            }
        );
    }
}
