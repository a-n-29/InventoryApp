package com.example.controller

import tornadofx.Controller
import com.example.model.BeveragesEntry
import com.example.model.BeveragesEntryModel
import com.example.model.BeveragesEntryTbl
import com.example.model.toBeveragesEntry
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

class BeverageController:Controller() {
    private val listOfItems: ObservableList<BeveragesEntryModel> = execute {
        BeveragesEntryTbl.selectAll().map{
            BeveragesEntryModel().apply {
                item = it.toBeveragesEntry()
            }
        }.asObservable()
    }

    var items: ObservableList<BeveragesEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    var foodModel = BeveragesEntryModel()

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
        newVolume: Double,
        newVolumeUnit: String
    ) : BeveragesEntry {
        val newEntry = execute {
            BeveragesEntryTbl.insert {
                //it[id] = newId
                it[itemName] = newItemName
                it[subtype] = newSubtype
                it[storageLocation] = newStorageLocation
                it[quantity] = newQuantity
                it[datePurchased] = newDatePurchased.toDate()
                it[additionalInformation] = newAdditionalInformation
                it[volume] = newVolume
                it[volumeUnit] = newVolumeUnit
            }
        }

        listOfItems.add(
            BeveragesEntryModel().apply {
                item = BeveragesEntry(newEntry[BeveragesEntryTbl.id], newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation, newVolume, newVolumeUnit)
            }
        )
        pieItemsData.add(PieChart.Data(newItemName,newQuantity.toDouble()))

        return BeveragesEntry(newEntry[BeveragesEntryTbl.id], newItemName, newSubtype, newStorageLocation, newQuantity, newDatePurchased, newAdditionalInformation, newVolume, newVolumeUnit)

    }



    fun update(UpdatedItem: BeveragesEntryModel): Int {
        return execute {
            BeveragesEntryTbl.update({ BeveragesEntryTbl.id eq (UpdatedItem.id.value.toInt()) }) {
                it[itemName] = UpdatedItem.itemName.value
                it[subtype] = UpdatedItem.subtype.value
                it[storageLocation] = UpdatedItem.storageLocation.value
                it[quantity] = UpdatedItem.quantity.value.toInt()
                it[datePurchased] = UpdatedItem.datePurchased.value.toDate()
                it[additionalInformation] = UpdatedItem.additionalInformation.value
                it[volume] = UpdatedItem.volume.value
                it[volumeUnit] = UpdatedItem.volumeUnit.value
            }
        }
    }

    fun delete(model: BeveragesEntryModel) {
        execute {
            BeveragesEntryTbl.deleteWhere{
                BeveragesEntryTbl.id eq (model.id.value.toInt())
            }
        }
        listOfItems.remove(model)
        removeModelFromPie(model)
    }

    fun updatePiecePie(model: BeveragesEntryModel) {
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

    fun removeModelFromPie(model: BeveragesEntryModel) {
        var currIndex = 0

        pieItemsData.forEachIndexed{ index, data ->
            if (data.name == model.itemName.value && index !=-1){
                currIndex = index
            }
        }
        pieItemsData.removeAt(currIndex)

    }
}