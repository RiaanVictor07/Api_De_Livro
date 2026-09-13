package Estudos.ApiLivro.Controller;

import Estudos.ApiLivro.Dto.AutoraRequestDto;
import Estudos.ApiLivro.Dto.AutoraResponseDto;
import Estudos.ApiLivro.Service.AutoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autoras")
public class AutoraController {

    private final AutoraService autoraService;

    public AutoraController(AutoraService autoraService) {
        this.autoraService = autoraService;
    }

    @PostMapping
    public ResponseEntity<AutoraResponseDto> criarAutora(@RequestBody @Valid AutoraRequestDto requestDTO) {
        AutoraResponseDto novaAutora = autoraService.salvarAutora(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAutora);}

    @GetMapping
    public ResponseEntity<List<AutoraResponseDto>> listarAutoras() {
        List<AutoraResponseDto> autoras = autoraService.listarAutoras();
        return ResponseEntity.ok(autoras);
    }
}