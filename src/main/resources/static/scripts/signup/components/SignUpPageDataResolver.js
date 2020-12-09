import VerifiedRegistrationData from "../entity/VerifiedRegistrationData.js";

export default class SignUpPageDataResolver {

    resolveRegistrationData() {
        return new VerifiedRegistrationData(
            $('#email').val(),
            $('#username').val(),
            $('#password').val()
        );
    }
}