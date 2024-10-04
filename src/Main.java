/*
Name: Qazi
Program Name: Qazi_SpotIt
Program Description: Hard-coded recreation of the popular game Spot It (Originally known as Dobble)
Creation Date: 09/24/24
Last Edit Date: INSERT TIME
Ethics Declaration: “This code is my own work”
*/

/* TODO Overall
Current errors
- Quit doesn't work in the first instance
- Input mismatch errors should be fixed therough the use of loops
- Asthetics not popping off

 */

import java.util.*;
import java.util.stream.Stream;

public class Main {
    // Credits to https://www.mymiller.name/wordpress/programming/java/spring/ansi-colors/ for the colour codes

    // Regular text colors
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";

    // Bright text colors
    public static final String BRIGHT_YELLOW = "\u001B[33;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";
    public static final String BRIGHT_MAGENTA = "\u001B[1;95m";
    public static final String BRIGHT_WHITE = "\u001B[37;1m";
    public static final String RED = "\u001B[1;91m";
    public static final String GREEN = "\u001B[1;92m";
    public static final String CYAN = "\u001B[0;96m";

    // Bright background colors
    public static final String BRIGHT_BACKGROUND_BLACK = "\u001B[40;1m";
    public static final String BRIGHT_BACKGROUND_RED = "\u001B[41;1m";

    static Random random = new Random();

    static String[] goodbyeMessages = {"WOWWWWWW", "I see how it is.", "Okay...", "I hope both sides of your pillow are warm tonight.",
    "HOW COULD YOU DO THIS :(((", "Alright.", "I'll remember this...", "Check outside of your window 🙂"};

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

    static List<Integer> times = new ArrayList<>();

    static int score;

    public static void calculateScore(int rounds) {
        int sum = 0;
        for (Integer time : times) {
            sum += time;
        }

        double averageTime = sum/ (double) rounds;

        score = (int) ((100/(averageTime + 100))*1000);

    }

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

    public static void processValues(int numArray, int mode, int numRounds, String[][] set) {
        score = 0;
        times.clear();

        // Generates two random values for selecting the cards
        long startTime = System.nanoTime();
        // TODO add exception statement things in the main body
        String word = "";

        if (mode == 1) {
            word = "letter";
        } else if (mode == 2) {
            word = "name/item?";
        } else if (mode == 31 || mode == 32 || mode == 33) {
            word = "letter";
        }

        int maxRows = 0;

        if (numArray == 3) {
            maxRows = 6;
        } else if (numArray == 4) {
            maxRows = 12;
        }

        for (int i = 0; i < numRounds; i++) {
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
                print(s, 1);
            }
            print("", 0);

            for (String s : cardB) {
                print(s, 1);
            }
            print("", 0);

            // Gets the correct answer
            cardB.retainAll(cardA);

            print("What is the common " + word + "?", 0);
            String guess = scanner.nextLine();

            // Modifies the user input to be comparable to the arrayList
            String guessMod = ("[" + guess + "]");

            if (!guessMod.equalsIgnoreCase((String.valueOf(cardB)))) {
                print("incorrect! Please guess again", 0);
                guess = scanner.nextLine();
                guessMod = ("[" + guess + "]");
            }
            else if (guessMod.equalsIgnoreCase(String.valueOf(cardB))) {
                print("correct", 0);
                }
            else if (guess.equalsIgnoreCase("quit")) {
                print("The correct answer was: ", 0);
                for (String item : cardB) {
                    print(item, 0);
                    return;
                }
            }
            int elapsedTime = (int) ((System.nanoTime() - startTime)/1_000_000_000);
            times.add(elapsedTime);
            }
        }



    public static void customValues() {
        print("Please input how many rounds you wish to play:", 0);
        int numRounds = 0;
        numRounds = scanner.nextInt();

        print("Please pick how many elements you want per card (A or B):", 0);
        print("---------------------------------------------------------", 0);
        print("      A)     3                             B)     4      ", 0);
        scanner.nextLine();
        String temp = scanner.nextLine();
        int numElementsPerCard = 0;

        //TODO Fix this
        if (temp.equalsIgnoreCase("a")) {
            numElementsPerCard = 3;
        } else if (temp.equalsIgnoreCase("b")) {
            numElementsPerCard = 4;
        } else {
            print("Please put in a valid input!", 0);
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
            processValues(4, 2, numRounds, modifiedSet);
        } else if (numElementsPerCard == 3) {
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
            processValues(3, 2, numRounds, modifiedSet);
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
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[1]  |"+ BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA +"                 Original                  " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE +"|" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[2]  | " + BRIGHT_BACKGROUND_BLACK + BLUE + "              Input-based                 " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "|" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[3]  | " + BRIGHT_BACKGROUND_BLACK + CYAN + "           Timed Game Variant         " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "    |" + RESET, 0);
        //TODO figure out why this is broken
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[4]  | " + BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "           Restore last score         " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "    |" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[5]  |      " + BRIGHT_BACKGROUND_RED + BLUE + "Quit (you don't want too ooooo)" + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "      |" + RESET, 0);

        int input;

        // Ensuring the inputted values are compatible with the code
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                while (input < 1 || input > 5) {
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

        // Get value for number of rounds
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Please enter the number of rounds you wish to play!", 0);
        int numRounds = scanner.nextInt();

        processValues(4, 1, numRounds, standardSet);
    }

    public static void timedGameVariant(int difficulty) {
        long startTime = System.nanoTime();
        long elapsedTime = (System.nanoTime() - startTime);
        switch(difficulty){
            case 1:
                processValues(4, 31, 3, standardSet);
                print(String.valueOf(elapsedTime), 0);
                return;
            case 2:
                processValues(4, 32, 5, standardSet);
                elapsedTime = (System.nanoTime()-startTime);
                print(String.valueOf(elapsedTime),0);
                return;
            case 3:
                processValues(4, 33, 10, standardSet);
                break;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        printMsgWithProgressBar("Generating the " + RED + "COOLEST " + RESET + "game of SpotIt!", 50, 60);
        print("", 0);
        print(GREEN + "Generation Complete. Welcome!" + RESET, 0);
        Thread.sleep(750);
        while (true) {
            int input = startMenu();

            switch (input) {
                case 1:
                    printMsgWithProgressBar(GREEN + "Loading...", 25, 25);
                    print("", 0);
                    print(RESET + "Done!", 0);
                    standardGame();
                    print(times.toString(), 0);
                    calculateScore(times.size());
                    print(String.valueOf(score), 0);
                    break;
                case 2:
                    printMsgWithProgressBar(GREEN + "Loading...", 25, 25);
                    print("", 0);
                    print(GREEN + "Done!", 0);
                    customValues();
                    print(times.toString(), 0);
                    calculateScore(times.size());
                    print(String.valueOf(score), 0);
                    break;
                case 3:
                    print("  Please enter the difficulty :)  ", 0);
                    print("----------------------------------", 0);
                    print("A) Easy   B) Medium  C) Impossible", 0);
                    String temp = scanner.nextLine();
                    int difficulty = 0;
                    if (temp.equalsIgnoreCase("A")) {
                        difficulty = 1;
                    }
                    else if (temp.equalsIgnoreCase("B")) {
                        difficulty = 2;
                    }
                    else if (temp.equalsIgnoreCase("C")) {
                        difficulty = 3;
                    }
                    timedGameVariant(difficulty);
                    break;
                case 4:
                    if (score > 0) {
                        print("Your last score was: " + score, 0);
                        print("Please press 'q' to return to the main menu!", 0);
                    }
                    else {
                        print("You haven't played yet!", 0);
                        print("Please press 'q' to return to the main menu and get playing!", 0);
                    }
                    String returnMain = scanner.nextLine();
                    if (returnMain.equalsIgnoreCase("q")) {
                        printMsgWithProgressBar(RED + "Loading...", 25, 25);
                        print("", 0);
                        print(RED + "Done!", 0);
                        break;
                    }
                    break;
                case 5:
                    int max = goodbyeMessages.length;
                    int index = random.nextInt(max - 1);
                    print(BRIGHT_BACKGROUND_BLACK + RED + goodbyeMessages[index] + RESET, 0);
                    System.exit(0);
                    break;
            }

        }

    }
}
