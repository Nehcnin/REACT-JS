import React, { useState, useEffect, useMemo } from 'react';
import Header from './components/Header';
import BookForm from './components/BookForm';
import BookDeleteForm from './components/BookDeleteForm';
import BookList from './components/BookList';
import PasswordChangeForm from './components/PasswordChangeForm';
import './App.css';

function App() {
  const [books, setBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await fetch('http://localhost:8080/bibliospring/books');
      const data = await response.json();
      setBooks(data);
    } catch (error) {
      console.error('Hiba a könyvek lekérésekor:', error);
    }
  };

  const filteredBooks = useMemo(() => {
    return books.filter(book =>
      book.title.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }, [books, searchTerm]);

  return (
    <div className="app">
      <Header />
      <div className="container">
        <BookForm onBookAdded={fetchBooks} />
        <BookDeleteForm onBookDeleted={fetchBooks} />
        <input
          type="text"
          placeholder="Keresés cím alapján..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-input"
        />
        <BookList books={filteredBooks} onBookDeleted={fetchBooks} />
        <PasswordChangeForm />
      </div>
    </div>
  );
}

export default App;
