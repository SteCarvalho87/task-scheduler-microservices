package com.stephanie.bff_agendador_tarefas.business.dto.in;

import com.stephanie.bff_agendador_tarefas.business.dto.out.TelefoneDTOResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTORequest {
    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTORequest> enderecos;
    private List<TelefoneDTOResponse> telefones;

}
