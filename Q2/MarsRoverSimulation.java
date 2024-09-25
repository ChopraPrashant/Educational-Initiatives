import java.util.Arrays;
import java.util.List;

// Command interface
interface Command {
    void execute(Rover rover);
}

// Rover class that encapsulates position and direction
class Rover {
    private int x, y;
    private String direction;
    private Grid grid;
    private String[] directions = { "N", "E", "S", "W" };

    public Rover(int x, int y, String direction, Grid grid) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.grid = grid;
    }

    public void moveForward() {
        // Calculate new position based on direction
        int newX = x, newY = y;

        switch (direction) {
            case "N":
                newY += 1;
                break;
            case "S":
                newY -= 1;
                break;
            case "E":
                newX += 1;
                break;
            case "W":
                newX -= 1;
                break;
        }

        // Check if the new position is valid (no obstacle, within grid bounds)
        if (grid.isValidMove(newX, newY)) {
            this.x = newX;
            this.y = newY;
        }
    }

    public void turnLeft() {
        // Rotate counter-clockwise
        this.direction = directions[(getDirectionIndex() - 1 + directions.length) % directions.length];
    }

    public void turnRight() {
        // Rotate clockwise
        this.direction = directions[(getDirectionIndex() + 1) % directions.length];
    }

    private int getDirectionIndex() {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(this.direction)) {
                return i;
            }
        }
        return -1; // Should never happen
    }

    public String report() {
        return "Rover is at (" + this.x + ", " + this.y + ") facing " + this.direction;
    }
}

// Concrete Command classes
class MoveCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.moveForward();
    }
}

class LeftCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.turnLeft();
    }
}

class RightCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.turnRight();
    }
}

// Grid class with obstacle detection and boundaries
class Grid {
    private int width, height;
    private List<int[]> obstacles;

    public Grid(int width, int height, List<int[]> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }

    public boolean isValidMove(int x, int y) {
        // Check if within grid bounds
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        // Check for obstacles
        for (int[] obstacle : obstacles) {
            if (obstacle[0] == x && obstacle[1] == y) {
                return false;
            }
        }
        return true;
    }
}

// Main class for running the program
public class MarsRoverSimulation {
    public static void main(String[] args) {
        // Initialize the grid with obstacles using Arrays.asList() for Java 8
        // compatibility
        List<int[]> obstacles = Arrays.asList(
                new int[] { 2, 2 },
                new int[] { 3, 5 });
        Grid grid = new Grid(10, 10, obstacles);

        // Initialize the rover at (0, 0) facing North
        Rover rover = new Rover(0, 0, "N", grid);

        // List of commands: M, M, R, M, L, M using Arrays.asList()
        List<Command> commands = Arrays.asList(
                new MoveCommand(), new MoveCommand(),
                new RightCommand(), new MoveCommand(),
                new LeftCommand(), new MoveCommand());

        // Execute each command
        for (Command command : commands) {
            command.execute(rover);
        }

        // Print final status report
        System.out.println(rover.report());
    }
}
