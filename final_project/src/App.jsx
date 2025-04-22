import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import Home from "./components/Home"; 
import AddBook from "./components/AddBook";
import EditBook from "./components/EditBook";
import PasswordChange from "./components/PasswordChange";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} /> 
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/add" element={<AddBook />} />
        <Route path="/edit/:title" element={<EditBook />} />
        <Route path="/password" element={<PasswordChange />} />
      </Routes>
    </Router>
  );
}

export default App;
