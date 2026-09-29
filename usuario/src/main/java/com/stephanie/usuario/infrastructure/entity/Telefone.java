package com.stephanie.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero", length = 10)
    private Long numero;
    @Column(name = "ddd", length = 3)
    private Long ddd;
    @Column(name = "usuario_id")
    private Long usuario_id;
}
