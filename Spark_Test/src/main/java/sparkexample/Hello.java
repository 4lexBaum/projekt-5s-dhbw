package sparkexample;

import static spark.Spark.get;

public class Hello {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            return "I couldnt come up with something better than hello";
        });
    }
}