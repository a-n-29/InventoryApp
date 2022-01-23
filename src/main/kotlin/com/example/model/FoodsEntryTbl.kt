package com.example.model


import com.example.model.FoodsEntryTbl.autoIncrement
import com.example.model.FoodsEntryTbl.primaryKey
import com.example.util.toJavaLocalDate
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import org.joda.time.LocalDate
import tornadofx.*

fun ResultRow.toFoodsEntry() = FoodsEntry(
    this[FoodsEntryTbl.id],
    this[FoodsEntryTbl.itemName],
    this[FoodsEntryTbl.subtype],
    this[FoodsEntryTbl.storageLocation],
    this[FoodsEntryTbl.quantity],
    this[FoodsEntryTbl.datePurchased].toJavaLocalDate(),
    this[FoodsEntryTbl.additionalInformation],
    this[FoodsEntryTbl.mass],
    this[FoodsEntryTbl.massUnit]


)

object FoodsEntryTbl: Table() {
    val id:Column<Int> = integer("id").autoIncrement().primaryKey()
    val itemName: Column<String> = varchar(name = "name", length = 50)
    val subtype: Column<String> = varchar(name = "subtype", length = 50)
    val storageLocation: Column<String> = varchar(name = "storage_location", length = 50)
    val quantity: Column<Int> = integer(name= "quantity")
    val datePurchased: Column<DateTime> = date(name = "date_purchased")
    val additionalInformation: Column<String> = varchar(name = "additional_info", length = 200)

    val mass: Column<Double> = double(name = "mass")
    val massUnit: Column<String> = varchar(name = "mass_unit", length = 50)
}


class FoodsEntry(id: Int, itemName: String, subtype: String, storageLocation: String, quantity: Int, datePurchased: java.time.LocalDate, additionalInformation: String, mass: Double, massUnit: String){

    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val itemNameProperty = SimpleStringProperty(itemName)
    val itemName by itemNameProperty

    val subtypeProperty = SimpleStringProperty(subtype)
    val subtype by subtypeProperty

    val storageLocationProperty = SimpleStringProperty(storageLocation)
    val storageLocation by storageLocationProperty

    val quantityProperty = SimpleIntegerProperty(quantity)
    val quantity by quantityProperty

    val datePurchasedProperty = SimpleObjectProperty<java.time.LocalDate>(datePurchased)
    val datePurchased by datePurchasedProperty

    val additionalInformationProperty = SimpleStringProperty(additionalInformation)
    val additionalInformation by additionalInformationProperty

    val massProperty = SimpleDoubleProperty(mass)
    val mass by massProperty

    val massUnitProperty = SimpleStringProperty(massUnit)
    val massUnit by massUnitProperty

    override fun toString(): String {
        return "FoodsEntry(id=$id, itemName=$itemName, subtype=$subtype, storageLocation=$storageLocation, quantity=$quantity, datePurchased=$datePurchased, additionalInformation=$additionalInformation, mass=$mass, massUnit=$massUnit)"
    }
}

class FoodsEntryModel : ItemViewModel<FoodsEntry>(){
    val id = bind{item?.idProperty}
    val itemName = bind{item?.itemNameProperty}
    val subtype = bind{item?.subtypeProperty}
    val storageLocation = bind{item?.storageLocationProperty}
    val quantity = bind{item?.quantityProperty}
    val datePurchased = bind{item?.datePurchasedProperty}
    val additionalInformation = bind{item?.additionalInformationProperty}
    val mass = bind{item?.massProperty}
    val massUnit = bind{item?.massUnitProperty}

}