import UserCard from "./UserCard";

const UserList = ({ users }) => {
  return (
    <div className="user-list">
      {users.length === 0 ? (
        <p>No results found.</p>
      ) : (
        users.map((user, index) => <UserCard key={user.id} user={user} number={index + 1} />)
      )}
    </div>
  );
};

export default UserList;
