package com.example.view

import com.example.controller.FoodController
import com.example.model.FoodsEntryModel
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import tornadofx.*

class FoodsEditor: View("Food Item Editor") {
    private val model = FoodsEntryModel()
    val controller: FoodController by inject()

    var mTableView: TableViewEditModel<FoodsEntryModel> by singleAssign()

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
                    field("Mass") {
                        maxWidth=220.0
                        textfield(model.mass){
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
                    field("Mass Unit") {
                        maxWidth=220.0
                        textfield(model.massUnit){
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
                    tableview<FoodsEntryModel> {
                        items = controller.items
                        mTableView = editModel

                        column("ID", FoodsEntryModel::id)
                        column("Item Name", FoodsEntryModel::itemName).makeEditable()
                        column("Subtype", FoodsEntryModel::subtype).makeEditable()
                        column("Mass", FoodsEntryModel::mass).makeEditable()
                        column("Mass Unit", FoodsEntryModel::massUnit).makeEditable()
                        column("Storage Location", FoodsEntryModel::storageLocation).makeEditable()
                        column("Quantity", FoodsEntryModel::quantity).makeEditable()
                        column("Date Purchased", FoodsEntryModel::datePurchased).makeEditable()
                        column("Additional Information", FoodsEntryModel::additionalInformation).makeEditable()

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
            model.subtype.value, model.storageLocation.value, model.quantity.value, model.datePurchased.value, model.additionalInformation.value, model.mass.value, model.massUnit.value )
    }
}