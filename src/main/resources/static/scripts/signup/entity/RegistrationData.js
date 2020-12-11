/**
 * A wrapper class, containing private fields:
 * <ul>
 *     <li>
 *         email
 *     </li>
 *     <li>
 *         username
 *     </li>
 *     <li>
 *         password
 *     </li>
 * </ul>
 */
export default class RegistrationData {

    #email;
    #username;
    #password;

    constructor(email, username, password) {
        this.#email = email;
        this.#username = username;
        this.#password = password;
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