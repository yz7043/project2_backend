package com.bfs.hibernateprojectdemo.dto.base;

import lombok.NonNull;
import static com.bfs.hibernateprojectdemo.utils.BeanUtils.updateProperties;
public interface OutputConverter<DtoT extends OutputConverter<DtoT, D>, D> {
    @SuppressWarnings("unchecked")
    @NonNull
    default <T extends DtoT> T convertFrom(@NonNull D domain){
        updateProperties(domain, this);
        return (T) this;
    }
}
