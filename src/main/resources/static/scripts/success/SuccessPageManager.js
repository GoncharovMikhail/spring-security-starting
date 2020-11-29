import SuccessPageConfigurer from "./SuccessPageConfigurer.js";
import SuccessPagePostRequestExecutor from "./SuccessPagePostRequestExecutor.js";

export default class SuccessPageManager {

    successPageConfigurer;
    successPagePostRequestExecutor;

    constructor() {
        this.successPageConfigurer = new SuccessPageConfigurer();
        this.successPagePostRequestExecutor = new SuccessPagePostRequestExecutor();
    }


    onEditButtonClicked(agendaId, editButton) {
        //todo раньше здесь был (editButton.innerText.toLowerCase()
        if (editButton.text().toLowerCase() === "edit") {
            this.successPageConfigurer.configureOnEditButtonClicked(editButton);
        } else {
            this.successPagePostRequestExecutor.executeUpdateAgendaByItsIdPostRequest(agendaId, editButton);
        }
    }

    /*todo подумать про посто-запрос(дто/без дто) + я думаю, конфигурить таблицу надо только если пост
        запрос прошел нормально*/
    onDeleteButtonClicked(agendaId, deleteButton) {
        this.successPagePostRequestExecutor.executeDeleteAgendaByItsIdPostRequest(agendaId);
    }

    onAddAgendaButtonClicked() {
        this.successPageConfigurer.addRowToTable();
    }
}