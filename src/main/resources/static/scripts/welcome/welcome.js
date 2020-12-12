import WelcomePageManager from "./components/WelcomePageManager.js";

/**
 * A "bridge" file between <pre> page manager </pre> and page itself.
 *
 * @type {WelcomePageManager} - an operation manager. It manages:
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
const welcomePageManager = new WelcomePageManager();

export function onSearchButtonClicked() {
    welcomePageManager.onSearchButtonClicked();
}