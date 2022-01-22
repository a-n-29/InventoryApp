package com.example.controller

import com.example.model.IngredientsEntry
import com.example.model.IngredientsEntryModel
import com.example.model.IngredientsEntryTbl
import com.example.model.toIngredientsEntry
import com.example.util.execute
import com.example.util.toDate
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.time.LocalDate

class ItemController:Controller() {

    private val listOfItems: ObservableList<IngredientsEntryModel> = execute {
        IngredientsEntryTbl.selectAll().map{
            IngredientsEntryModel().apply {
                item = it.toIngredientsEntry()
            }
        }.asObservable()
    }

    var items: ObservableList<IngredientsEntryModel> by singleAssign()

    var ingredientModel = IngredientsEntryModel()

    init {
        items = listOfItems
    }



    fun add( newItemName: String, newSubtype: String, newStorageLocation: String, newQuantity: Int, newDatePurchased: LocalDate, newAdditionalInformation: String) : IngredientsEntry {
        val newEntry = execute {
            IngredientsEntryTbl.insert {
                //it[id] = newId
                it[itemName] = newItemName
                it[subtype] = newSubtype
                it[storageLocation] = newStorageLocation
                it[quantity] = newQuantity
                it[datePurchased] = newDatePurchased.toDate()
                it[additionalInformation] = newAdditionalInformation
            }
        }

        listOfItems.add(
            IngredientsEntryModel().apply {
                item = IngredientsEntry(newEntry[IngredientsEntryTbl.id], newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation)
            }
        )

        return IngredientsEntry(newEntry[IngredientsEntryTbl.id], newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation)

    }



    fun update(UpdatedItem: IngredientsEntryModel): Int {
        return execute {
            IngredientsEntryTbl.update({ IngredientsEntryTbl.id eq (UpdatedItem.id.value.toInt()) }) {
                it[itemName] = UpdatedItem.itemName.value
                it[subtype] = UpdatedItem.subtype.value
                it[storageLocation] = UpdatedItem.storageLocation.value
                it[quantity] = UpdatedItem.quantity.value.toInt()
                it[datePurchased] = UpdatedItem.datePurchased.value.toDate()
                it[additionalInformation] = UpdatedItem.additionalInformation.value
            }
        }
    }

    fun delete(model: IngredientsEntryModel) {
        execute {
            IngredientsEntryTbl.deleteWhere{
                IngredientsEntryTbl.id eq (model.id.value.toInt())
            }
        }
        listOfItems.remove(model)
    }
}