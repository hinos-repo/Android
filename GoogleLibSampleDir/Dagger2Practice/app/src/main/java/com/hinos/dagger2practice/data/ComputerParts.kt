package com.hinos.dagger2practice.data

import com.hinos.dagger2practice.data.cpu.CPU

data class ComputerParts(
    val cpu : String,
    val ramSize : String,
    val diskSize : String
)