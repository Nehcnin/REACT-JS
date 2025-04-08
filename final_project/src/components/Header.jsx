import React from 'react';

function Header() {
  const username = localStorage.getItem('username');

  const handleLogout = () => {
    localStorage.removeItem('username');
    window.location.href = '/';
  };

  return (
    <div className="header">
      <span className="user">{username}</span>
      <button className="logout" onClick={handleLogout}>Kijelentkezés</button>
    </div>
  );
}

export default Header;
