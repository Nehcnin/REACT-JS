import React from 'react';

function BookList({ books, onBookDeleted }) {
  const deleteBook = async (title) => {
    try {
      const response = await fetch('http://localhost:8080/bibliospring/book', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ add: 'false', title }),
      });
      const result = await response.json();
      if (result.status === 'success') {
        onBookDeleted();
      } else {
        console.error('Hiba a törlésnél:', result.message);
      }
    } catch (error) {
      console.error('Hiba a törlés kérésnél:', error);
    }
  };

  return (
    <div className="book-list">
      <h2>Könyvlista</h2>
      <ul>
        {books.map((book, index) => (
          <li key={index} className="book-item">
            <p><strong>Cím:</strong> {book.title}</p>
            <p><strong>Szerzők:</strong> {book.authors}</p>
            <p><strong>Hozzáadta:</strong> {book.addedBy}</p>
            <button onClick={() => deleteBook(book.title)}>Törlés</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default BookList;
