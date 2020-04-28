package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void addCard(Map<String, String> map) {
        System.out.println("The card:");
        String card = scanner.nextLine();
        if (map.containsKey(card)) {
            System.out.println("The card \"" + card + "\" already exists.");
        } else {
            System.out.println("The definition of the card:");
            String cardDef = scanner.nextLine();
            if (map.containsValue(cardDef)) {
                System.out.println("The definition \"" + cardDef + "\" already exists.");
            } else {
                map.put(card, cardDef);
                System.out.println(String.format("The pair (\"%s\":\"%s\") has been added.", card, cardDef));
            }
        }
    }

    public static void askCard(Map<String, String> map) {
        System.out.println("How many times to ask?");
        int number = Integer.parseInt(scanner.nextLine());
        int count = 0;
        boolean flag = true;

        while(flag) {
            for (Map.Entry<String, String> card : map.entrySet()) {
                System.out.println("Print the definition of \"" + card.getKey() + "\":");
                String str = scanner.nextLine();
                if (!card.getValue().equals(str) && map.containsValue(str)) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (str.equals(entry.getValue()))
                            System.out.println("Wrong answer. The correct one is \"" + card.getValue() + "\"," +
                                    " you've just written the definition of \"" + entry.getKey() + "\".");
                    }
                } else if (!card.getValue().equals(str) && !map.containsValue(str))
                    System.out.println("Wrong answer. The correct one is \"" + card.getValue() + "\".");
                else
                    System.out.println("Correct answer.");
                count++;
                if (count == number){
                    flag = false;
                    break;
                }
            }
        }
    }

    public static void removeCard(Map<String, String> map) {
        System.out.println("The card:");
        String card = scanner.nextLine();
        if (map.containsKey(card)) {
            map.remove(card);
            System.out.println("The card has been removed.");
        } else System.out.println(String.format("Can't remove \"%s\": there is no such card.", card));
    }

    public static void importFile(Map<String, String> map) {
        System.out.println("File name:");
        File file = new File(scanner.nextLine());
        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                map.put(scanner.next(), scanner.next());
                count++;
            }
            System.out.println(count + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static void exportFile(Map<String, String> map) {
        System.out.println("File name:");
        File file = new File(scanner.nextLine());
        int count = 0;

        try (FileWriter writer = new FileWriter(file)) {
            for (Map.Entry<String, String> cards : map.entrySet()) {
                writer.write(cards.getKey() + " " + cards.getValue() + "\n");
                count++;
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
        System.out.println(count + " cards have been saved.");
    }


    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        boolean flag = true;

        while (flag) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String action = scanner.nextLine();
            switch (action) {
                case "add":
                    addCard(map);
                    break;
                case "remove":
                    removeCard(map);
                    break;
                case "import":
                    importFile(map);
                    break;
                case "export":
                    exportFile(map);
                    break;
                case "ask":
                    askCard(map);
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    flag = false;
                    scanner.close();
                    break;
                default:
                    System.out.println("Unsuitable action, please, try again");
            }
        }
    }
}
