package Estudos.ApiLivro.Service;

import Estudos.ApiLivro.Dto.EditoraRequestDto;
import Estudos.ApiLivro.Dto.EditoraResponseDto;
import Estudos.ApiLivro.Model.EditoraModel;
import Estudos.ApiLivro.Repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditoraService {

    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    public EditoraResponseDto salvarEditora(EditoraRequestDto requestDTO) {
        EditoraModel editora = new EditoraModel();
        editora.setNome(requestDTO.nome());

        EditoraModel editoraSalva = editoraRepository.save(editora);

        return new EditoraResponseDto(editoraSalva.getId(), editoraSalva.getNome());
    }

    public List<EditoraResponseDto> listarEditoras() {
        return editoraRepository.findAll().stream()
                .map(editora -> new EditoraResponseDto(editora.getId(), editora.getNome()))
                .collect(Collectors.toList());
    }
}