package models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
public class ShowSeat extends BaseModel{

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Show show;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Seat Seat;
    @Enumerated(EnumType.ORDINAL)
    private ShowSeatStatus showSeatStatus;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date bookedAt;
}
