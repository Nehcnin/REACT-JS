import { useState } from "react"
function Cart({cart, remFromCart}){

    return(
    <div>
        <h2>CART</h2>
        {cart.length === 0 ? (
            <p>Your cart is empty</p>
        ) : (
            cart.map(i => (
                <div>
                <span>{i.name} - {i.price}e</span>
                <button onClick={()=>remFromCart(i.name)}>REMOVE</button>
                </div>
            ))
        )
        }
    </div>
    );
}

export default Cart