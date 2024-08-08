package Dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.SequenceGenerators;

@Getter
@Setter
public class BookMovieResponseDto {
        private  int amount;
        private Long bookingId;
        private ResponseStatus responseStatus;
}
