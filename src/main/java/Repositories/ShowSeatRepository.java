package Repositories;

import models.Show;
import models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs );

    List<ShowSeat> findAllByShow( Show show );


    @Override
    ShowSeat save(ShowSeat showSeat);
}