import React from "react";
import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">
            Book Store
          </a>
          <Link className="btn btn-light ms-auto" to="/addbook">
            Add Book
          </Link>
        </div>
      </nav>
    </div>
  );
}
