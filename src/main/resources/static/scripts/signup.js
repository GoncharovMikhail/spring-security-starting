$('#submit').click(function () {
        let registration = new Registration(
            $("#email").val(),
            $("#username").val(),
            $("#password").val()
        );

        if (
            registration.getEmail() != null &&
            registration.getUsername() != null &&
            registration.getPassword() != null
        ) {
            registrationPostRequest(
                registration.getEmail(),
                registration.getUsername(),
                registration.getPassword()
            );
        }
    }
);

function registrationPostRequest(email, username, password) {
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
                window.location.href = '/login';
            },
            error: function () {
                alert('An error occurred');
            }
        }
    );
}

/*
 * A wrapper class, containing private fields:
 *  email,
 *  username,
 *  password.
 *
 */
class Registration {

    #email;
    #username;
    #password;

    constructor(email, username, password) {
        this.#email = this.validateEmail(email);
        this.#username = this.validateUsername(username);
        this.#password = this.validatePassword(password);
    }

    validateEmail(email) {
        let emailRegExp = /^(([^<>()\[\]\.,;:\s@"]+(\.[^<>()\[\]\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (emailRegExp.test(String(email).toLowerCase())) {
            return email;
        } else {
            alert('Incorrect email input');
            return null;
        }
    }

    validateUsername(username) {
        let usernameRegExp = /^\w{6,32}$/i;
        if (usernameRegExp.test(String(username).toLowerCase())) {
            return username.toLowerCase();
        } else {
            alert('Incorrect username input - username should have 6 to 36 symbols');
            return null;
        }
    }

    validatePassword(password) {
        let passwordRegExp = /^\w{6,32}$/;
        if (passwordRegExp.test(password)) {
            return password;
        } else {
            alert('Incorrect password input - username should have 6 to 36 symbols');
            return null;
        }
    }

    getEmail() {
        return this.#email;
    }

    getUsername() {
        return this.#username;
    }

    getPassword() {
        return this.#password;
    }
}
