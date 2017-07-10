package ua.dudka.kotlindemo

import org.springframework.web.bind.annotation.*
import ua.dudka.kotlindemo.model.Good
import ua.dudka.kotlindemo.repository.GoodRepository

@RestController
@RequestMapping("/goods")
class GoodsController(private val repository: GoodRepository) {

    @GetMapping
    fun goods(): List<Good> = repository.findAll()

    @PostMapping
    fun addGood(@RequestBody good: Good): Good = repository.save(good)

    @PutMapping(path = arrayOf("/{id}"))
    fun editGood(@PathVariable id: Int, @RequestBody good: Good): Good = repository.update(id, good)

    @DeleteMapping(path = arrayOf("/{id}"))
    fun deleteGood(@PathVariable id: Int) = repository.delete(id)

}