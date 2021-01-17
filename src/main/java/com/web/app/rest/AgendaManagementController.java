package com.web.app.rest;

import com.web.app.model.SaveNewAgendaRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.service.AgendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//todo мне не нравится документация ни к одному из контроллеров. Я нигде не написал про диспатчерсервлет,
// что вообще такое сервлет, что такое контейнер сервлетов(томкат) и как оно работает изнутри.
// но расписывать все это дело слишком долго + с ума сойти можно. Как быть?

/**
 * A {@link RestController} to handle requests for managing agendas.
 */
@RestController
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class AgendaManagementController {

    private final AgendaService agendaService;

    /**
     * Autowire this service.
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
     * which contains a {@link org.springframework.stereotype.Component} inside itself, or even beans from class, annotated with {@link org.springframework.stereotype.Component}
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
    public AgendaManagementController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * This method saves new {@code AgendaEntity}.
     *
     * @param saveNewAgendaRequest - better see it' javaDoc - {@link com.web.app.model.SignUpRequestDTO}
     * @return response message and status code, wrapped in {@link ResponseEntity<String>}.
     */
    @PostMapping("/saveNewAgenda")
    public ResponseEntity<String> saveNewAgenda(@RequestBody SaveNewAgendaRequestDTO saveNewAgendaRequest) {
        agendaService.saveNewAgenda(saveNewAgendaRequest);

        return new ResponseEntity<>("Successfully saved your agenda!", HttpStatus.OK);
    }

    /**
     * This method updates an {@code AgendaEntity} by <strong>it's</strong> {@code id}.
     *
     * @param updateAgendaRequest - better see it' javaDoc - {@link com.web.app.model.SignUpRequestDTO}
     * @return response message and status code, wrapped in {@link ResponseEntity<String>}.
     */
    @PostMapping("/updateAgendaByItsId")
    public ResponseEntity<String> updateAgendaById(@RequestBody UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaService.updateAgendaByItsId(updateAgendaRequest);

        return new ResponseEntity<>("Successfully updated your agenda!", HttpStatus.OK);
    }

    /**
     * This method deletes an {@code AgendaEntity} by <strong>it's</strong> {@code id}.
     *
     * @param json a {@code JSON} object, in form of:
     *             {@code
     *             {
     *             "agendaId":  "agendaIdValue"
     *             }
     *             }
     *             , it will be casted to the {@code Map<String, String>}.
     * @return response message and status code, wrapped in {@link ResponseEntity<String>}.
     */
    @PostMapping(value = "/deleteAgendaByItsId")
    public ResponseEntity<String> deleteAgendaByItsId(@RequestBody Map<String, String> json) {
        agendaService.deleteAgendaById(Long.parseLong(json.get("agendaId")));

        return new ResponseEntity<>("Successfully deleted your agenda!", HttpStatus.OK);
    }
}
