package com.example.controller

import com.example.model.IngredientsEntry
import com.example.model.IngredientsEntryModel
import com.example.model.IngredientsEntryTbl
import com.example.util.execute
import com.example.util.toDate
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import tornadofx.Controller
import java.time.LocalDate

class ItemController:Controller() {

    private val ingredientModel : IngredientsEntryModel()



    fun add(newItemName: String, newSubtype: String, newStorageLocation: String, newQuantity: Int, newDatePurchased: LocalDate, newAdditionalInformation: String) : IngredientsEntry {
        val newEntry = execute {
            IngredientsEntryTbl.insert {
                it[itemName] = newItemName
                it[subtype] = newSubtype
                it[storageLocation] = newStorageLocation
                it[quantity] = newQuantity
                it[datePurchased] = newDatePurchased.toDate()
                it[additionalInformation] = newAdditionalInformation
            }
        }
        return IngredientsEntry(newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation)
    }

    fun update(UpdatedItem: IngredientsEntryModel): Int {
        return execute {
            IngredientsEntryTbl.update({ IngredientsEntryTbl.itemName.value.equals(UpdatedItem.itemName.value) }) {
                it[itemName] = UpdatedItem.itemName.value
                it[subtype] = UpdatedItem.subtype. value
                it[storageLocation] = UpdatedItem.storageLocation.value
                it[quantity] = UpdatedItem.quantity.value
                it[datePurchased] = UpdatedItem.datePurchased.value.toDate()
                it[additionalInformation] = UpdatedItem.additionalInformation.value
            }
        }
    }
}