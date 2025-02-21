import { useEffect } from "react";
import { useState } from "react";
import GuessForm from "./GuessForm";
import Message from "./Message";

function GuessGame(){

    const [guess,setGuess] = useState("");
    const [randNr, setRandNr] = useState(null);
    const [message, setMessage] = useState("");
    const [attempts, setAttempts] = useState(0);

    useEffect(() => {
        setRandNr(Math.floor(Math.random()*100)+1);
    },[])

    const guessed = () =>{
        const n=parseInt(guess,10);
        if(isNaN(n)){
            setMessage("Incorrent number");
            return;
        }

        setAttempts(attempts+1);

        if(n>randNr){
            setMessage("Try a smaller number");
        } else if (n < randNr) {
            setMessage("Try a larger number");
        } else {
            setMessage(`Correct! You guessed it in ${attempts + 1} attempts.`);
        }
    };

    return(
        <>
            <h2>Guessing game</h2>
            <GuessForm
                guess={guess}
                setGuess={setGuess}
                guessed={guessed} />
            <Message message={message}/>
        </>
    );
};

export default GuessGame