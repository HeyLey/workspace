package iga.workspace.models

import io.ktor.application.ApplicationCall
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.eq
import org.litote.kmongo.newId
import org.litote.kmongo.or

suspend fun ApplicationCall.takeTable(employeeName: String, tableName: String) {
    mongo().tables.setEmployee(tableName, employeeName)
}


data class Table(
    val employeeName: String,
    val name: String,
    @BsonId
    val id: Id<Table> = newId()
)

val MongoDB.tables get() = TableOps(this)

class TableOps(private val db: MongoDB) {
    val collection get() = db.getCollection<Table>("tables")

    suspend fun byName(id: String): Table? = collection.find(Table::name eq id).first()

    suspend fun addTable(name: String) {
        if (collection.findOne(Table::name eq name) == null) {
            collection.insertOne(Table(employeeName="", name = name))
        }
    }
    suspend fun setEmployee(tableName: String, employeeName: String) {
        val table = collection.find(Table::name eq tableName).first()
        if (table == null) {
            collection.insertOne(Table(employeeName=employeeName, name = tableName))
        } else {
            collection.replaceOne(Table::name eq tableName,
                table.copy(
                    employeeName = employeeName,
                    name = tableName
                )
            )
        }
    }

    suspend fun searchEmployee(employeeName: String): List<Table> = collection.find(Table::employeeName eq employeeName).toList()

}