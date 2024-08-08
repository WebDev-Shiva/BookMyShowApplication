package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Show show;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Show seatType;
    private int price;


    }

