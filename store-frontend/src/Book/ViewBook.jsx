import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "axios";

export default function ViewBook() {
  const { bookId } = useParams(); // Get the book ID from the URL
  const [book, setBook] = useState(null);

  // Fetch book details on component mount
  useEffect(() => {
    const fetchBookDetails = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/books/${bookId}`
        );
        setBook(response.data);
      } catch (error) {
        console.error("Error fetching book details:", error);
      }
    };

    fetchBookDetails();
  }, [bookId]);

  if (!book) {
    return <p>Loading...</p>; // Show loading message until the book data is fetched
  }

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Book Details</h2>
          <div className="card">
            <div className="card-header">
              <h5>Details of Book ID: {book.id}</h5>
            </div>
            <ul className="list-group list-group-flush">
              <li className="list-group-item">
                <b>Title:</b> {book.title}
              </li>
              <li className="list-group-item">
                <b>Author:</b> {book.author}
              </li>
              <li className="list-group-item">
                <b>Publisher:</b> {book.publisher}
              </li>
              <li className="list-group-item">
                <b>Published Date:</b> {book.publishedDate}
              </li>
              <li className="list-group-item">
                <b>ISBN:</b> {book.isbn}
              </li>
              <li className="list-group-item">
                <b>Price:</b> ${book.price}
              </li>
              <li className="list-group-item">
                <b>Description:</b> {book.description}
              </li>
              <li className="list-group-item">
                <b>Category:</b> {book.category}
              </li>
              <li className="list-group-item">
                <b>Stock Quantity:</b> {book.stockQuantity}
              </li>
              {book.image && (
                <li className="list-group-item">
                  <b>Book Cover:</b>
                  <img
                    src={`data:image/jpeg;base64,${book.image}`}
                    alt="Book Cover"
                    width="100"
                  />
                </li>
              )}
            </ul>
          </div>
          <Link className="btn btn-primary my-2" to="/">
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}
