import { useState, useEffect, useMemo, useCallback } from "react";
import SearchBar from "./components/SearchBar";
import UserList from "./components/UserList";
import "./styles.css"; // CSS importálása

const App = () => {
  const [users, setUsers] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("https://dummyjson.com/users?limit=150")
      .then((res) => res.json())
      .then((data) => {
        setUsers(data.users);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching users:", error);
        setLoading(false);
      });
  }, []);

  const handleSearch = useCallback((term) => {
    setSearchTerm(term);
  }, []);

  const clearSearch = useCallback(() => {
    setSearchTerm("");
  }, []);

  const filteredUsers = useMemo(() => {
    return users.filter((user) =>
      `${user.firstName} ${user.lastName}`.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }, [users, searchTerm]);

  return (
    <div className="container">
      <h1>User Search</h1>
      <SearchBar onSearch={handleSearch} clearSearch={clearSearch} searchTerm={searchTerm} />
      {loading ? <p>Loading...</p> : <UserList users={filteredUsers} />}
    </div>
  );
};

export default App;
