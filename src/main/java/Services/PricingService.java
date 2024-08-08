package Services;

import Repositories.ShowSeatTypeRepository;
import models.ShowSeat;
import models.ShowSeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PricingService {

    @Autowired
    private ShowSeatTypeRepository showSeatTypeRepository;


    public int calculatedPrice(List<ShowSeat> finalSelectedSeatForBooking) {
        int amount = 0;
        List<ShowSeatType> allShowSeatTypes = showSeatTypeRepository.findAll();

        for (ShowSeat currShowSeat : finalSelectedSeatForBooking) {
            for (ShowSeatType currShowSeatType : allShowSeatTypes) {
                if (currShowSeat.getSeat().getSeatType().getPrice() == currShowSeatType.getSeatType().getPrice()) {
                    amount += currShowSeatType.getPrice();
                    break; // One seat can have only one type, we found that type, no need to iterate through
                    // the rest of the list :
                }
            }
        }

        return amount;
    }



}
