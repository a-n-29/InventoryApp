package com.example.controller

import com.example.model.IngredientsEntry
import com.example.model.IngredientsEntryModel
import com.example.model.IngredientsEntryTbl
import com.example.model.toIngredientsEntry
import com.example.util.execute
import com.example.util.toDate
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.time.LocalDate

class IngredientController:Controller() {

    private val listOfItems: ObservableList<IngredientsEntryModel> = execute {
        IngredientsEntryTbl.selectAll().map{
            IngredientsEntryModel().apply {
                item = it.toIngredientsEntry()
            }
        }.asObservable()
    }

    var items: ObservableList<IngredientsEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    var ingredientModel = IngredientsEntryModel()

    init {
        items = listOfItems

        items.forEach {
            pieItemsData.add(PieChart.Data(it.itemName.value, it.quantity.value.toDouble()))
        }
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
        pieItemsData.add(PieChart.Data(newItemName,newQuantity.toDouble()))

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
        removeModelFromPie(model)
    }

    fun updatePiecePie(model: IngredientsEntryModel) {
        val modelId = model.id
        var currIndex: Int

        items.forEachIndexed { index, data ->
            if (modelId == data.id) {
                // we have right object to update
                currIndex = index
                pieItemsData[currIndex].name = data.itemName.value
                pieItemsData[currIndex].pieValue = data.quantity.value.toDouble()
            } else { // ignore}

            }
        }
    }

     fun removeModelFromPie(model: IngredientsEntryModel) {
        var currIndex = 0

        pieItemsData.forEachIndexed{ index, data ->
            if (data.name == model.itemName.value && index !=-1){
                currIndex = index
            }
        }
        pieItemsData.removeAt(currIndex)

    }
}