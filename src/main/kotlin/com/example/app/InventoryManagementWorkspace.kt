package com.example.app

import com.example.controller.BeverageController
import com.example.controller.FoodController
import com.example.controller.IngredientController
import com.example.model.BeveragesEntryModel
import com.example.model.FoodsEntryModel
import com.example.model.IngredientsEntryModel
import com.example.util.createTables
import com.example.util.enableConsoleLogger
import com.example.view.BeveragesEditor
import com.example.view.FoodsEditor
import com.example.view.IngredientsEditor
import javafx.collections.ObservableList
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
        //var controllerIn: Controller = IngredientController()
        var listI: ObservableList<IngredientsEntryModel> = IngredientController().listOfItems
        //var controllerFd: Controller = FoodController()
        var listF: ObservableList<FoodsEntryModel> = FoodController().listOfItems
        // can't get it to print to terminal for some reason ---> print(listF)
        //var controllerBv: Controller = BeverageController()
        var listB: ObservableList<BeveragesEntryModel> = BeverageController().listOfItems

        // doc our views
        dock<IngredientsEditor>()
        dock<FoodsEditor>()
        dock<BeveragesEditor>()


        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
