import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./../styles/Dashboard.css";

function Dashboard() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [books, setBooks] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    const user = localStorage.getItem("username");
    if (!user) {
      navigate("/");
    } else {
      setUsername(user);
      fetchBooks();
    }
  }, [navigate]);

  const fetchBooks = async () => {
    try {
      const res = await fetch("http://localhost:8080/bibliospring/books", {
        credentials: "include",
      });
      const data = await res.json();
      setBooks(data);
    } catch (err) {
      console.error("Error fetching books:", err);
    }
  };

  const logout = () => {
    localStorage.removeItem("username");
    navigate("/");
  };

  const handleDelete = async (title) => {
    await fetch("http://localhost:8080/bibliospring/book", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ add: "false", title }),
    });
    fetchBooks();
  };

  const filteredBooks = books.filter((book) =>
    book.title.toLowerCase().includes(search.toLowerCase())
  );

  const handleClearSearch = () => {
    setSearch("");
  };

  return (
    <>
      <div className="header">
        <span className="user">{username}</span>
        <div className="search-container">
          <input
            type="text"
            className="search"
            placeholder="Search by title..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <button className="clear-search" onClick={handleClearSearch}>
            Clear
          </button>
        </div>
        <button className="logout" onClick={logout}>
          Logout
        </button>
      </div>

      <div className="nav-buttons">
        <Link to="/add">
          <button className="nav-btn1">Add Book</button>
        </Link>
        <Link to="/password">
          <button className="nav-btn2">Change Password</button>
        </Link>
      </div>

      <div className="bookList">
        {filteredBooks.map((book, index) => (
          <div key={index} className="bookItem">
            <p><b>Title:</b> {book.title}</p>
            <p><b>Authors:</b> {book.authors}</p>
            <p><b>Category:</b> {book.category}</p>
            <p><b>Added by:</b> {book.addedBy}</p>
            <Link to={`/edit/${encodeURIComponent(book.title)}`}>
              <button className="editBtn">Edit</button>
            </Link>
            <button className="deleteBtn" onClick={() => handleDelete(book.title)}>
              Delete
            </button>
          </div>
        ))}
      </div>
    </>
  );
}

export default Dashboard;
