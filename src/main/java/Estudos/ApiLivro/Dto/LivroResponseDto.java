package Estudos.ApiLivro.Dto;

public record LivroResponseDto(
        Long id,
        String titulo,
        String nomeAutora,
        String nomeEditora,
        int anoPublicacao,
        String sinopse,
        String urlCapa,
        String genero
) {}