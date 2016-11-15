package example.one;

/**
 * Created by Nikita on 04.11.2016.
 */
public class Client {
    private final int id;
    private final String fullName;
    private String greeting = "Greeting!";

    public Client(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public int getId() {
        return id;
    }


    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", greeting='" + greeting + '\'' +
                '}';
    }
}
