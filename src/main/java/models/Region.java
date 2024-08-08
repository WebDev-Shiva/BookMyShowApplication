package models;

import javax.persistence.OneToMany;
import java.util.List;

public class Region extends BaseModel{
    private String name;
    @OneToMany(mappedBy = "region")
    private List<Theater> theaters;
}
