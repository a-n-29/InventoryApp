package com.example.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import org.joda.time.LocalDate
import tornadofx.*

object IngredientsEntryTbl : Table(){

    val itemName: Column<String> = varchar(name = "name", length = 50)
    val subtype: Column<String> = varchar(name = "subtype", length = 50)
    val storageLocation: Column<String> = varchar(name = "storage_location", length = 50)
    val quantity: Column<Int> = integer(name= "quantity").autoIncrement().primaryKey()
    val datePurchased: Column<DateTime> = date(name = "date_purchased")
    val additionalInformation: Column<String> = varchar(name = "additional_info", length = 200)

}

class IngredientsEntry(itemName: String, subtype: String, storageLocation: String, quantity: Int, datePurchased: LocalDate, additionalInformation: String){

    val itemNameProperty = SimpleStringProperty(itemName)
    val itemName by itemNameProperty

    val subtypeProperty = SimpleStringProperty(subtype)
    val subtype by subtypeProperty

    val storageLocationProperty = SimpleStringProperty(itemName)
    val storageLocation by storageLocationProperty

    val quantityProperty = SimpleIntegerProperty(quantity)
    val quantity by quantityProperty

    val datePurchasedProperty = SimpleObjectProperty<LocalDate>(datePurchased)
    val datePurchased by datePurchasedProperty

    val additionalInformationProperty = SimpleStringProperty(additionalInformation)
    val additionalInformation by additionalInformationProperty

    override fun toString(): String {
        return "IngredientEntry(itemName=$itemName, subtype=$subtype, storageLocation=$storageLocation, quantity=$quantity, datePurchased=$datePurchased, additionalInformation=$additionalInformation"
    }
}