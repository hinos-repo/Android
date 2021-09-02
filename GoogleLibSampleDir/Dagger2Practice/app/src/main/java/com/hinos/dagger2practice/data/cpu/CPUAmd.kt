package com.hinos.dagger2practice.data.cpu


class CPUAmd constructor(private val cpuModelName : String) : CPU
{
    override fun getCPUName(): String
    {
        return cpuModelName
    }

    override fun getCompanyName(): String
    {
        return "AMD"
    }
}