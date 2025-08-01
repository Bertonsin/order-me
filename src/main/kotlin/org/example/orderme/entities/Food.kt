package org.example.orderme.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.UUID

@Entity
@Table(name = "foods")
class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @NotEmpty
    @Column(name = "name", nullable = false)
    var name: String = ""

    @ManyToMany(targetEntity = Ingredient::class)
    @JoinTable(name = "ingredients", joinColumns = [JoinColumn(name = "food_id")],
        inverseJoinColumns = [JoinColumn(name = "ingredient_id")])
    var ingredients: List<Ingredient> = listOf()

    @ManyToMany(targetEntity = Order::class)
    var orders: List<Order> = listOf()

}