package net.catibog.learningspring.business;

import net.catibog.learningspring.data.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(GuestRepository guestRepository, ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findAllByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setDate(date);
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort((o1, o2) -> {
            if (o1.getRoomName().equals(o2.getRoomName())) {
                return o1.getRoomNumber().compareTo(o2.getRoomNumber());
            }
            return o1.getRoomName().compareTo(o2.getRoomName());
        });
        return roomReservations;
    }

    public List<Guest> getHotelGuests() {
        Iterable<Guest> guests = this.guestRepository.findAll();
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort((o1, o2) -> {
            if (o1.getLastName().equals(o2.getLastName())) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
            return o1.getLastName().compareTo(o2.getLastName());
        });
        return guestList;
    }

    public Guest addHotelGuest(Guest guest) {
        return this.guestRepository.saveAndFlush(guest);
    }

    public List<Room> getHotelRooms() {
        Iterable<Room> rooms = this.roomRepository.findAll();
        List<Room> roomList = new ArrayList<>();
        rooms.forEach(roomList::add);
        roomList.sort((o1, o2) -> {
            if (o1.getName().equals(o2.getName())) {
                return o1.getBedInfo().compareTo(o2.getBedInfo());
            }
            return o1.getName().compareTo(o2.getName());
        });
        return roomList;
    }
}
