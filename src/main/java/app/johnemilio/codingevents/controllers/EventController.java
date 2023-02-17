package app.johnemilio.codingevents.controllers;

import app.johnemilio.codingevents.data.EventCategoryRepository;
import app.johnemilio.codingevents.data.EventRepository;
import app.johnemilio.codingevents.models.Event;
import app.johnemilio.codingevents.models.EventCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String events (@RequestParam(required = false) Integer categoryId, Model model) {
        if(categoryId == null){
            model.addAttribute("events", eventRepository.findAll());
            model.addAttribute("title", "All Events");
        }
        else{
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if(result.isEmpty()){
                model.addAttribute("title", "Invalid Category Id: "+ categoryId);
            }
            else{
                EventCategory category = result.get();
                model.addAttribute("title", "Events in Category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }
        }
        return "events/index";
    }

    @GetMapping("/create")
    public String renderCreateEventForm (Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    @PostMapping("/create")
    public String processCreateEvent (@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if (errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            model.addAttribute("errorMsg", "Bad data!");
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "redirect:/events";
    }

    @GetMapping("/delete")
    public String displayDeleteEventForm (Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());

        return "events/delete";
    }

    @PostMapping("/delete")
    public String processDeleteEventsForm (@RequestParam(required = false) int[] eventIds){

        if(eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:/events";
    }

}
