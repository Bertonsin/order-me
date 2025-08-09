package org.example.orderme.controllers

import org.apache.coyote.Response
import org.example.orderme.entities.Order
import org.example.orderme.exceptions.NotFoundException
import org.example.orderme.repositories.OrderRepository
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
@RequestMapping("/orders")
class OrderController(@Autowired val orderRepository: OrderRepository) {

    @PostMapping("")
    fun createOrder(@RequestBody order: Order): ResponseEntity<out Any> {
        try {
            orderRepository.save(order)
            return ResponseEntity(HttpStatus.OK)
        } catch (error: Exception){
            return ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("")
    fun listOrders(): ResponseEntity<out Any> {
        return try {
            ResponseEntity(orderRepository.findAll(), HttpStatus.OK)
        } catch (error: Exception){
            ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable("id") id: Long): ResponseEntity<out Any> {
        try {
            orderRepository.findById(id).orElseThrow { NotFoundException() }?.let {
                return ResponseEntity(it, HttpStatus.OK)
            }
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (error: Exception) {
            return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable("id") id: Long): ResponseEntity<out Any> {
        try {
            orderRepository.findById(id).orElseThrow { NotFoundException() }.run {
                orderRepository.deleteById(id)
            }
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (error: Exception) {
            return ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable("id") id: Long, @RequestBody order: Order): ResponseEntity<out Any> {
        try {
            orderRepository.findById(id).orElseThrow { NotFoundException() }.apply {
                foods = order.foods
            }
            return ResponseEntity(HttpStatus.OK)
        } catch (error: Exception) {
            return ResponseEntity(error, HttpStatus.NOT_FOUND)
        }
    }



}