function GuessForm({guess,setGuess, guessed}){
    return(
        <div>
            <input 
                type="number"
                value={guess}
                onChange={(e) => setGuess(e.target.value)}
                placeholder="Enter a number" />
            
            <button onClick={guessed}>Guess</button>
        </div>
    );
};

export default GuessForm;