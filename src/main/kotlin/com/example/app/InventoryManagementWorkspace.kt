package com.example.app

import com.example.controller.FoodController
import com.example.controller.IngredientController
import com.example.util.createTables
import com.example.util.enableConsoleLogger
import com.example.view.FoodsEditor
import com.example.view.IngredientsEditor
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class InventoryManagementWorkspace : Workspace("Inventory Management", NavigationMode.Tabs) {
    init {
        // initilize database etc.
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./app-inventory-manager.db", "org.sqlite.JDBC")
        createTables()

        /*
        val newEntry = execute {
            IngredientsEntryTbl.insert {
                it[itemName]="carrot"
                it[subtype]="soup base"
                it[storageLocation]="drawer left of fridge"
                it[quantity]=2
                it[datePurchased]= LocalDate.now().toDate()
                it[additionalInformation]="use on Tuesday"
            }
        }*/

        // pass in controller(s)
        IngredientController()
        //FoodController()

        // doc our views
        dock<IngredientsEditor>()
        //dock<FoodsEditor>()


        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
