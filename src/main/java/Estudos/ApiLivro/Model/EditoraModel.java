package Estudos.ApiLivro.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "editoras")
@Entity(name = "Editora")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EditoraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "editora")
    private List<LivroModel> livros;
}