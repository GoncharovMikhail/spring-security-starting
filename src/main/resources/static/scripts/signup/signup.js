import SignUpPageManager from "./components/SignUpPageManager.js";

/**
 * A "bridge" file between <pre> page manager </pre> and page itself.
 *
 * @type {SignUpPageManager} - an operation manager. It manages:
 * <ul>
 *     <li>
 *         <pre> DOM </pre> elements
 *     </li>
 *     <li>
 *         <pre> GET </pre> requests on this page
 *     </li>
 *     <li>
 *         <pre> POST </pre> requests on this page
 *     </li>
 * </ul>
 */
const signUpPageManager = new SignUpPageManager();

export function onSignUpButtonClicked() {
    signUpPageManager.onSignUpButtonClicked();
}

