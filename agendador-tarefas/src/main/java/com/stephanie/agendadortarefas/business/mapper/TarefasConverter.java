package com.stephanie.agendadortarefas.business.mapper;


import com.stephanie.agendadortarefas.business.dto.TarefasDTO;
import com.stephanie.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity (TarefasDTO dto);

    TarefasDTO paraTarefaDTO (TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTO> dtos);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> entities);
}

