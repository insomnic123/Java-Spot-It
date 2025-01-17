/*
Name: Qazi
Program Name: Qazi_SpotIt
Program Description: Hard-coded recreation of the popular game Spot It (Originally known as Dobble)
Creation Date: 09/24/24
Last Edit Date: 10/07/24
Ethics Declaration: “This code is my own work”
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

    // Messages generated using ChatGPT
    public static String[] goodbyeMessages = {
            "Oh, leaving so soon? I guess winning isn't for everyone.",
            "Fine, leave. I wasn’t having fun either.",
            "I see you're running away from your problems... again.",
            "Quitting already? You were *so* close... not really.",
            "Leaving now? Don't worry, nobody noticed you were here.",
            "Rage quitting, or just scared of a challenge?",
            "Goodbye! I hope your next game goes as well as this one. (Hint: it won’t.)",
            "Thanks for playing! Just kidding, you barely played at all.",
            "Oh, you’re done? I guess I'll go cry now... or not.",
            "So long! I bet you leave mid-conversation too.",
            "Quitting now? Wow, commitment really isn’t your thing, is it?",
            "Don’t worry, I’ll finish the game for you. Oh wait... I can't.",
            "I’ll pretend you didn’t just quit... but we both know the truth.",
            "Was it something I said? Or just your skill level?",
            "Leaving already? I had more fun with the loading screen.",
            "Wow, you either really suck, or you're just Prithviraj."
    };

    // Standard Set of Cards as required by the Assignment
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

    // Arraylist to store times
    static List<Integer> times = new ArrayList<>();

    static int score;

    // For game mode #3 - to store the pass/fail messages
    static String passFailMessage = "";

    // Method to calculate score & Output
    public static void calculateScore(int rounds, int mode) {
        // Takes sum of the times and finds the average time
        int sum = 0;
        for (Integer time : times) {
            sum += time;
        }

        double averageTime = sum/(double) rounds;

        // Sets a message for game mode #3
        if (mode == 31) {
            passFailMessage = sum < 25.00 ? CYAN + "You won!" : CYAN + "You've failed :(";
        }
        else if (mode == 32) {
            passFailMessage = sum < 15.00 ? CYAN + "You won!" : CYAN + "You've failed :(";
        }
        else if (mode == 33) {
            passFailMessage = sum < 10.00 ? CYAN + "You won!" : CYAN + "You've failed :(";
        }

        // Calculates score
        score = (int) ((100/(averageTime + 100))*1000);

        // Ensures that if the user quits the score is saved as 0 instead of 1000 during game mode 3
        if (mode == 31 || mode == 32 || mode == 33 && score == 1000) {
            score = 0;
        }
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

    // Processes the values for the various game modes
    public static void processValues(int numArray, int mode, int numRounds, String[][] set) {
        // Resets values
        score = 0;
        times.clear();
        String color = "";

        // Sets up start time
        long startTime = System.nanoTime();

        // Declaring variables
        String word = "";
        int maxRows = 0;

        // Ensures the correct word is used depending on the game mode
        if (mode == 1) {
            word = "letter";
            color = BRIGHT_MAGENTA;
        } else if (mode == 2) {
            word = "name/item?";
            color = BLUE;
        } else if (mode == 31 || mode == 32 || mode == 33) {
            word = "letter";
            color = CYAN;
        }

        // Sets the bounds of the card generation depending on the number of images per card
        if (numArray == 3) {
            maxRows = 6;
        } else if (numArray == 4) {
            maxRows = 12;
        }

        // Selects two cards at random based on their row
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
                print(BRIGHT_BACKGROUND_BLACK + color + s + " " + RESET, 1);
            }
            print("", 0);

            for (String s : cardB) {
                print(BRIGHT_BACKGROUND_BLACK + color + s + " " + RESET, 1);
            }
            print("", 0);

            // Gets the correct answer
            cardB.retainAll(cardA);

            print(BRIGHT_BACKGROUND_BLACK + color + "What is the common " + word + "?" + RESET, 0);
            String guess = scanner.nextLine();

            // Modifies the user input to be compatible with the arrayList
            String guessMod = ("[" + guess + "]");

            while (true) {  // Infinite loop until the correct guess or quit

                if (guess.equalsIgnoreCase("quit")) {
                    print(BRIGHT_BACKGROUND_BLACK + color + "The correct answer was: ", 0);
                    for (String item : cardB) {
                        print(item + RESET, 0);
                    }
                    return;

                } else if (guessMod.equalsIgnoreCase(String.valueOf(cardB))) {
                    print(BRIGHT_BACKGROUND_BLACK + color + "correct" + RESET, 0);
                    break;

                } else {
                    print(BRIGHT_BACKGROUND_BLACK + color + "incorrect! Please guess again" + RESET, 0);
                    guess = scanner.nextLine();
                    guessMod = ("[" + guess + "]");
                }
            }
            // Adds the elapsed time to the arraylist & resets the clock for the next round
            int elapsedTime = (int) ((System.nanoTime() - startTime)/1_000_000_000);
            times.add(elapsedTime);
            startTime = System.nanoTime();
            }
        }

    // A function to validate inputs and loop if a given input is incorrect
    // Returns 1 for option A, 2 for option B, 0 for an incorrect input
    public static int validateInput(String input, String valueA, String valueB, int mode) {
        input = input.toUpperCase();
        String color = "";

        // Sets the colours depending on the game mode
        if (mode == 1) {
            color = BRIGHT_MAGENTA;
        }

        else if (mode == 2) {
            color = BLUE;
        }

        else if (mode == 4) {
            color = BRIGHT_YELLOW;
        }

        else if (mode == 31 || mode == 32 || mode == 33) {
            color = CYAN;
        }

        if (input.equalsIgnoreCase(valueA)) {
            return(1);
        }

        else if (input.equalsIgnoreCase(valueB)) {
            return(2);
        }

        else {
            print(BRIGHT_BACKGROUND_BLACK + color + "Please input either " + valueA + " or " + valueB + "!" + RESET, 0);
            return(0);
        }
    }

    // The custom values game mode allows individuals to input their own values for a game with either 3 or 4 cards,
    // depending to whatever they choose to input
    public static void customValues() throws InterruptedException {
        // Initializing variables
        int numRounds = 0;
        boolean validInput = false;

        // Prevents input mismatch error
        print(BRIGHT_BACKGROUND_BLACK + BLUE + "Please input how many rounds you wish to play:" + RESET, 0);
        while (!validInput){
            try{
                numRounds = scanner.nextInt();
                while (numRounds < 0) {
                    print(BRIGHT_BACKGROUND_BLACK + BLUE + "Invalid input! Please enter an integer number." + RESET, 0);
                    numRounds = scanner.nextInt();
                }
                validInput = true;
            } catch (InputMismatchException e) {
                print(BRIGHT_BACKGROUND_BLACK + BLUE + "Invalid input! Please enter an integer number." + RESET, 0);
                scanner.nextLine();
            }
        }

        // Ensures the user chooses to play more than one round
        if (numRounds == 0) {
            print(BRIGHT_BACKGROUND_BLACK + RED + "Boi wha da freak why did you even click on this then??" + RESET, 0);
            Thread.sleep(500);
            return;
        }

        // Menu Screen !!
        print(BRIGHT_BACKGROUND_BLACK + BLUE + "Please pick how many elements you want per card (A or B):" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BLUE + "---------------------------------------------------------" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BLUE + "      A)     3                             B)     4      " + RESET, 0);
        scanner.nextLine();
        String temp = scanner.nextLine();
        int numElementsPerCard = 0;

        // Checks to see if the user inputted either a or b
        int validatingInput = validateInput(temp, "A","B", 2);

        while (validatingInput == 0) {
            temp = scanner.nextLine();
            validatingInput = validateInput(temp, "A", "B", 2);
        }

        // Sets the number of elements depending on the user's input
        if (validatingInput == 1) {
            numElementsPerCard = 3;
        }

        else if (validatingInput == 2) {
            numElementsPerCard = 4;
        }

        // Gets appropriate values if 4 images per card is chosen
        if (numElementsPerCard == 4) {
            print(BRIGHT_BACKGROUND_BLACK + BRIGHT_BLUE + "Please input thirteen names/items you wish to include (press enter after every option)", 0);
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

            // Uses the same processing method as game A except the letters are redefined based on the input
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

            // Processes the values
            processValues(4, 2, numRounds, modifiedSet);

            // Gets appropriate values if 3 images per card is chosen
        } else if (numElementsPerCard == 3) {
            print(BRIGHT_BACKGROUND_BLACK + BRIGHT_BLUE + "Please input seven names/items you wish to include (press enter after every option)", 0);
            String a = scanner.nextLine();
            String b = scanner.nextLine();
            String c = scanner.nextLine();
            String d = scanner.nextLine();
            String e = scanner.nextLine();
            String f = scanner.nextLine();
            String g = scanner.nextLine();

            // Same idea as the 4 images per card but w/ three
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
        calculateScore(numRounds, 2);
        print(BRIGHT_BACKGROUND_BLACK + BLUE + "Woah! Your score was: " + score + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BLUE + "Press 'R' to replay, or 'Q' to return to main menu!" + RESET, 0);

        String replay;

        replay = scanner.nextLine();
        replay(replay, 2);
    }

    // This is my magnum opus -- this whole project honestly pales in comparison to this amazing creation
    public static void print(String msg, int type) {
        if (type == 0) {
            System.out.println(msg);
        } else {
            System.out.print(msg);
        }
    }

// The Start Menu
    public static int startMenu() {
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "Please select an option from one of the following:" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "--------------------------------------------------" + RESET, 0);
        print(BRIGHT_WHITE + "[1]  |"+ BRIGHT_MAGENTA +"                 Original                  " + BRIGHT_WHITE +"|" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[2]  | " + BRIGHT_BACKGROUND_BLACK + BLUE + "              Input-based                 " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "|" + RESET, 0);
        print(BRIGHT_WHITE + "[3]  | " + CYAN + "           Timed Game Variant         " + BRIGHT_WHITE + "    |" + RESET, 0);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "[4]  | " + BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "           Restore last score         " + BRIGHT_BACKGROUND_BLACK + BRIGHT_WHITE + "    |" + RESET, 0);
        print(BRIGHT_WHITE + "[5]  |      " + BRIGHT_BACKGROUND_RED + BLUE + "Quit (you don't want too ooooo)" + RESET + "      |", 0);

        int input;

        // Ensuring the inputted values are compatible with the code
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                while (input < 1 || input > 5) {
                    print("That's an invalid input! Please put an integer between 1 and 5", 0);
                    input = scanner.nextInt();
                }
                break;

            } catch (InputMismatchException e) {
                print("That's an invalid input! Please put an integer between 1 and 5", 0);
                scanner.nextLine();
            }
        }
        return (input);
    }

    // Sets up replay -- Similar to the validateInput method
    public static void replay(String input, int mode) throws InterruptedException {
        input = input.toUpperCase();
        String color = "";

        if (mode == 1) {
            color = BRIGHT_MAGENTA;
        }
        else if (mode == 2) {
            color = BLUE;
        }
        else if (mode == 3) {
            color = CYAN;
        }

        // Calls the correct function depending on the game mode
        switch (input) {
            case ("R"):
                if (mode == 1) {
                    standardGame();
                }
                if (mode == 2) {
                    customValues();
                }
                if (mode == 3) {
                    timedGameVariant();
                }
                break;

            case "Q":
                return;

            default:
                print(BRIGHT_BACKGROUND_BLACK + color + "Invalid input! Please enter either Q or R!" + RESET,0);
                replay(scanner.nextLine(), mode);
        }
    }

    public static void standardGame() throws InterruptedException {
        String replay;

        // Get value for number of rounds
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Please enter the number of rounds you wish to play!", 0);
        int numRounds = 0;
        boolean validInput = false;

        while (!validInput){
            try{
                numRounds = scanner.nextInt();
                while (numRounds < 0) {
                    print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Invalid input! Please enter an integer number." + RESET, 0);
                    numRounds = scanner.nextInt();
                }
                validInput = true;
            } catch (InputMismatchException e) {
                print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Invalid input! Please enter an integer number." + RESET, 0);
                scanner.nextLine();
            }
        }

        if (numRounds == 0) {
            print(BRIGHT_BACKGROUND_BLACK + RED + "Boi wha da freak why did you even click on this then??" + RESET, 0);
            Thread.sleep(500);
            return;
        }

        processValues(4, 1, numRounds, standardSet);

        // 'Calculates' Score with a display
        printMsgWithProgressBar(RED + "Calculating score...", 25, 25);
        print(" " + RESET, 0);
        calculateScore(times.size(), 1);
        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Woah! Your score was: " + score + RESET, 0);

        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_MAGENTA + "Press 'R' to replay, or 'Q' to return to main menu!" + RESET, 0);
        // Asks the user about replaying
        replay = scanner.nextLine();
        replay(replay, 1);
    }

    // Timed Game Variant !!
    public static void timedGameVariant() throws InterruptedException {
        String replay;

        print(CYAN + "  Please enter the difficulty :)  ", 0);
        print(CYAN + "----------------------------------", 0);
        print(CYAN + "A) Easy   B) Medium  C) Impossible", 0);
        String temp = scanner.nextLine();

        // Sets difficulty in accordance with the temporary variable
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

        switch(difficulty){
            case 1:
                processValues(4, 31, 3, standardSet);
                calculateScore(3, 31);
                print(passFailMessage, 0);
                print(BRIGHT_BACKGROUND_BLACK + CYAN + "Press 'R' to replay, or 'Q' to return to main menu!" + RESET, 0);
                replay = scanner.nextLine();
                replay(replay, 3);
                return;
            case 2:
                processValues(4, 32, 5, standardSet);
                calculateScore(5, 32);
                print(passFailMessage, 0);
                print(BRIGHT_BACKGROUND_BLACK + CYAN + "Press 'R' to replay, or 'Q' to return to main menu!" + RESET, 0);
                replay = scanner.nextLine();
                replay(replay, 3);
                return;
            case 3:
                processValues(4, 33, 10, standardSet);
                calculateScore(10, 33);
                print(passFailMessage, 0);
                print(BRIGHT_BACKGROUND_BLACK + CYAN + "Press 'R' to replay, or 'Q' to return to main menu!" + RESET, 0);
                replay = scanner.nextLine();
                replay(replay, 3);
                break;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Shows fake loading screen
        printMsgWithProgressBar("Generating the " + RED + "COOLEST " + RESET + "game of SpotIt!", 50, 60);
        print("", 0);
        print(GREEN + "Generation Complete. Welcome!" + RESET, 0);

        // Creates pseudo delay
        Thread.sleep(750);

        // Uses start menu and calls neccesary methods depending on the input
        while (true) {
            int input = startMenu();

            switch (input) {
                case 1:
                    printMsgWithProgressBar(GREEN + "Loading...", 25, 25);
                    print("", 0);
                    print(RESET + "Done!", 0);
                    standardGame();
                    break;

                case 2:
                    printMsgWithProgressBar(GREEN + "Loading...", 25, 25);
                    print("", 0);
                    print(GREEN + "Done!" + RESET, 0);
                    customValues();
                    print(times.toString(), 0);
                    calculateScore(times.size(), 2);
                    print(String.valueOf(score), 0);
                    break;

                case 3:
                    timedGameVariant();
                    break;

                case 4:
                    if (score > 0) {
                        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "Your last score was: " + score + RESET, 0);
                        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "Please press 'q' to return to the main menu!" + RESET, 0);
                    }

                    else {
                        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "You haven't played yet!" + RESET, 0);
                        print(BRIGHT_BACKGROUND_BLACK + BRIGHT_YELLOW + "Please press 'q' to return to the main menu and get playing!" + RESET, 0);
                    }

                    String returnMain = scanner.nextLine();
                    int validInput = validateInput(returnMain, "q", "Q", 4);

                    while (validInput == 0) {
                        returnMain = scanner.nextLine();
                        validInput = validateInput(returnMain, "q", "Q", 4);
                    }

                    if (validInput == 1) {
                        printMsgWithProgressBar(RED + "Loading...", 25, 25);
                        print("", 0);
                        print(RED + "Done!", 0);
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
