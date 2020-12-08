package com.web.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

/* Generates an empty constructor. It is used to deserialize a JSON. */
@NoArgsConstructor
/* Generates a constructor for all fields of a class. */
@AllArgsConstructor
/* Generates getters for all fields of a class. */
@Getter
/* Generates setters for all fields of a class. */
@Setter
public class SaveNewAgendaRequestDTO {

    private String username;

    private DayOfWeek day;

    private String time;

    private String note;

    private boolean accessible;
}
