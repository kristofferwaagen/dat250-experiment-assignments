import React, { useState } from "react";

function CreateUserComponent() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleCreateUser = async () => {
    const user = {
      username: username,
      email: email,
      password: password, // Added password field
    };

    try {
      const response = await fetch("users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });

      if (response.ok) {
        const result = await response.text();
        setSuccessMessage(result);
        setErrorMessage("");
      } else {
        const error = await response.text();
        setErrorMessage(`Error: ${error}`);
        setSuccessMessage("");
      }
    } catch (error) {
      setErrorMessage("Network or server error");
      setSuccessMessage("");
    }
  };

  return (
    <div>
      <h2>Create User</h2>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Username"
      />
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Password"
      />
      <button onClick={handleCreateUser}>Create User</button>

      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
      {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
    </div>
  );
}

export default CreateUserComponent;
