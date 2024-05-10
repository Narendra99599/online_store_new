<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Customer Registration Form</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<style>
    * {
        font-family: 'Roboto', sans-serif;
    }

    .container {
        display: flex;
        flex-direction: column;
    }

    .body {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        gap: 20px;
        justify-content: space-around;
    }

    .card {
        width: 20%;
        padding: 20px;
        border: 3px solid black;
        border-radius: 20%;
        text-align: center;
        font-weight: bold;
    }

    .name {
        height: 50px;
        overflow: hidden;
    }

    .main {
        display: flex;
        flex-direction: column;
        gap: 20px;
        width : 80%;
        margin : 0 auto;
    }

    .options {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .header {
        display: flex;
        justify-content: space-between;
        height: 15vh;
        width : 80%;
        margin : 0 auto;
        align-items: center;
    }

    #img {
        font-size: 3rem;
        position: relative;
        top: 0px;
        left: 12px;
    }

    #cart {
        border: 2px solid black;
        border-radius: 70%;
        height: 70%;
        width: 7%;
    }

    .foot {
        display: flex;
        width: 100%;
        justify-content: center;
        gap: 20px;
    }

    #pages {
        width: 30px;
        font-weight: bold;
        padding: 10px;
        border-radius: 3px;
        background-color: white-smoke;
    }

    .items {
        width: 90%;
        margin: 1em auto;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }

    .item {
        width: 22%;
        display: flex;
        flex-direction: column;
        gap: 1em;
        margin: 1em auto;
        padding: 1em;
        align-items: center;
        justify-content: space-between;
        border-radius: 50px;
        box-shadow: 0 0 10px;
        transition: all 0.5s;
    }

    .item:hover {
        transform: scale(1.01);
    }

    .item img {
        width: 90%;
        height: inherit;
    }

    .btn {
        padding: 0.7em 2em;
        font-size: 1.1em;
        border-radius: 10px;
        border: none;
        background-color: black;
        color: white;
        transition: all 0.5s;
    }

    .btn:hover {
        color: rgb(231, 209, 209);
        background-color: rgb(114, 83, 83);
        transform: scale(1.1);
    }
    
</style>

<body>
    <div class="">
        <div class="header mb-2">
            <div>
                <h3 style="font-weight:bolder;">Online Store</h3>
            </div>
            <div id="cart"><i id="img" class="bi bi-cart4"></i>
            </div>
        </div>

        <div class="main">
            <div class="options">
                <div class="option1">
                    <select id="option1" name="category" class="form-select" aria-label="multiple select example">
                        <option selected>category</option>
                        <option value="men's clothing">men's clothing</option>
                        <option value="jewelery">jewelery</option>
                        <option value="electronics">electronics</option>
                        <option value="women's clothing">women's clothing</option>
                    </select>
                </div>

                <div class="option2">
                    <select id="option2" name="sort" class="form-select" aria-label="multiple select example">
                        <option selected>sort</option>
                        <option value="price">by price</option>
                        <option value="name">by name</option>
                    </select>
                </div>

            </div>
            <div class="items body">

            </div>
        </div>

        <div class="footer">
            <div class="foot">
                <div><button id="prev" class="btn btn-primary">Prev</button></div>
                <div>
                    <p id="pages">1</p>
                </div>
                <div><button id="next" class="btn btn-primary">Next</button></div>
            </div>
        </div>
    </div>
    <!-- Include Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
<script>
    $(document).ready(function () {
        // Function to create a product card
        function createProductCard(product) {
            var card = $("<div>").addClass("item");
            card.append($("<img>").attr({ src: product.image,height : "200px",width: "100%" }));
            card.append($("<p>").addClass("name").text(product.pname));
            card.append($("<p>").text(product.price));
            var btn = $("<button>").addClass("btn btn-primary").text("Add to cart");
            card.append(btn);
            btn.click(function () { // Correct spelling and syntax
                var url = "cart"; // The endpoint URL

                fetch(url, {
                    method: "POST", // Use POST method for creating/updating data
                    headers: {
                        "Content-Type": "application/json" // Set content type to JSON
                    },
                    body: JSON.stringify(product) // Convert the object to JSON string
                })
                    .then((response) => {
                        if (!response.ok) { // Check if the response is not successful
                            throw new Error(`Network response was not ok: ${response.statusText}`);
                        }
                        console.log("Data sent successfully");
                    })
                    .catch((error) => {
                        console.error("Fetch error:", error);
                        // Consider adding user-friendly error handling here
                    });
            });

            return card;
        }

        function fetchAndRenderProducts(url) {
            fetch(url)
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`Network response was not ok: ${response.statusText}`);
                    }
                    return response.json();
                })
                .then((data) => {
                    $(".body").empty(); // Clear existing content
                    console.log(data);
                    data.map((product) => {
                        $(".body").append(createProductCard(product));
                    });
                })
                .catch((error) => {
                    console.error("Fetch error:", error);
                    // Consider displaying a user-friendly error message
                });
        }

        // Initial fetch
        fetchAndRenderProducts("product?pages=" + 1);

        // Event handler for category and sort changes
        $("#option1").change(function () {
            var category = $("#option1").val();
            var sort = $("#option2").val();
            var pages = 1;
            if (category === "category") {
                category = null;
            }
            if (sort === "sort") {
                sort = null;
            }
            var url = "product?category=" + category + "&sort=" + sort + "&pages=" + pages;
            fetchAndRenderProducts(url);
        });
        $("#option2").change(function () {
            var category = $("#option1").val();
            var sort = $("#option2").val();
            var pages = 1;
            if (category === "category") {
                category = null;
            }
            if (sort === "sort") {
                sort = null;
            }
            var url = "product?category=" + category + "&sort=" + sort + "&pages=" + pages;
            fetchAndRenderProducts(url);
        });
        $("#prev").click(function () {
            var category = $("#option1").val();
            var sort = $("#option2").val();
            var pages = parseInt($("#pages").text()) - 1;
            console.log(pages);
            if (pages <= 0) {
                pages = 1;
            }
            if (category === "category") {
                category = null;
            }
            if (sort === "sort") {
                sort = null;
            }
            var url = "product?category=" + category + "&sort=" + sort + "&pages=" + pages;
            fetchAndRenderProducts(url);
            $("#pages").text(pages);
        });
        $("#next").click(function () {
            var category = $("#option1").val();
            var sort = $("#option2").val();
            var pages = parseInt($("#pages").text()) + 1;

            console.log(pages);
            if (category === "category") {
                category = null;
            }
            if (sort === "sort") {
                sort = null;
            }
            var url = "product?category=" + category + "&sort=" + sort + "&pages=" + pages;
            fetchAndRenderProducts(url);
            $("#pages").text(pages);
        });
        $("#cart").click(function () {

            window.location.href = "cart.jsp";

        });

    });
</script>

</html>