package hu.adatb.controller;

import hu.adatb.dao.FlightDao;
import hu.adatb.dao.FlightDaoImpl;
import hu.adatb.model.*;
import hu.adatb.utils.DistanceCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FlightController {

    private FlightDao dao = new FlightDaoImpl();
    private static FlightController single_instance = null;


    public static FlightController getInstance(){
        if(single_instance == null){
            single_instance = new FlightController();
        }
        return single_instance;
    }

    public boolean add(Flight flight) {
        return dao.add(flight);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public boolean update(Flight flight) {
        return dao.update(flight);
    }

    public List<Flight> getAll() {
        return dao.getAll();
    }

    public String SetTimeFormat(int time) {
        return (time < 10 ? ("0" + time) : Integer.toString(time));
    }

    public String GetDateTimeInRightFormat(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dateTime.format(formatter);
    }

    public String GetTravelTime(double lat1, double lon1, double lat2, double lon2, Plane plane) {
        var distance = DistanceCalculator.Distance(lat1, lon1, lat2, lon2);
        var travelTimeInMinutes = (int)((distance/plane.getSpeed()) * 60);

        int hours = travelTimeInMinutes / 60;
        int minutes = travelTimeInMinutes - hours * 60;

        return hours > 0
                ? hours + " óra " + minutes + " perc"
                : travelTimeInMinutes + " perc";
    }

    public String GetHotels(List<Hotel> hotels, String toAirportCityName) {
        var filteredHotels = hotels.stream().filter(hotel -> hotel.getCity().getName().equals(toAirportCityName)).collect(Collectors.toList());
        var allHotelsInCity = "";

        if (filteredHotels.size() == 0) {
            return "-";
        } else if (filteredHotels.size() == 1) {
            allHotelsInCity = filteredHotels.get(0).getName();
        } else {
            int i;
            for (i = 0; i < filteredHotels.size() - 1; i++) {
                allHotelsInCity += filteredHotels.get(i).getName() + ", ";
            }

            allHotelsInCity += filteredHotels.get(i).getName();
        }

        return allHotelsInCity;
    }

    public List<Ticket> GetTicketNumber(List<Booking> bookings, Flight flight) {
        var tickets = TicketController.getInstance().getAll();
        List<Ticket> filteredTickets = new ArrayList<>();
        var filteredBookings = bookings.stream().filter(booking -> booking.getFlight().getId() == flight.getId()).collect(Collectors.toList());

        for (var booking: filteredBookings) {
            filteredTickets.addAll(tickets.stream().filter(ticket -> ticket.getBooking().getId() == booking.getId()).collect(Collectors.toList()));
        }

        return filteredTickets;
    }

    public List<FlightAlertRelation> GetAlerts(Flight flight) {
        var relations = FlightAlertRelationController.getInstance().getAll();

        return relations.stream().filter(
                relation -> relation.getFlight().getId() == flight.getId()).collect(Collectors.toList());
    }
}
