package com.stephanie.bff_agendador_tarefas.business;

import com.stephanie.bff_agendador_tarefas.business.dto.out.TarefasDTOResponse;
import com.stephanie.bff_agendador_tarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviarEmail(@RequestBody TarefasDTOResponse dto){
        emailClient.enviarEmail(dto);
    }

}
