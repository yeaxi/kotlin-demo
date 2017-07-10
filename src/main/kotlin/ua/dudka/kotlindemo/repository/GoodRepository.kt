package ua.dudka.kotlindemo.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import ua.dudka.kotlindemo.model.Good
import java.sql.ResultSet
import java.util.*

@Repository
class GoodRepository(private val jdbcTemplate: JdbcTemplate) {
    private val rowMapper: GoodRowMapper = GoodRowMapper()

    fun findAll(): List<Good> = jdbcTemplate.query("select * from good", rowMapper)

    fun findById(id: Int): Optional<Good> {
        val goods = jdbcTemplate.query("SELECT * FROM GOOD WHERE id = $id", rowMapper)

        if (goods.isEmpty())
            return Optional.empty()
        else
            return Optional.of(goods[0])
    }

    fun save(good: Good): Good {
        val insert = SimpleJdbcInsert(jdbcTemplate).withTableName("GOOD")
        insert.setGeneratedKeyName("id")

        val args = mapOf("name" to good.name, "quantity" to good.quantity)
        val key = insert.executeAndReturnKey(args).toInt()

        return good.copy(id = key)
    }

    fun update(id: Int, good: Good): Good {
        if (exists(id)) {
            val (_, name, quantity) = good
            jdbcTemplate.execute("UPDATE GOOD SET name='$name', quantity='$quantity' WHERE id=$id")
            return good.copy(id = id)
        } else
            throw EntityNotFoundException()
    }

    fun delete(id: Int) {
        if (exists(id))
            jdbcTemplate.execute("DELETE FROM GOOD WHERE id = $id")
        else
            throw EntityNotFoundException()
    }

    private fun exists(id: Int): Boolean = findById(id).isPresent


    fun deleteAll() = jdbcTemplate.execute("DELETE FROM GOOD")

    private class GoodRowMapper : RowMapper<Good> {
        override fun mapRow(p0: ResultSet?, p1: Int): Good {
            val id = p0?.getInt("id")!!
            val name = p0.getString("name")!!
            val quantity = p0.getInt("quantity")
            return Good(id, name, quantity);
        }

    }
}