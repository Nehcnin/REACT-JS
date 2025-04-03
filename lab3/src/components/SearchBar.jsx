const SearchBar = ({ onSearch }) => {
  return (
    <div className="search-bar">
      <input type="text" placeholder="Search users..." onChange={(e) => onSearch(e.target.value)} />
      <button onClick={() => onSearch("")}>Clear</button>
    </div>
  );
};

export default SearchBar;
