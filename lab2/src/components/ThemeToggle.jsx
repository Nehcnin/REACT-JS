import { useTheme } from "../context/ThemeContext";

const ThemeToggle = () => {
  const { toggleTheme } = useTheme();

  return <button onClick={toggleTheme} className="toggle">Toggle Theme</button>;
};

export default ThemeToggle;
