import React, { useState } from 'react';

function BookDeleteForm({ onBookDeleted }) {
  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');

  const handleDeleteBook = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/bibliospring/book', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ add: 'false', title }),
      });

      const result = await response.json();

      if (result.status === 'success') {
        setMessage('Sikeresen törölve!');
        setTitle('');
        onBookDeleted();
      } else {
        setMessage('Hiba történt a törlés során!');
      }
    } catch (error) {
      setMessage('Hálózati hiba!');
    }

    setTimeout(() => setMessage(''), 2000);
  };

  return (
    <form className="delete-book-form" onSubmit={handleDeleteBook}>
      <p className="form-title">Könyv törlése</p>
      <label>
        Cím:
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </label>
      <button type="submit">Törlés</button>
      {message && <div className="message">{message}</div>}
    </form>
  );
}

export default BookDeleteForm;
