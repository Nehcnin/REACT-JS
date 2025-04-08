import React, { useState } from 'react';

function BookForm({ onBookAdded }) {
  const [title, setTitle] = useState('');
  const [authors, setAuthors] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const username = localStorage.getItem('username');

    try {
      const response = await fetch('http://localhost:8080/bibliospring/book', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ add: 'true', title, authors, username }),
      });
      const result = await response.json();
      if (result.status === 'success') {
        setMessage('Könyv sikeresen hozzáadva!');
        onBookAdded();
        setTitle('');
        setAuthors('');
      } else {
        setMessage('Hiba történt a könyv hozzáadásakor!');
      }
    } catch (error) {
      setMessage('Hálózati hiba történt!');
    }

    setTimeout(() => setMessage(''), 2000);
  };

  return (
    <form className="book-form" onSubmit={handleSubmit}>
      <p className="form-title">Könyv hozzáadása</p>
      <label>
        Cím:
        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
      </label>
      <label>
        Szerzők:
        <input type="text" value={authors} onChange={(e) => setAuthors(e.target.value)} required />
      </label>
      <button type="submit">Hozzáadás</button>
      {message && <div className="message">{message}</div>}
    </form>
  );
}

export default BookForm;
