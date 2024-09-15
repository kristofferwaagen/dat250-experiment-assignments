import React from "react";
import PollCreationComponent from "./Components/PollCreationComponent";
import ClearPollsButton from "./Components/ClearPollsButton";
import CreateUserComponent from "./Components/CreateUserComponent";
import ClearUsersButton from "./Components/ClearUserButton";

import "./App.css";

function App() {
  return (
    <div id="app">
      <CreateUserComponent />
      <ClearUsersButton />
      <h1>Welcome to PollApp</h1>
      <PollCreationComponent />
      <ClearPollsButton />
    </div>
  );
}

export default App;
