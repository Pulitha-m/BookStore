import React, { useEffect, useState } from "react";
import axios from "axios";

export default function Home() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = async () => {
    const result = await axios.get("http://localhost:8080/books");
    console.log(result.data);
    setBooks(result.data);
  };

  return (
    <div className="container">
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Title</th>
              <th scope="col">Author</th>
              <th scope="col">Publisher</th>
              <th scope="col">Published Date</th>
              <th scope="col">ISBN</th>
              <th scope="col">Price</th>
              <th scope="col">Description</th>
              <th scope="col">Category</th>
              <th scope="col">Stock Quantity</th>
              <th scope="col">Image</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>
            {books.map((book, index) => (
              <tr key={index}>
                <th scope="row">{book.bookId}</th>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.publisher}</td>
                <td>{book.publishedDate}</td>
                <td>{book.isbn}</td>
                <td>{book.price}</td>
                <td>{book.description}</td>
                <td>{book.category}</td>
                <td>{book.stockQuantity}</td>
                <td>
                  <img
                    src={`data:image/jpeg;base64,${book.image}`}
                    alt={book.title}
                    style={{ width: "50px", height: "50px" }}
                  />
                </td>
                <td>
                  <button className="btn btn-primary btn-sm me-2">Edit</button>
                  <button className="btn btn-danger btn-sm">Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
