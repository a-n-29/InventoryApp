package com.example.view

import com.example.model.IngredientsEntryModel
import tornadofx.*

class IngredientsEditor : View("My View") {
    override val root = borderpane {

        val model = IngredientsEntryModel()

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
                        action {  }
                    }

                    button("Delete"){
                        action {  }
                    }

                    button("Reset"){
                        action {  }
                    }
                }

            }
        }
    }
}
