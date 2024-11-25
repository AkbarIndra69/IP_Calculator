/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI2;

/**
 *
 * @author AKBAR
 */
public class IPAddress {
    private int[] octets;
    
    public IPAddress(String ip) throws InvalidIPException {
        this.octets = new int[4];
        parseIP(ip);
    }
    
    private void parseIP(String ip) throws InvalidIPException {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            throw new InvalidIPException("IP address harus memiliki 4 oktet");
        }
        
        for (int i = 0; i < 4; i++) {
            try {
                octets[i] = Integer.parseInt(parts[i]);
                if (octets[i] < 0 || octets[i] > 255) {
                    throw new InvalidIPException("Isi IP Addrress dengan angka 0 sampai 255");
                }
            } catch (NumberFormatException e) {
                throw new InvalidIPException("IP Address harus diisi dengan angka atau tidak boleh kosong!!");
            }
        }
    }
    
    public int[] getOctets() {
        return octets.clone();
    }
    
    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d", octets[0], octets[1], octets[2], octets[3]);
    }
}
