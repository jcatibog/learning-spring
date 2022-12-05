package net.catibog.learningspring.webservice;

import net.catibog.learningspring.business.ReservationService;
import net.catibog.learningspring.business.RoomReservation;
import net.catibog.learningspring.data.Guest;
import net.catibog.learningspring.data.Room;
import net.catibog.learningspring.util.DateUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebServiceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    public WebServiceController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @GetMapping("/guests")
    public List<Guest> getGuests() {
        return this.reservationService.getHotelGuests();
    }

    @PostMapping("/guests")
    public Guest addGuest(@RequestBody Guest guest) {
        return this.reservationService.addHotelGuest(guest);
    }

    @GetMapping("/reservations")
    public List<RoomReservation> getReservations(@RequestParam(value = "date", required = false) String dateString) {
        Date date = dateUtils.createDateFromString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }

    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return this.reservationService.getHotelRooms();
    }
}
