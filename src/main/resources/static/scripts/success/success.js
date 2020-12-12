import SuccessPageManager from "./components/SuccessPageManager.js";

/**
 * A "bridge" file between <pre> page manager </pre> and page itself.
 *
 * @type {SuccessPageManager} - an operation manager. It manages:
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
const successPageManager = new SuccessPageManager();

export function onEditButtonClicked(agendaEntityId, editButton) {
    successPageManager.onEditButtonClicked(agendaEntityId, editButton);
}

export function onDeleteButtonClicked(agendaId, deleteButton) {
    successPageManager.onDeleteButtonClicked(agendaId, deleteButton);
}

export function onAddButtonClicked(username) {
    successPageManager.onAddButtonClicked(username);
}

export function onSearchButtonClicked() {
    successPageManager.onSearchButtonClicked();
}

export function onAdminBanButtonClicked() {
    successPageManager.onAdminBanButtonClicked();
}

export function onAdminUnbanButtonClicked() {
    successPageManager.onAdminUnbanButtonClicked()
}


