package com.web.app.rest.util;

import com.web.app.entity.AgendaEntity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An util class to perform various actions to {@link AgendaEntity} instances.
 */
public final class AgendaUtil {

    /**
     * This is an util class - all methods here should be static an this class shouldn't be instantiated.
     */
    private AgendaUtil() {
        throw new AssertionError(this.getClass() +
                " can't be instantiated");
    }

    /**
     * This {@link Comparator<AgendaEntity>} defines, how agendas will be sorted, meaning,
     * which of agenda instances should be done before another.
     */
    private static final Comparator<AgendaEntity> agendaComparator = (firstAgenda, secondAgenda) -> {
        int dayDifference = firstAgenda.getDay().getValue() - secondAgenda.getDay().getValue();
        if (dayDifference != 0) {
            return dayDifference;
        } else {
            return firstAgenda.getTime().compareTo(secondAgenda.getTime());
        }
    };

    /**
     * This method sorts a {@link Collection<AgendaEntity>}, using {@link #agendaComparator}.
     *
     * @param agendaEntities a collection of user's agenda (this method is likely to be used only
     *                       to sort user's agenda).
     * @return sorted {@link List<AgendaEntity>}.
     */
    public static List<AgendaEntity> sortAgendas(Collection<AgendaEntity> agendaEntities) {
        return agendaEntities.stream()
                .sorted(agendaComparator)
                .collect(Collectors.toList());
    }
}
