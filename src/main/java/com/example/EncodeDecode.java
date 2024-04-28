package com.example;

public class EncodeDecode {
    private static final String REFERENCE_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";

    private char offset;

    public EncodeDecode(char offset) {
        if (!REFERENCE_TABLE.contains(String.valueOf(offset))) {
            throw new IllegalArgumentException("Character not in reference table!");
        }
        this.offset = offset;
    }

    public String encode(String text) {
        if (text.isEmpty()) {
            return text;
        }
        StringBuilder textEncoded = new StringBuilder();

        // ADD THE OFFSET CHARACTER
        textEncoded.append(offset);

        // GET INDEX OF OFFSET IN THE TABLE
        int difference = REFERENCE_TABLE.indexOf(offset);
        char[] charText = text.toCharArray();
        for (char c : charText) {
            int orgIndex = REFERENCE_TABLE.indexOf(c);
            // HANDLE THE SPACE
            if (orgIndex == -1) {
                textEncoded.append(c);
            } else {
                /*
                 * SHIFT AND LOOP THROUGH IF NEED
                 * EG: 7 + 44 - 1 = 50
                 * 50 % 44 = 6
                 * H WILL BECOME G!!
                 */
                int newIndex = (orgIndex + REFERENCE_TABLE.length() - difference) % REFERENCE_TABLE.length();
                textEncoded.append(REFERENCE_TABLE.charAt(newIndex));
            }
        }
        return textEncoded.toString();
    }

    public String decode(String textEncoded) {
        if (textEncoded.isEmpty()) {
            return textEncoded;
        }
        StringBuilder orgText = new StringBuilder();

        // USE THE FIRST LETTER TO GET INDEX FROM TABLE
        char firstLetter = textEncoded.charAt(0);
        int difference = REFERENCE_TABLE.indexOf(firstLetter);

        // SKIP THE OFFSET CHARACTER AT THE START
        char[] charText = textEncoded.substring(1).toCharArray();

        for (char c : charText) {
            int orgIndex = REFERENCE_TABLE.indexOf(c);
            // HANDLE THE SPACE
            if (orgIndex == -1) {
                orgText.append(c);
            } else {
                int newIndex = (orgIndex + difference) % REFERENCE_TABLE.length();
                orgText.append(REFERENCE_TABLE.charAt(newIndex));
            }
        }
        return orgText.toString();
    }

    public static void main(String[] args) {
        EncodeDecode encodeDecode = new EncodeDecode('F');
        String text = "HELLO WORLD";
        String encoded = encodeDecode.encode(text);
        System.out.println(encoded);
        String decoded = encodeDecode.decode(encoded);
        System.out.println(decoded);
    }
}