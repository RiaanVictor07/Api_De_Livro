package Estudos.ApiLivro.Repository;

import Estudos.ApiLivro.Model.LivroModel;
import Estudos.ApiLivro.Model.GENERO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<LivroModel, Long> {
    List<LivroModel> findByGenero(GENERO genero);
    List<LivroModel> findByAutora_Id(Long autoraId);
    List<LivroModel> findByEditora_Id(Long editoraId);
}