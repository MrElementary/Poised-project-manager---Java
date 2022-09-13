
// Person class creation for architect, contractor and customer
public class Person {

        // Attributes
        String name;
        String phone;
        String email;
        String address;

        // Constructor method
        public Person(String name, String phone, String email, String address) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
        }

        /* display method created for internal testing purposes, not used anywhere as
        display for overall project includes these details of persons already. */
        public String toString() {
            return "This is " + name + " " + phone + " " + email + " " + address;
        }

}
