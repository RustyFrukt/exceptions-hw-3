import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (число без разделителей) и пол(символ латиницей f или m), разделенные пробелом: ");
        String userInput = scanner.nextLine();

        try {
            Person person = parseUserInput(userInput);
            savePersonToFile(person);
            System.out.println("Данные сохранены в файл.");
        } catch (InvalidDataFormatException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: ");
            e.printStackTrace();
        }
    }

    private static Person parseUserInput(String userInput) throws InvalidDataFormatException {
        String[] data = userInput.split(" ");

        if (data.length != 6) {
            throw new InvalidDataFormatException("Введено неверное количество данных");
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String dateOfBirthString = data[3];
        String phoneNumberString = data[4];
        String genderString = data[5];

        // Проверка формата даты рождения
        if (!dateOfBirthString.matches("\\d{2}.\\d{2}.\\d{4}")) {
            throw new InvalidDataFormatException("Неверный формат даты рождения");
        }

        // Проверка формата номера телефона
        if (!phoneNumberString.matches("\\d+")) {
            throw new InvalidDataFormatException("Неверный формат номера телефона");
        }

        // Проверка формата пола
        if (!genderString.matches("[fm]")) {
            throw new InvalidDataFormatException("Неверный формат пола");
        }

        return new Person(lastName, firstName, middleName, dateOfBirthString, phoneNumberString, genderString);
    }

    private static void savePersonToFile(Person person) throws IOException {
        String fileName = person.getLastName() + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(person.toString());
        writer.newLine();
        writer.close();
    }
}

class Person {
    private String lastName;
    private String firstName;
    private String middleName;
    private String dateOfBirth;
    private String phoneNumber;
    private String gender;

    public Person(String lastName, String firstName, String middleName, String dateOfBirth, String phoneNumber, String gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " " + dateOfBirth + " " + phoneNumber + " " + gender;
    }
}

class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}
