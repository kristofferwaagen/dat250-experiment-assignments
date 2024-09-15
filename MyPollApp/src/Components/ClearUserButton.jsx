import React, { useState } from "react";

function ClearUsersButton() {
  const [statusMessage, setStatusMessage] = useState("");

  const clearUsers = async () => {
    try {
      const response = await fetch("/users/clear", {
        method: "DELETE",
      });
      if (response.ok) {
        setStatusMessage("All users cleared successfully.");
      } else {
        setStatusMessage("Failed to clear users.");
      }
    } catch (error) {
      setStatusMessage("Error occurred while clearing users.");
    }
  };

  return (
    <div>
      <button onClick={clearUsers}>Clear All Users</button>
      {statusMessage && <p>{statusMessage}</p>}
    </div>
  );
}

export default ClearUsersButton;
