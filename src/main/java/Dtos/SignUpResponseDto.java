package Dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpResponseDto {
    private ResponseStatus responseStatus;

    private Long userId;
}
