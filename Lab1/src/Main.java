import java.util.Scanner;

public class Main {
    private static final char FIRST_UPPERCASE = 'A';
    private static final char LAST_UPPERCASE = 'Z';
    private static final int ALPHABET_SIZE = LAST_UPPERCASE - FIRST_UPPERCASE + 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți textul: ");
        String text = scanner.nextLine().toUpperCase().replaceAll(" ", "");

        int key = getKeyFromUser(scanner);
        System.out.print("Alegeți operația (criptare sau decriptare): ");
        String operation = scanner.nextLine().toLowerCase();

        if (operation.equals("criptare")) {
            String encryptedText = encrypt(text, key);
            System.out.println("Textul criptat: " + encryptedText);
        } else if (operation.equals("decriptare")) {
            String decryptedText = decrypt(text, key);
            System.out.println("Textul decriptat: " + decryptedText);
        } else {
            System.out.println("Operație invalidă. Alegeți criptare sau decriptare.");
        }
    }

    private static int getKeyFromUser(Scanner scanner) {
        int key;
        while (true) {
            System.out.print("Introduceți cheia (între 1 și 25): ");
            try {
                key = Integer.parseInt(scanner.nextLine());
                if (key >= 1 && key <= 25) {
                    return key;
                } else {
                    System.out.println("Cheia trebuie să fie între 1 și 25.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Introduceți o cheie validă (număr întreg între 1 și 25).");
            }
        }
    }

    private static String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (c >= FIRST_UPPERCASE && c <= LAST_UPPERCASE) {
                char encryptedChar = (char) (((c - FIRST_UPPERCASE + key) % ALPHABET_SIZE) + FIRST_UPPERCASE);
                encryptedText.append(encryptedChar);
            }
        }

        return encryptedText.toString();
    }

    private static String decrypt(String text, int key) {
        StringBuilder decryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (c >= FIRST_UPPERCASE && c <= LAST_UPPERCASE) {
                char decryptedChar = (char) (((c - FIRST_UPPERCASE - key + ALPHABET_SIZE) % ALPHABET_SIZE) + FIRST_UPPERCASE);
                decryptedText.append(decryptedChar);
            }
        }

        return decryptedText.toString();
    }
}
