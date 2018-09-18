# ArpCacheReader
Library for reading Android Arp Cache File

ArpCacheReader provides some handy classes for reading the Android ARP (Address Resolution Protocol) cache file which 
resides at /proc/net/arp on your Android phone. 

The ARP cache file contains a mapping of IP addresses for your local Wi-Fi network. The file contains some useful information
such as the MAC address corresponding to each IP address, hardware type, and some additional fields. This library
reads the ARP cache file on your Android device and provides classes that present this information for each node on
your network.

You can see this post on medium for an explanation: https://medium.com/@billwixted/first-release-of-arpcachereader-20fecc60e584
