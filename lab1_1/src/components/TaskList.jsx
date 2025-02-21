import { useState } from "react";
import TaskForm from "./TaskForm";
import TaskItem from "./TaskItem";

function TaskList() {
    const [tasks, setTasks] = useState([]);

    const addTask = (text) => {
        setTasks([...tasks, { text, completed: false }]);
    };

    const completeTask = (index) => {
        const newTasks = [...tasks];
        newTasks[index].completed = !newTasks[index].completed;
        setTasks(newTasks);
    };

    const deleteTask = (index) => {
        setTasks(tasks.filter((_, i) => i !== index));
    };

    return (
        <div className="task-list">
            <h2>To-Do List</h2>
            <TaskForm addTask={addTask} />
            {tasks.length === 0 ? <p>No tasks yet!</p> : null}
            {tasks.map((task, index) => (
                <TaskItem
                    key={index}
                    task={task}
                    index={index}
                    completeTask={completeTask}
                    deleteTask={deleteTask}
                />
            ))}
        </div>
    );
}

export default TaskList;
