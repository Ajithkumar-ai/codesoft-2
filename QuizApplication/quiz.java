import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class quiz {
    private static String[][] questions = {
        {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid"},
        {"What is 2 + 2?", "3", "4", "5", "6"},
        {"What is the square root of 16?", "2", "4", "8", "16"},
        {"What is the primary language used for Android development?","Java","python","javascript","c++"},
        {"What protocol is used for securing communication over the internet?","HTTPS","HTTP","TCP","Internet protocol"},
        {"Which database is known for its document-oriented NoSQL capabilities?","Sql","mongodb","django","react"},
    };
 // Index of correct options (1-based)
    private static int[] correctAnswers = {1, 2, 2, 1, 1, 1};
    private static int currentQuestionIndex = 0;
    private static int score = 0;
    private static int timePerQuestion = 10; // time in seconds
    private static boolean timeUp = false;
// To store incorrect questions and answers
    private static List<String> incorrectQuestions = new ArrayList<>();
    private static List<Integer> userAnswers = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Quiz Application...");
        Thread.sleep(1000);
        System.out.println("You have " + timePerQuestion + " seconds to answer each question.");

        while (currentQuestionIndex < questions.length && !timeUp) {
            long startTime = System.currentTimeMillis();
            displayQuestion(currentQuestionIndex);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeUp = true;
                    System.out.println("\nTime's up! Moving to the next question.");
                    System.out.println("Quiz over! Your score is: " + score + " out of " + questions.length);
                }
            }, timePerQuestion * 1000);

            int userAnswer = -1;
            while (userAnswer < 1 || userAnswer > 4) {
                try {
                    userAnswer = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a number between 1 and 6: ");
                }
            }
            timer.cancel();

            if (timeUp) {
                break;
            }

            long endTime = System.currentTimeMillis();
            long timeTaken = (endTime - startTime) / 1000; // time in seconds
            System.out.println("Time taken: " + timeTaken + " seconds");

            checkAnswer(userAnswer);
            currentQuestionIndex++;
        }

       
        displayIncorrectAnswers();
        scanner.close();
    }

    private static void displayQuestion(int index) {
        System.out.println("\nQuestion " + (index + 1) + ": " + questions[index][0]);
        for (int i = 1; i <= 4; i++) {
            System.out.println(i + ". " + questions[index][i]);
        }
        System.out.print("Enter your answer(1-6): ");
    }

    private static void checkAnswer(int userAnswer) {
        int correctAnswer = correctAnswers[currentQuestionIndex];
        if (userAnswer == correctAnswer) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect. The correct answer is: " + correctAnswer + ". " + questions[currentQuestionIndex][correctAnswer]);
            // Store the incorrect question and user's answer
            incorrectQuestions.add(questions[currentQuestionIndex][0]);
            userAnswers.add(userAnswer);
        }
    }

    private static void displayIncorrectAnswers() {
        if (incorrectQuestions.isEmpty()) {
            System.out.println("Great job! You answered all questions correctly.");
        } else {
            System.out.println("\nSummary of incorrect answers:");
            for (int i = 0; i < incorrectQuestions.size(); i++) {
                System.out.println("Question: " + incorrectQuestions.get(i));
                System.out.println("Your answer: " + userAnswers.get(i));
                System.out.println("The Correct answer: " + correctAnswers[currentQuestionIndex - incorrectQuestions.size() + i] + "." + questions[currentQuestionIndex - incorrectQuestions.size() + i][correctAnswers[currentQuestionIndex - incorrectQuestions.size() + i]]);
            }
        }
    }
}

