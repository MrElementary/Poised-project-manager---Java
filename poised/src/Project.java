import java.time.LocalDate;

// Project class creation
public class Project {

    // Attributes
    int projNumber;
    String projName;
    String projType;
    String projAddress;
    String projERF;
    float projCost;
    float projSubCost;
    LocalDate projDeadline;
    Person projArchitect;
    Person projContractor;
    Person projCustomer;
    String projStatus;

    // Constructor method
    public Project(int projNumber, String projName, String projType, String projAddress, String projERF,
                   float projCost, float projSubCost, LocalDate projDeadline, Person projArchitect,
                   Person projContractor, Person projCustomer, String projStatus) {
        this.projNumber = projNumber;
        this.projName = projName;
        this.projType = projType;
        this.projAddress = projAddress;
        this.projERF = projERF;
        this.projCost = projCost;
        this.projSubCost = projSubCost;
        this.projDeadline = projDeadline;
        this.projArchitect = projArchitect;
        this.projContractor = projContractor;
        this.projCustomer = projCustomer;
        this.projStatus = projStatus;
    }

    /* display method
    will append projects to text file in this manner as well. */
    public String toString() {
        String projOutput = "----------------------------------------------------------------";
        projOutput += "\nProject number:                  " + projNumber;
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nProject name:                    " + projName;
        projOutput += "\nProject type:                    " + projType;
        projOutput += "\nProject Address:                 " + projAddress;
        projOutput += "\nProject ERF Number:              " + projERF;
        projOutput += "\nProject cost(Total):             " + projCost;
        projOutput += "\nProject cost(paid to date):      " + projSubCost;
        projOutput += "\nProject cost(remaining):         " + (projCost - projSubCost);
        projOutput += "\nStatus:                          " + projStatus;
        projOutput += "\nProject Deadline:                " + projDeadline + "\n";
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nProject Architect Contact Details";
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nFull Name:                       " + projArchitect.name;
        projOutput += "\nNumber:                          " + projArchitect.phone;
        projOutput += "\nEmail:                           " + projArchitect.email;
        projOutput += "\nAddress:                         " + projArchitect.address;
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nProject Contractor Contact Details";
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nFull Name:                       " + projContractor.name;
        projOutput += "\nNumber:                          " + projContractor.phone;
        projOutput += "\nEmail:                           " + projContractor.email;
        projOutput += "\nAddress:                         " + projContractor.address;
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nProject Customer Contact Details";
        projOutput += "\n----------------------------------------------------------------";
        projOutput += "\nFull Name:                       " + projCustomer.name;
        projOutput += "\nNumber:                          " + projCustomer.phone;
        projOutput += "\nEmail:                           " + projCustomer.email;
        projOutput += "\nAddress:                         " + projCustomer.address;
        projOutput += "\n----------------------------------------------------------------\n";

        return projOutput;
    }
}