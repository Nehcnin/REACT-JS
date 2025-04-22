import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./../styles/Add.css";

function AddBook() {
  const [title, setTitle] = useState("");
  const [authors, setAuthors] = useState("");
  const [category, setCategory] = useState("");
  const [message, setMessage] = useState("");
  const username = localStorage.getItem("username");
  const navigate = useNavigate();

  const handleAddBook = async (e) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8080/bibliospring/book", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ add: "true", title, authors, category, username }),
    });
    const result = await response.json();
    if (result.status === "success") {
      setMessage("Book added successfully!");
      setTimeout(() => navigate("/"), 1500);
    } else {
      setMessage("Error adding book.");
    }
  };

  return (
    <form className="formContainer" onSubmit={handleAddBook}>
      <h2>Add Book</h2>
      <input
        className="inp"
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <input
        className="inp"
        type="text"
        placeholder="Authors"
        value={authors}
        onChange={(e) => setAuthors(e.target.value)}
      />
      <input
        className="inp"
        type="text"
        placeholder="Category"
        value={category}
        onChange={(e) => setCategory(e.target.value)}
      />
      <input type="submit" value="Add Book" className="submm" />
      {message && <div className="mess">{message}</div>}
    </form>
  );
}

export default AddBook;
