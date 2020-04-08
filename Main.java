package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the number of cards");
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        int n = scanner.nextInt();
        String s = scanner.nextLine();
        String[] card = new String[n];
        String[] cardDef = new String[n];
        for (int i = 0; i < n; i++){
            System.out.println("The card #" + (i+1) +":");
            card[i] = scanner.nextLine();
            System.out.println("The definition of the card #" + (i+1) + ":");
            cardDef[i] = scanner.nextLine();
        }
        for (int i = 0; i < n; i++){
            System.out.println("Print the definition of \"" + card[i] + "\":");
            String str = scanner.nextLine();
            if (str.equals(cardDef[i]))
                System.out.println("Correct answer");
            else
                System.out.println("Wrong answer. The correct one is  \"" + cardDef[i] + "\".");
        }


    }
}
