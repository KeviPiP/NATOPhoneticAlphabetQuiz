import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Quiz {

	public static void main(String[] args) {

		String[] answers = new String[]
				{
						"ALFA", "BRAVO", "CHARLIE", "DELTA", "ECHO", "FOXTROT",
						"GOLF", "HOTEL", "INDIA", "JULIETT", "KILO", "LIMA",
						"MIKE", "NOVEMBER", "OSCAR", "PAPA", "QUEBEC", "ROMEO",
						"SIERRA", "TANGO", "UNIFORM", "VICTOR", "WHISKEY", "XRAY",
						"YANKEE", "ZULU"
				};

		// I'm creating an int array of 26 integers as a way to ensure that no duplicate questions are asked and to ensure
		// that it goes through every single letter.

		List<Integer> IntArray = new ArrayList<>();

		for (int i = 0; i <= 26; i++) {
			IntArray.add(i);
		}

		/*
		 we use a number greater than the total number of items in the answer array (which contains 25 items)
		 to bypass getting an ArrayOutOfBoundsException since the random number cannot be equal to the current length
		 of the IntArray. So having one extra number ensures that we can access all items in the answers array without error.
		*/

		/*
		we will randomly choose numbers from the IntArray and use that chosen number to index and access a string in the
		answers array. Then after the user inputs the correct answer (or fails to, twice), we remove this number from the
		int array, so that it cannot be chosen again, thus, we avoid asking duplicate questions.

		If you want to see it in action. print the IntArray after every correct answer and every incorrect answer, and you'll see what it's doing.
		 */

		// variables for main program
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		int correctAnswers = 0;
		int incorrectAnswers = 0;
		int tries = 2;
		int questionsProcessed = 1;
		int finalScore;

		// main program
		while (true) {
			// IntArray.size - 1 is used to make sure that the random number never equals the IntArray's current max length.
			// That way, the number 26 will never be chosen, as the number 26's index number will always be equal to the max length of the array.
			int randomNum = IntArray.get(rand.nextInt(IntArray.size() - 1));
			String randomPick = answers[randomNum];
			char grabFirstCharacterForQuestion = randomPick.charAt(0);

			while (true) {
				System.out.println("Question " + questionsProcessed);
				System.out.println("What do we use for " + grabFirstCharacterForQuestion + "?");
				System.out.println();
				System.out.println("Note: answer is case-insensitive");
				System.out.println("Your answer: ");
				String input = scan.nextLine();

				if (input.equalsIgnoreCase(randomPick)) {
					System.out.println("Correct!");
					questionsProcessed += 1;
					correctAnswers += 1;
					tries = 2;
					// removing chosen number from int array so we don't get a duplicate question due to it being chosen again.
					IntArray.remove(Integer.valueOf(randomNum));
					System.out.println();
					break;

				} else {
					System.out.println("Incorrect! Try again.");
					tries -= 1;
					System.out.println("You have " + tries + " tries left!");
					System.out.println();

					if (tries == 0) {
						System.out.println("You've ran out of tries.");
						System.out.println("The correct answer was: " + randomPick);
						questionsProcessed += 1;
						incorrectAnswers += 1;
						tries = 2;
						// removing chosen number from int array so we don't get a duplicate question due to it being chosen again.
						IntArray.remove(Integer.valueOf(randomNum));
						System.out.println();
						break;
					}
				}
			}

			/*
			 we stop at 27 instead of 26 because the questionsProcessed variable starts at 1 instead of 0 and the program adds 1 to it after every question.
			 this is to keep track of what question we're on as well as well as tell when the program to finish counting up.

			 If we had it at 26, the program would stop prematurely and never be able give the final 26th question.
			 */
			if (questionsProcessed == 27) {
				System.out.println("That's everything!");
				System.out.println();
				System.out.println("Here is your score:");
				System.out.println("Correct Answers: " + correctAnswers);
				System.out.println("Incorrect Answers: " + incorrectAnswers);

				if (correctAnswers == 26) {
					finalScore = 100;
					System.out.println("Your score is: " + finalScore + "%! Good job!");
				} else {
					finalScore = (int) Math.floor((double)correctAnswers / 26 * 100);
					System.out.println("Your score: " + finalScore + "%");
				}
				System.out.println("Thanks for playing!");
				System.out.println();
				break;
			}
		}

		// option to save scores to txt file...
		while (true) {
			System.out.println("Would you like to save your scores? Y/N");
			String input = scan.nextLine();

			if (input.equalsIgnoreCase("Y")) {
				SaveScores(correctAnswers, incorrectAnswers, finalScore);
				break;
			} else if (input.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("That wasn't a Y or N...");
				System.out.println();
			}
		}
	}

	public static void SaveScores(int correctAnswers, int incorrectAnswers, int finalScore) {

		// record current date (US DATE FORMAT)
		final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		final Date date = new Date();
		final String testDate = formatter.format(date);

		// create txt and check if it already exists or not.
		try {
			System.out.println("Checking if YourScores.txt exists...");
			File myObj = new File("YourScores.txt");
			if (myObj.createNewFile()) {
				System.out.println("YourScores.txt does not exist.");
				System.out.println("Creating file...");
				System.out.println();
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File: " + myObj.getName() + " already exists.");
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// write and append to the txt.
		try {
			FileWriter myWriter = new FileWriter("YourScores.txt", true);

			myWriter.write("-------------------" + System.lineSeparator());
			myWriter.write(System.lineSeparator());
			myWriter.write(testDate + System.lineSeparator());
			myWriter.write(System.lineSeparator());
			myWriter.write("Here is your score:" + System.lineSeparator());
			myWriter.write("Correct Answers: " + correctAnswers + System.lineSeparator());
			myWriter.write("Incorrect Answers: " + incorrectAnswers + System.lineSeparator());
			myWriter.write("Your score is: " + finalScore + "%!" + System.lineSeparator());
			myWriter.write(System.lineSeparator());
			myWriter.close();
			System.out.println("Successfully wrote to YourScores.txt.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
