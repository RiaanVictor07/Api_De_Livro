package Estudos.ApiLivro.Dto;

import jakarta.validation.constraints.NotBlank;

public record EditoraRequestDto(
        @NotBlank(message = "O nome da editora é obrigatório")
        String nome
) {}