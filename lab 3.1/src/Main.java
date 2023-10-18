import java.util.Scanner;

public class Main {
    private static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    private static final char PAD_CHAR = 'X'; // Character used for padding

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți cheia (lungime minimă 7, caracterele pot fi duplicate): ");
        String key = prepareKey(scanner.nextLine().toUpperCase());

        System.out.print("Introduceți textul: ");
        String text = prepareText(scanner.nextLine().toUpperCase());

        System.out.print("Alegeți operația (criptare sau decriptare): ");
        String operation = scanner.nextLine().toLowerCase();

        if (operation.equals("criptare")) {
            String encryptedText = encrypt(text, key);
            System.out.println("Textul criptat: " + addSpaces(encryptedText));
        } else if (operation.equals("decriptare")) {
            String decryptedText = decrypt(text, key);
            System.out.println("Textul decriptat: " + addSpaces(decryptedText));
        } else {
            System.out.println("Operație invalidă. Alegeți criptare sau decriptare.");
        }
    }

    private static String prepareKey(String key) {
        // Remove duplicates and replace J with I
        StringBuilder cleanedKey = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (cleanedKey.indexOf(String.valueOf(c)) == -1 && ALPHABET.indexOf(c) != -1) {
                if (c == 'J') {
                    cleanedKey.append('I');
                } else {
                    cleanedKey.append(c);
                }
            }
        }

        // Append the rest of the alphabet
        for (char c : ALPHABET.toCharArray()) {
            if (cleanedKey.indexOf(String.valueOf(c)) == -1) {
                cleanedKey.append(c);
            }
        }

        return cleanedKey.toString();
    }

    private static String prepareText(String text) {
        // Remove characters outside the allowed range
        StringBuilder cleanedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                if (c == 'J') {
                    cleanedText.append('I');
                } else {
                    cleanedText.append(c);
                }
            }
        }
        return cleanedText.toString();
    }

    private static String addSpaces(String text) {
        // Add spaces based on the original input (original message length)
        StringBuilder result = new StringBuilder();
        int originalLength = text.length();
        int index = 0;

        for (int i = 0; i < originalLength; i++) {
            result.append(text.charAt(i));
            index++;

            // Add a space if we've processed two characters
            if (index == 2 && i < originalLength - 1) {
                result.append(' ');
                index = 0;
            }
        }

        return result.toString();
    }

    private static char[][] generatePlayfairMatrix(String key) {
        char[][] matrix = new char[5][5];
        int keyIndex = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = key.charAt(keyIndex);
                keyIndex++;
            }
        }

        return matrix;
    }

    private static String encrypt(String text, String key) {
        char[][] matrix = generatePlayfairMatrix(key);
        return processPlayfair(text, matrix, 1); // 1 for encryption
    }

    private static String decrypt(String text, String key) {
        char[][] matrix = generatePlayfairMatrix(key);
        return processPlayfair(text, matrix, -1); // -1 for decryption
    }

    private static String processPlayfair(String text, char[][] matrix, int direction) {
        StringBuilder result = new StringBuilder();

        // Process the text in pairs of characters
        for (int i = 0; i < text.length(); i += 2) {
            char firstChar = text.charAt(i);
            char secondChar = (i + 1 < text.length()) ? text.charAt(i + 1) : PAD_CHAR;

            // Find the positions of the characters in the matrix
            int[] firstPosition = findPosition(matrix, firstChar);
            int[] secondPosition = findPosition(matrix, secondChar);

            // Apply Playfair rules
            char encryptedFirstChar, encryptedSecondChar;
            if (firstPosition[0] == secondPosition[0]) {
                // Same row
                encryptedFirstChar = matrix[firstPosition[0]][(firstPosition[1] + direction + 5) % 5];
                encryptedSecondChar = matrix[secondPosition[0]][(secondPosition[1] + direction + 5) % 5];
            } else if (firstPosition[1] == secondPosition[1]) {
                // Same column
                encryptedFirstChar = matrix[(firstPosition[0] + direction + 5) % 5][firstPosition[1]];
                encryptedSecondChar = matrix[(secondPosition[0] + direction + 5) % 5][secondPosition[1]];
            } else {
                // Different row and column
                encryptedFirstChar = matrix[firstPosition[0]][secondPosition[1]];
                encryptedSecondChar = matrix[secondPosition[0]][firstPosition[1]];
            }

            result.append(encryptedFirstChar).append(encryptedSecondChar);
        }

        return result.toString();
    }

    private static int[] findPosition(char[][] matrix, char c) {
        int[] position = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }

        return position;
    }
}
