import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function AddBook() {
  let navigate = useNavigate();
  const [book, setBook] = useState({
    title: "",
    author: "",
    publisher: "",
    publishedDate: "",
    isbn: "",
    price: "",
    description: "",
    category: "",
    stockQuantity: "",
    image: null,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook({ ...book, [name]: value });
  };

  const handleFileChange = (e) => {
    setBook({ ...book, image: e.target.files[0] });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Create a new FormData object
    const formData = new FormData();

    // Append all fields to the FormData object
    formData.append("title", book.title);
    formData.append("author", book.author);
    formData.append("publisher", book.publisher);
    formData.append("publishedDate", book.publishedDate);
    formData.append("isbn", book.isbn);
    formData.append("price", book.price);
    formData.append("description", book.description);
    formData.append("category", book.category);
    formData.append("stockQuantity", book.stockQuantity);
    formData.append("image", book.image); // Image file

    try {
      // Send the POST request with FormData
      await axios.post("http://localhost:8080/books/add", formData, {
        headers: {
          "Content-Type": "multipart/form-data", // Ensure the server knows it's a multipart form
        },
      });

      // Redirect after successful submission
      navigate("/");
    } catch (error) {
      console.error(
        "Error adding book:",
        error.response?.data || error.message
      );
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Add Book</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="title" className="form-label">
                Title
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the title of the book"
                name="title"
                value={book.title}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="author" className="form-label">
                Author
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the author's name"
                name="author"
                value={book.author}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="publisher" className="form-label">
                Publisher
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the publisher's name"
                name="publisher"
                value={book.publisher}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="publishedDate" className="form-label">
                Published Date
              </label>
              <input
                type="date"
                className="form-control"
                name="publishedDate"
                value={book.publishedDate}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="isbn" className="form-label">
                ISBN
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the ISBN"
                name="isbn"
                value={book.isbn}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="price" className="form-label">
                Price
              </label>
              <input
                type="number"
                className="form-control"
                placeholder="Enter the price"
                name="price"
                value={book.price}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="description" className="form-label">
                Description
              </label>
              <textarea
                className="form-control"
                placeholder="Enter a brief description of the book"
                name="description"
                value={book.description}
                onChange={handleChange}
                rows="3"
                required
              ></textarea>
            </div>
            <div className="mb-3">
              <label htmlFor="category" className="form-label">
                Category
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the category"
                name="category"
                value={book.category}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="stockQuantity" className="form-label">
                Stock Quantity
              </label>
              <input
                type="number"
                className="form-control"
                placeholder="Enter the stock quantity"
                name="stockQuantity"
                value={book.stockQuantity}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="image" className="form-label">
                Book Cover Image
              </label>
              <input
                type="file"
                className="form-control"
                name="image"
                onChange={handleFileChange}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">
              Add Book
            </button>
            <Link className="btn btn-danger mx-2" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
