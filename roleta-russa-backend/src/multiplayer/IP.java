package multiplayer;

import java.net.*;
import java.util.Enumeration;

public class IP {
	public static String getIP() throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); // lista de interfaces do dispositivo
		
		while (interfaces.hasMoreElements()) {
			NetworkInterface ni = interfaces.nextElement();
			Enumeration<InetAddress> ips = ni.getInetAddresses();
			while (ips.hasMoreElements()) {
				InetAddress ip = ips.nextElement();
				if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
					return ip.getHostAddress();
				}
			}
		}
		return "127.0.0.1";
	}
}
