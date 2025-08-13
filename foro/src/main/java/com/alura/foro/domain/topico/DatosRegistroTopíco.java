package com.alura.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopíco(
        Long id,
        @NotBlank String titulo,
        @NotBlank String  mensaje,
        @NotNull LocalDateTime fecha,
        @NotNull Status status,
        @NotBlank String autor,
        @NotBlank String curso
) {

    public DatosRegistroTopíco(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
