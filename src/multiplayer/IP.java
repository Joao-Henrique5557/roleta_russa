package multiplayer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IP {
	public static String getIP() throws SocketException {
		// interfaces do pc
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            // pegar ips da interface
            Enumeration<InetAddress> ips = ni.getInetAddresses();

            while (ips.hasMoreElements()) {
            	// pecorre os ips das interfaces
                InetAddress ip = ips.nextElement();

                // se ip não for um localhost e ip for um IPV4
                if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                	// retorna o primeiro ip válido
                    return ip.getHostAddress();
                }
            }
        }
        return "IPV4 não encontrado. Porfavor conectese a internet, use uma rede IPV6 ou procure problemas de DHCP!";
    }
}
