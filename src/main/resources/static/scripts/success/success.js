import SuccessPageManager from "./SuccessPageManager.js";

const successPageManager = new SuccessPageManager();

export default function onEditButtonClicked(agendaEntityId, editButton) {
    alert('qwe');
    successPageManager.onEditButtonClicked(agendaEntityId, editButton);
}

function onDeleteButtonClicked(agendaId, deleteButton) {
    successPageManager.onDeleteButtonClicked(agendaId, deleteButton);
}

function saveAgendaPostRequest(username, day, time, note, accessible) {
    $.post(
        {
            url: '/saveAgenda',
            contentType: "application/json; charset=UTF-8",
            mimeType: "text/html; charset=UTF-8",
            scriptCharset: "utf-8",
            data: JSON.stringify(
                {
                    "username": username,
                    "day": day,
                    "time": time,
                    "note": note,
                    "accessible": accessible
                }
            ),
            success: function () {
                alert('Successfully saved your agenda');
            },
            error: function () {
                //todo нормальные ерроры
                // с ифом если юзернейм уже есть - провильно обработать
                alert('An error occurred');
            }
        }
    );
}

