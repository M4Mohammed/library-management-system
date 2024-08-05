package com.project.librarymanagementsystem.patrons;

import com.project.librarymanagementsystem.exceptions.PatronNotFoundException;
import com.project.librarymanagementsystem.patrons.dto.PatronCreateDto;
import com.project.librarymanagementsystem.patrons.dto.PatronResponseDto;
import com.project.librarymanagementsystem.patrons.dto.PatronUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;

    public List<PatronResponseDto> getAllPatrons() {
        return patronRepository.findAll()
                .stream()
                .parallel()
                .map(patronMapper::toResponseDto)
                .toList();
    }

    @Cacheable(value = "patronCache", key = "#id")
    public PatronResponseDto getPatronById(UUID id) {
        return patronRepository.findById(id)
                .map(patronMapper::toResponseDto)
                .orElseThrow(() -> new PatronNotFoundException(id));
    }

    @Cacheable(value = "patronCache", key = "#patron.id")
    public PatronResponseDto addPatron(PatronCreateDto createDto) {
        return patronMapper.toResponseDto(patronRepository.save(patronMapper.toEntity(createDto)));
    }

    @Cacheable(value = "patronCache", key = "#id")
    public PatronResponseDto updatePatron(UUID id, PatronUpdateDto updateDto) {
        return patronRepository.findById(id)
                .map(patron -> {
                    patronMapper.updateEntity(updateDto, patron);
                    return patronMapper.toResponseDto(patronRepository.save(patron));
                })
                .orElseThrow(() -> new PatronNotFoundException(id));
    }

    public void deletePatron(UUID id) {
        patronRepository.deleteById(id);
    }
}
