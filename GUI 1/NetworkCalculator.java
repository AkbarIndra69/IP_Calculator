/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author AKBAR
 */
// File: NetworkCalculator.java
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
}
