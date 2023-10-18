import java.util.Scanner;

public class Main {
    private static final char FIRST_UPPERCASE = 'A';
    private static final char LAST_UPPERCASE = 'Z';
    private static final int ALPHABET_SIZE = LAST_UPPERCASE - FIRST_UPPERCASE + 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți textul: ");
        String text = scanner.nextLine().toUpperCase().replaceAll(" ", "");

        int key1 = getKeyFromUser(scanner, "Introduceți prima cheie (între 1 și 25): ");
        String key2 = getSecondKeyFromUser(scanner);

        System.out.print("Alegeți operația (criptare sau decriptare): ");
        String operation = scanner.nextLine().toLowerCase();

        if (operation.equals("criptare")) {
            String encryptedText = encrypt(text, key1, key2);
            System.out.println("Textul criptat: " + encryptedText);
        } else if (operation.equals("decriptare")) {
            String decryptedText = decrypt(text, key1, key2);
            System.out.println("Textul decriptat: " + decryptedText);
        } else {
            System.out.println("Operație invalidă. Alegeți criptare sau decriptare.");
        }
    }

    private static int getKeyFromUser(Scanner scanner, String message) {
        int key;
        while (true) {
            System.out.print(message);
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

    private static String getSecondKeyFromUser(Scanner scanner) {
        String key;
        while (true) {
            System.out.print("Introduceți a doua cheie (lungime minimă 7, să conțină doar litere): ");
            key = scanner.nextLine().toUpperCase();
            if (key.length() >= 7 && key.matches("^[A-Z]+$")) {
                return key;
            } else {
                System.out.println("A doua cheie trebuie să aibă o lungime minimă de 7 caractere și să conțină doar litere.");
            }
        }
    }

    private static String encrypt(String text, int key1, String key2) {
        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (c >= FIRST_UPPERCASE && c <= LAST_UPPERCASE) {
                int shift = Character.toUpperCase(key2.charAt(keyIndex)) - FIRST_UPPERCASE + 1;
                char encryptedChar = (char) (((c - FIRST_UPPERCASE + key1 + shift) % ALPHABET_SIZE) + FIRST_UPPERCASE);
                encryptedText.append(encryptedChar);

                keyIndex = (keyIndex + 1) % key2.length();
            }
        }

        return encryptedText.toString();
    }

    private static String decrypt(String text, int key1, String key2) {
        StringBuilder decryptedText = new StringBuilder();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (c >= FIRST_UPPERCASE && c <= LAST_UPPERCASE) {
                int shift = Character.toUpperCase(key2.charAt(keyIndex)) - FIRST_UPPERCASE + 1;
                char decryptedChar = (char) (((c - FIRST_UPPERCASE - key1 - shift + ALPHABET_SIZE) % ALPHABET_SIZE) + FIRST_UPPERCASE);
                decryptedText.append(decryptedChar);

                keyIndex = (keyIndex + 1) % key2.length();
            }
        }

        return decryptedText.toString();
    }
}
