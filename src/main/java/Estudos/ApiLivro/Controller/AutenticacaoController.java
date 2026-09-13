package Estudos.ApiLivro.Controller;

import Estudos.ApiLivro.Dto.LoginRequestDto;
import Estudos.ApiLivro.Dto.LoginResponseDto;
import Estudos.ApiLivro.Dto.RegistroRequestDto;
import Estudos.ApiLivro.Model.UsuarioModel;
import Estudos.ApiLivro.Repository.UsuarioRepository;
import Estudos.ApiLivro.Security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<LoginResponseDto> registrar(@RequestBody @Valid RegistroRequestDto dto) {
        if (usuarioRepository.existsByLogin(dto.login())) {
            throw new IllegalArgumentException("Este login já está em uso.");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        usuarioRepository.save(usuario);

        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponseDto(token, usuario.getNome()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        Authentication authentication = authenticationManager.authenticate(authToken);

        UsuarioModel usuario = (UsuarioModel) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDto(token, usuario.getNome()));
    }
}