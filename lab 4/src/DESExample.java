import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class DESExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți blocul de 8 biți (ex. 10101010): ");
        String inputBits = scanner.nextLine();

        // Verifică dacă blocul de intrare are dimensiunea corectă
        if (inputBits.length() != 8) {
            System.out.println("Blocul de intrare trebuie să fie de 8 biți.");
            return;
        }

        byte[] input = inputBits.getBytes(StandardCharsets.UTF_8);

        // Generare cheie DES
        byte[] key = generateDESKey();

        // Criptare DES
        byte[] encrypted = desEncrypt(key, input);

        System.out.println("Cheia generată: " + Arrays.toString(key));
        System.out.println("Text criptat: " + Arrays.toString(encrypted));
    }

    private static byte[] generateDESKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[8];
        random.nextBytes(key);
        return key;
    }

    private static byte[] desEncrypt(byte[] key, byte[] input) {
        BufferedBlockCipher cipher = new BufferedBlockCipher(new DESEngine());

        CipherParameters keyParam = new KeyParameter(key);
        cipher.init(true, keyParam);

        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int outputLen = cipher.processBytes(input, 0, input.length, output, 0);

        try {
            cipher.doFinal(output, outputLen);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }
}
