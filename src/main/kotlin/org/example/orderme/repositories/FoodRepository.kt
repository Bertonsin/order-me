package org.example.orderme.repositories

import org.example.orderme.entities.Food
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : JpaRepository<Food, Long> {

}