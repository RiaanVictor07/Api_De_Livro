package Estudos.ApiLivro.Dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroRequestDto(
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotBlank(message = "O login é obrigatório") String login,
        @NotBlank(message = "A senha é obrigatória") String senha
) {}