/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI2;


/**
 *
 * @author AKBAR
 */
public class NetworkCalculator {
    private IPAddress ip;
    private SubnetMask subnetMask;
    
    public NetworkCalculator(IPAddress ip, SubnetMask subnetMask) {
        this.ip = ip;
        this.subnetMask = subnetMask;
    }
    
    public String getNetworkAddress() {
        int[] ipOctets = ip.getOctets();
        int[] maskOctets = subnetMask.getMaskOctets();
        int[] network = new int[4];
        
        for (int i = 0; i < 4; i++) {
            network[i] = ipOctets[i] & maskOctets[i];
        }
        
        return String.format("%d.%d.%d.%d", network[0], network[1], network[2], network[3]);
    }
    
    public String getBroadcastAddress() {
        int[] ipOctets = ip.getOctets();
        int[] maskOctets = subnetMask.getMaskOctets();
        int[] broadcast = new int[4];
        
        for (int i = 0; i < 4; i++) {
            broadcast[i] = ipOctets[i] | (~maskOctets[i] & 0xFF);
        }
        
        return String.format("%d.%d.%d.%d", broadcast[0], broadcast[1], broadcast[2], broadcast[3]);
    }
    
    public int getNumberOfHosts() {
        return (int) Math.pow(2, 32 - subnetMask.getPrefix()) - 2;
    }
    
    public int getNumberOfSubnets() {
        int firstOctet = ip.getOctets()[0];
        int maskPrefix = subnetMask.getPrefix();
        int defaultPrefix;
        int maxPrefix;
        
        // Menentukan default prefix dan max prefix berdasarkan kelas IP
        if (firstOctet >= 1 && firstOctet <= 126) { // Class A
            defaultPrefix = 8;
            maxPrefix = 30;  // Menyisakan minimal 2 bit untuk host
        } else if (firstOctet >= 128 && firstOctet <= 191) { // Class B
            defaultPrefix = 16;
            maxPrefix = 30;
        } else if (firstOctet >= 192 && firstOctet <= 223) { // Class C
            defaultPrefix = 24;
            maxPrefix = 30;
        } else {
            // IP address tidak valid atau merupakan kelas khusus
            return 0;
        }
        
        // Memastikan subnet mask tidak melebihi batas maksimum
        if (maskPrefix > maxPrefix) {
            maskPrefix = maxPrefix;
        }
        
        // Menghitung jumlah bit yang dipinjam
        int borrowedBits = maskPrefix - defaultPrefix;
        
        // Hanya menghitung subnet jika ada bit yang dipinjam
        if (borrowedBits > 0) {
            return (int) Math.pow(2, borrowedBits);
        }
        
        return 1; // Jika tidak ada subnetting, hanya ada 1 network
    }
}