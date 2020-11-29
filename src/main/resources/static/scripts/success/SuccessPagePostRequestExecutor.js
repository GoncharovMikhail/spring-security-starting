import SuccessPageConfigurer from "./SuccessPageConfigurer.js";

export default class SuccessPagePostRequestExecutor {

    successPageConfigurer;

    constructor() {
        this.successPageConfigurer = new SuccessPageConfigurer();
    }

    /*TODO это костыль - мы сразу меняем значения в таблицеБ не дождавшись ответа постреквеста*/
    executeUpdateAgendaByItsIdPostRequest(agendaId, editButton) {
        let day;
        let time;
        let note;
        let accessible;

        $(editButton).closest('tr').find('td')
            .each(function (column, cell) {

                    if ($(cell).attr('name') === 'day') {
                        day = $(cell).find(':selected').text();
                        this.successPageConfigurer.setValueToElementAndSetEditability(cell, day, 'false');
                    }

                    if ($(cell).attr('name') === 'time') {
                        time = $(cell).find('input:first').val();
                        this.successPageConfigurer.setValueToElementAndSetEditability(cell, time, 'false');
                    }

                    if ($(cell).attr('name') === 'note') {
                        note = $(cell).val();
                        this.successPageConfigurer.setValueToElementAndSetEditability(cell, note, 'false');
                    }

                    if ($(cell).attr('name') === 'accessible') {
                        accessible = $(cell).find(':selected').text().toLowerCase();
                        this.successPageConfigurer.setValueToElementAndSetEditability(cell, accessible, 'false');
                    }
                }
            );

        this.successPageConfigurer.setValueToElementAndSetEditability(editButton, edit, 'false');

        this.#updatePostRequest(agendaId, day, time, note, accessible);
    }


    #updatePostRequest(agendaId, day, time, note, accessible) {
        $.post(
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
                ),
                success: function (editButton) {
                    alert('Successfully updated your agenda');
                },
                error: function () {
                    //todo нормальные ерроры
                    // с ифом если юзернейм уже есть - провильно обработать
                    alert('An error occurred');
                }
            }
        );
    }
    //todo подумать
    executeDeleteAgendaByItsIdPostRequest(agendaId) {

    }
}