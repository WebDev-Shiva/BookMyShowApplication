package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@Entity
public class Screen extends BaseModel{

    private String name;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "screen", orphanRemoval = true )
    private List<Seat> seat;


}
