import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import ProductList from './components/ProductList'
import Cart from './components/Cart'


function App() {
  const [cart,setCart] = useState([]);

  const [products,setProducts] = useState([
    {name:"Galaxy S22", price: 1000},
    {name:"Vivobook X53", price: 400},
    {name:"TUF F15", price: 700},
    {name:"Logitech G500", price: 200}
  ]);

  const addToCart = (prod) =>{
    if(!cart.some(item => item.name === prod.name)){
      setCart([...cart,prod]);
    }
  };

  const remFromCart = (name) =>{
    setCart(cart.filter((i) => i.name !== name));
  }

  return (
    <>

      <ProductList 
          products={products} 
          addToCart={addToCart} 
          remFromCart={remFromCart} />
      <Cart 
        cart={cart}
        remFromCart={remFromCart} />
    </>
  )
}

export default App
