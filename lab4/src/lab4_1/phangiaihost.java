package lab4_1;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class phangiaihost {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java network.HostInspector <hostname>");
			return;
		}

		try {
			InetAddress[] addresses = InetAddress.getAllByName(args[0]);
			System.out.println("Host: " + args[0]);
			for (InetAddress address : addresses) {
				System.out.println("- IP: " + address.getHostAddress());
				if (address instanceof Inet4Address) {
					System.out.println("  Type: IPv4");
				} else if (address instanceof Inet6Address) {
					System.out.println("  Type: IPv6");
				} else {
					System.out.println("  Type: Unknown");
				}

				System.out.println("  Canonical: " + address.getCanonicalHostName());
				System.out.println("  Loopback: " + address.isLoopbackAddress());
				System.out.println("  Site local: " + address.isSiteLocalAddress());
			}
		} catch (UnknownHostException e) {
			System.err.println("Không phân giải được host: " + args[0]);
		}
	}
}