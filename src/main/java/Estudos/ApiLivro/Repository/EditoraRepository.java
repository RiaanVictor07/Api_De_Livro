package Estudos.ApiLivro.Repository;

import Estudos.ApiLivro.Model.EditoraModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<EditoraModel, Long> {
}