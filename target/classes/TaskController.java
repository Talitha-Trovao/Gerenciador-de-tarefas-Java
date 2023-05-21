import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "task-list";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        model.addAttribute("task", task);
        return "task-details";
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        model.addAttribute("task", task);
        return "task-form";
    }

    @PostMapping("/tasks/{id}/edit")
    public String editTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));

        task.setTitulo(updatedTask.getTitulo());
        task.setDescricao(updatedTask.getDescricao());
        task.setStatus(updatedTask.getStatus());
        task.setCompletionDate(updatedTask.getCompletionDate());

        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));

        taskRepository.delete(task);
        return "redirect:/";
    }
}
