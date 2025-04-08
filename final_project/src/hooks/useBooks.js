import { useState } from "react";

const useBooks = (username) => {
  const [books, setBooks] = useState([]);

  const fetchBooks = async () => {
    const res = await fetch("http://localhost:8080/bibliospring/books");
    const data = await res.json();
    setBooks(data);
  };

  const addBook = async (title, authors) => {
    const res = await fetch("http://localhost:8080/bibliospring/book", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ add: "true", title, authors, username }),
    });
    const result = await res.json();
    return result.status === "success";
  };

  const deleteBook = async (title) => {
    await fetch("http://localhost:8080/bibliospring/book", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ add: "false", title }),
    });
  };

  return { books, fetchBooks, addBook, deleteBook };
};

export default useBooks;
