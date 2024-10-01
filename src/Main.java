/*
Name: Qazi
Program Name: Qazi_SpotIt
Program Description: Hard-coded recreation of the popular game Spot It (Originally known as Dobble)
Creation Date: 09/24/24
Last Edit Date: INSERT TIME
Ethics Declaration: “This code is my own work”
*/

import java.util.*;
import java.util.stream.Stream;

public class Main {
    // Credits to https://www.mymiller.name/wordpress/programming/java/spring/ansi-colors/ for the colour codes

    // Regular text colors
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";

    // Bright text colors
    public static final String BRIGHT_YELLOW = "\u001B[33;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";
    public static final String BRIGHT_MAGENTA = "\u001B[1;95m";
    public static final String BRIGHT_WHITE = "\u001B[37;1m";
    public static final String RED = "\u001B[1;91m";
    public static final String GREEN = "\u001B[1;92m";

    // Bright background colors
    public static final String BRIGHT_BACKGROUND_BLACK = "\u001B[40;1m";
    public static final String BRIGHT_BACKGROUND_RED = "\u001B[41;1m";

    static Random random = new Random();

    static String[] goodbyeMessages = {"WOWWWWWW", "I see how it is.", "Okay...", "I hope both sides of your pillow are warm tonight.",
    "HOW COULD YOU DO THIS :(((", "Alright.", "I'll remember this...", "Check outside of your window 🙂"};

    static int standardMaxRows = 12;
    static int standardMin = 0;

    static String[][] standardSet = {
            {"a", "b", "c", "d"}, // Card One
            {"a", "e", "f", "g"}, // Card Two
            {"a", "h", "i", "j"}, // Card Three
            {"a", "k", "l", "m"}, // Card Four
            {"b", "e", "h", "k"}, // Card Five
            {"b", "f", "i", "l"}, // Card Six
            {"b", "g", "j", "m"}, // Card Seven
            {"c", "e", "j", "l"}, // Card Eight
            {"c", "f", "h", "m"}, // Card Nine
            {"c", "g", "i", "k"}, // Card Ten
            {"d", "e", "i", "m"}, // Card Eleven
            {"d", "f", "j", "k"}, // Card Twelve
            {"d", "g", "h", "l"} // Card Thirteen
    };

    static Scanner scanner = new Scanner(System.in);

    static int score = 0;

    // I found this really cool thing here https://medium.com/javarevisited/how-to-display-progressbar-on-the-standard-console-using-java-18f01d52b30e
    // and wanted to include it :(((
    public static void printMsgWithProgressBar(String message, int length, long timeInterval)
    {
        char incomplete = '░'; // U+2591 Unicode Character
        char complete = '█'; // U+2588 Unicode Character
        StringBuilder builder = new StringBuilder();
        Stream.generate(() -> incomplete).limit(length).forEach(builder::append);
        System.out.println(message);
        for(int i = 0; i < length; i++)
        {
            builder.replace(i,i+1,String.valueOf(complete));
            String progressBar = "\r"+builder;
            System.out.print(progressBar);
            try
            {
                Thread.sleep(timeInterval);
            }
            catch (InterruptedException ignored)
            {

            }
        }
    }

    public static void processValues(int numArray, String[][] set) {
        // Generates two random values for selecting the cards

        // TODO add exception statement things in the main body

        int maxRows = 0;

        if (numArray == 3) {
            maxRows = 6;
        }
        else if (numArray == 4) {
            maxRows = 12;
        }

        int firstCardRow = random.nextInt(maxRows);
        int secondCardRow = random.nextInt(maxRows);

        // Prevents cards from being the same value
        if (firstCardRow == secondCardRow) {
            firstCardRow += 1;
        }
        if (firstCardRow == maxRows) {
            firstCardRow -= 1;
        }

        // Picks card A at random
        List<String> cardA = new ArrayList<>(Arrays.asList(set[firstCardRow]).subList(0, numArray));
        // Picks card B at random
        List<String> cardB = new ArrayList<>(Arrays.asList(set[secondCardRow]).subList(0, numArray));

        // Shuffles Cards
        Collections.shuffle(cardA);
        Collections.shuffle(cardB);

        // Prints Card A and Card B
        for (String s : cardA) {
            print(s + ", ", 1);
        }
        print("", 0);

        for (String s : cardB) {
            print(s + ", ", 1);
        }
        print("", 0);

        // Gets the correct answer
        cardB.retainAll(cardA);


        print("What is the common item?", 0);
        String guess = scanner.nextLine();

        // Modifies the user input to be comparable to the arrayList
        String guessMod = ("[" + guess + "]");

        while (!guessMod.equalsIgnoreCase((String.valueOf(cardB)))) {

            print("incorrect! Please guess again", 0);
            guess = scanner.nextLine();
            guessMod = ("[" + guess + "]");
        }

        if (guessMod.equalsIgnoreCase(String.valueOf(cardB))) {
            print("correct", 0);
            score += 1;
            print("Do you wish to play again?", 0);
            String replay = scanner.nextLine();
            if (replay.equalsIgnoreCase("Y")) {
                customValues();
            } else {
                print("Okay!", 0);
                return;
            }

            if (guess.equalsIgnoreCase("quit")) {
                print("The correct answer was: ", 0);
                for (String item : cardB) {
                    print(item, 0);
                }
            }
        }
    }

    public static void customValues() {

        print("Please pick how many elements you want per card (A or B):", 0);
        print("---------------------------------------------------------", 0);
        print("      A)     3                             B)     4      ", 0);

        String temp = scanner.nextLine();
        int numElementsPerCard = 0;

        if (temp.equalsIgnoreCase("a")) {
            numElementsPerCard = 3;
        } else if (temp.equalsIgnoreCase("b")) {
            numElementsPerCard = 4;
        } else {
            print("Please put in a valid input!", 0);
            temp = scanner.nextLine();
        }

        if (numElementsPerCard == 4) {
            print(BRIGHT_BACKGROUND_BLACK + BRIGHT_BLUE + "Please input thirteen names/items you wish to include", 0);
            String a = scanner.nextLine();
            String b = scanner.nextLine();
            String c = scanner.nextLine();
            String d = scanner.nextLine();
            String e = scanner.nextLine();
            String f = scanner.nextLine();
            String g = scanner.nextLine();
            String h = scanner.nextLine();
            String i = scanner.nextLine();
            String j = scanner.nextLine();
            String k = scanner.nextLine();
            String l = scanner.nextLine();
            String m = scanner.nextLine();

            String[][] modifiedSet = {
                    {a, b, c, d}, // Card One
                    {a, e, f, g}, // Card Two
                    {a, h, i, j}, // Card Three
                    {a, k, l, m}, // Card Four
                    {b, e, h, k}, // Card Five
                    {b, f, i, l}, // Card Six
                    {b, g, j, m}, // Card Seven
                    {c, e, j, l}, // Card Eight
                    {c, f, h, m}, // Card Nine
                    {c, g, i, k}, // Card Ten
                    {d, e, i, m}, // Card Eleven
                    {d, f, j, k}, // Card Twelve
                    {d, g, h, l} // Card Thirteen
            };

            // Generates two random values for selecting the cards
            processValues(4, modifiedSet);
        }
        else if (numElementsPerCard == 3) {
                print(BRIGHT_BACKGROUND_BLACK + BRIGHT_BLUE + "Please input seven names/items you wish to include", 0);
                String a = scanner.nextLine();
                String b = scanner.nextLine();
                String c = scanner.nextLine();
                String d = scanner.nextLine();
                String e = scanner.nextLine();
                String f = scanner.nextLine();
                String g = scanner.nextLine();

                String[][] modifiedSet = {
                        {a, b, c},
                        {a, d, e},
                        {a, f, g},
                        {b, d, f},
                        {b, e, g},
                        {c, d, g},
                        {c, e, f}
                };

                // Generates two random values for selecting the cards
                processValues(3, modifiedSet);
        }
    }

    public static void print(String msg, int type) {
        if (type == 0) {
            System.out.println(msg);
        } else {
            System.out.print(msg);
        }
    } // for fun hehe

    public static int startMenu() {
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "Please select an option from one of the following:" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "--------------------------------------------------" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[1]  |"+ BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA +"                  Original                 " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE +"|" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[2]  | " + BRIGHT_BACKGROUND_BLACK + BLUE + "               Input-based                " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "|" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[3]  | " + BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "            Restore last score        " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "    |" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[4]  |       " + BRIGHT_BACKGROUND_RED + BLUE + "Quit (you don't want too ooooo)" + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "     |" + RESET, 0);

        int input;

        // Ensuring the inputted values are compatible with the code
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                while (input < 1 || input > 4) {
                    print("That's an invalid input! Please put an integer between 1 and 4", 0);
                    input = scanner.nextInt();
                }
                break;
            } catch (InputMismatchException e) {
                print("That's an invalid input! Please put an integer between 1 and 4", 0);
                scanner.nextLine();
            }
        }
        return (input);
    }

    public static void standardGame() {
        score = 0;
        // Get value for number of rounds
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Please enter the number of rounds you wish to play!", 0);
        int numRounds = scanner.nextInt();
        long startTime = System.nanoTime();

        for (int i = 0; i < numRounds; i++){
            int firstCardRow = random.nextInt(standardMaxRows - standardMin + 1) + standardMin;
            int secondCardRow = random.nextInt(standardMaxRows - standardMin + 1) + standardMin;

            // Prevents cards from being the same value
            if (firstCardRow == secondCardRow) {
                firstCardRow += 1;
            }
            if (firstCardRow == 13) {
                firstCardRow -= 1;
            }

            // Picks card A at random
            List<String> cardA = new ArrayList<>(Arrays.asList(standardSet[firstCardRow]).subList(0, 4));
            // Picks card B at random
            List<String> cardB = new ArrayList<>(Arrays.asList(standardSet[secondCardRow]).subList(0, 4));

            // Shuffles Cards
            Collections.shuffle(cardA);
            Collections.shuffle(cardB);

            // Prints Card A and Card B
            for (String s : cardA) {
                print(s, 1);
            }
            print("", 0);

            for (String s : cardB) {
                print(s, 1);
            }
            print("", 0);

            // Gets the correct answer
            cardB.retainAll(cardA);

            print("What is the common letter?", 0);
            String guess = scanner.nextLine();

            // Modifies the user input to be comparable to the arrayList
            String guessMod = ("[" + guess + "]");

            while (!guessMod.equalsIgnoreCase((String.valueOf(cardB)))) {
                print("incorrect! Please guess again", 0);
                guess = scanner.nextLine();
                guessMod = ("[" + guess + "]");
                if (guessMod.equalsIgnoreCase(String.valueOf(cardB))) {
                    print("correct", 0);
                    score += 1;
                    }
            if (guess.equalsIgnoreCase("quit")) {
                print("The correct answer was: ", 0);
                for (String item : cardB) {
                    print(item, 0);
                return;
                }
            }
        }

            }
            long estimatedTime = System.nanoTime() - startTime;
            print(String.valueOf(estimatedTime/1_000_000_000), 0);
            print("Do you wish to play again?", 0);
            print("--------------------------", 0);
            print("  A) " + GREEN + "Yes " + MAGENTA + "          B) " + RED + "No  " + RESET, 0);
            String replay = scanner.nextLine();
            if (replay.equalsIgnoreCase("A")) {
                standardGame();
            } else if (replay.equalsIgnoreCase("B")) {
                print("Okay!", 0);
            }
            else {
                print("Invalid input! Please select A or B", 0);
                replay = scanner.nextLine();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        printMsgWithProgressBar("Generating the " + RED + "COOLEST " + RESET + "game of SpotIt!", 50, 60);
        print("", 0);
        print(GREEN + "Generation Complete. Welcome!" + RESET, 0);
        Thread.sleep(500);
        while (true) {
            int input = startMenu();

            switch (input) {
                case 1:
                    standardGame();
                    break;
                case 2:
                    customValues();
                    break;
                case 3:
                    print("Your last score was: " + score, 0);
                    break;
                case 4:
                    int max = goodbyeMessages.length;
                    int index = random.nextInt(max - 1);
                    print(BRIGHT_BACKGROUND_BLACK + RED + goodbyeMessages[index] + RESET, 0);
                    System.exit(0);
                    break;
            }

        }

    }
}
