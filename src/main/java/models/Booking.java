package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
public class Booking extends BaseModel{
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    //Booking and User relationship: A Booking is associated with a single User and a User can have multiple Bookings.
    // This is a @ManyToOne relationship.
    private User user;
    private Date bookedAt;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //Booking and Show relationship: A Booking is associated with a single Show and a Show can have multiple Bookings.
    // This is a @ManyToOne relationship.
    private Show show;
    private int amount;
    private List<Payment> payments;
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "booking_show_seat",joinColumns = @JoinColumn(name = "booking_id"),
    inverseJoinColumns = @JoinColumn(name="show_seat_id"))
    private List<ShowSeat> showSeats;


    }

