package com.humberto.versiculos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Versiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoVersiculo tipo;

    private String referenciaBiblica;

    @Column(length = 1000)
    private String conteudo;

    private LocalDate data;
}
