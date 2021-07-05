package com.sahibinden.backend.mapper;

import com.sahibinden.backend.model.AdDocument;
import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdMapper {

   AdDocument toAdDocument(AdCreateRequest adCreateRequest);

   AdResponse toAdResponse(AdDocument adDocument);

}
