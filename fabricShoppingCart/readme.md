# Shopping Cart Application
## Shopping Cart API Documentation

This document provides an overview of the Shopping Cart API, detailing the various endpoints available for creating and managing a shopping cart. 
The API allows users to create a cart, add items, update item quantities, remove items, view item prices, and view the total price of items in their cart.

## Endpoints

### 1. Create Cart

- **Description**: Create a new shopping cart.
- **Method**: POST
- **Endpoint**: `api/v1/cart?userId=<userId>`
- **Request Body**: None
  - **Response**:
      - **Status Code**: 200
      - **Body**:
        ```json
        {
          "status_code": 200,
          "status": "success",
          "data": {
            "id": 1,
            "userId": 1,
            "items": null
          }
        }
        ```

| Method | Endpoint              | Description           | Request Body | Response           |
|--------|-----------------------|-----------------------|--------------|--------------------|
| POST   | /cart?userId=<userId> | Create a new cart     | None         | `{ "cartId": "string" }` |

### 2. Add Item to Cart

- **Description**: Add an item to the shopping cart.
- **Method**: POST
- **Endpoint**: `api/v1/cart/{cartId}/items`
- **Request Body**:
  ```json
  {
    "name" : "Flour",
    "unitPrice" : 20.9,
    "quantity" : 1
  }
  ```
- **Response**:
    - **Status Code**: 200 OK
    - **Body**:
      ```json
      {
        "message": "Item added to cart",
        "status_code": 200,
        "status": "success",
        "data": {
             "id": 1,
             "userId": 1,
             "items": [
                    {
                       "id": 1,
                       "name": "Oil",
                       "unitPrice": 10.9,
                       "quantity": 4
                    },
                    {
                        "id": 2,
                        "name": "Flour",
                        "unitPrice": 20.9,
                        "quantity": 1
                    }
             ]
        }
      }
      ```

| Method | Endpoint                   | Description           | Request Body                                      | Response                        |
|--------|----------------------------|-----------------------|---------------------------------------------------|---------------------------------|
| POST   | /cart/{cartId}/items       | Add item to cart      | `{ "itemId": "string", "quantity": "integer" }`   | `{ "message": "Item added to cart" }` |

### 3. Update Item Quantity

- **Description**: Update the quantity of an item in the cart.
- **Method**: PUT
- **Endpoint**: `api/v1/cart/{cartId}/items`
- **Request Body**:
  ```json
  {
    "id" : 2,
    "name" : "Oil",
    "unitPrice" : 20.6,
    "quantity" : 5
  }
  ```
- **Response**:
    - **Status Code**: 200 OK
    - **Body**:
      ```json
      {
        "status_code": 200,
        "status": "success",
        "data": {
            "id": 2,
            "name": "Oil",
            "unitPrice": 20.6,
            "quantity": 5
        }
      }
      ```

| Method | Endpoint                   | Description                  | Request Body                                      | Response                           |
|--------|----------------------------|------------------------------|---------------------------------------------------|------------------------------------|
| PUT    | /cart/{cartId}/items       | Update item quantity         | `{ "itemId": "string", "quantity": "integer" }`   | `{ "message": "Item quantity updated" }` |

### 4. Remove Item

- **Description**: Remove an item from the shopping cart.
- **Method**: DELETE
- **Endpoint**: `api/v1/cart/{cartId}/items/{itemId}`
- **Request Body**: None
- **Response**:
    - **Status Code**: 200 OK
    - **Body**:
      ```json
      {
        "status_code": 200,
        "status": "success",
        "data": "Item removed"
      }
      ```

| Method | Endpoint                          | Description            | Request Body | Response                          |
|--------|-----------------------------------|------------------------|--------------|-----------------------------------|
| DELETE | /cart/{cartId}/items/{itemId}     | Remove item from cart  | None         | `{ "message": "Item removed from cart" }` |

### 5. View Item Price

- **Description**: View the price of items in the cart.
- **Method**: GET
- **Endpoint**: `api/v1/cart/{cartId}`
- **Request Body**: None
- **Response**:
    - **Status Code**: 200 OK
    - **Body**:
      ```json
      {
        "message": "Item added to cart",
        "status_code": 200,
        "status": "success",
        "data": {
             "id": 1,
             "userId": 1,
             "items": [
                    {
                       "id": 1,
                       "name": "Oil",
                       "unitPrice": 10.9,
                       "quantity": 4
                    },
                    {
                        "id": 2,
                        "name": "Flour",
                        "unitPrice": 20.9,
                        "quantity": 1
                    }
             ]
        }
      }
      ```

| Method | Endpoint         | Description                | Request Body | Response                                                       |
|--------|------------------|----------------------------|--------------|----------------------------------------------------------------|
| GET    | /cart/{cartId}   | View item prices in cart   | None         | `{ "items": [{ "itemId": "string", "price": "number" }] }` |

### 6. View Cart Total

- **Description**: View the total price of items in the cart.
- **Method**: GET
- **Endpoint**: `api/v1/cart/{cartId}/total`
- **Request Body**: None
- **Response**:
    - **Status Code**: 200 OK
    - **Body**:
      ```json
      {
        "status_code": 200,
        "status": "success",
        "data": 103.0
      }
      ```

| Method | Endpoint                   | Description              | Request Body | Response                        |
|--------|----------------------------|--------------------------|--------------|---------------------------------|
| GET    | /cart/{cartId}/total       | View total cart price    | None         | `{ "totalPrice": "number" }` |

## Example Usage

### Create Cart
```sh
curl --location --request POST http://api.example.com/v1/cart?userId=1
```

### Add Item to Cart
```sh
curl --location 'localhost:8080/api/v1/cart/1/items' \
--header 'Content-Type: application/json' \
--data '{
    "name" : "Flour",
    "unitPrice" : 20.9,
    "quantity" : 1
}'
```

### Update Item Quantity
```sh
curl --location --request PUT 'localhost:8080/api/v1/cart/1/items' \
--header 'Content-Type: application/json' \
--data '{
    "id" : 2,
    "name" : "Oil",
    "unitPrice" : 20.6,
    "quantity" : 5
}'
```

### Remove Item
```sh
curl --location --request DELETE 'localhost:8080/api/v1/cart/1/items/1'
```

### View Item Price
```sh
curl --location --request GET http://api.example.com/v1/cart/{cartId}
```

### View Cart Total
```sh
curl --location --request GET http://api.example.com/v1/cart/{cartId}/total
```

This concludes the documentation for the Shopping Cart API. If you have any questions or need further assistance, please contact our support team.

## SetUp
Local Setup
1. Install java
2. Install IDE with spring boot
3. Run the application
4. The application is using in-memory database whose console can be accessed at http://localhost:8080/h2-console/
