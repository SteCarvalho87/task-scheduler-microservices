package com.stephanie.bff_agendador_tarefas.business.dto.in;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTORequest {

    private String email;
    private String senha;

}