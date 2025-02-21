import { useState } from "react";
function TaskItem({ task, index, completeTask, deleteTask }) {
    return (
      <div className={`task-item ${task.completed ? "completed" : ""}`}>
        <span>{task.text}</span>
        <button onClick={() => completeTask(index)}>✔</button>
        <button onClick={() => deleteTask(index)}>❌</button>
      </div>
    );
  }

  export default TaskItem