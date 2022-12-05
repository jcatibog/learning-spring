package net.catibog.learningspring.web;

import net.catibog.learningspring.business.ReservationService;
import net.catibog.learningspring.data.Guest;
import net.catibog.learningspring.data.GuestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestsController {
    private final ReservationService reservationService;

    public GuestsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getGuests(Model model) {
        model.addAttribute("guests", this.reservationService.getHotelGuests());

        return "hotel-guests";
    }
}
