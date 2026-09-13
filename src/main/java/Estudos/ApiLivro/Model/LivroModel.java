package Estudos.ApiLivro.Model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "livros")
@Entity(name = "Livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private int anoPublicacao;
    private String sinopse;
    private String urlCapa;

    @Enumerated(EnumType.STRING)
    private GENERO genero;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private EditoraModel editora;

    @ManyToOne
    @JoinColumn(name = "autora_id")
    private AutoraModel autora;
}