const SearchBar = ({ onSearch, clearSearch, searchTerm }) => {
  return (
    <div className="search-bar">
      <input
        type="text"
        placeholder="Search users..."
        value={searchTerm}
        onChange={(e) => onSearch(e.target.value)}
      />
      <button onClick={clearSearch}>Clear</button>
    </div>
  );
};

export default SearchBar;
