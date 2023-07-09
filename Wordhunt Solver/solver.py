def create_grid(letters):
    grid = []
    for i in range(0, len(letters), 4):
        row = letters[i:i+4]
        grid.append(list(row))
    return grid


def load_words():
    with open("Wordhunt Solver/words.txt", "r") as f:
        words = [line.strip() for line in f]
    return set(words)


def find_words(grid, words):
    results = []
    for word in words:
        for i in range(4):
            for j in range(4):
                if grid[i][j] == word[0]:
                    if check_word(grid, word, i, j, set()):
                        results.append(word)
    # sort the results by word length in descending order
    results.sort(key=lambda x: len(x), reverse=True)
    return results


def check_word(grid, word, x, y, visited):
    if not word:
        return True
    if x < 0 or x >= 4 or y < 0 or y >= 4:
        return False
    if (x, y) in visited:
        return False
    if grid[x][y] != word[0]:
        return False
    visited.add((x, y))
    for i in range(-1, 2):
        for j in range(-1, 2):
            if i == 0 and j == 0:
                continue
            if check_word(grid, word[1:], x+i, y+j, visited):
                return True
    visited.remove((x, y))
    return False


# Get user input
print("")
letters = input("Enter your letters here: ")
grid = create_grid(letters)

# Load words from a dictionary file
words = load_words()

# Find words in the grid
results = find_words(grid, words)

# Print the results sorted by word length in descending order
print("Results:")
if results:
    # Sort the results by word length in descending order
    results.sort(key=lambda x: len(x), reverse=False)
    for result in results:
        print(result)
else:
    print("No words found.")

# Print the user input grid

print("")
print("↑↑↑↑↑↑ Results ↑↑↑↑↑↑")
print("")
print("-------------------------")
print("")
print("↓↓↓↓↓↓ Grid ↓↓↓↓↓↓")
print("")
for row in grid:
    print(row)
print("")