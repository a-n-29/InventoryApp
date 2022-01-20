package com.example.view

import com.example.controller.ItemController
import com.example.model.IngredientsEntryModel
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

                        }
                    }
                }

                fieldset {
                    field("Subtype") {
                        maxWidth=220.0
                        textfield(model.subtype){

                        }

                    }
                }

                fieldset {
                    field("Storage Location") {
                        maxWidth=220.0
                        textfield(model.storageLocation){

                        }

                    }
                }

                fieldset {
                    field("Quantity") {
                        maxWidth=220.0
                        textfield(model.quantity){

                        }

                    }
                }

                fieldset{
                    field("Date Purchased") {
                        maxWidth=220.0
                        datepicker(model.datePurchased){
                        }
                    }
                }

                fieldset {
                    field("Additional Information") {
                        maxWidth=220.0
                        textfield(model.additionalInformation){

                        }

                    }
                }

                hbox( 10.0){
                    button("Add Item"){
                        action {
                            addItem()
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
        controller.add(model.id.value, model.itemName.value, model.subtype.value, model.storageLocation.value, model.quantity.value, model.datePurchased.value, model.additionalInformation.value )
    }
}
