/**
 * A wrapper class, containing private fields:
 *  email,
 *  username,
 *  password.
 *
 * Either all fields are verified, or Error is thrown
 */
export default class VerifiedRegistrationData {

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
            throw new Error('Incorrect email input')
        }
    }

    validateUsername(username) {
        let usernameRegExp = /^\w{6,32}$/i;
        if (usernameRegExp.test(String(username).toLowerCase())) {
            return username.toLowerCase();
        } else {
            alert('Incorrect username input - username should have 6 to 36 symbols');
            throw new Error('Incorrect username input')
        }
    }

    validatePassword(password) {
        let passwordRegExp = /^\w{6,32}$/;
        if (passwordRegExp.test(password)) {
            return password;
        } else {
            alert('Incorrect password input - username should have 6 to 36 symbols');
            throw new Error('Incorrect password input');
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