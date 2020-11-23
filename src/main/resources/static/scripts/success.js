'use strict';

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

const DAY_SELECT_CLASS_NAME = 'day-select-class-name';

const CLEAVE_INPUT_CLASS_NAME = 'cleave-formatted-input';

const NOTE_INPUT_CLASS_NAME = 'note-input-class-name';

const BOOLEAN_SELECT_CLASS_NAME = 'true-or-false-select';

function configureEditableElement(htmlElement) {

    if ($(htmlElement).attr('name') === 'day') {

        let daySelectList = document.createElement('select');

        DAYS_OF_WEEK.forEach(DAY => {
                let option = document.createElement('option');
                option.value = DAY.toUpperCase();
                option.text = DAY.toUpperCase();
                daySelectList.appendChild(option);
            }
        );

        htmlElement.innerHTML = daySelectList.outerHTML;
        htmlElement.className = DAY_SELECT_CLASS_NAME;
    }

    if ($(htmlElement).attr('name') === 'time') {

        /* Create an <input/> element and set it th the <td/> element */
        let cleaveFormattedInput = document.createElement('input');

        cleaveFormattedInput.className = CLEAVE_INPUT_CLASS_NAME;
        htmlElement.innerHTML = cleaveFormattedInput.outerHTML;

        /* Cleave formats the input.
         * NOTE: we format input AFTER setting created <input/> to html document.
         * Cleave works ONLY AFTER ALL DOM ELEMENTS WERE LOADED.
         *
         * Also, set '.' before className - thus we indicate that we select
         * an element by it's class */
        new Cleave('.' + CLEAVE_INPUT_CLASS_NAME,
            {
                time: true,
                timePattern: ['h', 'm'],
            }
        );

        /* Set default time value */
        const DEFAULT_TIME_INPUT_VALUE = '08:00';
        $('.' + CLEAVE_INPUT_CLASS_NAME).val(DEFAULT_TIME_INPUT_VALUE);
    }

    if ($(htmlElement).attr('name') === 'note') {
        htmlElement.className = NOTE_INPUT_CLASS_NAME;
    }

    if ($(htmlElement).attr('name') === 'accessible') {

        let trueOrFalseSelect = document.createElement('select');

        trueOrFalseSelect.className = BOOLEAN_SELECT_CLASS_NAME;
        BOOLEANS.forEach(boolean => {
                let option = document.createElement('option');
                option.value = boolean.toUpperCase();
                option.text = boolean.toUpperCase();
                trueOrFalseSelect.appendChild(option);
            }
        );

        htmlElement.innerHTML = trueOrFalseSelect.outerHTML;
    }
}

function getRowAsObjectFromTableCorrespondingToAnElement(element) {
    return $(element).closest('tr').find('td');
}

function updateAgendaById(agendaId, editButton) {
    if (editButton.innerText.toLowerCase() === "update") {
        let rowCorrespondingToAnEditButtonAsObject = getRowAsObjectFromTableCorrespondingToAnElement(editButton);

        <!-- Set all elements in the row contenteditable -->
        rowCorrespondingToAnEditButtonAsObject.attr('contenteditable', 'true');

        <!-- 'UPDATE' and 'DELETE' tds shouldn't be contenteditable -->
        $("#update").attr('contenteditable', 'false');
        $("#delete").attr('contenteditable', 'false');

        let rowHtmlElementsAsArray = rowCorrespondingToAnEditButtonAsObject.toArray();

        rowHtmlElementsAsArray
            .filter(element => element.isContentEditable)
            .forEach(htmlElement => {
                    configureEditableElement(htmlElement)
                }
            );

        $(editButton).text('save');
    } else {
        let rowCorrespondingToAnEditButtonAsObject = getRowAsObjectFromTableCorrespondingToAnElement(editButton);

        const DAY_TO_UPDATE = $('.' + DAY_SELECT_CLASS_NAME).find(':selected').text();
        $('.' + DAY_SELECT_CLASS_NAME).html(DAY_TO_UPDATE);

        const TIME_TO_UPDATE = $('.' + CLEAVE_INPUT_CLASS_NAME).val();
        $('.' + CLEAVE_INPUT_CLASS_NAME).html(TIME_TO_UPDATE);

        const AGENDA_TO_UPDATE = $('.' + NOTE_INPUT_CLASS_NAME).text();
        $('.' + NOTE_INPUT_CLASS_NAME).html(AGENDA_TO_UPDATE);

        <!-- ONLY lowercase 'true' or 'false' can be mapped from json to boolean in java -->
        const ACCESSIBLE_TO_UPDATE = $('.' + BOOLEAN_SELECT_CLASS_NAME).find(':selected').text().toLowerCase();
        $('.' + BOOLEAN_SELECT_CLASS_NAME).html(ACCESSIBLE_TO_UPDATE.toUpperCase());

        rowCorrespondingToAnEditButtonAsObject.attr('contenteditable', 'false');

        <!-- Set new text to a button -->
        $(editButton).text("update");

        updateAgendaByIdPostRequest(
            agendaId,
            DAY_TO_UPDATE,
            TIME_TO_UPDATE,
            AGENDA_TO_UPDATE,
            ACCESSIBLE_TO_UPDATE
        );
    }
}

function updateAgendaByIdPostRequest(agendaId, day, time, agenda, accessible) {
    $.post(
        {
            url: '/updateAgendaById',
            contentType: "application/json; charset=UTF-8",
            mimeType: "text/html; charset=UTF-8",
            scriptCharset: "utf-8",
            data: JSON.stringify(
                {
                    "id": agendaId,
                    "day": day,
                    "time": time,
                    "note": agenda,
                    "accessible": accessible
                }
            ),
            success: function () {
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

function addAgenda() {
    $('.agenda-table > tbody:last-child').append(
        '<tr>' +
            '<td name="day"></td>' +
            '<td name = "time"></td>' +
            '<td name="note"></td>' +
            '<td name = "accessible"></td>' +
            //как правильно написать?
            '<td id="save">' +
                '<button onclick="saveAgendaPostRequest(this)">update</button>' +
            '</td>' +
            '<td id = "delete row"><button onclick = "deleteRow()">delete</button></td>' +
        '</tr>'
    );
}

function saveAgendaPostRequest() {}

function deleteRow() {
    let table = $(".agenda-table");
    table.deleteRow(table.rows.length - 1);
}


