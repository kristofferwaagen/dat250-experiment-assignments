import React from "react";
import PollCreationComponent from "./Components/PollCreationComponent";
import CounterComponent from "./Components/CounterComponent";
import "./App.css";

function App() {
  return (
    <div id="app">
      <h1>Welcome to PollApp</h1>
      <PollCreationComponent />
      <CounterComponent />
    </div>
  );
}

export default App;
