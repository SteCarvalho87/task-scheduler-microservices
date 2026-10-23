package com.stephanie.agendadortarefas.business.mapper;


import com.stephanie.agendadortarefas.business.dto.TarefasDTORecord;
import com.stephanie.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity (TarefasDTORecord dto);

    TarefasDTORecord paraTarefaDTO (TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTORecord> dtos);

    List<TarefasDTORecord> paraListaTarefasDTORecord(List<TarefasEntity> entities);
}

