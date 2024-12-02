import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function EditBook() {
  let navigate = useNavigate();

  const { bookId } = useParams();

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

  const [imagePreview, setImagePreview] = useState(null); // State for image preview

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook({ ...book, [name]: value });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setBook({ ...book, image: file });

    // Create a URL for image preview
    const reader = new FileReader();
    reader.onloadend = () => {
      setImagePreview(reader.result);
    };
    if (file) reader.readAsDataURL(file);
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

    // Only append the image if one was selected
    if (book.image) {
      formData.append("image", book.image);
    }

    try {
      // Send the PUT request with FormData
      await axios.put(
        `http://localhost:8080/books/updateBook/${bookId}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data", // Ensure the server knows it's a multipart form
          },
        }
      );

      // Redirect after successful submission
      navigate("/");
    } catch (error) {
      console.error(
        "Error updating book:",
        error.response?.data || error.message
      );
    }
  };

  useEffect(() => {
    loadBook();
  }, []);

  const loadBook = async () => {
    try {
      const result = await axios.get(`http://localhost:8080/books/${bookId}`);
      setBook(result.data);
      if (result.data.image) {
        // Set the image preview to the current image if available
        setImagePreview(`data:image/jpeg;base64,${result.data.image}`);
      }
    } catch (error) {
      console.error("Error fetching book details:", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit Book</h2>
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
              />
              {imagePreview && (
                <div className="mt-2">
                  <img
                    src={imagePreview}
                    alt="Current Book Cover"
                    width="100"
                  />
                  <p>Current Image</p>
                </div>
              )}
            </div>
            <button type="submit" className="btn btn-primary">
              Update Book
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
