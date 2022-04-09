import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    String author;
    String typy;
    String message;
    UUID uuid;



    public Message(String author, String typy, String message, UUID uuid) {
        this.author = author;
        this.typy = typy;
        this.message = message;
        this.uuid = uuid;
    }

    public Message(String author, String typy, String message) {
        this.author = author;
        this.typy = typy;
        this.message = message;
    }

    public Message() {
    }
}
