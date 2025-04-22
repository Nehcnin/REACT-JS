import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./../styles/Password.css";

function PasswordChange() {
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [message, setMessage] = useState("");
  const username = localStorage.getItem("username");
  const navigate = useNavigate();

  const handlePasswordChange = async (e) => {
    e.preventDefault();
    const res = await fetch("http://localhost:8080/bibliospring/passwordChange", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ username, oldPassword, newPassword }),
    });
    const result = await res.json();
    setMessage(result.status === "success" ? "Password changed!" : "Error changing password.");
    setTimeout(() => navigate("/Dashboard"), 1500);
  };

  return (
    <form className="formContainer" onSubmit={handlePasswordChange}>
      <h2>Change Password</h2>
      <input
        className="inp"
        type="password"
        placeholder="Old Password"
        value={oldPassword}
        onChange={(e) => setOldPassword(e.target.value)}
      />
      <input
        className="inp"
        type="password"
        placeholder="New Password"
        value={newPassword}
        onChange={(e) => setNewPassword(e.target.value)}
      />
      <input type="submit" value="Change Password" className="submm" />
      {message && <div className="mess">{message}</div>}
    </form>
  );
}

export default PasswordChange;
