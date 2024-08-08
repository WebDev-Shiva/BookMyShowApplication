package Dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BookingMovieRequestDto {
    private Long userId;

    private Long showId;

    private List<Long> showSeatIds;
}
