import SuccessPageConfigurer from "./SuccessPageConfigurer.js";
import SuccessPagePostRequestExecutor from "./SuccessPagePostRequestExecutor.js";
import SuccessPageDataResolver from "./SuccessPageDataResolver.js";

export default class SuccessPageManager {

    successPageConfigurer;
    successPagePostRequestExecutor;
    successPageDataResolver;

    constructor() {
        this.successPageConfigurer = new SuccessPageConfigurer();
        this.successPagePostRequestExecutor = new SuccessPagePostRequestExecutor();
        this.successPageDataResolver = new SuccessPageDataResolver();
    }

    //todo ТЕСТИТЬ. Я В ШОКЕ
    dataBeforeUpdate;

    onEditButtonClicked(agendaId, editButton) {

        if ($(editButton).text().toLowerCase() === "edit") {
            this.dataBeforeUpdate = this.successPageDataResolver
                .resolveDataFromNonContentEditableRowCorrespondingToSpecificButton(editButton);

            this.successPageConfigurer.configureOnEditButtonClicked(editButton);
        } else {
            let newData;
            try {
                newData = this.successPageDataResolver
                    .resolveDataFromContentEditableRowCorrespondingToSpecificButton(editButton);
            } catch (e) {
                alert('Something inputted wrong - agenda should not be empty and time should be type of HH:MM');
            }
            if (newData == null) {
                this.successPageConfigurer.setRowData(editButton, this.dataBeforeUpdate);
                return;
            }

            if (this.dataBeforeUpdate.equals(newData)) {
                alert('No update needed - you changed nothing!');
                this.successPageConfigurer.setRowData(editButton, newData);
                return;
            }

            this.successPagePostRequestExecutor
                .executeUpdateAgendaByItsIdPostRequest(agendaId, editButton)
                .done((jqXHR) => {
                        //todo че мы тут писали чтоб выводилось сообщения с бека?
                        alert(jqXHR.responseText);
                        this.successPageConfigurer.setRowData(editButton, newData);
                    }
                )
                .fail((jqXHR) => {
                        //todo че мы тут писали чтоб выводилось сообщения с бека?
                        alert(jqXHR.responseText);
                        this.successPageConfigurer.setRowData(editButton, dataBeforeUpdate);
                    }
                )
        }
    }

    onDeleteButtonClicked(agendaId, deleteButton) {
        this.successPagePostRequestExecutor
            .executeDeleteAgendaByItsIdPostRequest(agendaId, deleteButton)
            .done((jqXHR) => {
                    //todo че мы тут писали чтоб выводилось сообщения с бека?
                    alert(jqXHR.responseText);
                    this.successPageConfigurer.deleteRowFromTable(deleteButton);
                }
            )
            .fail((jqXHR) => {
                    alert(jqXHR.responseText);
                }
            )
    }

    onAddAgendaButtonClicked(username) {
        const saveButtonInLastRow = this.successPageConfigurer.addRowToTable();

        saveButtonInLastRow.click(() => {
                this.successPagePostRequestExecutor.executeSaveNewAgendaPostRequest(saveButtonInLastRow, username)
                    .done((jqXHR) => {
                            alert(jqXHR.responseText);
                            this.successPageConfigurer.afterSuccessfullySavingNewAgenda(saveButtonInLastRow);
                        }
                    )
                    .fail((jqXHR) => {
                            alert(jqXHR.responseText);
                        }
                    );
            }
        )
    }
}