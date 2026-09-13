package Estudos.ApiLivro.Controller;

import Estudos.ApiLivro.Dto.LivroRequestDto;
import Estudos.ApiLivro.Dto.LivroResponseDto;
import Estudos.ApiLivro.Service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDto> criarLivro(@RequestBody @Valid LivroRequestDto requestDTO) {
        LivroResponseDto novoLivro = livroService.salvarLivro(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> listarLivros() {
        List<LivroResponseDto> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }
}