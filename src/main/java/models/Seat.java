package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{
    private int seatNo;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private SeatType SeatType;
    private int rowNo;
    private int ColNo;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Screen screen;


}
