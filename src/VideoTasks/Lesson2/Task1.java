package VideoTasks.Lesson2;

import java.util.Random;

enum Variants {
    Rock, Paper, Scissors
}

public class Task1 {
    public static void main(String[] args) {
        Player bot = new Player();
        Player anton = new Player(Variants.Scissors, "Anton");

        System.out.println(bot.whoWins(anton));
    }
}

class Player {
    private String name;
    private Variants variant;

    public Player() {
        this.name = "Bot";
        this.variant = getRandomVariant();
    }

    public Player(Variants variant, String name) {
        this.variant = variant;
        this.name = name;
    }

    private Variants getRandomVariant() {
        Variants[] variants = Variants.values();
        Random random = new Random();
        return variants[random.nextInt(Variants.values().length)];
    }

    public String whoWins(Player opponent) {
        if (this.variant == opponent.variant) {
            return "Ничья";
        }

        return switch (this.variant) {
            case Rock ->
                    (opponent.variant == Variants.Scissors) ? "Победил игрок с именем: " + this.name : "Победил игрок с именем: " + opponent.name;
            case Paper ->
                    (opponent.variant == Variants.Rock) ? "Победил игрок с именем: " + this.name : "Победил игрок с именем: " + opponent.name;
            case Scissors ->
                    (opponent.variant == Variants.Paper) ? "Победил игрок с именем: " + this.name : "Победил игрок с именем: " + opponent.name;
            default -> "Ничья";
        };
    }
}



