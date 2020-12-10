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

export function onAddAgendaButtonClicked(username) {
    successPageManager.onAddAgendaButtonClicked(username);
}

export function onSearchButtonClicked(username) {
    successPageManager.onSearchButtonClicked(username);
}

export function onAdminBanButtonClicked(username) {
    successPageManager.onAdminBanButtonClicked(username);
}

export function onAdminUnbanButtonClicked(username) {
    successPageManager.onAdminUnbanButtonClicked(username)
}


