// java implementation because why not
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordHuntSolver {

    public static char[][] createGrid(String letters) {
        char[][] grid = new char[4][4];
        for (int i = 0; i < letters.length(); i += 4) {
            String row = letters.substring(i, Math.min(i + 4, letters.length()));
            for (int j = 0; j < row.length(); j++) {
                grid[i / 4][j] = row.charAt(j);
            }
        }
        return grid;
    }

    public static Set<String> loadWords() throws IOException {
        Set<String> words = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Wordhunt Solver/words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        }
        return words;
    }

    public static List<String> findWords(char[][] grid, Set<String> words) {
        List<String> results = new ArrayList<>();
        for (String word : words) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (grid[i][j] == word.charAt(0)) {
                        if (checkWord(grid, word, i, j, new HashSet<>())) {
                            results.add(word);
                        }
                    }
                }
            }
        }
        results.sort((a, b) -> Integer.compare(b.length(), a.length()));
        return results;
    }

    public static boolean checkWord(char[][] grid, String word, int x, int y, Set<String> visited) {
        if (word.isEmpty()) {
            return true;
        }
        if (x < 0 || x >= 4 || y < 0 || y >= 4) {
            return false;
        }
        String pos = x + "," + y;
        if (visited.contains(pos)) {
            return false;
        }
        if (grid[x][y] != word.charAt(0)) {
            return false;
        }
        visited.add(pos);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (checkWord(grid, word.substring(1), x + i, y + j, visited)) {
                    return true;
                }
            }
        }
        visited.remove(pos);
        return false;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("");

        String letters = "Enter your letters here: ";
        char[][] grid = createGrid(letters);
        Set<String> words = loadWords();
        List<String> results = findWords(grid, words);

        System.out.println("Results:");
        if (!results.isEmpty()) {
            results.sort(String::compareTo);
            for (String result : results) {
                System.out.println(result);
            }
        } else {
            System.out.println("No words found.");
        }

        System.out.println("");
        System.out.println("↑↑↑↑↑↑ Results ↑↑↑↑↑↑");
        System.out.println("");
        System.out.println("-------------------------");
        System.out.println("");
        System.out.println("↓↓↓↓↓↓ Grid ↓↓↓↓↓↓");
        System.out.println("");
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }
}

