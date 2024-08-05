package com.project.librarymanagementsystem.patrons;

import com.project.librarymanagementsystem.patrons.dto.PatronCreateDto;
import com.project.librarymanagementsystem.patrons.dto.PatronResponseDto;
import com.project.librarymanagementsystem.patrons.dto.PatronUpdateDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatronMapper {

    Patron toEntity(PatronCreateDto patronCreateDto);

    void updateEntity(PatronUpdateDto patronUpdateDto, @MappingTarget Patron patron);

    PatronResponseDto toResponseDto(Patron patron);
}