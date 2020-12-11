import SuccessPageManager from "./components/SuccessPageManager.js";

/**
 * A .js file to execute all success.html file functions.
 * @type {SuccessPageManager} - an operation manager (it manages DOM elements, GET, POST requests,
 * sent from this page and so on)
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


