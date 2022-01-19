package com.example.app

import com.example.util.createTables
import com.example.util.enableConsoleLogger
import javafx.scene.control.TabPane
import org.jetbrains.exposed.sql.Database
import tornadofx.*

class InventoryManagementWorkspace : Workspace("Inventory Management", NavigationMode.Tabs) {
    init {
        // initilize database etc.
        enableConsoleLogger()
        Database.connect("jdbc:sqlite:./app-inventory-manager.db", "org.sqlite.JDBC")
        createTables()

        // pass in controller(s)

        // doc our views

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
