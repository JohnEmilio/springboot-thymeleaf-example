package app.johnemilio.codingevents.controllers;

import app.johnemilio.codingevents.data.EventData;
import app.johnemilio.codingevents.models.Event;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/events")
public class EventController {


    @GetMapping
    public String events (Model model) {
        model.addAttribute("events", EventData.getAllEvents());
        return "events/index";
    }

    @GetMapping("/create")
    public String renderCreateEventForm () {
        return "events/create";
    }

    @PostMapping("/create")
    public String processCreateEvent (@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if (errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            model.addAttribute("errorMsg", "Bad data!");
            return "events/create";
        }
        EventData.add(newEvent);
        return "redirect:/events";
    }

    @GetMapping("/delete")
    public String displayDeleteEventForm (Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAllEvents());

        return "events/delete";
    }

    @PostMapping("/delete")
    public String processDeleteEventsForm (@RequestParam(required = false) int[] eventIds){

        if(eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }

        return "redirect:/events";
    }

}