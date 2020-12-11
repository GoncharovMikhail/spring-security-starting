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

    configureOnEditButtonClicked(editButton) {
        this.#configureRow(editButton);
        /* Set <pre> "save" </pre> text to specified button */
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

            /* Create an <pre> <select...></select> </pre> element */
            let daySelectList = document.createElement('select');

            DAYS_OF_WEEK.forEach(DAY => {
                    /* Create an <pre> <option...></option> </pre> element */
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

                    /* Append this <pre> <option...></option> </pre> element to the
                     * <pre> <select...></select> </pre> element */
                    daySelectList.appendChild(option);
                }
            );


            /* Place this <pre> <select...></select> </pre> element inside the
             * <pre> <td...></td> </pre> element,
             * having name "day" */
            editableElement.innerHTML = daySelectList.outerHTML;
        }

        if ($(editableElement).attr('name') === 'time') {
            /* Read the 'TIME' cell's text, it may be null, if we read it from a new-added row */
            let timeBeforeUpdate = $(editableElement).text();

            /* Create an <pre> <input...></input> </pre> element and set it the
             * <pre> <td...></td> </pre> element */
            let cleaveFormattedInput = document.createElement('input');

            /* Set a class name to this input (thus it can by styled with .css) */
            cleaveFormattedInput.className = CLEAVE_INPUT_CLASS_NAME;
            /* Place this<pre> <input...></input> </pre> element inside the
             * <pre> <td...></td> </pre> element,
             * having name <pre> "time" </pre> */
            editableElement.innerHTML = cleaveFormattedInput.outerHTML;

            /* Cleave formats the input.
             * NOTE: we format input AFTER setting created <pre> <input...></input> </pre>
             * to html document. Cleave works ONLY AFTER ALL DOM ELEMENTS WERE LOADED.
             *
             * Also, set '.' before className - thus we indicate that we select
             * an element by it's class name */
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
            /* Read the <pre> "accessible" </pre> cell's text.
             * It may be null, if we read it from a new-added row */
            let accessibleBeforeUpdate = $(editableElement).text();

            /* Create a <pre> <select...></select> </pre> element */
            let trueOrFalseSelect = document.createElement('select');

            BOOLEANS.forEach(boolean => {
                    /* Create an <pre> <option...></option> </pre> element */
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

                    /* Append this <pre> <option...></option> </pre> element to the
                     * <pre> <select...></select> </pre> element */
                    trueOrFalseSelect.appendChild(option);
                }
            );

            /* Place this <pre> <select...></select> </pre> element inside the
             * <pre> <td...></td> </pre> element, having name <pre> "accessible" </pre> */
            editableElement.innerHTML = trueOrFalseSelect.outerHTML;
        }
    }

    setRowData(specificButton, verifiedData) {
        $(specificButton).closest('tr').find('td')
            .each((column, cell) => {

                    /* Each cell shouldn't be content editable now */
                    $(cell).attr('contenteditable', 'false');

                    /* Set all cell's values */
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

        /* Set <pre> specificButton's </pre> text to <pre> "edit" </pre>*/
        $(specificButton).html('edit');
    }

    addRowToTable() {
        /* Add a new row to the end of the table. */
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

        /* Get the <pre> "delete" </pre> button in the last row (it is the last in this row). */
        let deleteButtonInLastRow = $('.agenda-table > tbody:last-child').find('td:last');

        /* Set <pre> "delete" </pre> button <pre> click </pre> function. */
        deleteButtonInLastRow.click(() => {
                this.deleteRowFromTable(deleteButtonInLastRow);
            }
        );

        /* Get the <pre> "save" </pre> button in the last row
         * (it goes before the <pre> "delete" </pre> button). */
        let saveButtonInLastRow = deleteButtonInLastRow.prev('td');

        this.#configureRow(saveButtonInLastRow);

        /* Returns the <pre> "save" </pre> button in the last row.
         * It will be configured by the <pre> SuccessPageManager </pre>. */
        return saveButtonInLastRow;
    }

    deleteRowFromTable(deleteButton) {
        /* Delete a row, corresponding to the <pre> "delete" </pre> button. */
        $(deleteButton).closest('tr').remove();
    };

    afterSuccessfullySavingNewAgenda(saveButtonInLastRow, justSavedRowDataInTheDatabase) {
        this.setRowData(saveButtonInLastRow, justSavedRowDataInTheDatabase);
        //todo Чето сложно - агенда теперь менеджится сервером (ей присваивается айдишник и тд и тп
        // + у меня таблица должна быть отсортирована - а сортировать джаваскритом как-то не в кайф),
        // да и onDelete должен быть переопределен. Сложна
        location.reload();
        return false;
    }
}

