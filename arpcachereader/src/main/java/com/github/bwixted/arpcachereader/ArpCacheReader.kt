package com.github.bwixted.arpcachereader

import android.content.Context
import android.util.Log
import com.github.bwixted.arpcachereader.HardwareType
import com.github.bwixted.arpcachereader.HardwareTypes
import com.github.bwixted.arpcachereader.NetworkNode

import com.google.gson.Gson

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.HashMap

class ArpCacheReader {

    private val tag = "ArpTableAnalyzer"

    private val arpTableFilePath = "/proc/net/arp"

    private val mapNodes = HashMap<String, NetworkNode>()
    private var hardwareTypes = HardwareTypes()
    private var arpFlags = ArpFlags()

    val networkNodes: Map<String, NetworkNode>
        get() = mapNodes

    fun init(context: Context): Boolean {
        loadArpFlags(context)
        loadHardwareTypes(context)
        return readArpTable()
    }

    private fun loadHardwareTypes(context: Context) {

        val filename = "hardwaretypes.json"

        val json = context.assets.open(filename).bufferedReader().use{
            it.readText()
        }

        hardwareTypes = Gson().fromJson(json, HardwareTypes::class.java)

    }

    private fun loadArpFlags(context: Context) {


        val filename = "arpflags.json"

        val json = context.assets.open(filename).bufferedReader().use{
            it.readText()
        }

        arpFlags = Gson().fromJson(json, ArpFlags::class.java)

    }

    private fun findHardwareType(number: Int?): HardwareType {
        for (hardwareType in hardwareTypes) {
            if (hardwareType.number == number) {
                return hardwareType
            }
        }
        // must be unassigned
        return findHardwareType(-1)
    }

    private fun findHardwareType(hwType: String): HardwareType {

        // convert hex to dec
        var ht: Int? = -1
        try {
            ht = Integer.decode(hwType)
        } catch (e: NumberFormatException) {
        }

        return findHardwareType(ht)

    }

    private fun findArpFlag(number: Int?): ArpFlag {
        for (arpFlag in arpFlags) {
            if (arpFlag.number == number) {
                return arpFlag
            }
        }
        // must be unassigned
        return findArpFlag(-1)
    }

    private fun getArpFlag(flag: String): ArpFlag {

        // convert hex to dec
        var f: Int? = -1
        try {
            f = Integer.decode(flag)
        } catch (e: NumberFormatException) {
        }

        return findArpFlag(f)

    }

    private fun readArpEntry(tokens: List<String>,it: String) {

        val ip = tokens[0]
        if (ip == "IP") {
            // this is the header line; ignore
            return
        }

        val hwType = tokens[1]
        val hardwareType = findHardwareType(hwType)
        val flags = tokens[2]
        val (number, description) = getArpFlag(flags)
        val mac = tokens[3]
        val mask = tokens[4]
        val device = tokens[5]
        if (mac.matches("..:..:..:..:..:..".toRegex())) {
            val networkNode = NetworkNode(ip, hardwareType.number, hardwareType.description,
                    number, description, mac, mask, device, it)
            mapNodes[ip] = networkNode
        }

    }

    private fun readArpTable(): Boolean {

        val bufferedReader = File(arpTableFilePath).bufferedReader();
        val lineList = mutableListOf<String>()

        bufferedReader.useLines { lines -> lines.forEach { lineList.add(it) } }

        lineList.forEach {

            val tokens = it.split(" +".toRegex())

            if (tokens.size >= 4) {

                readArpEntry(tokens,it)

            }
        }

        return true
    }

}
