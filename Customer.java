/**
 *
 * @author Fred
 */
public class Customer {

    private String username, password, status;
    private int points;

    Customer(String username, String password) {
        this.username = username;
        this.password = password;
        points = 0;
        setStatus(points);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = this.points + points;
        setStatus(this.points);
    }

    public void setStatus(int points) {
        if (points > 1000) {
            status = "Gold";
        } else {
            status = "Silver";
        }
    }
}
