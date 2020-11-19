package com.web.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

/**
 * A wrapper-class. Used for saving new agenda.
 *
 * @see com.web.app.rest.CommonTemplatesController
 */
/* Generates an empty constructor. It is used to deserialize a JSON. */
@NoArgsConstructor
/* Generates a constructor for all fields of a class. */
@AllArgsConstructor
/* Generates getters for all fields of a class. */
@Getter
/* Generates setters for all fields of a class. */
@Setter
public class SaveAgendaRequestDTO {

    private DayOfWeek dayOfWeek;

    private String time;

    private boolean accessible;

    private String note;
}
