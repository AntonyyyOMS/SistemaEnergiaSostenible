package modelo;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    public void undoLastCommand() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
        }
    }
    
    public void clearHistory() {
        history.clear();
    }
}