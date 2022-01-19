package com.example.app

import javafx.scene.control.TabPane
import tornadofx.*

class InventoryManagementWorkspace : Workspace("Inventory Management", NavigationMode.Tabs) {
    init {
        // initilize database etc.

        // pass in controller(s)

        // doc our views

        tabContainer.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    }
}
