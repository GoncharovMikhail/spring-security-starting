import SignUpPagePostRequestsExecutor from "./SignUpPagePostRequestsExecutor.js";
import SignUpPageDataResolver from "./SignUpPageDataResolver.js";
import Verifier from "../../common/Verifier.js";


export default class SignUpPageManager {

    signUpPagePostRequestsExecutor;
    signUpPageDataResolver;

    constructor() {
        this.signUpPagePostRequestsExecutor = new SignUpPagePostRequestsExecutor();
        this.signUpPageDataResolver = new SignUpPageDataResolver();
    }

    onSignUpButtonClicked() {
        const registrationData = this.signUpPageDataResolver
            .resolveRegistrationData();

        try {
            Verifier.verifyRegistrationData(registrationData)
        } catch (e) {
            alert('Wrong input');
            return;
        }

        this.signUpPagePostRequestsExecutor
            .executeRegistrationPostRequest(registrationData);
    }
}