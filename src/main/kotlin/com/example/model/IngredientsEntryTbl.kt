package com.example.model

import com.example.util.toJavaLocalDate
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import org.joda.time.LocalDate
import tornadofx.*

fun ResultRow.toIngredientsEntry() = IngredientsEntry(
    this[IngredientsEntryTbl.id],
    this[IngredientsEntryTbl.itemName],
    this[IngredientsEntryTbl.subtype],
    this[IngredientsEntryTbl.storageLocation],
    this[IngredientsEntryTbl.quantity],
    this[IngredientsEntryTbl.datePurchased].toJavaLocalDate(),
    this[IngredientsEntryTbl.additionalInformation]

)


object IngredientsEntryTbl : Table(){
    val id:Column<Int> = integer("id").autoIncrement().primaryKey()
    val itemName: Column<String> = varchar(name = "name", length = 50)
    val subtype: Column<String> = varchar(name = "subtype", length = 50)
    val storageLocation: Column<String> = varchar(name = "storage_location", length = 50)
    val quantity: Column<Int> = integer(name= "quantity")
    val datePurchased: Column<DateTime> = date(name = "date_purchased")
    val additionalInformation: Column<String> = varchar(name = "additional_info", length = 200)

}

class IngredientsEntry(id: Int, itemName: String, subtype: String, storageLocation: String, quantity: Int, datePurchased: java.time.LocalDate, additionalInformation: String){

    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val itemNameProperty = SimpleStringProperty(itemName)
    val itemName by itemNameProperty

    val subtypeProperty = SimpleStringProperty(subtype)
    val subtype by subtypeProperty

    val storageLocationProperty = SimpleStringProperty(itemName)
    val storageLocation by storageLocationProperty

    val quantityProperty = SimpleIntegerProperty(quantity)
    val quantity by quantityProperty

    val datePurchasedProperty = SimpleObjectProperty<java.time.LocalDate>(datePurchased)
    val datePurchased by datePurchasedProperty

    val additionalInformationProperty = SimpleStringProperty(additionalInformation)
    val additionalInformation by additionalInformationProperty

    override fun toString(): String {
        return "IngredientEntry(ExpensesEntry(id=$id, itemName=$itemName, subtype=$subtype, storageLocation=$storageLocation, quantity=$quantity, datePurchased=$datePurchased, additionalInformation=$additionalInformation"
    }
}

class IngredientsEntryModel : ItemViewModel<IngredientsEntry>(){
    val id = bind{item?.idProperty}
    val itemName = bind{item?.itemNameProperty}
    val subtype = bind{item?.subtypeProperty}
    val storageLocation = bind{item?.storageLocationProperty}
    val quantity = bind{item?.quantityProperty}
    val datePurchased = bind{item?.datePurchasedProperty}
    val additionalInformation = bind{item?.additionalInformationProperty}

}