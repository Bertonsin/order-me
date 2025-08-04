package org.example.orderme.controllers

import org.example.orderme.entities.Ingredient
import org.example.orderme.exceptions.NotFoundException
import org.example.orderme.repositories.IngredientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingredients")
class IngredientController @Autowired constructor(val ingredientRepository: IngredientRepository) {

    @PostMapping("")
    fun createIngredient(@RequestBody ingredient: Ingredient): ResponseEntity<Object> {
        try {
            ingredientRepository.save<Ingredient>(ingredient);
            return ResponseEntity(HttpStatus.CREATED)
        } catch (error: Exception) {
            println(error.message)
            return ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    fun getIngredients(): ResponseEntity<out Any> {
        try {
            val ingredients = ingredientRepository.findAll();
            return ResponseEntity(ingredients, HttpStatus.OK);
        } catch (error: Exception){
            println(error.message)
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable("id") id: Long): ResponseEntity<out Any> {
        try {
            val ingredient = ingredientRepository.findById(id).orElseThrow { NotFoundException() }
            return ResponseEntity(ingredient, HttpStatus.OK)
        }
        catch (error: Exception){
            println(error.message)
            return ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}