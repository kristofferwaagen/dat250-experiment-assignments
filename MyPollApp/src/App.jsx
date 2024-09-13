import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            <div>
              <h2>Welcome to the Poll App</h2>
              <p>Select an option from the menu.</p>
            </div>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
