package com.github.bwixted.arpcachereader

import android.util.Log

import java.net.InetAddress

data class NetworkNode(val ip: String,              // ip address
                   val hwType: Int,                 // data link layer protocol (1=ethernet)
                   val hwTypeDescription: String,   // description of "hwType"
                   val flags: Int,                  // arp flags
                   val flagsDescription: String,    // description of "flags"
                   val mac: String,                 // hardware address
                   val mask: String,                // netmask
                   val device: String,              // device such as "wlan0" etc.
                   val entry: String?) {            // raw entry from the arp table file

}
