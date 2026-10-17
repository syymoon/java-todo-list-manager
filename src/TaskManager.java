import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private final Path storageFile;
    private int nextId = 1;

    public TaskManager(Path storageFile) throws IOException {
        this.storageFile = storageFile;
        load();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(String title) throws IOException {
        tasks.add(new Task(nextId++, title, false));
        save();
    }

    public boolean completeTask(int id) throws IOException {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.markCompleted();
                save();
                return true;
            }
        }
        return false;
    }

    public boolean deleteTask(int id) throws IOException {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                save();
                return true;
            }
        }
        return false;
    }

    private void load() throws IOException {
        if (!Files.exists(storageFile)) {
            return;
        }
        for (String line : Files.readAllLines(storageFile, StandardCharsets.UTF_8)) {
            String[] parts = line.split(";", 3);
            if (parts.length != 3) {
                continue;
            }
            try {
                int id = Integer.parseInt(parts[0]);
                if (id < 1 || (!parts[1].equals("true") && !parts[1].equals("false"))) {
                    continue;
                }
                boolean completed = Boolean.parseBoolean(parts[1]);
                // Decode titles so special characters (e.g. semicolons) work safely.
                String title = URLDecoder.decode(parts[2], StandardCharsets.UTF_8);
                tasks.add(new Task(id, title, completed));
                if (id >= nextId) {
                    nextId = id + 1;
                }
            } catch (IllegalArgumentException exception) {
                System.err.println("Skipping an invalid saved task.");
            }
        }
    }

    private void save() throws IOException {
        Path parent = storageFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            // Keep the original file format to read tasks saved by older versions.
            String encodedTitle = URLEncoder.encode(task.getTitle(), StandardCharsets.UTF_8);
            lines.add(task.getId() + ";" + task.isCompleted() + ";" + encodedTitle);
        }
        Files.write(storageFile, lines, StandardCharsets.UTF_8);
    }
}
