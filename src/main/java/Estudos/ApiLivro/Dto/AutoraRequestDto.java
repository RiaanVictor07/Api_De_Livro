package Estudos.ApiLivro.Dto;

import jakarta.validation.constraints.NotBlank;

public record AutoraRequestDto(
        @NotBlank(message = "O nome da autora é obrigatório")
        String nome
) {}