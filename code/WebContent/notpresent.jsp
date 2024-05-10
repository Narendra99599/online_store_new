<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.ProductsList, models.Product,java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Shopping Cart</title>
    <!-- Include some basic styling -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
.qty {
  height: 40px; /* Set height */
  width: 120px; /* Increase width for better spacing */
  display: flex; /* Use flexbox for alignment */
  justify-content: space-between; /* Distribute space evenly between elements */
  align-items: center; /* Center items vertically */
  border-radius: 10px; /* Smooth corners */
  padding: 5px; /* Add padding for inner space */
  transition: all 0.3s; /* Smooth transitions for hover effects */
}

.incdec {
  background: #4CAF50; /* Green background for increment/decrement buttons */
  padding: 5px 10px; /* Padding for spacing */
  border-radius: 5px; /* Slightly rounded corners */
  font-size: 1rem; /* Font size for readability */
  font-weight: 700; /* Bold text */
  color: white; /* White text color for contrast */
  transition: all 0.3s; /* Smooth transitions for hover effects */
}

.incdec:hover {
  background: #66bb6a; /* Lighter green on hover */
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2); /* Light shadow effect */
  cursor: pointer; /* Change cursor to pointer on hover */
}

.th{
	position: relative;
	left : 70px
}

      
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1>This are the products available products in your region</h1>
        <p>Select this products only which is present in your cart</p>
        
        <%
          
          	ProductsList lst = (ProductsList) request.getAttribute("notPresent");
        	HashMap<Integer,Product> productSet = (HashMap<Integer,Product>) session.getAttribute("productSet");
        	HashMap<Integer,Product> set = new HashMap<>();
        	
            if (lst == null || lst.isEmpty()) {
                out.println("<p>Your cart is empty.</p>");
            } else {
        %>
            <table class="table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Image</th>
                        <th class = "th">Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Product product : lst) {
                       		if(productSet.containsKey(product.getPid())){
                       			set.put(product.getPid(), product);
                       		}
                    %>
                        <tr>
                            <td><%= product.getPid() %></td>
                            <td><%= product.getPname() %></td>
                            <td><%= product.getPrice() %></td>
                            <td><img src="<%= product.getImage() %>" alt="<%= product.getPname() %>" width="50" height="50"/></td>
                        </tr>
                    <%
                        }
                    	session.setAttribute("productSet", set);
                    %>
                </tbody>
            </table>
        <%
            }
        %>
        
    </div>

    <!-- Include Bootstrap JS for styling -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
    </script>
</body>
</html>
