import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./layout/NavBar";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddBook from "./Book/AddBook";
import EditBook from "./Book/EditBook";
import ViewBook from "./Book/ViewBook";

function App() {
  return (
    <>
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/addbook" element={<AddBook />} />
          <Route exact path="/editbook/:bookId" element={<EditBook />} />
          <Route exact path="/viewbook/:bookId" element={<ViewBook />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
