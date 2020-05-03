package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static List<String> logList = new ArrayList<>();
    public static Map<String, Integer> mapMistakes = new TreeMap<>();

    public static void outputMsg(String line) {
        logList.add(line);
        System.out.println(line);
    }

    public static String inputMsg() {
        String line = scanner.nextLine();
        logList.add(line);
        return line;
    }

    public static void saveMistakes(String card){
        if (mapMistakes.containsKey(card))
            mapMistakes.put(card, mapMistakes.get(card) + 1);
        else
            mapMistakes.put(card, 1);
    }

    public static void hardestCard(){
        StringBuilder errorsCards = new StringBuilder();
        int errors = 0;
        if (mapMistakes.isEmpty())
            outputMsg("There are no cards with errors.");
        else {
            for (Map.Entry<String, Integer> cards : mapMistakes.entrySet()){
                if (cards.getValue() > errors) errors = cards.getValue();
            }
            for (Map.Entry<String, Integer> card : mapMistakes.entrySet()){
                if (card.getValue() == errors) errorsCards.append(", \"").append(card.getKey()).append("\"");
            }
            outputMsg("The hardest card is" + errorsCards.toString().replaceFirst(",", "") +", "
                    + errors);
        }
    }

    public static void resetStats(){
        mapMistakes.clear();
        outputMsg("Card statistics has been reset.");
    }

    public static void exportLog() {
        outputMsg("File name:");
        File file = new File(inputMsg());

        try (FileWriter writer = new FileWriter(file)) {
            outputMsg("The log has been saved.");
            for (String log : logList)
                writer.write(log + "\n");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void addCard(Map<String, String> map) {
        outputMsg("The card:");
        String card = inputMsg();
        if (map.containsKey(card)) {
            outputMsg("The card \"" + card + "\" already exists.");
        } else {
            outputMsg("The definition of the card:");
            String cardDef = inputMsg();
            if (map.containsValue(cardDef)) {
                outputMsg("The definition \"" + cardDef + "\" already exists.");
            } else {
                map.put(card, cardDef);
                outputMsg(String.format("The pair (\"%s\":\"%s\") has been added.", card, cardDef));
            }
        }
    }

    public static void askCard(Map<String, String> map) {
        outputMsg("How many times to ask?");
        int number = Integer.parseInt(inputMsg());
        int count = 0;
        boolean flag = true;

        while (flag) {
            for (Map.Entry<String, String> card : map.entrySet()) {
                outputMsg("Print the definition of \"" + card.getKey() + "\":");
                String str = inputMsg();
                if (!card.getValue().equals(str) && map.containsValue(str)) {
                    saveMistakes(card.getKey());
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        if (str.equals(entry.getValue())) {
                            outputMsg("Wrong answer. The correct one is \"" + card.getValue() + "\"," +
                                    " you've just written the definition of \"" + entry.getKey() + "\".");
                        }
                    }
                } else if (!card.getValue().equals(str) && !map.containsValue(str)) {
                    saveMistakes(card.getKey());
                    outputMsg("Wrong answer. The correct one is \"" + card.getValue() + "\".");
                } else
                    outputMsg("Correct answer.");
                count++;
                if (count == number) {
                    flag = false;
                    break;
                }
            }
        }
    }

    public static void removeCard(Map<String, String> map) {
        outputMsg("The card:");
        String card = inputMsg();
        if (map.containsKey(card)) {
            map.remove(card);
            mapMistakes.remove(card);
            outputMsg("The card has been removed.");
        } else outputMsg(String.format("Can't remove \"%s\": there is no such card.", card));
    }

    public static void importFile(Map<String, String> map) {
        outputMsg("File name:");
        File file = new File(inputMsg());
        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String lineCards = scanner.nextLine();
                String[] cards = lineCards.split(":")[0].split(",");
                int num = Integer.parseInt(lineCards.split(":")[1]);
                map.put(cards[0], cards[1]);
                if (num != 0)
                    mapMistakes.put(cards[0], num);
                count++;
            }
            outputMsg(count + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            outputMsg("File not found.");
        }
    }

    public static void exportFile(Map<String, String> map) {
        outputMsg("File name:");
        File file = new File(inputMsg());
        int count = 0;

        try (FileWriter writer = new FileWriter(file)) {
            for (Map.Entry<String, String> cards : map.entrySet()) {
                writer.write(cards.getKey() + "," + cards.getValue() + ":" +
                        mapMistakes.getOrDefault(cards.getKey(), 0) + "\n");
                count++;
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
        outputMsg(count + " cards have been saved.");
    }

    public static void exportCards(String[] args, Map<String, String> map){
        File file;
        if (args[0].equals("-export"))
            file = new File(args[1]);
        else file = new File(args[3]);
        int count = 0;

        try(FileWriter writer = new FileWriter(file)) {
            for (Map.Entry<String, String> cards : map.entrySet()) {
                writer.write(cards.getKey() + "," + cards.getValue() + "\n");
                count++;
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
        outputMsg(count + " cards have been saved.");
    }

    public static void importCards(String[] args, Map<String, String> map){
        File file;
        if (args[0].equals("-import")){
            file = new File(args[1]);
        }else file = new File(args[3]);
        int count = 0;

        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNext()){
                String[] cards = scanner.nextLine().split(",");
                map.put(cards[0], cards[1]);
                count++;
            }
            outputMsg(count + " cards have been loaded.");
        }catch (FileNotFoundException e) {
            outputMsg("File not found.");
        }
    }


    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        boolean flag = true;
        if (args.length == 4 && (args[0].equals("-import") || args[2].equals("-import")))
            importCards(args, map);
        else if (args.length == 2 && args[0].equals("-import"))
            importCards(args, map);

        while (flag) {
            outputMsg("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String action = inputMsg();
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
                    outputMsg("Bye bye!");
                    if (args.length == 4 && (args[0].equals("-export") || args[2].equals("-export")))
                        exportCards(args, map);
                    else if(args.length == 2 && args[0].equals("-export"))
                        exportCards(args, map);
                    flag = false;
                    scanner.close();
                    break;
                case "log":
                    exportLog();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                default:
                    outputMsg("Unsuitable action, please, try again");
            }
        }
    }
}
