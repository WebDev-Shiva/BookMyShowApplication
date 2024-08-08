package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity(name = "shows")// show is a reserved keyword in SQL, it'll throw error, if we don't change the name of the model
public class Show extends BaseModel{
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Movie movie;

    private Date startTime;

    private Date endTime;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Screen screen;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Feature> Features;
    private int price;


    public void setPrice(int price) {
        this.price = price;
    }
}
