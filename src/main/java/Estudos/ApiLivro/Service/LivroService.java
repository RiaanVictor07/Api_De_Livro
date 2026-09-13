package Estudos.ApiLivro.Service;

import Estudos.ApiLivro.Dto.LivroRequestDto;
import Estudos.ApiLivro.Dto.LivroResponseDto;
import Estudos.ApiLivro.Exception.RecursoNaoEncontradoException;
import Estudos.ApiLivro.Model.AutoraModel;
import Estudos.ApiLivro.Model.EditoraModel;
import Estudos.ApiLivro.Model.GENERO;
import Estudos.ApiLivro.Model.LivroModel;
import Estudos.ApiLivro.Repository.AutoraRepository;
import Estudos.ApiLivro.Repository.EditoraRepository;
import Estudos.ApiLivro.Repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutoraRepository autoraRepository;
    private final EditoraRepository editoraRepository;

    public LivroService(LivroRepository livroRepository, AutoraRepository autoraRepository, EditoraRepository editoraRepository) {
        this.livroRepository = livroRepository;
        this.autoraRepository = autoraRepository;
        this.editoraRepository = editoraRepository;
    }

    public LivroResponseDto salvarLivro(LivroRequestDto requestDTO) {
        AutoraModel autora = autoraRepository.findById(requestDTO.autoraId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Autora não encontrada com ID: " + requestDTO.autoraId()));

        EditoraModel editora = editoraRepository.findById(requestDTO.editoraId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Editora não encontrada com ID: " + requestDTO.editoraId()));

        LivroModel livro = new LivroModel();
        livro.setTitulo(requestDTO.titulo());
        livro.setAnoPublicacao(requestDTO.anoPublicacao());
        livro.setGenero(GENERO.valueOf(requestDTO.genero()));
        livro.setUrlCapa(requestDTO.urlCapa());
        livro.setSinopse(requestDTO.sinopse());
        livro.setAutora(autora);
        livro.setEditora(editora);

        LivroModel livroSalvo = livroRepository.save(livro);

        return new LivroResponseDto(
                livroSalvo.getId(),
                livroSalvo.getTitulo(),
                livroSalvo.getAutora().getNome(),
                livroSalvo.getEditora().getNome(),
                livroSalvo.getAnoPublicacao(),
                livroSalvo.getSinopse(),
                livroSalvo.getUrlCapa(),
                livroSalvo.getGenero().name()
        );
    }

    public List<LivroResponseDto> listarLivros() {
        return livroRepository.findAll().stream()
                .map(livro -> new LivroResponseDto(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutora().getNome(),
                        livro.getEditora().getNome(),
                        livro.getAnoPublicacao(),
                        livro.getSinopse(),
                        livro.getUrlCapa(),
                        livro.getGenero().name()
                ))
                .collect(Collectors.toList());
    }
}