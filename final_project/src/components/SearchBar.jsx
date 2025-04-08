const SearchBar = ({ search, setSearch }) => (
    <input
      type="text"
      placeholder="Search books..."
      value={search}
      onChange={(e) => setSearch(e.target.value)}
    />
  );
  
  export default SearchBar;
  