import React, { useState } from 'react';

function PasswordChangeForm() {
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const username = localStorage.getItem('username');

    try {
      const response = await fetch('http://localhost:8080/bibliospring/passwordChange', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ username, oldPassword, newPassword }),
      });

      const result = await response.json();
      if (result.status === 'success') {
        setMessage('Sikeres jelszóváltoztatás!');
      } else {
        setMessage('Hiba a jelszóváltoztatás során!');
      }
    } catch (error) {
      setMessage('Hálózati hiba!');
    }

    setTimeout(() => setMessage(''), 2000);
  };

  return (
    <form className="password-form" onSubmit={handleSubmit}>
      <p className="form-title">Jelszó megváltoztatása</p>
      <label>
        Régi jelszó:
        <input type="password" value={oldPassword} onChange={(e) => setOldPassword(e.target.value)} required />
      </label>
      <label>
        Új jelszó:
        <input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required />
      </label>
      <button type="submit">Jelszó módosítása</button>
      {message && <div className="message">{message}</div>}
    </form>
  );
}

export default PasswordChangeForm;
