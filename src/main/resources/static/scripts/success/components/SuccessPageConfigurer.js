import SuccessPageDataResolver from "./SuccessPageDataResolver.js";

export const DAYS_OF_WEEK = [
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
];

export const BOOLEANS = [
    'true',
    'false'
];

const CLEAVE_INPUT_CLASS_NAME = 'cleave-formatted-input';

export default class SuccessPageConfigurer {
    successPageDataResolver = new SuccessPageDataResolver();

    configureOnEditButtonClicked(editButton) {
        this.#configureRow(editButton);
        $(editButton).html('save');
    }

    #configureRow(editButton) {
        $(editButton).closest('tr').find('td')
            .each((column, cell) => {
                    /* These cells are "special" and shouldn't be configured */
                    if ($(cell).attr('name') !== 'edit' &&
                        $(cell).attr('name') !== 'delete' &&
                        $(cell).attr('name') !== 'save-new-agenda' &&
                        $(cell).attr('name') !== 'delete-row') {

                        $(cell).attr('contenteditable', 'true');
                        this.#configureEditableElement(cell);
                    }
                }
            );
    }

    #configureEditableElement(editableElement) {
        if ($(editableElement).attr('name') === 'day') {
            /* Read the 'DAY OF WEEK' cell's text, it may be null, if we read it from a new-added row */
            let dayBeforeUpdate = $(editableElement).text();

            /* Create an <select...></select> element */
            let daySelectList = document.createElement('select');

            DAYS_OF_WEEK.forEach(DAY => {
                    /* Create an <option...></option> element */
                    let option = document.createElement('option');
                    /* Set an option's value */
                    option.value = DAY.toUpperCase();
                    /* Set option's text */
                    option.text = DAY.toUpperCase();

                    /* Set default option selected to the one which was set before,
                     * NOTE: if we add a new row to the table, there is no value,
                     * which ws set before, so, we should it's nullability first */
                    if (dayBeforeUpdate !== null) {
                        if (option.text === dayBeforeUpdate) {
                            $(option).attr('selected', 'selected');
                        }
                    }

                    /* Append this <option.../> element th the <select...></select> element */
                    daySelectList.appendChild(option);
                }
            );


            /* Place this <select...></select> element inside the <td...></td> element,
             * having name "day" */
            editableElement.innerHTML = daySelectList.outerHTML;
        }

        if ($(editableElement).attr('name') === 'time') {
            /* Read the 'TIME' cell's text, it may be null, if we read it from a new-added row */
            let timeBeforeUpdate = $(editableElement).text();

            /* Create an <input/> element and set it the <td/> element */
            let cleaveFormattedInput = document.createElement('input');

            /* Set a class name to this input (thus it can by styled with .css) */
            cleaveFormattedInput.className = CLEAVE_INPUT_CLASS_NAME;
            /* Place this <input...></input> element inside the <td...></td> element,
             * having name "time" */
            editableElement.innerHTML = cleaveFormattedInput.outerHTML;

            /* Cleave formats the input.
             * NOTE: we format input AFTER setting created <input/> to html document.
             * Cleave works ONLY AFTER ALL DOM ELEMENTS WERE LOADED.
             *
             * Also, set '.' before className - thus we indicate that we select
             * an element by it's class name */
            //todo а клив первым параметром принимает jQuery селектор что ли? Сам объект он не принимает?
            new Cleave('.' + CLEAVE_INPUT_CLASS_NAME,
                {
                    time: true,
                    timePattern: ['h', 'm'],
                }
            );

            /* Set a time value */
            const DEFAULT_TIME = '09:00';
            if (timeBeforeUpdate !== "") {
                $('.' + CLEAVE_INPUT_CLASS_NAME).val(timeBeforeUpdate);
            } else {
                $('.' + CLEAVE_INPUT_CLASS_NAME).val(DEFAULT_TIME);
            }
        }

        if ($(editableElement).attr('name') === 'accessible') {
            /* Read the 'ACCESSIBLE' cell's text, it may be null, if we read it from a new-added row */
            let accessibleBeforeUpdate = $(editableElement).text();

            /* Create an <select...></select> element */
            let trueOrFalseSelect = document.createElement('select');

            BOOLEANS.forEach(boolean => {
                    /* Create an <option...></option> element */
                    let option = document.createElement('option');
                    /* Set an option's value */
                    option.value = boolean.toUpperCase();
                    /* Set an option's text */
                    option.text = boolean.toUpperCase();

                    /* Set default option selected to the one which was set before,
                     * NOTE: if we add a new row to the table, there is no value,
                     * which ws set before, so, we should it's nullability first */
                    if (accessibleBeforeUpdate !== null) {
                        if (option.text === accessibleBeforeUpdate.toUpperCase()) {
                            $(option).attr('selected', 'selected');
                        }
                    }

                    /* Append this <option.../> element th the <select...></select> element */
                    trueOrFalseSelect.appendChild(option);
                }
            );

            /* Place this <select...></select> element inside the <td...></td> element,
             * having name "accessible" */
            editableElement.innerHTML = trueOrFalseSelect.outerHTML;
        }
    }

    setRowData(specificButton, verifiedData) {
        $(specificButton).closest('tr').find('td')
            .each((column, cell) => {

                    $(cell).attr('contenteditable', 'false');

                    if ($(cell).attr('name') === 'day') {
                        $(cell).html(verifiedData.getDay());
                    }

                    if ($(cell).attr('name') === 'time') {
                        $(cell).html(verifiedData.getTime());
                    }

                    if ($(cell).attr('name') === 'note') {
                        $(cell).html(verifiedData.getNote());
                    }

                    if ($(cell).attr('name') === 'accessible') {
                        $(cell).html(verifiedData.isAccessible());
                    }
                }
            );

        $(specificButton).html('edit');
    }

    addRowToTable() {
        $('.agenda-table > tbody:last-child').append(
            '<tr>' +
            '<td name="day"></td>' +
            '<td name="time"></td>' +
            '<td name="note"></td>' +
            '<td name="accessible"></td>' +
            '<td name="save-new-agenda">' +
            '<button>save</button>' +
            '</td>' +
            '<td name ="delete-row">' +
            '<button>delete</button>' +
            '</td>' +
            '</tr>'
        );

        let deleteButtonInLastRow = $('.agenda-table > tbody:last-child').find('td:last');

        deleteButtonInLastRow.click(() => {
                this.deleteRowFromTable(deleteButtonInLastRow);
            }
        );

        let saveButtonInLastRow = deleteButtonInLastRow.prev('td');

        this.#configureRow(saveButtonInLastRow);

        /* Returns the 'SAVE' button in the last row*/
        return saveButtonInLastRow;
    }

    deleteRowFromTable(deleteButton) {
        $(deleteButton).closest('tr').remove();
    };

    afterSuccessfullySavingNewAgenda(saveButtonInLastRow) {
        this.setRowData(saveButtonInLastRow, this.successPageDataResolver.resolveDataFromContentEditableRowCorrespondingToSpecificButton(saveButtonInLastRow));
        location.reload();
        return false;
    }
}

