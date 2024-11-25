/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI2;


/**
 *
 * @author AKBAR
 */
public class SubnetMask {
    private int prefix;
    private int[] maskOctets;
    
    public SubnetMask(int prefix) throws InvalidSubnetException {
        if (prefix < 1 || prefix > 31) {
            throw new InvalidSubnetException("Subnet mask harus antara /1 dan /31");
        }
        this.prefix = prefix;
        this.maskOctets = calculateMaskOctets();
    }
    
    private int[] calculateMaskOctets() {
        int[] mask = new int[4];
        int bits = prefix;
        
        for (int i = 0; i < 4; i++) {
            mask[i] = getMaskOctet(bits > 8 ? 8 : bits);
            bits -= 8;
            if (bits < 0) bits = 0;
        }
        
        return mask;
    }
    
    private int getMaskOctet(int bits) {
        return (0xFF << (8 - bits)) & 0xFF;
    }
    
    public int getPrefix() {
        return prefix;
    }
    
    public int[] getMaskOctets() {
        return maskOctets.clone();
    }
    
    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d", maskOctets[0], maskOctets[1], maskOctets[2], maskOctets[3]);
    }
}
