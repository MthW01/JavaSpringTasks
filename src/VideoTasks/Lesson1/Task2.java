package VideoTasks.Lesson1;

public class Task2 {
    public static void main(String[] args) {
        int[][] x = {{20, 34, 2}, {9, 12, 18}, {3, 4, 5}};
        int min = x[0][0];

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                if (x[i][j] < min) {
                    min = x[i][j];
                }
            }
        }

        System.out.println(min);
    }
}
