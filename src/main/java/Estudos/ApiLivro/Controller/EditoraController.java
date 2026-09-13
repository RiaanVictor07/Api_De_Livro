package Estudos.ApiLivro.Controller;

import Estudos.ApiLivro.Dto.EditoraRequestDto;
import Estudos.ApiLivro.Dto.EditoraResponseDto;
import Estudos.ApiLivro.Service.EditoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @PostMapping
    public ResponseEntity<EditoraResponseDto> criarEditora(@RequestBody @Valid EditoraRequestDto requestDTO) {
        EditoraResponseDto novaEditora = editoraService.salvarEditora(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEditora);
    }

    @GetMapping
    public ResponseEntity<List<EditoraResponseDto>> listarEditoras() {
        List<EditoraResponseDto> editoras = editoraService.listarEditoras();
        return ResponseEntity.ok(editoras);
    }
}