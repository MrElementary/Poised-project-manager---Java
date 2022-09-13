import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Poised {

    /* definition and initialization of array that will hold
    the project numbers. Once the text file gets implemented
    I will populate this with existing project numbers
    from the file. For now this list gets wiped
    on each new program instance. */
    public static int[] projNumberArray = {1, 2, 3};

    public static Project[] projArray = {};

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        /* Example projects created before use of text file
        to play around with in editing them and testing code. */
        Project projOne = new Project(1, "House Tyson", "House",
                "22 Jump Street", "268", 568000,
                200000, LocalDate.of(2024, Month.APRIL, 13),
                new Person("Jan Grobler", "0872249912", "jan@classarchitects.com",
                        "24 Parson Street"),
                new Person("Pieter Wentzel", "0872244827",
                        "pieter@classcontractors.com", "16 Alibi Street"),
                new Person("Mike Tyson", "0872241234",
                        "mike@tysonboxing.com", "22 Jump Street"), "Incomplete");

        Project projTwo = new Project(2, "Stoltz redo apartment", "Apartment",
                "19 lang Street", "3", 600000,
                150000, LocalDate.of(2023, Month.SEPTEMBER, 1),
                new Person("Jan Grobler", "0872249912", "jan@classarchitects.com",
                        "24 Parson Street"),
                new Person("Pieter Wentzel", "0872244827",
                        "pieter@classcontractors.com", "16 Alibi Street"),
                new Person("Tanzi Stoltz", "0872241234",
                        "tanzi@stoltz.com", "16 Jump Street"), "Incomplete");

        Project projThree = new Project(3, "Stoltz redo office", "Office",
                "19 lang Street", "4", 200000,
                150000, LocalDate.of(2022, Month.JANUARY, 19),
                new Person("Jan Grobler", "0872249912", "jan@classarchitects.com",
                        "24 Parson Street"),
                new Person("Pieter Wentzel", "0872244827",
                        "pieter@classcontractors.com", "16 Alibi Street"),
                new Person("Wihan Stoltz", "0872241234",
                        "wihan@stoltz.com", "16 Jump Street"), "Complete");

        // Sample code to populate projects for testing code
        projArray = addProj(projArray, projOne);
        projArray = addProj(projArray, projTwo);
        projArray = addProj(projArray, projThree);

        // User Interface starting point
        System.out.println("Welcome to Poised project management 1.0\n");
        // Main menu call
        menuMethod();
    }

    /* Main menu method from where program calls to other
    methods based on user input. All choices eventually
    lead back here.
    Implemented a switch cases for all options.
    *******NOTE******
    I have included the finalize task inside the editMethod() function!*/
    public static void menuMethod() {

        while (true) {
            String menu = """
                    Please select from the following options:
                    N - Create new project
                    E - Edit existing project
                    A - View all projects
                    I - View incomplete projects
                    D - View projects past due date
                    V - View specific project
                    Exit - Close the program""";
            System.out.println(menu);
            String choice = input.nextLine().toLowerCase();

            // used switch to cover if this option or that option.
            switch (choice) {

                // exit block
                case "exit":
                    System.exit(0);

                    /* call to create new project
                    after which it will be displayed in full
                    and then return to main menu */
                case "n":
                    createProjectMethod();
                    System.out.println("\nNew project details captured. Returning to main menu....\n");
                    break;

                // call to edit project details.
                case "e":
                    editMethod();
                    break;

                // call to view all projects
                case "a":
                    for (Project project : projArray) {
                        System.out.println(project);
                    }
                    break;

                // call to view incomplete projects
                case "i": {
                    int counter = 0;
                    for (Project project : projArray) {
                        if (Objects.equals(project.projStatus, "Incomplete")) {
                            counter += 1;
                            System.out.println(project);
                        }
                    }
                    if (counter == 0) {
                        System.out.println("There are no incomplete projects. Returning to main menu...\n");
                    }
                    break;
                }

                // call to view projects past due date
                case "d": {
                    int counter = 0;
                    for (Project project : projArray) {
                        if (project.projDeadline.isBefore(LocalDate.now())
                                && Objects.equals(project.projStatus, "Incomplete")) {
                            counter += 1;
                            System.out.println(project);
                        }
                    }
                    if (counter == 0) {
                        System.out.println("There are no overdue projects. Returning to main menu...\n");
                    }
                    break;
                }

                /* call to view specific project by name or number
                also includes switch case inside for options to return to menu
                as well as picking an object by name and picking an object by number */
                case "v": {

                    // First offer choice of which lookup method to use
                    while (true) {
                        System.out.println("Please specify if you're looking for a project by name or by number, " +
                                "or press M to return to main menu");
                        String secondChoice = input.nextLine().toLowerCase();

                        // inner switch for name, number and menu
                        switch (secondChoice) {

                            // Case for searching project by name
                            case "name":
                                System.out.println("Please specify the name of the project you're looking for: ");
                                String thirdChoice = input.nextLine();
                                for (Project project : projArray) {
                                    if (thirdChoice.matches(project.projName)) {
                                        System.out.println(project);
                                        menuMethod();
                                    }
                                }
                                System.out.println("Name entered was either invalid or not found\n");
                                break;

                            // Case for searching by number
                            case "number":
                                try {
                                    System.out.println("Please specify the number of the project you're looking for: ");
                                    int fourthChoice = input.nextInt();
                                    for (Project project : projArray) {
                                        if (project.projNumber == fourthChoice) {
                                            System.out.println(project);
                                            menuMethod();
                                        }
                                    }
                                    System.out.println("Number entered was either invalid or not found\n");
                                    String blank = input.nextLine();
                                } catch (InputMismatchException ie) {
                                    System.out.println("Invalid input entered\n");
                                    String blank = input.nextLine();
                                }
                                break;

                            // case for returning to main menu.
                            case "m":
                                menuMethod();
                            default:
                                System.out.println("Incorrect option selected\n");
                                break;
                        }
                    }
                }
                default:
                    System.out.println("Incorrect option selected\n");
                    break;
            }
        }
    }

    /* function for creating a new project object.
    each argument of the object parameters has been encased
    in its own while loop to account for incorrect input*/
    public static void createProjectMethod() {
        /* declaring variables from each loop for
        creating the object. Had to create them here as they are localized within
        the while loop */
        int projNumber;
        String projType;
        String projAddress;
        String erfNumber;
        float totalCostNumber;
        float paidCostNumber;
        LocalDate projDeadLine;
        String projName;
        Person architect;
        Person contractor;
        Person customer;
        String projStatus = "Incomplete";

        /* code block to get input for the project number.
        calls to function that creates and keeps track of
        existing project numbers */
        while (true) {
            try {
                String userText = "Please provide a project number: ";
                System.out.println(userText);
                projNumber = input.nextInt();

                /* This if loop checks if this project's number has been used yet.
                Since we're not writing to a text file of saved previous projects yet
                this check will always start on an array with only one example
                project in it. However, as long as the program runs it will check
                created projects in the same instance of the program. */
                int finalProjNumber = projNumber;
                if (Arrays.stream(projNumberArray).anyMatch(x -> x == finalProjNumber)) {
                    System.out.println("That project number already exists.");
                }
                else {
                    // Call to below function for project number array to avoid duplicates.
                    projNumberArray = addProjNumber(projNumberArray, projNumber);
                    System.out.println(Arrays.toString(projNumberArray));
                    break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect input received");
            }
        }

        // code block to get input for project type.
        while (true) {
            String userText = """
                    Please provide a project type from the following:
                                              
                    House
                    Apartment
                    Office
                    Business
                    Outdoor area
                    """;
            System.out.println(userText);
            projType = input.nextLine().toLowerCase().trim();
            String[] types = {"house", "apartment", "office", "business", "outdoor area"};

            // if loop checks from above array of project type choices
            if (Arrays.stream(types).noneMatch(projType::equals)) {
                System.out.println("Not a valid project type\n");
            } else {
                break;
            }
        }

        /* code block to get input for project Address.
        Intentionally using while loop that doesn't loop
        just to make all inputs look universal */
        while (true) {
            String userText = "Please provide a project address: ";
            System.out.println(userText);
            projAddress = input.nextLine();
            break;
        }

        /* code block to get input for ERF number.
        exception handling for not entering integer. */
        while (true) {
            try {
                String userText = "Please provide a project ERF number: ";
                System.out.println(userText);
                erfNumber = input.nextLine();
                break;
            }
            catch (InputMismatchException ex) {
                System.out.println("Incorrect input received");
            }
        }

        /* code block to get input for total cost of project
        exception handling for not entering integer. */
        while (true) {
            try {
                String userText = "Please provide the total cost of the project: ";
                System.out.println(userText);
                String costNumberString = input.nextLine();
                // This line is to remove space if user enters e.g. "500 000"
                costNumberString = costNumberString.replace(" ","");
                totalCostNumber = Float.parseFloat(costNumberString);
                break;
            }
            catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("Incorrect input received");
            }
        }

        /* code block to get input for cost paid to date
        exception handling for not entering integer */
        while (true) {
            try {
                String userText = "Please provide cost contributed so far to the project: ";
                System.out.println(userText);
                String costNumberString = input.nextLine();
                // This line is to remove space if user enters e.g. "500 000"
                costNumberString = costNumberString.replace(" ","");
                paidCostNumber = Float.parseFloat(costNumberString);

                // if loop here to check if value is more than total project value
                if (paidCostNumber > totalCostNumber){
                    System.out.println("This value cannot be more than total project cost");
                }
                else
                    break;
            }
            catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("Incorrect input received");
            }
        }

        /* code block to get deadline for project
        code converts string to localdate of chosen format
        exception handling for incorrect date format */
        while (true) {
            try {
                String userText = "Please provide a project deadline date in format(day month year) e.g '2022 06 15': ";
                System.out.println(userText);
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy MM d");
                String temp = input.nextLine();
                projDeadLine = LocalDate.parse(temp, dateFormat);
                break;
            }
            catch (IllegalArgumentException | DateTimeParseException Ie) {
                System.out.println("You did not enter a correct date format.");
            }
        }

        /* calls to method for creating a person object which are used as parameters
        of the project object */
        System.out.println("\nPlease enter details for architect:");
        architect = personMethod();
        System.out.println("\nPlease enter details for contractor:");
        contractor = personMethod();
        System.out.println("\nPlease enter details for customer:");
        customer = personMethod();

        /* code block to get name of project
        defaults to project type and first name
        of customer object if nothing entered */
        while (true) {
            String userText = "Please provide a project name: ";
            System.out.println(userText);
            projName = input.nextLine();
            if ("".equals(projName)) {
                String tempNameVar = customer.name.split(" ")[0];
                projName = projType + " " + tempNameVar;
                System.out.println(projName);
            }
            break;
        }
        // Creates new project and adds it to the array of projects
        Project newProj = new Project(projNumber, projName, projType, projAddress, erfNumber, totalCostNumber,
                paidCostNumber, projDeadLine, architect, contractor, customer, projStatus);
        System.out.println(newProj);
        projArray = addProj(projArray, newProj);
    }

    /* function for creating a person object of which the project object contains 3 versions
    one for architect, one for customer and one for contractor. */
    public static Person personMethod() {
        String persName;
        String persSurname;
        String persFullName;
        String phoneNumber;
        String email;
        String persAddress;

        /* loop to get name input of person.
        error handling included if name contains non alphabet characters
        and make sure a name and surname is entered. Basically just check that string has two words. */
        while (true) {
            try {
                String userText = "Please enter the full name: ";
                System.out.println(userText);
                String[] archFullNameArray = input.nextLine().split(" ");
                persName = archFullNameArray[0].strip();
                persSurname = archFullNameArray[1].strip();
                if (persName.matches("[a-zA-Z]+") && persSurname.matches("[a-zA-Z]+")) {
                    persFullName = persName + " " + persSurname;
                    break;
                }
                else {
                    System.out.println("You did not enter a valid name");
                    personMethod();
                }
            }
            catch (InputMismatchException | ArrayIndexOutOfBoundsException Ie) {
                System.out.println("Incorrect name format");
            }
        }

        /* loop to get telephone number
        error handling includes making sure string only contains numeric values
        and has to be of length 10 as per standard cell numbers e.g. 0831234567 */
        while (true) {
            String userText = "Please enter the phone number: ";
            System.out.println(userText);
            phoneNumber = input.nextLine().strip();
            if (phoneNumber.matches("^[0-9]*$") && (phoneNumber.length() == 10)) {
                break;
            }
            else System.out.println("Invalid phone number format");
        }

        /* loop to get email address
        didn't make the error handling too complicated.
        just ensured that string contains an '@' sign at the least. */
        while (true) {
            String userText = "Please enter the email address: ";
            System.out.println(userText);
            email = input.nextLine();
            if (email.contains("@")){
                break;
            }
            else System.out.println("Invalid email address entered");
        }

        /* loop to get address
        doesn't need to be a loop since I provided no error handling
        just encapsulated in loop to make all inputs look universal and neat */
        while (true) {
            String userText = "Please provide the person's address: ";
            System.out.println(userText);
            persAddress = input.nextLine();
            break;
        }

        // return value of function which returns newly created person object
        return new Person(persFullName, phoneNumber, email, persAddress);
    }

    /* function that populates array with project numbers
    to avoid creating projects with the same number.
    Takes the already existing array and creates
    a new array of length + new project number whenever a new
    Project is created */
    public static int[] addProjNumber(int[] projNumberArr, int newProjNumber) {
        int i;
        int lenOfArr = projNumberArr.length;

        // create a new array of size lenOfList+1
        int[] newarr = new int[lenOfArr + 1];

        /* insert the elements from
        the old array into the new array
        insert all elements till lenOfArr
        then insert newProjNumber at lenOfArr+1 */
        for (i = 0; i < lenOfArr; i++)
            newarr[i] = projNumberArr[i];

        newarr[lenOfArr] = newProjNumber;

        return newarr;
    }


    /* function that populates array with projects
    will eventually update to populate array from text file. */
    public static Project[] addProj(Project[] projArr, Project newProj) {
        int i;
        int lenOfArr = projArr.length;

        // create a new array of size lenOfList+1
        Project[] newarr = new Project[lenOfArr + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till lenOfArr
        // then insert newProj at lenOfArr+1
        for (i = 0; i < lenOfArr; i++)
            newarr[i] = projArr[i];

        newarr[lenOfArr] = newProj;

        return newarr;
    }

    // function to initiate code blocks for editing parts of the objects
    public static void editMethod() {
        int numberChoice;
        String editChoice;
        Project chosenProj = null;

        /* loop for selecting a valid project
        and store that project in variable that will be used for editing attributes
        in next loop */
        while (true) {
            try {
                System.out.println("Please choose which project you'd like to edit from the list of project numbers: ");
                System.out.println(Arrays.toString(projNumberArray));
                numberChoice = input.nextInt();
                int finalNumberChoice = numberChoice;
                if (Arrays.stream(projNumberArray).anyMatch(x -> x == finalNumberChoice)){
                    for (Project project : projArray) {
                        if (project.projNumber == numberChoice) {
                            chosenProj = project;
                            System.out.println("Listing chosen project to edit: ");
                            System.out.println(chosenProj);
                            break;
                        }
                    }
                    break;
                }
                else{
                    System.out.println("invalid number chosen");
                }
            }
            catch(InputMismatchException Ie) {
                System.out.println("Incorrect input entered");
            }
        }

        // loop to select what you want to edit in the chosen project object
        while (true) {
            String menu = """
                    Please select from the following options to edit:
                    D - Adjust due date
                    F - Adjust fee paid to date
                    C - Adjust Contractor contact details
                    P - Finalize project
                    M - Return to main menu""";
            System.out.println(menu);
            String choice = input.nextLine().toLowerCase();

            /* code block to adjust deadline date of chosen project
            inside block included if statement for completed project,
            as well as error handling for incorrect dates including attempts at
            backdating a project. */
            if ("d".equals(choice)){
                while (true) {
                    assert chosenProj != null;

                    /* This if statement will check if the project is already completed and
                    will notify accordingly that deadline cannot be adjusted. */
                    if (chosenProj.projStatus.equals("Complete")) {
                        System.out.println("This project is already completed. Cannot adjust due date");
                        System.out.println("Returning to main menu...\n");
                        menuMethod();
                    }

                    System.out.println("Please provide new date in format(day month year) e.g '2022 06 15': ");
                    try {
                        LocalDate newProjDeadline;
                        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy MM d");
                        String temp = input.nextLine();
                        newProjDeadline = LocalDate.parse(temp, dateFormat);
                        if (chosenProj.projDeadline.isAfter(newProjDeadline)) {
                            System.out.println("That date is already prior to current deadline date.");
                        } else if (newProjDeadline.isBefore(LocalDate.now())) {
                            System.out.println("Cannot assign date from the past.");
                        } else {
                            chosenProj.projDeadline = newProjDeadline;
                            System.out.println("\nNew deadline has been assigned. Returning to main menu...\n");
                            menuMethod();
                        }
                    }
                    catch (IllegalArgumentException | DateTimeParseException Ie) {
                        System.out.println("You did not enter a correct date format.");
                    }
                }
            }

            /* code block to adjust total amount of fee paid to date
            includes error handling for incorrect input,
            as well as for undervalue or overvalue of input.
            lastly, inserted if loop at start if project has been paid in full. */
            if ("f".equals(choice)) {
                assert chosenProj != null;
                if (chosenProj.projSubCost == chosenProj.projCost) {
                    System.out.println("This project has already been paid in full.");
                    System.out.println("Returning to main menu...\n");
                    menuMethod();
                }
                while (true) {
                    System.out.println("\nPlease give the new amount of total contributions to date: ");
                    try {
                        float newSubCost;
                        String newSubCostString = input.nextLine();
                        newSubCostString = newSubCostString.replace(" ","");
                        newSubCost = Float.parseFloat(newSubCostString);
                        if (newSubCost > chosenProj.projCost){
                            System.out.println("This value cannot be more than total project cost");
                        } else if (newSubCost < chosenProj.projSubCost) {
                            System.out.println("Cannot change contribution to less than previously paid");
                        } else {
                            chosenProj.projSubCost = newSubCost;
                            System.out.println("\nNew total contribution has been assigned. Returning to main menu...\n");
                            menuMethod();
                        }
                    }
                    catch (InputMismatchException | NumberFormatException ex) {
                        System.out.println("Incorrect input received");
                    }
                }
            }

            // code block to update the contractors details
            if ("c".equals(choice)) {
                Person newContractor;
                newContractor = personMethod();
                assert chosenProj != null;
                chosenProj.projContractor = newContractor;
                System.out.println("\nContractor details updated, returning to main menu...");
                menuMethod();
            }

            // code block to return to main menu
            if ("m".equals(choice)) {
                menuMethod();
            }

            /* code block for finalizing a project
            with no text file this will only be temporary as well.
            error handling provided for trying to mark task that's already completed
            as well as invalid input.
            ****NOTE***
            I don't know if we are meant to code the invoice creation in this task as yet
            however I left it out because I will create it dynamically via a text file instead
            of temporary display on the output.
            for now this will only allow the user to mark a project as complete in the object attributes. */
            if ("p".equals(choice)){

                while  (true) {
                    assert chosenProj != null;
                    if (Objects.equals(chosenProj.projStatus, "Complete")) {
                        System.out.println("This project has already been finalized. Returning to main menu...\n");
                        menuMethod();
                    }
                    System.out.println("Are you sure you want to finalize this project? Press Y to finalize or" +
                            " N to return to main menu:");
                    String sureOfChoice = input.nextLine().toLowerCase();
                    if ("n".equals(sureOfChoice)){
                        menuMethod();
                    } else if ("y".equals(sureOfChoice)) {
                        chosenProj.projStatus = "Complete";
                        System.out.println("Project marked Complete. Returning to main menu...\n");
                        menuMethod();
                    }
                    else {
                        System.out.println("Invalid input received");
                    }
                }
            }

            // invalid choice loop back in editMethod()
            else {
                System.out.println("Invalid choice");
            }
        }
    }

}