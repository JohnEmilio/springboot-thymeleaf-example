package app.johnemilio.codingevents.controllers;

import app.johnemilio.codingevents.data.EventCategoryRepository;
import app.johnemilio.codingevents.models.EventCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllEvents(Model model){
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "eventCategories/index";
    }

    @GetMapping("/create")
    public String renderCreateEventCategoryForm(Model model){
        model.addAttribute("title", "Create Category");
        model.addAttribute(new EventCategory());
        return "eventCategories/create";
    }

    @PostMapping("/create")
    public String processCreateEventCategoryForm(@ModelAttribute @Valid EventCategory newCategory, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("title", "Create Category");
            model.addAttribute("errorMsg", "Bad data!");
            return "eventCategories/create";
        }
        eventCategoryRepository.save(newCategory);
        return "redirect:/eventCategories";
    }

}
