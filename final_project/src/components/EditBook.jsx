import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "./../styles/Edit.css";

function EditBook() {
  const { title } = useParams();
  const [newTitle, setNewTitle] = useState("");
  const [authors, setAuthors] = useState("");
  const [category, setCategory] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBook = async () => {
      try {
        const res = await fetch("http://localhost:8080/bibliospring/books", {
          credentials: "include",
        });
        const data = await res.json();
        const found = data.find((b) => b.title === title);
        if (found) {
          setNewTitle(found.title || "");
          setAuthors(found.authors || "");
          setCategory(found.category || "");
        } else {
          navigate("/");
        }
      } catch (err) {
        console.error("Error fetching book for edit:", err);
        navigate("/");
      }
    };
    fetchBook();
  }, [title, navigate]);

  const handleEdit = async (e) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8080/bibliospring/edit", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({
        oldTitle: title,
        newTitle: newTitle || "",
        authors: authors || "",
        category: category || "",
      }),
    });
    const result = await response.json();
    if (result.status === "success") {
      navigate("/Dashboard");
    } else {
      alert("Error editing book.");
    }
  };

  return (
    <form className="formContainer" onSubmit={handleEdit}>
      <h2>Edit Book</h2>
      <input
        className="inp"
        type="text"
        placeholder="Title"
        value={newTitle}
        onChange={(e) => setNewTitle(e.target.value)}
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
      <input type="submit" value="Save Changes" className="submm" />
    </form>
  );
}

export default EditBook;
