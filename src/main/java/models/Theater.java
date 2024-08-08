package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.swing.plaf.synth.Region;
import java.util.List;
@Getter
@Setter
@Entity
public class Theater extends BaseModel{

    private String name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Region region;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screen> screens;


}
