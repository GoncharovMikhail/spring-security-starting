package com.web.app.service.impl;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.SaveNewAgendaRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.repository.AgendaRepository;
import com.web.app.service.AgendaService;
import com.web.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * A service for managing agendas.
 *
 * @see com.web.app.service.AgendaService
 */
@Service
public class AgendaServiceImpl implements AgendaService {

    /**
     * Components, needed for executing requests, interconnect to agenda.
     */
    private final AgendaRepository agendaRepository;

    private final UsersService usersService;

    /**
     * Autowire all this components.
     * <p>
     * An instance of a class, annotated with
     * <ul>
     *     <li>
     *         {@link Service},
     *     </li>
     *     <li>
     *         {@link org.springframework.stereotype.Repository},
     *     </li>
     * </ul>
     * or any other annotation,
     * which contains a {@link org.springframework.stereotype.Component} inside itself,
     * will be created and stored in so-called "IOC-container", meaning,
     * {@code Spring} will create an instance of a class,
     * configuring it how by the way (for example, inject another component
     * or inject a field value with the {@link org.springframework.context.annotation.PropertySource} and
     * {@link org.springframework.beans.factory.annotation.Value} annotations) -
     * {@link org.springframework.beans.factory.config.BeanPostProcessor} magic going under the hood
     * (well, not quite magic. If you want to learn more about {@code Spring} internals - better
     * watch some of <i>Eugeniy Borisov's</i> talks on youtube).
     * <strong>only once</strong>,
     * i.e., that instance will be a <strong>singleton</strong> by default,
     * but we can configure this if needed.
     * <p>
     * {@link Autowired} above a method(a constructor as well) means that all method's parameters
     * will be injected from the "IOC-container".
     * <p>
     * <strong>NOTE:</strong> we can also {@code Autowire} components by putting {@link Autowired} above
     * the field we want to {@code Autowire}, but constructor injection is preferred.
     * <p>
     * <strong>NOTE:</strong> prefer inject by interface, not an implementation-class.
     */
    @Autowired
    public AgendaServiceImpl(AgendaRepository agendaRepository, UsersService usersService) {
        this.agendaRepository = agendaRepository;
        this.usersService = usersService;
    }

    /**
     * This method saves new agenda to the database.
     * <p>
     * <strong>Note:</strong> the <strong>id</strong>
     * will be assigned to this agenda automatically:
     * see {@link AgendaEntity#id} - it is annotated with
     * {@code @GeneratedValue(strategy = GenerationType.IDENTITY)},
     * but any other attributes we should assign manually.
     *
     * @see AgendaService#saveNewAgenda(SaveNewAgendaRequestDTO) for additional details.
     */
    @Override
    public void saveNewAgenda(SaveNewAgendaRequestDTO saveNewAgendaRequest) {
        AgendaEntity agendaToSave = new AgendaEntity();

        UsersEntity usersid = usersService.loadUserByUsername(saveNewAgendaRequest.getUsername());

        /* Set user, that owns this agenda. */
        agendaToSave.setUsersid(usersid);

        agendaToSave.setCreated(new Date());
        agendaToSave.setUpdated(new Date());

        agendaToSave.setDay(saveNewAgendaRequest.getDay());
        agendaToSave.setTime(saveNewAgendaRequest.getTime());
        agendaToSave.setAccessible(saveNewAgendaRequest.isAccessible());
        agendaToSave.setNote(saveNewAgendaRequest.getNote());

        agendaRepository.save(agendaToSave);
    }

    /**
     * This method updates agenda by specified id.
     *
     * @see AgendaService#updateAgendaByItsId(UpdateAgendaByItsIdRequestDTO) for additional details.
     */
    @Override
    public void updateAgendaByItsId(UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaRepository.updateAgendaByItsId(
                new Date(),
                updateAgendaRequest.getDay(),
                updateAgendaRequest.getTime(),
                updateAgendaRequest.isAccessible(),
                updateAgendaRequest.getNote(),
                updateAgendaRequest.getId()
        );
    }

    /**
     * This method deletes user's agenda by specified agenda's id.
     *
     * @see AgendaService#deleteAgendaById(Long) for additional details.
     */
    @Override
    public void deleteAgendaById(Long agendaId) {
        agendaRepository.deleteById(agendaId);
    }
}
