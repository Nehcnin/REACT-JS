import ProductItem from "./ProductItem";
import { useState } from "react";

function ProductList ({products,addToCart, remFromCart}) {
    return(
        <>
            <h2>PRODUCTS</h2>
            {products.map(i => (
                <ProductItem 
                    prod={i}
                    addToCart={addToCart} />
            ))}
        </>
    );
}

export default ProductList