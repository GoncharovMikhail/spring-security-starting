import VerifiedRowData from "../entity/VerifiedRowRata.js";

export default class SuccessPageDataResolver {

    resolveDataFromContentEditableRowCorrespondingToSpecificButton(specificButton) {
        let day;
        let time;
        let note;
        let accessible;

        $(specificButton).closest('tr').find('td')
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

        return new VerifiedRowData(day, time, note, accessible);
    }

    resolveRowDataFromNonContentEditableRowCorrespondingToSpecificButton(specificButton) {
        let day;
        let time;
        let note;
        let accessible;

        $(specificButton).closest('tr').find('td')
            .each((column, cell) => {

                    if ($(cell).attr('name') === 'day') {
                        day = $(cell).text();
                    }

                    if ($(cell).attr('name') === 'time') {
                        time = $(cell).text();
                    }

                    if ($(cell).attr('name') === 'note') {
                        note = $(cell).text();
                    }

                    if ($(cell).attr('name') === 'accessible') {
                        accessible = $(cell).text().toLowerCase();
                    }
                }
            );

        return new VerifiedRowData(day, time, note, accessible);
    }
}