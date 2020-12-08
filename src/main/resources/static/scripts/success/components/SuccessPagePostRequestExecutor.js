import SuccessPageDataResolver from "./SuccessPageDataResolver.js";

export default class SuccessPagePostRequestExecutor {

    successPageDataResolver = new SuccessPageDataResolver();

    executeUpdateAgendaByItsIdPostRequest(agendaId, editButton) {
        const verifiedRowDataToUpdate = this.successPageDataResolver
            .resolveDataFromContentEditableRowCorrespondingToSpecificButton(editButton);

        return this.#updateAgendaByItsIdPostRequest(
            agendaId,
            verifiedRowDataToUpdate.getDay(),
            verifiedRowDataToUpdate.getTime(),
            verifiedRowDataToUpdate.getNote(),
            verifiedRowDataToUpdate.isAccessible()
        );
    }

    #updateAgendaByItsIdPostRequest(agendaId, day, time, note, accessible) {
        return $.post(
            {
                url: '/updateAgendaByItsId',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: JSON.stringify(
                    {
                        "id": agendaId,
                        "day": day,
                        "time": time,
                        "note": note,
                        "accessible": accessible
                    }
                )
            }
        );
    }

    executeDeleteAgendaByItsIdPostRequest(agendaId) {
        return $.post(
            {
                url: '/deleteAgendaByItsId',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: JSON.stringify(
                    {
                        "agendaId": agendaId
                    }
                )
            }
        );
    }

    executeSaveNewAgendaPostRequest(saveButton, username) {
        const verifiedRowData = this.successPageDataResolver
            .resolveDataFromContentEditableRowCorrespondingToSpecificButton(saveButton);

        alert(username);
        alert(verifiedRowData.getDay(),
            verifiedRowData.getTime(),
            verifiedRowData.getNote(),
            verifiedRowData.isAccessible());

        return this.#saveNewAgendaPostRequest(
            username,
            verifiedRowData.getDay(),
            verifiedRowData.getTime(),
            verifiedRowData.getNote(),
            verifiedRowData.isAccessible()
        );
    }

    #saveNewAgendaPostRequest(username, day, time, note, accessible) {
        return $.post(
            {
                url: '/saveNewAgenda',
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
                )
            }
        );
    }
}