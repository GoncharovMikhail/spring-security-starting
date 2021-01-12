import SignUpPagePostRequestsExecutor from "./SignUpPagePostRequestsExecutor.js";
import SignUpPageDataResolver from "./SignUpPageDataResolver.js";
import Verifier from "../../common/Verifier.js";

/**
 * A manager for <pre> signup </pre> page.
 */
export default class SignUpPageManager {

    signUpPagePostRequestsExecutor;
    signUpPageDataResolver;

    constructor() {
        this.signUpPagePostRequestsExecutor = new SignUpPagePostRequestsExecutor();
        this.signUpPageDataResolver = new SignUpPageDataResolver();
    }

    onSignUpButtonClicked() {
        /* Resolve username for signing up... */
        const registrationData = this.signUpPageDataResolver
            .resolveRegistrationData();

        /* ...try to verify it ...*/
        try {
            Verifier.verifyRegistrationData(registrationData)
        } catch (e) {
            alert('Wrong input');
            return;
        }

        //todo тут проблема. Как фиксить?

        /* ...if everything is fine,
         * execute a <pre> '/registration' </pre>
         * <pre> POST </pre> request. */
        this.signUpPagePostRequestsExecutor
            .executeRegistrationPostRequest(registrationData)
            .done((response) => {
                    alert(response);
                    window.location.href = '/login';
                }
            )
            .fail((response) => {
                    alert(response.responseText);
                }
            );
    }
}