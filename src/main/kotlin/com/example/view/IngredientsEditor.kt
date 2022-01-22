package com.example.view

import com.example.controller.ItemController
import com.example.model.IngredientsEntryModel
import javafx.scene.input.KeyCode
import tornadofx.*

class IngredientsEditor : View("Ingredient Item Editor") {
    private val model = IngredientsEntryModel()
    val controller: ItemController by inject()

    override val root = borderpane {

        center = vbox {
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
                        action {  }
                    }

                    button("Reset"){
                        action {  }
                    }

                }

                fieldset {
                    tableview<IngredientsEntryModel> {
                        items = controller.items

                        column("ID", IngredientsEntryModel::id)
                        column("Item Name",IngredientsEntryModel::itemName)
                        column("Subtype",IngredientsEntryModel::subtype)
                        column("Storage Location",IngredientsEntryModel::storageLocation)
                        column("Quantity",IngredientsEntryModel::quantity)
                        column("Date Purchased",IngredientsEntryModel::datePurchased)
                        column("Additional Information",IngredientsEntryModel::additionalInformation)

                    }
                }

            }
        }
    }

    private fun addItem() {
        controller.add(model.itemName.value, model.subtype.value, model.storageLocation.value, model.quantity.value, model.datePurchased.value, model.additionalInformation.value )
    }
}
