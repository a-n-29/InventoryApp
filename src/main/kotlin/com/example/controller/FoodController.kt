package com.example.controller

import tornadofx.Controller
import com.example.model.FoodsEntry
import com.example.model.FoodsEntryModel
import com.example.model.FoodsEntryTbl
import com.example.model.toFoodsEntry
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

class FoodController:Controller() {
    val listOfItems: ObservableList<FoodsEntryModel> = execute {
        FoodsEntryTbl.selectAll().map{
            FoodsEntryModel().apply {
                item = it.toFoodsEntry()
            }
        }.asObservable()
    }

    @JvmName("getListOfItemsF")
    fun getListOfItems(): ObservableList<FoodsEntryModel> {
        return listOfItems
    }

    var items: ObservableList<FoodsEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    var foodModel = FoodsEntryModel()

    init {
        items = listOfItems

        items.forEach {
            pieItemsData.add(PieChart.Data(it.itemName.value, it.quantity.value.toDouble()))
        }
    }



    fun add(
        newItemName: String,
        newSubtype: String,
        newStorageLocation: String,
        newQuantity: Int,
        newDatePurchased: LocalDate,
        newAdditionalInformation: String,
        newMass: Double,
        newMassUnit: String
    ) : FoodsEntry {
        val newEntry = execute {
            FoodsEntryTbl.insert {
                //it[id] = newId
                it[itemName] = newItemName
                it[subtype] = newSubtype
                it[storageLocation] = newStorageLocation
                it[quantity] = newQuantity
                it[datePurchased] = newDatePurchased.toDate()
                it[additionalInformation] = newAdditionalInformation
                it[mass] = newMass
                it[massUnit] = newMassUnit
            }
        }

        listOfItems.add(
            FoodsEntryModel().apply {
                item = FoodsEntry(newEntry[FoodsEntryTbl.id], newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation, newMass, newMassUnit)
            }
        )
        pieItemsData.add(PieChart.Data(newItemName,newQuantity.toDouble()))

        return FoodsEntry(newEntry[FoodsEntryTbl.id], newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation, newMass, newMassUnit)

    }



    fun update(UpdatedItem: FoodsEntryModel): Int {
        return execute {
            FoodsEntryTbl.update({ FoodsEntryTbl.id eq (UpdatedItem.id.value.toInt()) }) {
                it[itemName] = UpdatedItem.itemName.value
                it[subtype] = UpdatedItem.subtype.value
                it[storageLocation] = UpdatedItem.storageLocation.value
                it[quantity] = UpdatedItem.quantity.value.toInt()
                it[datePurchased] = UpdatedItem.datePurchased.value.toDate()
                it[additionalInformation] = UpdatedItem.additionalInformation.value
                it[mass] = UpdatedItem.mass.value
                it[massUnit] = UpdatedItem.massUnit.value
            }
        }
    }

    fun delete(model: FoodsEntryModel) {
        execute {
            FoodsEntryTbl.deleteWhere{
                FoodsEntryTbl.id eq (model.id.value.toInt())
            }
        }
        listOfItems.remove(model)
        removeModelFromPie(model)
    }

    fun updatePiecePie(model: FoodsEntryModel) {
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

    fun removeModelFromPie(model: FoodsEntryModel) {
        var currIndex = 0

        pieItemsData.forEachIndexed{ index, data ->
            if (data.name == model.itemName.value && index !=-1){
                currIndex = index
            }
        }
        pieItemsData.removeAt(currIndex)

    }
}