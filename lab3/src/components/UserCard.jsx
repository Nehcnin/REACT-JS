import React from "react";

const UserCard = React.memo(({ user, number }) => {
  return (
    <div className="user-card">
      <h2>{number}. {user.firstName} {user.lastName}</h2>
      <p>{user.email}</p>
      <p>{user.address.city}</p>
    </div>
  );
});

export default UserCard;
