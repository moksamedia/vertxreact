index {
    path    = 0
    method  = 1
    handler = 2
}

routes = [
        ["/products/:productID",    "GET", "handleGetProduct"],
        ["/products/:productID",    "PUT", "handleAddProduct"],
        ["/products",               "GET", "handleListProduct"]
]



