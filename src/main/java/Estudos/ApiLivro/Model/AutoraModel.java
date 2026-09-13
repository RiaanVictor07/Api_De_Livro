package Estudos.ApiLivro.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "autoras")
@Entity(name = "Autora")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AutoraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "autora")
    private List<LivroModel> livros;
}