package ua.dudka.kotlindemo

import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.test.context.junit4.SpringRunner
import ua.dudka.kotlindemo.model.Good
import ua.dudka.kotlindemo.repository.EntityNotFoundException
import ua.dudka.kotlindemo.repository.GoodRepository

/**
 * @author Rostislav Dudka
 */
@SpringBootTest(webEnvironment = NONE)
@RunWith(SpringRunner::class)
class GoodRepositoryTest {

    @Autowired lateinit var repository: GoodRepository


    @After
    fun tearDown() {
        repository.deleteAll()
    }

    @Test
    fun findAllShouldReturnCreatedGood() {
        val good = repository.save(Good(0, "beer", 1))

        val listGoods = repository.findAll()

        val actualGood = listGoods[0]
        assertNotNull(actualGood)
        assertEquals(good, actualGood)
    }

    @Test
    fun findByIdShouldReturnCreatedGood() {
        val good = repository.save(Good(0, "beer", 1))

        val foundGood = repository.findById(good.id).get()

        assertEquals(good, foundGood)
    }

    @Test
    fun findByNonexistentIdShouldReturnEmptyGood() {

        val nonexistentId = 404
        val foundGood = repository.findById(nonexistentId)

        assertFalse(foundGood.isPresent)
    }

    @Test
    fun goodShouldSave() {
        val good = repository.save(Good(0, "beer", 1))

        assertNotNull(good)
        assertTrue(good.id > 0)

        val actualGood = repository.findById(good.id)
        assertTrue(actualGood.isPresent)
        assertEquals(good, actualGood.get())
    }

    @Test
    fun goodShouldUpdate() {
        val good = repository.save(Good(0, "beer", 1))

        good.quantity = 2
        repository.update(good.id, good)

        val foundGood = repository.findById(good.id).get()
        assertEquals(good, foundGood)
    }

    @Test(expected = EntityNotFoundException::class)
    fun updateNonexistentGoodShouldThrowException() {
        val nonexistentId = 405
        val good = Good(0, "beer", 1)

        repository.update(nonexistentId, good)
    }

    @Test
    fun deleteExistentGoodShouldOK() {
        val good = repository.save(Good(0, "beer", 1))

        repository.delete(good.id)
    }

    @Test(expected = EntityNotFoundException::class)
    fun deleteNoneExistentGoodShouldThrowException() {
        val nonexistentId = 404
        repository.delete(nonexistentId)
    }

}