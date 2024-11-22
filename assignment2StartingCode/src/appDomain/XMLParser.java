package appDomain;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import implementations.MyStack;
import implementations.MyQueue;

public class XMLParser {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Command to prompt: java -jar Parser.jar <path-to-XML-file>");
            return;
        }

        String filePath = args[0];
        File xmlFile = new File(filePath);

        if (!xmlFile.exists()) {
            System.out.println("File not found - " + filePath + "\nPlease check your file path again :-)");
            return;
        }

        try (Scanner scanner = new Scanner(xmlFile)) {
            MyStack<String> stack = new MyStack<>();
            MyQueue<String> errorQueue = new MyQueue<>();
            boolean rootTagFound = false;

            int lineNumber = 0;

            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                boolean isLineValid = true;

                // Ignore processing instructions
                if (line.startsWith("<?xml") && line.endsWith("?>")) {
                    continue;
                }

                // Process self-closing tags
                if (line.contains("<") && line.contains("/>")) {
                    continue; // Self-closing tags require no further processing
                }

                // Process opening and closing tags
                int currentIndex = 0;
                while (currentIndex < line.length()) {
                    int openTagStart = line.indexOf('<', currentIndex);
                    if (openTagStart == -1) break;

                    int openTagEnd = line.indexOf('>', openTagStart);
                    if (openTagEnd == -1) {
                        isLineValid = false;
                        errorQueue.enqueue("Invalid close tag at line " + lineNumber + ": " + line);
                        break;
                    }

                    String tagContent = line.substring(openTagStart + 1, openTagEnd);

                    if (tagContent.startsWith("/")) {
                        // Closing tag
                        String tagName = tagContent.substring(1);
                        if (stack.isEmpty()) {
                            isLineValid = false;
                            errorQueue.enqueue("Error at line " + lineNumber + ": Unmatched closing tag </" + tagName + ">");
                        } else if (!stack.peek().equals(tagName)) {
                            isLineValid = false;
                            errorQueue.enqueue("Error at line " + lineNumber + ": Mismatched closing tag </" + tagName + ">");
                        } else {
                            stack.pop();
                        }
                    } else {
                        // Opening tag
                        if (!rootTagFound) {
                            rootTagFound = true;
                        }
                        stack.push(tagContent);
                    }

                    currentIndex = openTagEnd + 1;
                }

                if (!isLineValid) {
                    errorQueue.enqueue("Error at line " + lineNumber + ": Invalid XML line -> " + line);
                }
            }

            // Handle unclosed tags in the stack
            while (!stack.isEmpty()) {
                String unclosedTag = stack.pop();
                errorQueue.enqueue("Error: Unclosed tag <" + unclosedTag + ">");
            }

            // Ensure only one root tag exists
            if (!rootTagFound) {
                errorQueue.enqueue("Error: No root tag found.");
            }

            // Print errors
            System.out.println("================ERROR LOG================");
            if (errorQueue.isEmpty()) {
                System.out.println("No errors found.");
            } else {
                while (!errorQueue.isEmpty()) {
                    System.out.println(errorQueue.dequeue());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
