package com.example.app

import com.example.model.IngredientsEntryTbl
import com.example.util.createTables
import com.example.util.enableConsoleLogger
import com.example.util.execute
import com.example.util.toDate
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import tornadofx.*
import java.time.LocalDate

class InventoryManagementWorkspace : Workspace("Inventory Management", NavigationMode.Tabs) {
    init {
        // initilize database etc.
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./app-inventory-manager.db", "org.sqlite.JDBC")
        createTables()


        val newEntry = execute {
            IngredientsEntryTbl.insert {
                it[itemName]="carrot"
                it[subtype]="soup base"
                it[storageLocation]="drawer left of fridge"
                it[quantity]=2
                it[datePurchased]= LocalDate.now().toDate()
                it[additionalInformation]="use on Tuesday"
            }
        }

        // pass in controller(s)

        // doc our views

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
