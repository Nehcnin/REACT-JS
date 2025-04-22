// src/components/Home.jsx
import { useNavigate } from "react-router-dom";
import "./../styles/Home.css";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="box">
      <h1>Welcome</h1>
      <p>Choose an option:</p>
      <button className="subm" onClick={() => navigate("/login")}>Login</button>
      <button className="subm" onClick={() => navigate("/register")}>Register</button>
    </div>
  );
}

export default Home;
