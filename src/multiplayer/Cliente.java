package multiplayer;

import java.util.Scanner;

public class Cliente {
    private String server_ip;
    private int server_port;
    
    public void login(Scanner tc) {
    	System.out.println("Qual é o IP do servidor local? ");
    	this.server_ip = tc.next(); // Ip do dev: 192.168.3.52 
    	System.out.println("Qual é a porta do servidor local? ");
    	this.server_port = tc.nextInt(); // normalmente 5000
    }
}
