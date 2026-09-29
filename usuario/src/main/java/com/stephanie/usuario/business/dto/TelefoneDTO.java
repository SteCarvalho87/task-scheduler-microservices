package com.stephanie.usuario.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {
    private Long id;
    private Long numero;
    private Long ddd;

}
