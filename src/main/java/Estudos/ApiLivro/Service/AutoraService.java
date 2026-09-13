package Estudos.ApiLivro.Service;

import Estudos.ApiLivro.Dto.AutoraRequestDto;
import Estudos.ApiLivro.Dto.AutoraResponseDto;
import Estudos.ApiLivro.Model.AutoraModel;
import Estudos.ApiLivro.Repository.AutoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoraService {

    private final AutoraRepository autoraRepository;

    public AutoraService(AutoraRepository autoraRepository) {
        this.autoraRepository = autoraRepository;
    }

    public AutoraResponseDto salvarAutora(AutoraRequestDto requestDTO) {

        AutoraModel autora = new AutoraModel();
        autora.setNome(requestDTO.nome());


        AutoraModel autoraSalva = autoraRepository.save(autora);


        return new AutoraResponseDto(autoraSalva.getId(), autoraSalva.getNome());
    }

    public List<AutoraResponseDto> listarAutoras() {

        return autoraRepository.findAll().stream()
                .map(autora -> new AutoraResponseDto(autora.getId(), autora.getNome()))
                .collect(Collectors.toList());
    }
}