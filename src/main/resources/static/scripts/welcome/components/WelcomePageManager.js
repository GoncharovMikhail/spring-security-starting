import CommonGetRequestsExecutor from "../../common/CommonGetRequestsExecutor.js";
import WelcomePageDataResolver from "./WelcomePageDataResolver.js";
import Verifier from "../../common/Verifier.js";
import {ALERT_WRONG_USERNAME_INPUT} from "../../common/Constants.js";

/**
 * A manager for <pre> welcome </pre> page.
 */
export default class WelcomePageManager {

    commonGetRequestsExecutor;
    welcomePageDataResolver;

    constructor() {
        this.welcomePageDataResolver = new WelcomePageDataResolver();
        this.commonGetRequestsExecutor = new CommonGetRequestsExecutor();
    }

    onSearchButtonClicked() {
        /* Resolve username for searching agenda... */
        const username = this.welcomePageDataResolver
            .resolveUsernameForSearchingAgendas();

        /* ...try to verify it ...*/
        try {
            Verifier.verifyUsername(username);
        } catch (e) {
            alert(ALERT_WRONG_USERNAME_INPUT);
            return;
        }

        /* ...if everything is fine,
         * execute a <pre> '/search' </pre>
         * <pre> GET </pre> request. */
        //todo а гет реквест может быть дан и фейл?
        this.commonGetRequestsExecutor
            .executeSearchSomeOnesAgendaGetRequest(username);
    }
}