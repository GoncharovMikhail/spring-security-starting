import SignUpPagePostRequestsExecutor from "./SignUpPagePostRequestsExecutor.js";
import SignUpPageDataResolver from "./SignUpPageDataResolver.js";


export default class SignUpPageManager {

    signUpPagePostRequestsExecutor;
    signUpPageDataResolver;

    constructor() {
        this.signUpPagePostRequestsExecutor = new SignUpPagePostRequestsExecutor();
        this.signUpPageDataResolver = new SignUpPageDataResolver();
    }

    onSignUpButtonClicked() {
        const verifiedRegistrationData = this.signUpPageDataResolver
            .resolveRegistrationData();

        this.signUpPagePostRequestsExecutor
            .executeRegistrationPostRequest(
                verifiedRegistrationData.getEmail(),
                verifiedRegistrationData.getUsername(),
                verifiedRegistrationData.getPassword()
            );
    }
}