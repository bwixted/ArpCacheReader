package com.github.bwixted.arpsamples

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.bwixted.arpcachereader.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tag = "ArpCacheReader"
        Log.i(tag, tag);

        val arpCacheReader = ArpCacheReader()

        if (arpCacheReader.init(this)) {
            Log.d(tag, "init completed successfully")
        }

        val networkNodeMap = arpCacheReader.networkNodes

        for (entry in networkNodeMap.entries) {
            Log.i(tag, "----------------------------------")

            val networkNode = entry.value

            Log.i(tag, "NetworkNode")
            Log.i(tag, "Raw Entry: " + networkNode.entry)
            Log.i(tag, "IP: " + networkNode.ip)
            Log.i(tag, "Hardware Type: " + networkNode.hwType)
            Log.i(tag, "Hardware Type Description: " + networkNode.hwTypeDescription)
            Log.i(tag, "Flags: " + networkNode.flags)

            Log.i(tag, "Flags Description: " + networkNode.flagsDescription)
            Log.i(tag, "Mac: " + networkNode.mac)
            Log.i(tag, "Mask: " + networkNode.mask)
            Log.i(tag, "Device: " + networkNode.device)
        }

    }
}

