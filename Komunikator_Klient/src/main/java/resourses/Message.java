package resourses;



import java.io.Serializable;
import java.util.UUID;

 public class Message implements Serializable {
 private   String author;
 private   String typy;
 private   String message;
 private   UUID uuid;


    public Message() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTypy() {
        return typy;
    }

    public void setTypy(String typy) {
        this.typy = typy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String author, String typy, String message, UUID uuid) {
        this.author = author;
        this.typy = typy;
        this.message = message;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Message(String author, String typy, String message) {
        this.author = author;
        this.typy = typy;
        this.message = message;
    }
}
