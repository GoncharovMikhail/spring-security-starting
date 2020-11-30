const DAYS_OF_WEEK = [
    'monday',
    'tuesday',
    'wednesday',
    'thursday',
    'friday',
    'saturday',
    'sunday'
];

const BOOLEANS = [
    'true',
    'false'
];

const CLEAVE_INPUT_CLASS_NAME = 'cleave-formatted-input';

export default class SuccessPageConfigurer {

    constructor() {
    }

    configureOnEditButtonClicked(editButton) {
        this.#configureRow(editButton);
        $(editButton).text('save');
    }

    #configureRow(editButton) {
        $(editButton).closest('tr').find('td')
        //todo переписал function на лямбду и все ок. Каво?
            .each((column, cell) => {
                    /* These cells are "special" and shouldn't be configured */
                    if ($(cell).attr('id') !== 'edit' &&
                        $(cell).attr('id') !== 'delete' &&
                        $(cell).attr('id') !== 'save-new-agenda' &&
                        $(cell).attr('id') !== 'delete-row') {

                        $(cell).attr('contenteditable', 'true');
                        this.#configureEditableElement(cell);
                    }
                }
            );
    }

    #configureEditableElement(editableElement) {
        if ($(editableElement).attr('name') === 'day') {
            /* Create an <select...></select> element */
            let daySelectList = document.createElement('select');

            DAYS_OF_WEEK.forEach(DAY => {
                    /* Create an <option...></option> element */
                    let option = document.createElement('option');
                    /* Set an option's value */
                    option.value = DAY.toUpperCase();
                    /* Set option's text */
                    option.text = DAY.toUpperCase();
                    /* Append this <option.../> element th the <select...></select> element */
                    daySelectList.appendChild(option);
                }
            );

            /* Place this <select...></select> element inside the <td...></td> element,
             * having name "day" */
            editableElement.innerHTML = daySelectList.outerHTML;
        }

        if ($(editableElement).attr('name') === 'time') {

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

            /* create a variable, representing the default time value */
            let defaultTime = '08:00';
            /* Set default time value */
            $('.' + CLEAVE_INPUT_CLASS_NAME).val(defaultTime);
        }

        if ($(editableElement).attr('name') === 'accessible') {
            /* Create an <select...></select> element */
            let trueOrFalseSelect = document.createElement('select');

            BOOLEANS.forEach(boolean => {
                    /* Create an <option...></option> element */
                    let option = document.createElement('option');
                    /* Set an option's value */
                    option.value = boolean.toUpperCase();
                    /* Set an option's text */
                    option.text = boolean.toUpperCase();
                    /* Append this <option.../> element th the <select...></select> element */
                    trueOrFalseSelect.appendChild(option);
                }
            );

            /* Place this <select...></select> element inside the <td...></td> element,
             * having name "accessible" */
            editableElement.innerHTML = trueOrFalseSelect.outerHTML;
        }
    }

    afterEditPostRequest(editButton, day, time, note, accessible) {
        $(editButton).closest('tr').find('td')
            .each((column, cell) => {

                    if ($(cell).attr('name') === 'day') {
                        $(cell).html(day);
                    }

                    if ($(cell).attr('name') === 'time') {
                        $(cell).html(time);
                    }

                    if ($(cell).attr('name') === 'note') {
                        $(cell).html(note);
                    }

                    if ($(cell).attr('name') === 'accessible') {
                        $(cell).html(accessible);
                    }
                }
            );
    }

    addRowToTable() {
        $('.agenda-table > tbody:last-child').append(
            '<tr>' +
            '<td name="day" contenteditable="true"></td>' +
            '<td name="time" contenteditable="true"></td>' +
            '<td name="note" contenteditable="true"></td>' +
            '<td name="accessible" contenteditable="true"></td>' +
            '<td id="save-new-agenda">' +
            '<button onclick="getSaveRowInfo(this)">save</button>' +
            '</td>' +
            '<td id="delete-row">' +
            '<button onclick="deleteRow()">delete row</button>' +
            '</td>' +
            '</tr>'
        );
        //todo правильно ли?
        this.#configureRow($('.agenda-table > tbody:last-child'));
    }


    deleteRow() {
        let table = $(".agenda-table");
        table.deleteRow(table.rows.length - 1);
    }
}