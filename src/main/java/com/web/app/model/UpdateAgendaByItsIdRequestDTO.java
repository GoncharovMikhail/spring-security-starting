package com.web.app.model;

import lombok.*;

import java.time.DayOfWeek;

/**
 * A wrapper class for updating agenda data.
 *
 * @see com.web.app.rest.AgendaManagementController#updateAgendaById(UpdateAgendaByItsIdRequestDTO)
 */
/* Generates an empty constructor. It is used to deserialize a JSON. */
@NoArgsConstructor
/* Generates a constructor for all fields of a class. */
@AllArgsConstructor
/* Generates getters for all fields of a class. */
@Getter
/* Generates setters for all fields of a class. */
@Setter
public class UpdateAgendaByItsIdRequestDTO {

    private Long id;

    private DayOfWeek day;

    private String time;

    private boolean accessible;

    private String note;
}
