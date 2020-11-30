import SuccessPageConfigurer from "./SuccessPageConfigurer.js";

export default class SuccessPagePostRequestExecutor {

    successPageConfigurer;

    constructor() {
        this.successPageConfigurer = new SuccessPageConfigurer();
    }

    executeUpdateAgendaByItsIdPostRequest(agendaId, editButton) {
        let day;
        let time;
        let note;
        let accessible;

        $(editButton).closest('tr').find('td')
            .each((column, cell) => {

                    if ($(cell).attr('name') === 'day') {
                        day = $(cell).find(':selected').text();
                    }

                    if ($(cell).attr('name') === 'time') {
                        time = $(cell).find('input:first').val();
                    }

                    if ($(cell).attr('name') === 'note') {
                        note = $(cell).text();
                    }

                    if ($(cell).attr('name') === 'accessible') {
                        accessible = $(cell).find(':selected').text().toLowerCase();
                    }
                }
            );

        this.#updatePostRequest(agendaId, day, time, note, accessible);
    }


    #updatePostRequest(agendaId, day, time, note, accessible, editButton) {
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
                success: () => {
                    alert('Successfully updated your agenda');
                    this.successPageConfigurer.afterEditPostRequest(editButton, day, time, note, accessible);
                },
                error: () => {
                    //todo нормальные ерроры
                    // с ифом если юзернейм уже есть - провильно обработать
                    alert('An error occurred');
                    this.successPageConfigurer.afterEditPostRequest(editButton, day, time, note, accessible);
                }
            }
        );
    }

    //todo подумать
    executeDeleteAgendaByItsIdPostRequest(agendaId) {

    }
}