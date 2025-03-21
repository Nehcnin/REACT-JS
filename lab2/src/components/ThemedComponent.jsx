import { useTheme } from "../context/ThemeContext";

const ThemedComponent = () => {
  const { theme } = useTheme();

  return (
    <div className={theme ==="light" ? "themed-comp-light" : "themed-comp-dark"}>
      <p>The current theme is {theme}</p>
    </div>
  );
};

export default ThemedComponent;
