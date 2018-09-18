# ArpCacheReader
Library for reading Android Arp Cache File

ArpCacheReader provides some handy classes for reading the Android ARP (Address Resolution Protocol) cache file which 
resides at /proc/net/arp on your Android phone. 

The ARP cache file contains a mapping of IP addresses for your local Wi-Fi network. The file contains some useful information
such as the MAC address corresponding to each IP address, hardware type, and some additional fields. This library
reads the ARP cache file on your Android device and provides classes that present this information for each node on
your network.

You can see this post on medium for an explanation: https://medium.com/@billwixted/first-release-of-arpcachereader-20fecc60e584

## Implementation

To integrate the arpcachereader library include the following in your app build.gradle

```groovy
    implementation 'com.google.code.gson:gson:2.8.0'
    implementation 'com.github.bwixted:arpcachereader:v1.0'
```

This includes arpcachereader and also gson which my library depends on

In your Kotlin code import the package

```kotlin
import com.github.bwixted.arpcachereader.ArpCacheReader
```

To get started create an ArpCacheReader and initialize it passing in an Andriod context

```kotlin
        val arpCacheReader = ArpCacheReader()

        if (arpCacheReader.init(this)) {
            Log.d(tag, "init completed successfully")
        }
```

To traverse the list of nodes found in your ARP cache get the "networkNodes" and iterate through it:

```kotlin
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

```

That's it. This is a pretty simple library but it will save you some time if you need to access this information and it also provides some helpful textual descriptions via the "hwTypeDescription" and "flagsDescription" fields.


## License

    Copyright (C) 2018 Bill Wixted

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



