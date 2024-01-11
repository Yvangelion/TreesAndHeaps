import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

class TreeNode {
    char data;
    TreeNode left; // create left and right nodes for virtual tree
    TreeNode right;

    TreeNode(char data) { // constructor to create data
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class A7TreesP2 { // main 
    private TreeNode root; // head of virtual tree

    public A7TreesP2() { // initialize with head node with blank space 
        root = new TreeNode(' ');
    }

    public void buildTree(String filePath) throws IOException {  // reads file path and file to get morse data
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) { // read line by line 
            char letter = line.charAt(0);
            String morseCode = reader.readLine();
            addNode(letter, morseCode); // if the letter has its morse code save it 
            //System.out.print(letter + morseCode);
      }
        reader.close();
    }
    
    

    private void addNode(char letter, String morseCode) {  // Recursive helper method to build the  code tree
        TreeNode current = root;
        for (char c : morseCode.toCharArray()) { //Traverse the tree according to virtual tree and add the character when node is found
            if (c == '.') {
                if (current.left == null) {
                    current.left = new TreeNode(' ');
                }
                current = current.left;
            } else if (c == '-') {
                if (current.right == null) {
                    current.right = new TreeNode(' ');
                }
                current = current.right;
            }
        }
        if (current != null) { // add the character to the current node
            current.data = letter;
        }
    }

    public String encode(String message) {
        StringBuilder encodedMessage = new StringBuilder();
        for (char c : message.toLowerCase().toCharArray()) {
            if (Character.isLetter(c) || c == '.' || c == '-') {
                encodedMessage.append(encodeCharacter(c)).append(" "); 
            }
        }
        return encodedMessage.toString().trim();
    }

    private String encodeCharacter(char c) { //go through virtual tree and when character is found save the code
        TreeNode current = root;
        if (c == '.') {
        	System.out.print(c);
            current = current.left;
        } else if (c == '-') {
            current = current.right;
        } else {
            return "";
        }
        return traverseTree(current);
    }

    private String traverseTree(TreeNode node) { // travese tree by  adding nodes when found
        if (node == null) {
            return "";
        }
        if (node.data != ' ') {
            return String.valueOf(node.data);
        }
        String leftResult = traverseTree(node.left);
        String rightResult = traverseTree(node.right);
        if (!leftResult.isEmpty()) {
            return "." + leftResult;
        } else if (!rightResult.isEmpty()) {
            return "-" + rightResult;
        }
        return "";
    }

    public String decode(String morseCode) { // when code is sent, look for code that matches with letter and save it until for loop over
        StringBuilder decodedMessage = new StringBuilder();
        String[] morseWords = morseCode.split("\\s{3}");
        for (String word : morseWords) {
            String[] morseChars = word.split("\\s+");
            for (String morseChar : morseChars) {
                char decodedChar = decodeCharacter(morseChar);
                if (decodedChar != ' ') {
                    decodedMessage.append(decodedChar);
                }
            }
            decodedMessage.append(" ");
        }
        return decodedMessage.toString().trim();
    }

    private char decodeCharacter(String morseChar) { //
        TreeNode current = root;
        for (char c : morseChar.toCharArray()) {
            if (c == '.') {
                current = current.left;
            } else if (c == '-') {
                current = current.right;
            } else {
                return ' ';
            }
        }
        return current.data;
    }

    public static void main(String[] args) {
    	A7TreesP2 tree = new A7TreesP2();
        try {
            tree.buildTree("C:\\Users\\Dom\\Documents\\morse.txt"); // file location
        } catch (IOException e) {
            System.out.println("Error reading morse.txt file: " + e.getMessage());
            return;
        }

        System.out.println("Morse Code Translator"); //  cases for selections
        System.out.println("Options: ");
        System.out.println("1. Encode Message");
        System.out.println("2. Decode Message");
        System.out.println("3. Exit");

        while (true) {
            int choice = Helper.getIntInput("Enter your choice: ");
            if (choice == 1) {
                String message = Helper.getInput("Enter the message to encode: ");
                String encodedMessage = tree.encode(message);
                System.out.println("Encoded Message: " + encodedMessage);
            } else if (choice == 2) {
                String morseCode = Helper.getInput("Enter the morse code to decode (use . for dot, - for dash, and space between letters): ");
                String decodedMessage = tree.decode(morseCode);
                System.out.println("Decoded Message: " + decodedMessage);
            } else if (choice == 3) {
                System.out.println("Program closed");
                break;
            } else {
                System.out.println("Please pick 1, 2 or 3");
            }
        }
    }
}

class Helper {
    public static int getIntInput(String message) { // helper class containing utility methods for input handling
        try {
            System.out.print(message);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return getIntInput(message);
        }
    }

    public static String getInput(String message) {
        try {
            System.out.print(message);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Please try again.");
            return getInput(message);
        }
    }
}
