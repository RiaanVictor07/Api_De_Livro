package Estudos.ApiLivro.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LivroRequestDto(
        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotNull(message = "O ID da autora é obrigatório")
        Long autoraId,

        @NotNull(message = "O ID da editora é obrigatório")
        Long editoraId,

        @Positive(message = "O ano de publicação deve ser um número positivo")
        int anoPublicacao,

        @NotBlank(message = "A sinopse não pode estar em branco")
        String sinopse,

        @NotBlank(message = "A URL da capa não pode estar em branco")
        String urlCapa,


        @NotBlank(message = "O gênero é obrigatório")
        String genero
) {}