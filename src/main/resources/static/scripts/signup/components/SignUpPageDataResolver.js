import RegistrationData from "../entity/RegistrationData.js";

export default class SignUpPageDataResolver {

    resolveRegistrationData() {
        return new RegistrationData(
            $('#email').val().toLowerCase(),
            $('#username').val().toLowerCase(),
            $('#password').val()
        );
    }
}