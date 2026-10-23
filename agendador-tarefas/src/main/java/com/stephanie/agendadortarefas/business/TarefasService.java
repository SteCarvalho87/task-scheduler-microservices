package com.stephanie.agendadortarefas.business;

import com.stephanie.agendadortarefas.business.dto.TarefasDTORecord;
import com.stephanie.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.stephanie.agendadortarefas.business.mapper.TarefasConverter;
import com.stephanie.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.stephanie.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.stephanie.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.stephanie.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.stephanie.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;


    public TarefasDTORecord gravarTarefa(String token, TarefasDTORecord dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        TarefasDTORecord dtoFinal = new TarefasDTORecord(null,
                dto.nomeTarefa(),
                dto.descricao(),
                LocalDateTime.now(),
                null,
                email,
                null,
                StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dtoFinal);
        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTORecord> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListaTarefasDTORecord(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTORecord> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListaTarefasDTORecord(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorID(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id, e.getCause());
        }

    }

    public TarefasDTORecord alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar o status da tarefa" + id, e.getCause());
        }
    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar o status da tarefa" + id, e.getCause());
        }
    }

}
