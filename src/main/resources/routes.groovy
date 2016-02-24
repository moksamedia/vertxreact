index {
    method  = 0
    path    = 1
    handler = 2
}

routes = [
        ["GET", "/products/:productID",     "handleGetProduct"],
        ["PUT", "/products/:productID",     "handleAddProduct"],
        ["GET", "/products",                "handleListProduct"]
]



