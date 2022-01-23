package com.example.view

import com.example.controller.BeverageController
import com.example.model.BeveragesEntryModel
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import tornadofx.*
import kotlin.error

class BeveragesEditor:View("Beverage Item Editor") {
    private val model = BeveragesEntryModel()
    val controller: BeverageController by inject()

    var mTableView: TableViewEditModel<BeveragesEntryModel> by singleAssign()

    override val root = borderpane {

        left = vbox {
            form{


                fieldset {
                    field("Item Name"){
                        maxWidth=220.0
                        textfield(model.itemName){
                            this.required()
                            validator{
                                when{
                                    it.isNullOrEmpty() -> error("Field cannot be empty")
                                    it.length < 3 -> error("Too short")
                                    else -> null
                                }
                            }

                        }
                    }
                }

                fieldset {
                    field("Subtype") {
                        maxWidth=220.0
                        textfield(model.subtype){
                            this.required()
                            validator{
                                when{
                                    it.isNullOrEmpty() -> error("Field cannot be empty")
                                    it.length < 3 -> error("Too short")
                                    else -> null
                                }
                            }
                        }

                    }
                }

                fieldset {
                    field("Volume") {
                        maxWidth=220.0
                        textfield(model.volume){
                            this.required()
                            validator{
                                when(it){
                                    null -> error("The quantity cannot be blank")
                                    else -> null
                                }
                            }
                        }

                    }
                }

                fieldset {
                    field("Volume Unit") {
                        maxWidth=220.0
                        textfield(model.volumeUnit){
                            this.required()
                            validator{
                                when(it){
                                    null -> error("The quantity cannot be blank")
                                    else -> null
                                }
                            }
                        }

                    }
                }

                fieldset {
                    field("Storage Location") {
                        maxWidth=220.0
                        textfield(model.storageLocation){
                            this.required()
                            validator{
                                when{
                                    it.isNullOrEmpty() -> error("Field cannot be empty")
                                    it.length < 3 -> error("Too short")
                                    else -> null
                                }
                            }
                        }

                    }
                }

                fieldset {
                    field("Quantity") {
                        maxWidth=220.0
                        textfield(model.quantity){
                            this.required()
                            validator{
                                when(it){
                                    null -> error("The quantity cannot be blank")
                                    else -> null
                                }
                            }
                        }

                    }
                }

                fieldset{
                    field("Date Purchased") {
                        maxWidth=220.0
                        datepicker(model.datePurchased){
                            this.required()
                            validator {
                                when {
                                    it?.dayOfMonth.toString().isEmpty()
                                            || it?.month.toString().isEmpty()
                                            || it?.year.toString().isEmpty() -> error("The date entry cannot be blank")
                                    else -> null
                                }
                            }
                        }
                    }
                }

                fieldset {
                    field("Additional Information") {
                        maxWidth=220.0
                        textfield(model.additionalInformation){
                            this.required()
                            validator{
                                when{
                                    it.isNullOrEmpty() -> error("Field cannot be empty")
                                    it.length < 3 -> error("Too short")
                                    else -> null
                                }
                            }
                            setOnKeyPressed {
                                if (it.code == KeyCode.ENTER)
                                {
                                    model.commit{
                                        addItem()
                                        model.rollback()
                                    }
                                }
                            }
                        }

                    }
                }

                hbox( 10.0){

                    button("Add Item"){
                        enableWhen(model.valid)
                        action {
                            model.commit{
                                addItem()
                                model.rollback()
                            }
                        }
                    }

                    button("Delete"){
                        action {
                            val selectedItem = mTableView.tableView.selectedItem
                            controller.delete(selectedItem!!)
                        }
                    }

                    button("Reset"){
                        action {  }
                    }

                }



            }
        }

        center = vbox {
            form{
                fieldset {
                    tableview<BeveragesEntryModel> {
                        items = controller.items
                        mTableView = editModel

                        column("ID", BeveragesEntryModel::id)
                        column("Item Name", BeveragesEntryModel::itemName).makeEditable()
                        column("Subtype", BeveragesEntryModel::subtype).makeEditable()
                        column("Volume", BeveragesEntryModel::volume).makeEditable()
                        column("Volume Unit", BeveragesEntryModel::volumeUnit).makeEditable()
                        column("Storage Location", BeveragesEntryModel::storageLocation).makeEditable()
                        column("Quantity", BeveragesEntryModel::quantity).makeEditable()
                        column("Date Purchased", BeveragesEntryModel::datePurchased).makeEditable()
                        column("Additional Information", BeveragesEntryModel::additionalInformation).makeEditable()

                        onEditCommit {
                            controller.update(it)
                            controller.updatePiecePie(it)
                        }

                    }
                }
            }
        }

        right = vbox {
            alignment = Pos.CENTER
            piechart {
                data = controller.pieItemsData
            }
        }
    }

    private fun addItem() {
        controller.add(model.itemName.value,
            model.subtype.value, model.storageLocation.value, model.quantity.value, model.datePurchased.value, model.additionalInformation.value, model.volume.value, model.volumeUnit.value )
    }
}