package com.hinos.dagger2practice.data.cpu

class CPUIntel constructor(private val cpuModelName : String) : CPU
{
    override fun getCPUName(): String
    {
        return cpuModelName
    }

    override fun getCompanyName(): String
    {
        return "INTEL"
    }
}