package org.example.orderme.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;

    @NotEmpty
    @Column(name = "name", nullable = false)
    var name: String = "";

    @NotNull
    @Column(name="amount", nullable = false)
    var amount: Double = 0.0;

    @NotNull
    @Column(name = "created_at")
    val createdAt: Instant = Instant.now();

    @ManyToMany(targetEntity = Food::class)
    val foods: List<Food> = listOf()

}