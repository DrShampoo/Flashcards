package flashcards;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        System.out.println("Input the number of cards:");
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        int n = scanner.nextInt();
        String s = scanner.nextLine();

        for (int i = 0; i < n; i++){
            System.out.println("The card #" + (i+1) +":");
            String card = scanner.nextLine();
            while (map.containsKey(card)){
                System.out.println("The card \"" + card + "\" already exists. Try again:");
                card = scanner.nextLine();
            }
            System.out.println("The definition of the card #" + (i+1) + ":");
            String cardDef = scanner.nextLine();
            while (map.containsValue(cardDef)){
                System.out.println("The definition \"" + cardDef + "\" already exists. Try again:");
                cardDef = scanner.nextLine();
            }
            map.put(card, cardDef);
        }

        for (Map.Entry<String, String> card : map.entrySet()){
            System.out.println("Print the definition of \"" + card.getKey() + "\":");
            String str = scanner.nextLine();
            if (!card.getValue().equals(str)&&map.containsValue(str)){
                for (Map.Entry<String, String> entry : map.entrySet()){
                    if (str.equals(entry.getValue()))
                        System.out.println("Wrong answer. The correct one is \"" + card.getValue() + "\"," +
                                " you've just written the definition of \"" + entry.getKey() + "\".");
                }
                }
            else if (!card.getValue().equals(str)&&!map.containsValue(str))
                System.out.println("Wrong answer. The correct one is \"" + card.getValue() + "\".");
            else
                System.out.println("Correct answer.");
        }

    }
}
