package ua.dudka.kotlindemo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

@SpringBootApplication
open class KotlinDemoApplication

fun main(args: Array<String>) {
    SpringApplication.run(KotlinDemoApplication::class.java, *args)
}

@RestController
@RequestMapping("/goods")
class GoodsController(val goodService: GoodService) {

    @GetMapping
    fun goods(): ArrayList<Good> {
        return goodService.getGoods()
    }

    @PostMapping
    fun addGood(@RequestBody good: Good) {
        goodService.addGood(good)

    }

    @PutMapping(path = arrayOf("/{id}"))
    fun editGood(@PathVariable id: Int, @RequestBody good: Good) {
        goodService.editGood(id, good)
    }

    @DeleteMapping(path = arrayOf("/{id}"))
    fun deleteGood(@PathVariable id: Int) {
        goodService.deleteGood(id)
    }

}

@Service
class GoodService {
    private val goods: ArrayList<Good> = ArrayList();

    fun getGoods(): ArrayList<Good> {
        return goods;

    }

    fun addGood(good: Good) {
        goods.add(good)
    }

    fun editGood(goodId: Int, good: Good) {
        goods[goodId] = good

    }

    fun deleteGood(goodId: Int) {
        goods.removeAt(goodId)
    }
}

data class Good(val name: String, val quantity: Int)
