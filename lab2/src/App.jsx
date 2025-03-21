import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserList from "./components/UserList";
import UserDetail from "./components/UserDetail";
import ThemeToggle from "./components/ThemeToggle";
import ThemedComponent from "./components/ThemedComponent";
import "./styles/theme.css";


function App() {
  return (
    <Router>
      <ThemeToggle />
      <ThemedComponent />
      <Routes>
        <Route path="/" element={<UserList />} />
        <Route path="/user/:id" element={<UserDetail />} />
      </Routes>
    </Router>
  );
}

export default App;
