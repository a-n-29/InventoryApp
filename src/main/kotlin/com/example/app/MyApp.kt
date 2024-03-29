package com.example.app

import com.example.view.MainView
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(InventoryManagementWorkspace::class, Styles::class){

    override fun start(stage: Stage) {
        with(stage){
            width = 1200.0
            height = 600.0
        }

        super.start(stage)
    }
}