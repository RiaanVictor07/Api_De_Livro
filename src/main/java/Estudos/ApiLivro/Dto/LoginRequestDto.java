package Estudos.ApiLivro.Dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String login,
        @NotBlank String senha
) {}