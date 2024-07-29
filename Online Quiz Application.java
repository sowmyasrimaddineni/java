import java.util.*;


class Question {
     String questionText;
     String[] options;
     int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(int userAnswer) {
        return userAnswer == correctAnswer;
    }
}

class Quiz {
     List<Question> questions;
     int score;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            int userAnswer = getUserInput(scanner);
            if (question.isCorrect(userAnswer - 1)) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong!");
            }
        }
        System.out.println("Quiz finished! Your score is: " + score + "/" + questions.size());
        scanner.close();
    }

     int getUserInput(Scanner scanner) {
        int userInput;
        while (true) {
            System.out.print("Your answer: ");
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                if (userInput > 0 && userInput <= 4) { 
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
        return userInput;
    }
}

public class Main {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        String[] options1 = {"Java", "Python", "C++", "JavaScript"};
        quiz.addQuestion(new Question("Which programming language is used for Android development?", options1, 0));

        String[] options2 = {"HTML", "CSS", "JavaScript", "SQL"};
        quiz.addQuestion(new Question("Which language is used for styling web pages?", options2, 1));

        String[] options3 = {"1", "2", "3", "4"};
        quiz.addQuestion(new Question("What is the value of 1+1?", options3, 1));

        quiz.start();
    }
}
