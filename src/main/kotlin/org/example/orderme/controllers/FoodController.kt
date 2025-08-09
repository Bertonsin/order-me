package org.example.orderme.controllers

import org.example.orderme.entities.Food
import org.example.orderme.exceptions.NotFoundException
import org.example.orderme.repositories.FoodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/foods")
class FoodController (@Autowired val foodRepository: FoodRepository) {

    @PostMapping("")
    fun createFood(@RequestBody food: Food): ResponseEntity<out Any> {
        try {
            foodRepository.save(food);
            return ResponseEntity<Long>(food.id,HttpStatus.CREATED);
        } catch (error: Exception){
            return ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    fun listFoods(): ResponseEntity<out Any> {
        return try {
            ResponseEntity(foodRepository.findAll(), HttpStatus.OK);
        } catch (error: Exception){
            ResponseEntity(error,HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getFood(@PathVariable("id") id: Long): ResponseEntity<out Any> {
        return try {
            ResponseEntity(foodRepository.findById(id).orElseThrow{ NotFoundException()}, HttpStatus.OK)
        } catch (error: Exception) {
            ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteFood(@PathVariable("id") id: Long): ResponseEntity<out Any> {
        try {
            foodRepository.findById(id).ifPresent {
                foodRepository.deleteById(id)
            }
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (error: Exception) {
            return ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateFood(@PathVariable("id") id: Long, @RequestBody food: Food): ResponseEntity<out Any> {
        try {
           foodRepository.findById(id).orElseThrow { NotFoundException() }.apply {
               name = food.name
               orders = food.orders
               ingredients = food.ingredients
           }
            return ResponseEntity(HttpStatus.OK)
        } catch (error: Exception) {
            return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}