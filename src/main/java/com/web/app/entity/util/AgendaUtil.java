package com.web.app.entity.util;

import com.web.app.entity.AgendaEntity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class AgendaUtil {

    private AgendaUtil() {
        throw new AssertionError(this.getClass() + " can't be instantiated");
    }

    private static final Comparator<AgendaEntity> agendaComparator = (agendaEntity, t1) -> {
        int dayDifference = agendaEntity.getDay().getValue() - t1.getDay().getValue();
        if (dayDifference != 0) {
            return dayDifference;
        } else {
            return agendaEntity.getTime().compareTo(t1.getTime());
        }
    };

    public static List<AgendaEntity> sortAgendas(Collection<AgendaEntity> agendaEntities) {
        return agendaEntities.stream()
                .sorted(agendaComparator)
                .collect(Collectors.toList());
    }
}
