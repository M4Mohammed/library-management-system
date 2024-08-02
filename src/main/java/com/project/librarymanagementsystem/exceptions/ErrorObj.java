package com.project.librarymanagementsystem.exceptions;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class ErrorObj {
    private final String message;
    private final String error;

}
