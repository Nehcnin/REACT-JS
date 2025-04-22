import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./../styles/Login.css";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response = await fetch("http://localhost:8080/bibliospring/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ login: "false", username, password }),
    });

    if (response.ok) {
      localStorage.setItem("username", username);
      navigate("/dashboard");
    } else {
      setMessage("Error at registering!");
      setTimeout(() => setMessage(""), 2000);
    }
  };

  return (
    <div className="box">
      <form onSubmit={handleSubmit} className="formm">
        <p>User name:</p>
        <input type="text" className="inp" value={username} onChange={(e) => setUsername(e.target.value)} />
        <p>Password:</p>
        <input type="password" className="inp" value={password} onChange={(e) => setPassword(e.target.value)} />
        <br />
        <input type="submit" value="Register" className="subm" />
        {message && <div className="mess">{message}</div>}
      </form>
    </div>
  );
}

export default Register;
