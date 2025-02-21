function ProductItem({prod,addToCart}){
    return (
        <div>
            <span>{prod.name} - {prod.price}e</span>
            <button onClick={()=>addToCart(prod)}>ADD</button>
        </div>
    );
}

export default ProductItem